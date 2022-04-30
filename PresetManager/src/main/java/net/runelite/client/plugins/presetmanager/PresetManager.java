/*
 * Copyright (c) 2019-2020, fresh_mozz
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package net.runelite.client.plugins.presetmanager;

import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.inject.Provides;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.bank.BankSearch;
import net.runelite.client.plugins.freshutils.FreshUtils;
import net.runelite.client.util.HotkeyListener;
import org.apache.commons.lang3.StringUtils;
import org.pf4j.Extension;

import static net.runelite.client.plugins.presetmanager.PresetRenderEngine.*;


@Extension
@PluginDescriptor(
	name = "Preset Manager",
	description = "Manage presets and quickly pull out items from bank to wear or preset the inventory",
	tags = {"Cool", "Awesome", "Vera"}
)
@Slf4j
@SuppressWarnings("unused")
@PluginDependency(FreshUtils.class)
public class PresetManager extends Plugin
{
	private static final Splitter NEWLINE_SPLITTER = Splitter
		.on("\n")
		.omitEmptyStrings()
		.trimResults();
	private static final int SNAPSHOT_LIMIT = 20;
	private static final String VIEW_TAB = "View preset ";
	private static final int PRESET_BUTTON_SIZE = 25;
	private static final int PRESET_BUTTON_X = 408;
	private static final int PRESET_BUTTON_Y = 5;
	private static final int SEARCHBOX_LOADED = 750;

	private static final int WITHDRAW_ONE = 786460;
	private static final int WITHDRAW_ALL = 786469;
	private static final int WITHDRAW_ITEM = 786455;
	private static final int WITHDRAW_NOTED = 786457;


	private boolean presetTabActive = false;
	private int presetToView = 1;

	@Inject
	private BankSearch bankSearch;

	@Inject
	private Client client;

	@Inject
	private KeyManager keyManager;

	@Inject
	private PresetManagerConfig config;

	@Inject
	private EventBus eventBus;

	@Inject
	private FreshUtils utils;

	private ExecutorService executor;
	private Robot robot;
	private Gson gson;
	private Widget parent;
	private Widget presetIconWidget;
	private Widget presetBackgroundWidget;
	private ArrayList<Widget> addedWidgets = new ArrayList<>();

	@Provides
	PresetManagerConfig getConfig(ConfigManager manager)
	{
		return manager.getConfig(PresetManagerConfig.class);
	}

	@Override
	protected void startUp() throws AWTException
	{
		executor = Executors.newSingleThreadExecutor();
		robot = new Robot();
		gson = new Gson();

		if (client.getGameState() == GameState.LOGGED_IN)
		{
			keyManager.registerKeyListener(snapshotKey);
		}
	}

	@Override
	protected void shutDown()
	{
		executor.shutdown();
		keyManager.unregisterKeyListener(snapshotKey);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN)
		{
			keyManager.unregisterKeyListener(snapshotKey);
			return;
		}
		keyManager.registerKeyListener(snapshotKey);
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		if (event.getGroupId() == WidgetID.BANK_GROUP_ID)
		{
			init();
		}
	}

	@Subscribe
	public void onScriptPreFired(ScriptPreFired event)
	{
		int scriptId = event.getScriptId();

		if (scriptId == ScriptID.BANKMAIN_SEARCH_TOGGLE)
		{
			handleSearch();
		}
	}

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent event)
	{
		String eventName = event.getEventName();

		int[] intStack = client.getIntStack();
		int intStackSize = client.getIntStackSize();

		if ("getSearchingTagTab".equals(eventName))
		{
			intStack[intStackSize - 1] = presetTabActive ? 1 : 0;
			if (presetTabActive)
			{
				// As we're on the quest helper tab, we don't need to check again for tab tags
				// Change the name of the event so as to not proc another check
				event.setEventName("getSearchingQuestHelperTab");
			}
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		handleClick(event);
	}

	@Subscribe
	public void onScriptPostFired(ScriptPostFired event) {
		if (event.getScriptId() == ScriptID.BANKMAIN_SEARCHING)
		{
			// The return value of bankmain_searching is on the stack. If we have a tag tab active
			// make it return true to put the bank in a searching state.
			if (presetTabActive)
			{
				client.getIntStack()[client.getIntStackSize() - 1] = 1; // true
			}
			return;
		} else if (event.getScriptId() != ScriptID.BANKMAIN_BUILD)
		{
			return;
		} else if (!presetTabActive)
		{
			return;
		} else if (client.getWidget(WidgetInfo.BANK_ITEM_CONTAINER) == null)
		{
			return;
		}

		PresetConverter.getEquipmentFromId(presetToView, PresetConverter.getPresetFromConfig(config))
				.ifPresent(equipmentPreset -> handleRender(client.getWidget(WidgetInfo.BANK_ITEM_CONTAINER),
						addedWidgets,
						this,
						equipmentPreset,
						client));
	}

	public void init() {
		parent = client.getWidget(WidgetInfo.BANK_CONTAINER);

		presetBackgroundWidget = WidgetUtils.createGraphic(parent,
				"preset-helper",
				SpriteID.UNKNOWN_BUTTON_SQUARE_SMALL,
				PRESET_BUTTON_SIZE,
				PRESET_BUTTON_SIZE,
				PRESET_BUTTON_X,
				PRESET_BUTTON_Y);

		for (int preset = 1; preset <= 5; preset++) {
			presetBackgroundWidget.setAction(preset, VIEW_TAB + PresetConverter.GET_NAME_FROM_ID.apply(gson.fromJson(config.configBlob(), PresetList.class), preset));
		}

		presetBackgroundWidget.setOnOpListener((JavaScriptCallback) this::handleTagTab);

		presetIconWidget = WidgetUtils.createGraphic(parent,
				"",
				SpriteID.KOUREND_FAVOUR_ARCEUUS_ICON,
				PRESET_BUTTON_SIZE - 6,
				PRESET_BUTTON_SIZE - 6,
				PRESET_BUTTON_X + 3, PRESET_BUTTON_Y + 3);

		if (presetTabActive)
		{
			presetTabActive = false;
			activateTab();
		}
	}

	private void handleTagTab(ScriptEvent event)
	{
		presetToView = event.getOp() - 1; // op starts at 2
		client.setVarbit(Varbits.CURRENT_BANK_TAB, 0);

		if (presetTabActive)
		{
			closeTab();
			bankSearch.reset(true);
		}
		else
		{
			activateTab();
			// openTag will reset and relayout
		}

		client.playSoundEffect(SoundEffectID.UI_BOOP);
	}

	private long getMillis()
	{
		return (long) (Math.random() * config.randLow() + config.randHigh());
	}

	private final HotkeyListener snapshotKey = new HotkeyListener(() -> config.snapshot())
	{
		@Override
		public void hotkeyPressed()
		{
			applyLimitAndPossiblyUpdateConfig(config.presetNum());
		}
	};


	private void applyLimitAndPossiblyUpdateConfig(int configToUpdate) {
		PresetList presets = PresetConverter.getPresetFromConfig(config);
		String name = config.name();

		if (configToUpdate >= 1 && configToUpdate <= 5) {
			config.configBlob(PresetConverter.updateConfigWithSnapshot(presets, client, configToUpdate, name));
		} else {
			WidgetUtils.dispatchError("ERROR! Preset is out of bounds!", client);
		}
	}

	public Widget createPresetInteractionWidget(Widget container, EquipmentPreset preset, int placementIndex)
	{
		Widget presetWidget = container.createChild(-1, WidgetType.GRAPHIC);

		presetWidget.setName(preset.configName); // name is a UUID that will help determine which config was clicked
		presetWidget.setOnOpListener(ScriptID.NULL);
		presetWidget.setHasListener(true);

		if (StringUtils.isNotEmpty(preset.configName)) {
			presetWidget.setText(preset.configName);
		} else {
			presetWidget.setText("<col=ff9040> No ConfigName text here </col>");
		}

		renderPreset(presetWidget, placementIndex);
		presetWidget.setAction(1, "Withdraw equipment ");
		presetWidget.setAction(2, "Withdraw inventory ");
		presetWidget.setItemId(preset.configIcon);

		presetWidget.setOnOpListener((JavaScriptCallback) this::handlePresetClick);
		return presetWidget;
	}

	private int addSectionHeader(Widget itemContainer, String title, int totalSectionsHeight)
	{
		addedWidgets.add(WidgetUtils.createGraphic(itemContainer,
										SpriteID.RESIZEABLE_MODE_SIDE_PANEL_BACKGROUND,
										ITEMS_PER_ROW * ITEM_HORIZONTAL_SPACING,
										LINE_HEIGHT,
										ITEM_ROW_START,
										totalSectionsHeight));
		addedWidgets.add(WidgetUtils.createText(itemContainer,
									title,
									new Color(228, 216, 162).getRGB(),
									(ITEMS_PER_ROW * ITEM_HORIZONTAL_SPACING) + ITEM_ROW_START,
									TEXT_HEIGHT,
									ITEM_ROW_START,
									totalSectionsHeight + LINE_VERTICAL_SPACING));

		return totalSectionsHeight + LINE_VERTICAL_SPACING + TEXT_HEIGHT;
	}


	/**
	 * HANDLE INTERACTIONS WITH PRESETS
	 */
	public void handlePresetClick(ScriptEvent event)
	{
		client.getLogger().info("Clicked on item widget @: " + event.getSource().getOriginalX() + ":" + event.getSource().getOriginalY());
		client.getLogger().info("Widget to click first: " + event.getOp());
		Widget deposit_widget = event.getOp() == 2 ? client.getWidget(WidgetInfo.BANK_DEPOSIT_EQUIPMENT) : client.getWidget(WidgetInfo.BANK_DEPOSIT_INVENTORY);
		client.getLogger().info("Deposit widget bounds: " + deposit_widget.getBounds());
		client.getLogger().info("Name: " + deposit_widget.getRSName());

		if (deposit_widget != null) {
			schedule(deposit_widget);
		}

		// withdraw equipment
		PresetConverter.getEquipmentFromId(presetToView, PresetConverter.getPresetFromConfig(config)).ifPresent(equipmentPreset -> {
			client.getLogger().info("Config being parsed: " + gson.toJson(equipmentPreset));

			// equipment first
			withdrawItems(event.getOp() == 2 ? equipmentPreset.playerEquipment : equipmentPreset.inventorySetup);
			//equipItems(equipmentPreset.inventorySetup);
		});
	}

	private void withdrawItems(List<ItemConfig> configs) {
		client.getLogger().info("Bank items to withdraw: " + gson.toJson(configs));
		Widget itemContainer = client.getWidget(WidgetInfo.BANK_ITEM_CONTAINER);

		for (Widget itemToWithdraw : itemContainer.getDynamicChildren()) {
			configs.stream()
					.filter(configItem -> configItem.itemId == itemToWithdraw.getItemId())
					.findFirst()
					.ifPresent(itemConfig -> withdrawItem(itemToWithdraw, itemConfig));
		}
	}

	private void withdrawItem(Widget itemWidget, ItemConfig itemConfig) {
		Rectangle bounds = itemWidget.getBounds();

		if (bounds.x > 0 && bounds.y > 0) {
			client.getLogger().info("Trying to get: " + itemWidget.getItemId() + " at: " + itemWidget.getBounds());

			if (itemConfig.itemCount > 1) {
				toggleNotedAndQuantity(true);
				schedule(itemWidget);
				toggleNotedAndQuantity(false);
			} else {
				schedule(itemWidget);
			}
		}
	}

	private void toggleNotedAndQuantity(boolean turnOnWithdrawMany) {
		if (turnOnWithdrawMany) {
			schedule(client.getWidget(WITHDRAW_ALL));
			schedule(client.getWidget(WITHDRAW_NOTED));
		} else {
			schedule(client.getWidget(WITHDRAW_ONE));
			schedule(client.getWidget(WITHDRAW_ITEM));
		}
	}

	public void destroy()
	{
		if (presetTabActive)
		{
			closeTab();
			bankSearch.reset(true);
		}

		parent = null;

		if (presetIconWidget != null)
		{
			presetIconWidget.setHidden(true);
		}

		if (presetBackgroundWidget != null)
		{
			presetBackgroundWidget.setHidden(true);
		}
		presetTabActive = false;
	}

	public void handleClick(MenuOptionClicked event)
	{
		String menuOption = event.getMenuOption();

		// If click a base tab, close
		boolean clickedTabTag = menuOption.startsWith("View tab") && !event.getMenuTarget().equals("preset-helper");
		boolean clickedOtherTab = menuOption.equals("View all items") || menuOption.startsWith("View tag tab");
		if (presetTabActive && (clickedTabTag || clickedOtherTab))
		{
			closeTab();
		}
	}

	public void handleSearch()
	{
		if (presetTabActive)
		{
			closeTab();
			// This ensures that when clicking Search when tab is selected, the search input is opened rather
			// than client trying to close it first
			client.setVar(VarClientStr.INPUT_TEXT, "");
			client.setVar(VarClientInt.INPUT_TYPE, 0);
		}
	}

	public void closeTab()
	{
		addedWidgets.forEach(widget -> widget.setHidden(true));
		presetTabActive = false;
		if (presetBackgroundWidget != null)
		{
			presetBackgroundWidget.setSpriteId(SpriteID.UNKNOWN_BUTTON_SQUARE_SMALL);
			presetBackgroundWidget.revalidate();
		}
	}

	private void activateTab()
	{
		if (presetTabActive)
		{
			return;
		}

		presetBackgroundWidget.setSpriteId(SpriteID.UNKNOWN_BUTTON_SQUARE_SMALL_SELECTED);
		presetBackgroundWidget.revalidate();
		presetTabActive = true;

		bankSearch.reset(true);

		Widget searchButtonBackground = client.getWidget(WidgetInfo.BANK_SEARCH_BUTTON_BACKGROUND);
		if (searchButtonBackground != null)
		{
			searchButtonBackground.setOnTimerListener((Object[]) null);
			searchButtonBackground.setSpriteId(SpriteID.EQUIPMENT_SLOT_TILE);
		}
	}

	private static int randomDelay() {
		return randomDelay(1, 49) + randomDelay(50, 250);
	}

	private static int randomDelay(int min, int max)
	{
		Random rand = new Random();
		int n = rand.nextInt(max) + 1;
		if (n < min)
		{
			n += min;
		}
		return n;
	}

	private void schedule(Widget widget)
	{
		executor.execute(() -> simLeftClick(widget, randomDelay()));
	}

	private void simLeftClick(Widget widget, int delay)
	{
		try
		{
			Thread.sleep(delay);
			Rectangle bounds = widget.getBounds();
			client.getLogger().info("Scheduled click in: " + delay + "ms @: " + bounds);
			utils.click(bounds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}

package net.runelite.client.plugins.externals.ui;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemComposition;
import net.runelite.client.plugins.externals.FreshUtils;
import net.runelite.client.plugins.externals.api.GameAPI;
import net.runelite.client.plugins.externals.api.InventoryItem;
import net.runelite.client.plugins.externals.api.ItemQuantity;
import net.runelite.client.plugins.externals.api.iWidget;


import javax.inject.Inject;
import java.util.*;

@Slf4j
public class Bank {
    public final GameAPI gameAPI;

    @Inject
    public Bank(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
    }

    public void depositInventory() {
        checkBankOpen();

        if (gameAPI.inventory().count() != 0) {
            gameAPI.widget(12, 42).interact(0);
            gameAPI.waitUntil(() -> gameAPI.inventory().count() == 0, 5);
        }
    }

    public void depositEquipment() {
        checkBankOpen();

        if (gameAPI.equipment().count() != 0) {
            gameAPI.widget(12, 44).interact(0);
            gameAPI.tick();
        }
    }

    private void checkBankOpen() {
        if (!isOpen()) {
            throw new IllegalStateException("bank isn't open");
        }

        if (gameAPI.widget(12, 114) != null && gameAPI.widget(12, 114).nestedInterface() == 664) {
            log.info("[Bank] Closing bank tutorial");
            gameAPI.widget(664, 9).select();
            gameAPI.tick();
        }
    }

    /**
     * Withdraws an item from the bank.
     *
     * @param id       the unnoted id of the item to withdraw
     * @param quantity the quantity of that item to withdraw
     * @param noted    whether the noted version of the item should be withdrawn
     * @return the quantity actually withdrawn (may be less then the requested
     * quantity if there are not enough in the bank or the inventory is too full)
     */
    public int withdraw(int id, int quantity, boolean noted) {
        checkBankOpen();
        ItemComposition definition = gameAPI.getFromClientThread(() -> gameAPI.client().getItemComposition(id));
        setNotedMode(noted);

        var inventoryCapacity = inventoryCapacity(noted ? definition.getLinkedNoteId() : id);

        if (inventoryCapacity == 0) {
            return 0;
        }

        for (iWidget item : FreshUtils.bankitems) {
            if (item.itemId() == id) {
                log.info("[Bank] Found item " + id + "(requested = " + quantity + ", bank = " + item.quantity() + ", capacity = " + inventoryCapacity + ")");

                quantity = Math.min(quantity, item.quantity());
                log.info("Quantity: {}", quantity);
                if (quantity == withdrawDefaultQuantity()) {
                    log.info("Withdrawing default quantity: " + withdrawDefaultQuantity());
                    item.interact(0); // default
                } else if (item.quantity() <= quantity || inventoryCapacity <= quantity) {
                    log.info("Withdrawing all");
                    item.interact(6); // all
                } else if (quantity == 1) {
                    log.info("Withdrawing 1");
                    item.interact(1); // 1
                } else if (quantity == 5) {
                    log.info("Withdrawing 5");
                    item.interact(2); // 5
                } else if (quantity == 10) {
                    log.info("Withdrawing 10");
                    item.interact(3); // 10
                } else if (quantity == withdrawXDefaultQuantity()) {
                    log.info("Withdraw X Default quantity: {}, {}", quantity, withdrawXDefaultQuantity());
                    item.interact(4); // last
                } else {
                    log.info("Withdrawing X");
                    item.interact(5);
                    gameAPI.chooseNumber(quantity);
                    gameAPI.tick(2);
                }

                return Math.min(inventoryCapacity, quantity);
            }
        }

        log.info("[Bank] Item not found: {}, {}, {}", id, quantity, noted);
        return 0;
    }

    /**
     * Deposits all of an item to the bank except for the given IDs.
     *
     *@param delay allow a random delay between depositing items
     * @param ids       the ids of the items to NOT deposit
     */
    public void depositExcept(boolean delay, Collection<Integer> ids) {
        var items = gameAPI.inventory().withoutId(ids).all();
        Set<Integer> deposited = new HashSet<>();
        while (gameAPI.inventory().withoutId(ids).exists()) {
            for (InventoryItem item : items) {
                if (deposited.contains(item.id())) {
                    continue;
                }
                deposit(item.id(), Integer.MAX_VALUE);
                deposited.add(item.id());
                if (delay) gameAPI.randomDelay();
            }
        }
    }

    /**
     * Deposits all of an item to the bank except for the given IDs.
     *
     *@param delay allow a random delay between depositing items
     * @param ids       the ids of the items to NOT deposit
     */
    public void depositExcept(boolean delay, int... ids) {
        var items = gameAPI.inventory().withoutId(ids).all();
        Set<Integer> deposited = new HashSet<>();
        while (gameAPI.inventory().withoutId(ids).exists()) {
            for (InventoryItem item : items) {
                if (deposited.contains(item.id())) {
                    continue;
                }
                deposit(item.id(), Integer.MAX_VALUE);
                deposited.add(item.id());
                if (delay) gameAPI.randomDelay();
            }
        }
    }

    /**
     * Deposits all of an item to the bank.
     *
     *@param delay allow a random delay between depositing items
     * @param ids       the ids of the items to deposit all of
     */
    public void depositAll(boolean delay, int... ids) {
        gameAPI.inventory().withId(ids).forEach(i -> {
            deposit(i.id(), Integer.MAX_VALUE);
            if (delay) gameAPI.randomDelay();
        });
    }

    /**
     * Deposits an item to the bank.
     *
     * @param id       the id of the item to deposit
     * @param quantity the quantity of that item to deposit
     * @return whether inventory item was successfully found and deposited
     */
    public boolean deposit(int id, int quantity) {
        checkBankOpen();

        iWidget targetItem = null;
        int count = 0;

        for (iWidget item : FreshUtils.bankInventoryitems) {
            if (item.itemId() == id) {
                if (targetItem == null) {
                    targetItem = item;
                }

                if (item.quantity() > 1) {
                    count = item.quantity();
                    break;
                } else {
                    count++;
                }
            }
        }

        if (targetItem != null) {
            log.info("[Bank] Found item (requested = " + quantity + ", inventory = " + count + ")");

            quantity = Math.min(quantity, count);

            if (quantity == withdrawDefaultQuantity()) {
                targetItem.interact(1); // default
            } else if (count <= quantity) {
                targetItem.interact(7); // all
            } else if (quantity == 1) {
                targetItem.interact(2); // 1
            } else if (quantity == 5) {
                targetItem.interact(3); // 5
            } else if (quantity == 10) {
                targetItem.interact(4); // 10
            } else if (quantity == withdrawXDefaultQuantity()) {
                targetItem.interact(5); // last
            } else {
                targetItem.interact(6);
                gameAPI.tick(2);
                gameAPI.chooseNumber(quantity);
            }

            return true;
        }

        log.info("[Bank] Inventory Item not found");
        return false;
    }

    public int item(int... ids) {
        for (var item : items()) {
            for (var id : ids) {
                if (item.itemId() == id) {
                    return item.itemId();
                }
            }
        }
        return -1;
    }

    public boolean contains(int... ids) {
        return Arrays.stream(ids).anyMatch(i -> items().stream()
                .anyMatch(b -> b.itemId() == i && b.quantity() >= 1));
    }

    public boolean contains(ItemQuantity... items) {
        return Arrays.stream(items).allMatch(i -> items().stream()
                .anyMatch(b -> b.itemId() == i.id && b.quantity() >= i.quantity));
    }

    public boolean contains(List<ItemQuantity> items) {
        return items.stream().allMatch(i -> items().stream()
                .anyMatch(b -> b.itemId() == i.id && b.quantity() >= i.quantity));
    }

    private void setNotedMode(boolean noted) {
        if (noted != withdrawNoted()) {
            if (!noted) {
                gameAPI.widget(12, 22).interact(0);
            } else {
                gameAPI.widget(12, 24).interact(0);
            }

            gameAPI.waitUntil(() -> noted == withdrawNoted());
        }
    }

    private void completeBankTutorial() {
        if (gameAPI.widget(12, 114).nestedInterface() == 664) {
            log.info("[Bank] Closing bank tutorial");
            gameAPI.widget(664, 9).select();
            gameAPI.waitUntil(() -> gameAPI.widget(12, 114).nestedInterface() == -1);
        }
    }

    public List<iWidget> items() {
        if (FreshUtils.bankitems.isEmpty()) {
            gameAPI.tick(); //Give time for items to load
        }
        return FreshUtils.bankitems;
    }

    public int quantity(int id) {
        if (!isOpen()) {
            throw new IllegalStateException("bank not open");
        }
        for (iWidget item : FreshUtils.bankitems) {
            if (item.itemId() == id) {
                return item.quantity();
            }
        }

        return 0;
    }

    public boolean isOpen() {
        return gameAPI.getFromClientThread(() -> gameAPI.container(InventoryID.BANK) != null);
    }

    public void close() {
        if (isOpen()) {
            gameAPI.widget(12, 2, 11).interact(0);
            gameAPI.clientThread.invoke(() -> gameAPI.client.runScript(138));
        }
    }

    public boolean withdrawNoted() {
        return gameAPI.varb(3958) == 1;
    }

    public int withdrawDefaultQuantity() {
        switch (gameAPI.varb(6590)) {
            case 0:
                return 1;
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return withdrawXDefaultQuantity();
            case 4:
                return Integer.MAX_VALUE;
            default:
                throw new IllegalStateException("unknown withdraw quantity type " + gameAPI.varb(6590));
        }
    }

    public int withdrawXDefaultQuantity() {
        return gameAPI.varb(3960);
    }

    private int inventoryCapacity(int id) {
        boolean stackable = gameAPI.getFromClientThread(() -> gameAPI.client.getItemComposition(id).isStackable());
        if (stackable) {
            InventoryItem item = gameAPI.inventory().withId(id).first();
            return item == null ? Integer.MAX_VALUE : Integer.MAX_VALUE - item.quantity();
        } else {
            return 28 - gameAPI.inventory().size();
        }
    }
}

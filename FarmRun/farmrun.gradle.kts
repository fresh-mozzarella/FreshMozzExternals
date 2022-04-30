

version = "1.0.0"

project.extra["PluginName"] = "FarmRun"
project.extra["PluginDescription"] = "Does farming runs for you."

dependencies {
    compileOnly(project(":FreshUtils"))
}

tasks {
    jar {
        manifest {
            attributes(mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
    }
}

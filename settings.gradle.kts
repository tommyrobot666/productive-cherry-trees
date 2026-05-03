val slug: String by settings
rootProject.name = slug

pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/")
		maven("https://api.modrinth.com/maven/")
		maven {
			name = "Cassian's Maven"
			url = uri("https://maven.cassian.cc")
		}
		gradlePluginPortal()
	}
}

//ERROR, FATAL, DANGER:
// Don't EVER try to use this, it's a bunch of stinky poo that will waste your time
// PUT REPOS IN THE BUILD FILE
dependencyResolutionManagement {
	repositories {
		// Modrinth Maven - see: https://support.modrinth.com/en/articles/8801191-modrinth-maven
		// Mods may be pulled from here will this format: "maven.modrinth:<slug>:<version>"
		exclusiveContent {
			forRepositories(maven("https://api.modrinth.com/maven")).filter {
				includeGroup("maven.modrinth")
			}
			forRepository {
				maven {
					name = "Cassian's Maven"
					url = uri("https://maven.cassian.cc")
				}
			}
			filter {
				includeGroupAndSubgroups("cc.cassian")
			}
		}
	}
}

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}


rootProject.name = "MiRaRecipes"
include(":app")


gradle.projectsLoaded {
allprojects {
   repositories{
       maven{
           url = uri("https://maven.google.com")
       }
   }
}
}
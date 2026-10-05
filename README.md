# Starsector Gradle Template

A Gradle-based template for creating mods for **Starsector 0.98a-RC8**.

This template provides a ready-to-use development environment with:

- Java 17 and Kotlin support
- Gradle
- Starsector 0.98a API
- Automatic mod packaging
- Automatic mod installation
- Configurable mod metadata
- Debugging support

## Requirements

Before using this template, make sure you have:

- Starsector 0.98a-RC8.
- Java 17 JDK.
- IDE for coding (IntelliJ IDEA, VS Code, Eclipse IDE).
- Git (optional, but recommended).

The template uses Gradle Wrapper, so a separate Gradle installation is not required.

## Project Structure

The project structure shows the interoperability between Java and Kotlin for developing a mod for Starsector, it is not necessary to use both languages at the same time.

```
starsector-gradle-template/
├── gradle/
│   └── wrapper/
│
├── mod/
│   ├── data/
│   ├── graphics/
│   └── jars/
│
├── buildSrc/
│   ├── src/
│   │   └── main/
│   │       ├── kotlin/
│   │       │   └── org/
│   │       │       └── endrylrm/
│   │       │           └── ModBuilder/
│   │       │               ├── extensions/
│   │       │               │   ├── BuildExtension.kt
│   │       │               │   ├── ModBuilderExtension.kt
│   │       │               │   └── ModExtension.kt
│   │       │               ├── model/
│   │       │               │   ├── DependencyContainer.kt
│   │       │               │   ├── JarContainer.kt
│   │       │               │   ├── ModDependency.kt
│   │       │               │   └── ModInfo.kt
│   │       │               ├── tasks/
│   │       │               │   ├── BuildModTask.kt
│   │       │               │   ├── GenerateModInfoTask.kt
│   │       │               │   └── InstallModTask.kt
│   │       │               └── ModBuilderPlugin.kt
│   │       └── resources/
│   │           └── META-INF/
│   │               └── gradle-plugins/
│   │                   └── org.endrylrm.mod-builder.properties
│   └── build.gradle.kts
│
├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── example/
│       │           └── ModPlugin.java
│       │
│       ├── kotlin/
│       │   └── org/
│       │       └── example/
│       │           └── ExampleScript.kt
│       │
│       └── resources/
│
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── LICENSE
├── README.md
└── settings.gradle.kts
```

## Configuration

The mod configuration is defined using the `ModBuilder` Gradle DSL in your `build.gradle.kts`.

```kotlin
plugins {
    id("org.endrylrm.mod-builder")
}

modBuilder {
    gameDirectory.set(file("E:/Fractal Softworks/Starsector"))

    mod {
        id = "starsector-gradle-template"
        name = "Starsector Gradle Template"
        version = "0.1.0"
        author = "Your Name"
        utility = false
        description = "A Gradle template for Starsector mods."
        gameVersion = "0.98a-RC8"

        jars {
            add("jars/ExampleMod.jar")
            add("jars/ExampleMod2.jar")
        }

        modPlugin = "org.example.ExampleModPlugin"

        dependencies {
            add {
                id = "example_dependency"
                name = "Example Mod Dependency"
                version = "0.1.0"
            }
        }
    }

    build {
        copyToGame = true
    }
}
```

### Game properties

The `gameDirectory` property specifies the path to the Starsector installation. It is used by ModBuilder to access the Starsector API and to install the generated mod.

| Property       | Description                         |
|----------------|-------------------------------------|
| gameDirectory  | Path to the Starsector installation |

### Mod configuration

The mod block contains the information used to generate the mod_info.json file.

| Property        | Description                                                                                            |
|-----------------|--------------------------------------------------------------------------------------------------------|
| id              | Your Mod Id                                                                                            |
| name            | Mod name displayed in Starsector                                                                       |
| version         | Current Mod version (defaults to 0.1.0)                                                                |
| author          | Mod author name / nickname                                                                             |
| utility         | Is this a utility mod? (defaults to false)                                                             |
| description     | Mod description                                                                                        |
| modPlugin       | Main Java class used as the mod plugin                                                                 |
| gameVersion     | Target Starsector version (defaults to 0.98a-RC8)                                                      |
| totalConversion | whether this mod is a total conversion or not. defaults to null, so it will not show on the json file. |


Example:

```kotlin
mod {
    id = "example_mod"
    name = "Example Mod"
    version = "0.1.0"
    author = "Example Author"
    utility = false
    description = "Example mod description."
    modPlugin = "org.example.ExampleModPlugin"
    gameVersion = "0.98a-RC8"
}
```


### Jar Files

The `jars` block defines the JAR files that should be included in the generated `mod_info.json`.

Use `add()` to add a JAR path:

```kotlin
jars {
    add("jars/ExampleMod.jar")
    add("jars/ExampleMod2.jar")
}
```

generates the following in `mod_info.json`:

```json
"jars": [
    "jars/ExampleMod.jar",
    "jars/ExampleMod2.jar"
]
```

### Mod Dependencies

Defines the Mod Dependencies in the `dependencies` that should be included in the generated `mod_info.json`.

Use `add` to add a mod dependency:

```kotlin
dependencies {
    add {
        id = "example_dependency"
        name = "Example Mod Dependency"
        version = "0.1.0"
    }
}
```

The `version` property is optional:

```kotlin
dependencies {
    add {
        id = "example_dependency"
        name = "Example Mod Dependency"
    }
}
```

generates the following in `mod_info.json` as an example:

```json
"dependencies": [
  {
    "id": "example_dependency",
    "name": "Example Mod Dependency",
    "version": "0.1.0"
  }
]
```

### Replace properties

Defines the files to be replaced in the game, should be only used together with `totalConversion` option as it makes your mod incompatible with others.

Use `add` to add a file to replace from the game:

```kotlin
replace {
    add("data/missions/mission_list.csv")
}
```

generates the following in `mod_info.json` as an example:

```json
"replace": [
    "data/missions/mission_list.csv"
]
```

### Build properties

The `build` block controls how the mod is packaged.

| Property   | Description                                                                                              |
|------------|----------------------------------------------------------------------------------------------------------|
| folderName | Name of the mod folder created under `build/mod/`. Defaults to the mod ID.                               |
| copyToGame | Whether the generated mod should be installed into the Starsector `mods` directory. Defaults to `false`. |

Example:

```kotlin
build {
    folderName = "ExampleMod"
    copyToGame = true
}
```

If `folderName` is not specified, the mod ID is used:

```kotlin
mod {
    id = "example_mod"
}

build {
    // folderName defaults to "example_mod"
}
```

### Generated `mod_json.info`

ModBuilder generates the `mod_info.json` file under:

```
build/generated/mod_info.json
```

For example, the configuration:

```kotlin
mod {
    id = "example_mod"
    name = "Example Mod"
    version = "0.1.0"
    author = "Example Author"
    utility = false
    description = "Example mod description."
    gameVersion = "0.98a-RC8"

    jars {
        add("jars/ExampleMod.jar")
    }

    modPlugin = "org.example.ExampleModPlugin"

    dependencies {
        add {
            id = "example_dependency"
            name = "Example Dependency"
            version = "0.1.0"
        }
    }
}
```

generates the following `mod_info.json`:

```json
{
    "id": "example_mod",
    "name": "Example Mod",
    "version": "0.1.0",
    "author": "Example Author",
    "utility": "false",
    "description": "Example mod description.",
    "gameVersion": "0.98a-RC8",
    "jars": [
        "jars/ExampleMod.jar"
    ],
    "modPlugin": "org.example.ExampleMod",
    "dependencies": [
        {
            "id": "example_dependency",
            "name": "Example Dependency",
            "version": "0.1.0"
        }
    ]
}
```

## Building the Project

To compile the Java source code:

```shell
.\gradlew.bat build
```

The compiled JAR will be generated in:

```
build/libs/
```

## Building the Mod

To create the Starsector mod directory:

```shell
.\gradlew.bat buildMod
```

The generated mod will be located at:

```
build/mod/<modId>/
```

Your mod structure will look like this:

```
<modId>/
├── mod_info.json
├── data/
├── graphics/
└── jars/
    └── <modJarFile>.jar
```

The mod_info.json file is generated by Gradle and its values are populated using Gradle build script and will look like this:

```json
{
    "id": "example_mod",
    "name": "Example Mod",
    "version": "0.1.0",
    "author": "Example Author",
    "utility": false,
    "description": "Example mod description.",
    "plugin": "org.example.ExampleMod",
    "gameVersion": "0.98a-RC8",
    "jars": [
        "jars/ExampleMod.jar"
    ],
    "dependencies": [
        {
            "id": "dependency_id",
            "name": "Dependency Name"
        },
        {
            "id": "dependency_id_2",
            "name": "Dependency Name 2",
            "version": "0.1.0"
        }
    ]
}
```

The Gradle script source is in `buildSrc/src/` in case you want to improve it.

## Installing the Mod

To build and install the mod directly into the Starsector installation:

```shell
.\gradlew.bat installMod
```

The mod will be installed at:

```
<starsectorDir>/mods/<modId>/
```

For example:

```
E:/Fractal Softworks/Starsector/mods/starsector-gradle-template/
```

## Cleaning the Build

To remove generated build files:

```shell
.\gradlew.bat clean
```

To rebuild everything from scratch:

```shell
.\gradlew.bat clean build
```

## Mod Plugin

The default plugin is:

```java
package org.example;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

public class ExampleModPlugin extends BaseModPlugin
{
    @Override
    public void onApplicationLoad()
    {
        Global.getLogger(ExampleModPlugin.class).info("My mod - Plugin loaded...");
    }

    @Override
    public void onGameLoad(boolean newGame)
    {
        Global.getLogger(ExampleModPlugin.class).info("My mod - Game loaded...");
    }
}
```

or in kotlin:
```kotlin
package org.example

import com.fs.starfarer.api.BaseModPlugin
import com.fs.starfarer.api.Global

class ExampleModPlugin : BaseModPlugin()
{
    override fun onApplicationLoad()
    {
        Global.getLogger(javaClass).info("My mod - Plugin loaded...")
    }

    override fun onGameLoad(newGame: boolean)
    {
        Global.getLogger(javaClass).info("My mod - Game loaded...")
    }
}
```

The class is referenced by mod_info.json through the modPlugin property.

## Development Workflow

A typical development cycle is:

```
Edit source code
↓
.\gradlew.bat installMod
↓
Start Starsector
↓
Test the mod
↓
Check the Starsector log
```


For a clean rebuild:

```
.\gradlew.bat clean installMod
```

## Debugging

The template can be configured to run Starsector with Java debugging enabled.

The Starsector JVM can be started with JDWP enabled on port 5005:

```
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
```

IntelliJ IDEA can then connect using a Remote JVM Debug configuration:

```
Host: localhost
Port: 5005
```

This allows breakpoints to be placed directly inside the mod source code.

## Starsector Version

This template currently targets:

- Starsector 0.98a-RC8 which requires Java 17.

The template is intended specifically for mod development against this Starsector version.

## License

This template is licensed under the [MIT License](LICENSE).

Starsector and its associated libraries remain property of Fractal Softworks.
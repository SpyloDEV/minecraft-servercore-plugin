# ServerCorePlus

This repository contains `ServerCorePlus`, a Paper plugin with a smaller server-core feature set.

It is focused on a handful of common utility systems instead of trying to cover everything: spawn handling, starter items, daily rewards, a rewards menu and a few admin commands.

## Included systems

- spawn and setspawn
- one-time starter kit
- daily reward handling
- rewards GUI
- heal and feed commands with cooldowns
- basic admin reload command
- configurable messages and reward items

## Commands

- `/spawn`
- `/setspawn`
- `/starter`
- `/daily`
- `/rewards`
- `/heal [player]`
- `/feed [player]`
- `/servercore reload`

## Configuration

The plugin ships with:

- `config.yml`
- `messages.yml`

The default config includes:

- join-to-spawn toggle
- starter kit toggle and item list
- daily reward toggle and reward items
- heal and feed cooldown values
- default spawn coordinates

## Build

Requirements:

- Java 21
- Maven
- Paper API `1.21.4-R0.1-SNAPSHOT`

Build command:

```bash
mvn clean package
```

The output jar is created in:

```bash
target/servercoreplus-1.0.0.jar
```

## Notes

- this is a classic `plugin.yml` Paper plugin
- the repository name and the plugin name differ slightly; the plugin itself is called `ServerCorePlus`
- the repository also includes small Windows helper scripts for initial Git setup and pushing

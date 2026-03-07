# ServerCorePlus

A medium-sized Paper plugin for Minecraft servers.

## Features

- `/spawn` and `/setspawn`
- `/starter` one-time starter kit
- `/daily` 24-hour reward system
- `/rewards` GUI menu for claiming rewards
- `/heal` and `/feed` with cooldowns
- `/servercore reload`
- Join messages
- Player data saved in `playerdata.yml`
- Fully configurable items and messages

## Tech stack

- Java 21
- Maven
- Paper API `1.21.4-R0.1-SNAPSHOT`

## Build

```bash
mvn clean package
```

The jar will be created in:

```bash
target/servercoreplus-1.0.0.jar
```

## Commands

- `/spawn`
- `/setspawn`
- `/starter`
- `/daily`
- `/rewards`
- `/heal [player]`
- `/feed [player]`
- `/servercore reload`

## Config files

- `src/main/resources/config.yml`
- `src/main/resources/messages.yml`

## GitHub push

You can use the included Windows scripts:

- `setup-git.bat`
- `push-to-github.ps1`

Recommended flow:

1. Extract the zip
2. Open the folder in Terminal
3. Run `setup-git.bat`
4. Create a new empty GitHub repository
5. Run `powershell -ExecutionPolicy Bypass -File .\push-to-github.ps1`
6. Paste your GitHub repository URL when asked

## Notes

This project is built as a classic `plugin.yml` Paper plugin with a Maven-based Paper API setup.

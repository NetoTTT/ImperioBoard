# ImperioBoard

Configurable YML-driven scoreboard for Spigot 1.8.8.

## Features

- Fully customizable sidebar via `config.yml`
- Native placeholders: `{player}`, `{coins}`, `{cash}`, `{faction}`, `{tag}`, `{power}`, `{zona}`, `{territorios}`
- PlaceholderAPI support via `{papi_NAME}`
- Factions support (SafeZone, WarZone, Wilderness)
- Auto-updates every N ticks
- No flicker — reuses the same scoreboard object

## Commands

| Command | Description |
|---------|-------------|
| `/hb reload` | Reload config.yml |
| `/hb debug` | Zone/faction debug info |

## Config

`plugins/ImperioBoard/config.yml`

```yaml
title: "&6&lMY SERVER"
update-ticks: 10
lines:
  - "&7&m-----------------------"
  - " &7Player: &f{player}"
  - " {zona}"
  - "..."
```

## Placeholders

| Placeholder | Description |
|-------------|-------------|
| `{player}` | Player name |
| `{online}` | Online players |
| `{coins}` | Vault balance |
| `{cash}` | HaskCash balance |
| `{faction}` | Faction name |
| `{tag}` | Faction tag |
| `{power}` | Current power |
| `{power_max}` | Max power |
| `{zona}` | Current territory |
| `{territorios}` | Land count |
| `{papi_NAME}` | PlaceholderAPI |

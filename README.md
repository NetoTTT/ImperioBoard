# ImperioBoard

Scoreboard configurável via YML para Spigot 1.8.8.

## Funcionalidades

- Scoreboard lateral totalmente configurável via `config.yml`
- Placeholders nativos: `{player}`, `{coins}`, `{cash}`, `{faction}`, `{tag}`, `{power}`, `{zona}`, `{territorios}`
- Placeholders do PlaceholderAPI via `{papi_NOME}`
- Suporte a Factions (SafeZone, WarZone, Wilderness)
- Atualização automática a cada N ticks
- Sem flicker — reusa o mesmo scoreboard

## Comandos

| Comando | Descrição |
|---------|-----------|
| `/hb reload` | Recarrega o config.yml |
| `/hb debug` | Debug das zonas/facções |

## Config

`plugins/ImperioBoard/config.yml`

```yaml
title: "&6&lIMPERIO HOME"
update-ticks: 10
lines:
  - "&7&m-----------------------"
  - " &7Jogador: &f{player}"
  - " {zona}"
  - "..."
```

## Placeholders

| Placeholder | Descrição |
|-------------|-----------|
| `{player}` | Nome do jogador |
| `{online}` | Jogadores online |
| `{coins}` | Saldo Vault |
| `{cash}` | Saldo HaskCash |
| `{faction}` | Nome da facção |
| `{tag}` | Tag da facção |
| `{power}` | Poder atual |
| `{power_max}` | Poder máximo |
| `{zona}` | Território atual |
| `{territorios}` | Quantidade de terras |
| `{papi_NOME}` | PlaceholderAPI |

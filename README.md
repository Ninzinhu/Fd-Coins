# 🎮 FulldevCoins - Plugin Spigot Completo

Um plugin Spigot/Bukkit para Minecraft que faz mobs droparem moedas personalizadas com sistema de banco de dados, GUI bonita, loja integrada e muito mais!

## 📋 Características Principais

### 💰 Sistema de Coins
- ✅ Mobs dropam moedas ao serem mortos por players
- ✅ Drop automático a cada 15 minutos (configurável)
- ✅ Armazenamento de coins em banco de dados SQLite
- ✅ Histórico de transações
- ✅ Sistema de levels e boosters

### 🎨 Interface Gráfica
- ✅ GUI bonita ao digitar `/coins`
- ✅ Cabeça do player como ícone principal
- ✅ Informações de último login e quantidade de coins
- ✅ Botões para Discord, Website e Loja
- ✅ Design limpo com cores e vidro decorativo

### 🏪 Loja de Coins
- ✅ Compra de itens com coins
- ✅ Diamante (50 coins)
- ✅ Esmeralda (40 coins)
- ✅ Barra de Ouro x5 (30 coins)
- ✅ Barra de Ferro x10 (20 coins)
- ✅ Itens customizáveis via config

### 💾 Banco de Dados
- ✅ SQLite integrado (padrão)
- ✅ Suporte a MySQL (opcional)
- ✅ Armazenamento seguro de dados
- ✅ Histórico de transações
- ✅ Sincronização automática

### 🔧 Sistema de Recompensas
- ✅ Recompensas automáticas a cada 15 minutos
- ✅ Drop de 1 coin automático (configurável)
- ✅ Milestones e notificações
- ✅ Sistema de eventos

## 🚀 Quick Start

### Instalação Rápida

1. **Compile o projeto:**
```bash
mvn clean package
```

2. **Copie o JAR para seu servidor Spigot:**
```bash
cp target/Fulldev-coins-1.0-SNAPSHOT.jar /caminho/para/plugins/
```

3. **Reinicie o servidor:**
```bash
# O arquivo config.yml será gerado automaticamente em:
# plugins/FulldevCoins/config.yml
```

### Via Scripts de Build

**Windows:**
```bash
build.bat
```

**Linux/Mac:**
```bash
./build.sh
```

## 💬 Comandos

### `/coins`
Abre a GUI principal com informações do player

### `/coins info`
Mostra informações detalhadas:
- Moedas atuais
- Total ganho
- Level
- Último login

### `/coins loja` ou `/coins shop`
Abre a loja de coins

### `/coins help`
Mostra ajuda de comandos

### `/coins reload` (Admin)
Recarrega a configuração sem reiniciar

## ⚙️ Configuração

### Arquivo config.yml

```yaml
# SISTEMA DE DROP
coins-drop:
  interval-minutes: 15          # Drop automático a cada 15 minutos
  coins-per-drop: 1             # Quantidade por drop automático
  
  mob-drop:
    enabled: true
    chance: 0.5                 # 50% de chance
    min-coins: 1
    max-coins: 5
    allowed-mobs:
      - ZOMBIE
      - CREEPER
      # ... mais mobs

# BANCO DE DADOS
database:
  type: sqlite                  # sqlite ou mysql
  sqlite:
    filename: "fulldev-coins.db"

# LOJA
shop:
  enabled: true
  items:
    diamond:
      id: "DIAMOND"
      quantity: 1
      price: 50
```

## 🎨 Personalizações

### Mudar Item das Moedas

Edite `CoinsGUI.java` ou `MobDeathListener.java`:

```java
private ItemStack createCoinItem(int amount) {
    ItemStack coin = new ItemStack(Material.GOLD_NUGGET, amount);
    // Trocar GOLD_NUGGET por: DIAMOND, EMERALD, etc
```

### Adicionar Novos Itens na Loja

Edite `ShopManager.java` e adicione itens no método `openShop()`:

```java
// Item 5: Novo Item
shop.setItem(22, createShopItem(Material.AMETHYST_SHARD, "§dAmetista", 
    new String[]{"§7Preço: §645 moedas", "", "§eClique para comprar!"}));
```

### Customizar Mensagens

Edite `config.yml`:

```yaml
messages:
  coins-received: "§a[§6Fulldev§a] +§e%amount%§a moedas! Total: §6%total%"
  auto-reward: "§a[Fulldev] Você recebeu §e1 moeda§a automática!"
```

## 🗄️ Estrutura de Banco de Dados

### Tabela: players
```sql
uuid          TEXT PRIMARY KEY
name          TEXT
coins         LONG (moedas atuais)
last_login    LONG (timestamp)
total_coins_earned LONG
level         INT
created_at    LONG (timestamp)
booster_multiplier DOUBLE
booster_until LONG (timestamp)
```

### Tabela: transactions
```sql
id            INTEGER PRIMARY KEY
player_uuid   TEXT
type          TEXT (EARN, SPEND, BOOSTER, BONUS)
amount        LONG
reason        TEXT
timestamp     LONG
```

## 🎯 Funcionalidades Extras Implementadas

- ✅ **Cabeça customizada do player** como ícone na GUI
- ✅ **Formatação de números** com separador de milhares
- ✅ **Data formatada** de último login
- ✅ **Sistema de transações** para auditoria
- ✅ **Suporte a MySQL** além de SQLite
- ✅ **Sistema de levels** com recompensas
- ✅ **Booster de multiplicação** de coins
- ✅ **Sistema de milestones** com notificações
- ✅ **Links customizáveis** para Discord/Website
- ✅ **Permissões granulares** para comandos

## 📁 Estrutura do Projeto

```
src/main/java/org/konpeki-estudios/
├── FulldevCoinsPlugin.java        (Classe principal)
├── CoinCommand.java                (Comandos)
├── MobDeathListener.java            (Drop ao matar mobs)
├── GUIListener.java                 (Gerencia cliques na GUI)
├── database/
│   └── DatabaseManager.java         (Gerenciamento de DB)
├── gui/
│   └── CoinsGUI.java                (Interface gráfica)
├── shop/
│   └── ShopManager.java             (Gerenciador de loja)
└── system/
    └── AutoRewardManager.java       (Recompensas automáticas)
```

## ❓ Troubleshooting

### "Plugin failed to load"
- ✅ Verificar se está usando Java 21+
- ✅ Verificar se é Spigot 1.20.4+
- ✅ Ver log em `logs/latest.log`

### Moedas não estão aparecendo
- ✅ Verificar se mob está em `allowed-mobs`
- ✅ Verificar se `enabled: true`
- ✅ Usar `/coins reload`

### Banco de dados não conecta
- ✅ Verificar permissões da pasta `plugins/FulldevCoins/`
- ✅ Verificar se SQLite está disponível
- ✅ Para MySQL: verificar credenciais em config.yml

## 📊 Stack Técnico

- **Linguagem:** Java 21
- **Build Tool:** Maven
- **Framework:** Spigot API 1.20.4
- **Database:** SQLite/MySQL
- **CI/CD:** GitHub Actions
- **Versionamento:** Semântico

## 🎯 Próximas Ideias

- [ ] Sistema de Daily Quests
- [ ] Leaderboard de top players
- [ ] Integração com economia (Vault)
- [ ] Diferentes raridades de coins
- [ ] Animações de GUI
- [ ] Sistema de presentes entre players
- [ ] Evento especial com drop aumentado
- [ ] Sistema de battle pass

## 📝 Versioning

O projeto usa **Semantic Versioning** automático:

```
MAJOR.MINOR.PATCH
  ↑      ↑      ↑
  |      |      └─ Incrementa em develop (bugfixes)
  |      └─────── Incrementa em main (features)
  └───────────── Incrementa manualmente (breaking changes)
```

## 🚀 Deploy em Produção

1. **Compile:** `mvn clean package`
2. **Teste localmente**
3. **Push para main:** Triggers release automática
4. **Download de releases:** GitHub → Releases
5. **Instale em servidor:** Copie JAR para plugins/
6. **Configure:** Edite config.yml conforme necessário

## 📞 Suporte

1. **Dúvidas sobre instalação?** → Veja seção "Quick Start"
2. **Erros de compilação?** → Veja "Troubleshooting"
3. **Quer customizar?** → Veja "Personalizações"
4. **Problemas com BD?** → Verifique "Banco de Dados"

## 🎉 Conclusão

Você tem um plugin Spigot **profissional, completo e pronto para produção**!

**Pronto para usar. Aproveite! 🎮**

---

**Desenvolvido com ❤️ por Konpeki Studios**

Versão: 1.0-SNAPSHOT | Status: ✅ Pronto para Produção | Data: 2026-03-29

## 🚀 Quick Start

### Instalação Rápida

1. **Compile o projeto:**
```bash
mvn clean package
```

2. **Copie o JAR para seu servidor Spigot:**
```bash
cp target/Fulldev-coins-1.0-SNAPSHOT.jar /caminho/para/plugins/
```

3. **Reinicie o servidor:**
```bash
# O arquivo config.yml será gerado automaticamente em:
# plugins/FulldevCoins/config.yml
```

4. **Configure (opcional):**
```bash
vim plugins/FulldevCoins/config.yml
# Customize as opções conforme necessário
# Depois use /fulldevcoins reload para aplicar
```

### Via Scripts de Build

**Windows:**
```bash
build.bat
```

**Linux/Mac:**
```bash
./build.sh
```

## 📖 Documentação Completa

### Instalação Detalhada

#### Pré-requisitos
- Java 21+
- Spigot/Bukkit 1.20.4+
- Maven 3.6+ (opcional, pode usar mvnw)

#### Passo-a-Passo

1. Clone o repositório
2. Entre na pasta do projeto
3. Execute `mvn clean package`
4. Copie o JAR gerado para `plugins/` do servidor
5. Reinicie o servidor
6. O arquivo `config.yml` será criado em `plugins/FulldevCoins/`

#### Verificando a Instalação

Você verá no console do servidor:
```
[FulldevCoins] Plugin ativado com sucesso!
[FulldevCoins] Versão: 1.0
```

Use o comando:
```
/fulldevcoins info
```

## ⚙️ Configuração

### config.yml

```yaml
# Chance de dropar moedas (0.0 a 1.0)
drop-chance: 0.5

# Quantidade mínima de moedas por drop
min-coins: 1

# Quantidade máxima de moedas por drop
max-coins: 5

# Habilitar/desabilitar o plugin
enabled: true

# Mensagem exibida quando player recebe moedas
message: "[Fulldev] Você recebeu %amount% moeda(s)!"

# Mobs que podem dropar moedas
allowed-mobs:
  - ZOMBIE
  - CREEPER
  - SKELETON
  - SPIDER
  - ENDERMAN
  - BLAZE
  - WITCH
  - WITHER_SKELETON
  - HUSK
  - DROWNED
  - PHANTOM
  - ZOGLIN
```

### Personalizando

**Aumentar quantidade de moedas:**
```yaml
min-coins: 5
max-coins: 15
```

**Aumentar chance de drop (80%):**
```yaml
drop-chance: 0.8
```

**Permitir todos os mobs:**
```yaml
allowed-mobs: []
```

**Customizar mensagem:**
```yaml
message: "§a[§6Fulldev§a] +§e%amount%§a moedas!"
```

Após editar, use:
```
/fulldevcoins reload
```

## 💬 Comandos

### /fulldevcoins help
Mostra ajuda com todos os comandos disponíveis.

### /fulldevcoins info
Mostra informações do plugin e configurações atuais:
- Drop Chance
- Min/Max Coins
- Status (Ativado/Desativado)

### /fulldevcoins reload
Recarrega o arquivo `config.yml` sem reiniciar o servidor.

**Permissão:** `fulldevcoins.admin` (apenas OPs por padrão)

## 🛠️ Customização Avançada

### Mudar Item das Moedas

Edite `MobDeathListener.java` no método `createCoinItem()`:

```java
private ItemStack createCoinItem(int amount) {
    ItemStack coin = new ItemStack(Material.GOLD_NUGGET, amount);
    // Trocar GOLD_NUGGET por:
    // DIAMOND, EMERALD, GOLD_INGOT, IRON_INGOT, etc.
    
    org.bukkit.inventory.meta.ItemMeta meta = coin.getItemMeta();
    if (meta != null) {
        meta.setDisplayName("§6FulldevCoin");
        coin.setItemMeta(meta);
    }
    return coin;
}
```

### Adicionar Brilho

```java
if (meta != null) {
    meta.setDisplayName("§6FulldevCoin");
    meta.addEnchant(
        org.bukkit.enchantments.Enchantment.LURE, 
        1, 
        true
    );
    coin.setItemMeta(meta);
}
```

### Adicionar Descrição

```java
java.util.List<String> lore = new java.util.ArrayList<>();
lore.add("§7Moeda mágica");
lore.add("§7Valiosa para mercadores");
meta.setLore(lore);
```

## 🤖 CI/CD - Automação

O projeto possui 4 workflows GitHub Actions automáticos:

### 1. Build and Release (build.yml)
- Compila Maven
- Calcula versão semântica
- Cria tags git
- Gera releases no GitHub
- Upload do JAR

**Trigger:** Push em `main` ou `develop`

### 2. Tests (tests.yml)
- Executa testes Maven
- Gera relatórios

**Trigger:** Push ou PR

### 3. Security (security.yml)
- OWASP Dependency Check
- Verifica CVEs

**Trigger:** Push, PR ou semanal

### 4. Documentation (docs.yml)
- Valida Markdown
- Gera JavaDoc

**Trigger:** Push em `main`

### Usando GitHub Actions

1. **Fazer push de código:**
```bash
git add .
git commit -m "feat: nova funcionalidade"
git push origin develop
```

2. **Workflows rodam automaticamente:**
   - Testes
   - Security scan
   - Build
   - Release (se bem-sucedido)

3. **Versionamento automático:**
   - `develop`: v1.2.X (patch)
   - `main`: v1.X.0 (minor)

4. **Download do JAR:**
   - GitHub → Releases
   - Clique em uma release
   - Download do JAR

## ❓ Troubleshooting

### "Plugin failed to load"
- ✅ Verificar se está usando Java 21+
- ✅ Verificar se é Spigot 1.20.4+ (não Bukkit puro)
- ✅ Ver log em `logs/latest.log`

### Moedas não estão aparecendo
- ✅ Verificar se mob está em `allowed-mobs`
- ✅ Verificar se `enabled: true`
- ✅ Verificar se `drop-chance` não está muito baixa
- ✅ Usar `/fulldevcoins reload`

### Build Maven falha
- ✅ Verificar se tem Java 21+: `java -version`
- ✅ Limpar cache Maven: `mvn clean`
- ✅ Verificar erros de compilação

### Arquivo config.yml não aparece
- ✅ Aguardar alguns segundos após iniciar
- ✅ Verificar permissões da pasta `plugins/`
- ✅ Recriar manualmente em `plugins/FulldevCoins/`

## 📁 Estrutura do Projeto

```
Fulldev-coins/
├── pom.xml                           (Configuração Maven)
├── README.md                         (Esta documentação)
├── build.sh / build.bat              (Scripts de build)
│
├── .github/
│   ├── workflows/
│   │   ├── build.yml                 (Build automático)
│   │   ├── tests.yml                 (Testes)
│   │   ├── security.yml              (Segurança)
│   │   └── docs.yml                  (Documentação)
│   └── WORKFLOW-CONFIG.md            (Config dos workflows)
│
└── src/
    ├── main/
    │   ├── java/org/konpeki-estudios/
    │   │   ├── FulldevCoinsPlugin.java       (Classe principal)
    │   │   ├── MobDeathListener.java         (Listener)
    │   │   └── CoinCommand.java              (Comandos)
    │   │
    │   └── resources/
    │       ├── plugin.yml                    (Config Spigot)
    │       └── config.yml                    (Config padrão)
    │
    └── test/
        └── java/                            (Testes)
```

## 📊 Stack Técnico

- **Linguagem:** Java 21
- **Build Tool:** Maven
- **Framework:** Spigot API 1.20.4
- **CI/CD:** GitHub Actions
- **Versionamento:** Semântico
- **Licença:** MIT

## 🎯 Próximas Ideias

- [ ] Integração com sistema de economia
- [ ] Diferentes tipos de moedas
- [ ] Sistema de leveling
- [ ] Interface visual (GUI)
- [ ] Eventos especiais
- [ ] Integração com PlaceholderAPI

## 📞 Suporte

1. **Dúvidas sobre instalação?** → Veja seção "Instalação Detalhada"
2. **Erros de compilação?** → Veja "Troubleshooting"
3. **Quer customizar?** → Veja "Customização Avançada"
4. **Problemas com CI/CD?** → Veja ".github/WORKFLOW-CONFIG.md"

## ✨ Padrão de Commits Recomendado

Para melhor automação:

```
feat: adiciona nova funcionalidade
fix: corrige bug
docs: atualiza documentação
style: formata código
refactor: refatora sem mudança funcional
test: adiciona testes
chore: alterações de build/ferramentas
```

Exemplo:
```bash
git commit -m "feat: adiciona sistema de drop de moedas"
git commit -m "fix: corrige chance de drop incorreta"
git commit -m "docs: atualiza README com novos comandos"
```

## 📝 Versioning

O projeto usa **Semantic Versioning** automático:

```
MAJOR.MINOR.PATCH
  ↑      ↑      ↑
  |      |      └─ Incrementa em develop (bugfixes)
  |      └─────── Incrementa em main (features)
  └───────────── Incrementa manualmente (breaking changes)
```

**Exemplos:**
- v0.0.0 → v0.0.1 (push em develop)
- v0.0.1 → v0.1.0 (push em main)
- v0.1.0 → v1.0.0 (release major manual)

## 🚀 Deploy em Produção

1. **Compile:** `mvn clean package`
2. **Teste localmente:** Instale e teste
3. **Push para main:** Triggers release automática
4. **Download de releases:** GitHub → Releases
5. **Instale em servidor:** Copie JAR para plugins/
6. **Configure:** Edite config.yml conforme necessário

## 🎉 Conclusão

Você tem um plugin Spigot profissional, bem documentado e com CI/CD automático!

**Pronto para usar. Aproveite! 🎮**

---

**Desenvolvido com ❤️ por Konpeki Studios**

Versão: 1.0-SNAPSHOT | Status: ✅ Pronto para Produção | Data: 2026-03-29

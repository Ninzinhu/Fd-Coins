# 🎮 FulldevCoins - Plugin Spigot

Um plugin Spigot/Bukkit para Minecraft que faz mobs droparem moedas personalizadas com sistema de CI/CD automático.

## 📋 Características

- ✅ Mobs dropam moedas ao serem mortos por players
- ✅ Quantidade de moedas configurável (mín e máx)
- ✅ Chance de drop configurável (0.0 a 1.0)
- ✅ Lista de mobs permitidos customizável
- ✅ Mensagens personalizadas no chat
- ✅ Sistema de comandos (`/fulldevcoins`)
- ✅ Sistema de permissões integrado
- ✅ CI/CD automático com GitHub Actions
- ✅ Versionamento semântico automático
- ✅ Releases automáticas no GitHub

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

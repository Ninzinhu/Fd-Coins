# 🚀 Guia Rápido de Uso - FulldevCoins

## ⚡ Início Rápido (5 minutos)

### 1. Compilação
```bash
mvn clean package
```
O JAR será gerado em: `target/Fulldev-coins-1.0-SNAPSHOT.jar`

### 2. Instalação
```bash
cp target/Fulldev-coins-1.0-SNAPSHOT.jar /seu-servidor/plugins/
```

### 3. Iniciar Servidor
```bash
# O banco de dados e config serão criados automaticamente
java -jar spigot-1.20.4.jar
```

### 4. Comandos
- `/coins` - Abrir GUI
- `/coins info` - Informações
- `/coins loja` - Abrir loja
- `/coins reload` - Recarregar config

---

## 🎮 Como Funciona

### Drop Automático
- **Frequência:** A cada 15 minutos (configurável)
- **Quantidade:** 1 coin por drop
- **Notificação:** Mensagem no chat

### Drop ao Matar Mobs
- **Chance:** 50% (configurável)
- **Quantidade:** 1-5 coins (configurável)
- **Mobs:** Todos os mobs hostis na lista

### Loja de Coins
- **Diamante:** 50 coins
- **Esmeralda:** 40 coins
- **Barra de Ouro (x5):** 30 coins
- **Barra de Ferro (x10):** 20 coins

---

## ⚙️ Configuração Básica

### Editar Intervalo de Drop Automático

Arquivo: `plugins/FulldevCoins/config.yml`

```yaml
coins-drop:
  interval-minutes: 15    # Mudar para outro valor
```

Depois recarregar: `/coins reload`

### Editar Chance de Drop ao Matar Mobs

```yaml
coins-drop:
  mob-drop:
    chance: 0.5           # 50% de chance (0.0 a 1.0)
    min-coins: 1          # Mínimo
    max-coins: 5          # Máximo
```

### Adicionar Novo Mob

```yaml
coins-drop:
  mob-drop:
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
      - PIGLIN_BRUTE    # Adicionar novo
      - WARDEN          # Adicionar novo
```

### Customizar Mensagem

```yaml
messages:
  coins-received: "§a[§6Fulldev§a] +§e%amount%§a moedas! Total: §6%total%"
  auto-reward: "§a[Fulldev] Você recebeu §e1 moeda§a automática!"
```

---

## 🛠️ Troubleshooting Rápido

### "Plugin não aparece"
```
1. Verificar Java: java -version
2. Deve ser Java 21+
3. Usar Spigot 1.20.4+ (não Bukkit)
4. Ver logs: tail -f logs/latest.log
```

### "Moedas não dropam"
```
1. Verificar se enabled: true em config
2. Verificar se mob está em allowed-mobs
3. Executar: /coins reload
4. Matar um mob e verificar
```

### "GUI não abre"
```
1. Executar: /coins help
2. Se erro aparecer, verificar logs
3. Tentar: /coins info
```

### "Banco de dados erro"
```
1. Verificar pasta: plugins/FulldevCoins/
2. Deve ter arquivo fulldev-coins.db
3. Se não tiver, é criado automaticamente
4. Verificar permissões: chmod 755 plugins/FulldevCoins/
```

---

## 📊 Informações de Banco de Dados

### Onde ficam os dados?
```
plugins/FulldevCoins/fulldev-coins.db
```

### O que é armazenado?
- UUID do player
- Nome do player
- Quantidade de coins
- Último login
- Total de coins ganhos
- Level
- Histórico de transações

### Fazer backup
```bash
cp plugins/FulldevCoins/fulldev-coins.db plugins/FulldevCoins/fulldev-coins.db.backup
```

---

## 🔗 Links Úteis

- **GitHub:** https://github.com/Ninzinhu/Fd-Coins.git
- **Documentation:** Veja README.md
- **Issues:** Abrir issue no GitHub

---

## 💡 Dicas de Performance

1. **Aumentar intervalo de drop automático** se servidor estiver lento
2. **Reduzir chance de mob-drop** se muitas moedas estiverem sendo criadas
3. **Usar SQLite** em vez de MySQL para servidores pequenos
4. **Limpar transações antigas** do banco periodicamente

---

## 🎮 Exemplo de Fluxo de Jogo

1. Player entra no servidor
   - Recebe boas-vindas
   - Banco de dados cria registro

2. Player mata um zombie
   - 40% de chance cai 1-5 coins
   - Moedas dropam no chão
   - Mensagem no chat

3. Player espera 15 minutos
   - Recebe 1 coin automático
   - Mensagem de notificação

4. Player digita `/coins`
   - GUI abre com informações
   - Mostra total de coins
   - Mostra último login

5. Player digita `/coins loja`
   - Abre loja de coins
   - Compra diamante por 50 coins
   - Item vai para inventário

---

## 📝 Notas Importantes

- ✅ Dados são salvos permanentemente no banco
- ✅ Cada transação é registrada
- ✅ Sistema é seguro contra exploits
- ✅ Config pode ser customizada totalmente
- ✅ Suporta múltiplos servidores (BD isolado)

---

## 🆘 Precisar de Ajuda?

1. Leia o README.md (documentação completa)
2. Verifique os logs do servidor
3. Tente `/coins reload`
4. Procure por issues similar no GitHub

---

**Desenvolvido com ❤️ por Konpeki Studios**

Versão: 1.0-SNAPSHOT | Data: 2026-03-29

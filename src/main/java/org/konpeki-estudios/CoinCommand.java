package org.konpeki_estudios;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.konpeki_estudios.database.DatabaseManager;
import org.konpeki_estudios.gui.CoinsGUI;
import org.konpeki_estudios.shop.ShopManager;

public class CoinCommand implements CommandExecutor {

    private final FulldevCoinsPlugin plugin;
    private final DatabaseManager database;
    private final ShopManager shopManager;

    public CoinCommand(FulldevCoinsPlugin plugin, DatabaseManager database, ShopManager shopManager) {
        this.plugin = plugin;
        this.database = database;
        this.shopManager = shopManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Verificar se é um player
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser usado por players!");
            return false;
        }

        Player player = (Player) sender;

        // Se não houver argumentos, abrir GUI
        if (args.length == 0) {
            openCoinsGUI(player);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "help":
                showHelp(player);
                return true;

            case "info":
                showInfo(player);
                return true;

            case "reload":
                if (!player.hasPermission("fulldevcoins.admin")) {
                    player.sendMessage("§cVocê não tem permissão!");
                    return false;
                }
                plugin.reloadConfig();
                player.sendMessage("§a✅ Configuração recarregada!");
                return true;

            case "loja":
            case "shop":
                shopManager.openShop(player);
                return true;

            case "give":
                if (!player.hasPermission("fulldevcoins.admin")) {
                    player.sendMessage("§cVocê não tem permissão!");
                    return false;
                }
                if (args.length < 3) {
                    player.sendMessage("§cUso: /coins give <player> <amount>");
                    return false;
                }
                // TODO: Implementar dar coins
                return true;

            default:
                player.sendMessage("§cComando desconhecido! Use /coins help");
                return false;
        }
    }

    /**
     * Abrir GUI de coins
     */
    private void openCoinsGUI(Player player) {
        // Obter dados do player do banco
        DatabaseManager.PlayerData playerData = database.getOrCreatePlayer(player.getUniqueId(), player.getName());

        if (playerData != null) {
            CoinsGUI.openCoinsGUI(player, playerData.getCoins(), playerData.getLastLogin());
        } else {
            player.sendMessage("§c❌ Erro ao carregar suas informações!");
        }
    }

    /**
     * Mostrar ajuda
     */
    private void showHelp(Player player) {
        player.sendMessage("");
        player.sendMessage("§6===== FulldevCoins - Ajuda =====");
        player.sendMessage("§7Comandos disponíveis:");
        player.sendMessage("§e/coins §7- Abre a GUI de coins");
        player.sendMessage("§e/coins info §7- Mostra informações");
        player.sendMessage("§e/coins loja §7- Abre a loja");
        player.sendMessage("§e/coins help §7- Mostra esta mensagem");
        if (player.hasPermission("fulldevcoins.admin")) {
            player.sendMessage("§e/coins reload §7- Recarrega configuração");
            player.sendMessage("§e/coins give <player> <amount> §7- Da coins a um player");
        }
        player.sendMessage("§6==================================");
        player.sendMessage("");
    }

    /**
     * Mostrar informações
     */
    private void showInfo(Player player) {
        DatabaseManager.PlayerData playerData = database.getOrCreatePlayer(player.getUniqueId(), player.getName());

        if (playerData == null) {
            player.sendMessage("§c❌ Erro ao carregar informações!");
            return;
        }

        player.sendMessage("");
        player.sendMessage("§6===== FulldevCoins - Informações =====");
        player.sendMessage("§7Player: §e" + playerData.getName());
        player.sendMessage("§7Moedas: §6" + CoinsGUI.formatNumber(playerData.getCoins()));
        player.sendMessage("§7Total ganho: §6" + CoinsGUI.formatNumber(playerData.getTotalCoinsEarned()));
        player.sendMessage("§7Level: §e" + playerData.getLevel());
        player.sendMessage("§7Último login: §7" + CoinsGUI.formatDate(playerData.getLastLogin()));
        player.sendMessage("§6========================================");
        player.sendMessage("");
    }
}

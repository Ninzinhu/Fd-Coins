package org.konpeki_estudios;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinCommand implements CommandExecutor {

    private final FulldevCoinsPlugin plugin;

    public CoinCommand(FulldevCoinsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Verificar se o sender é um player
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser usado por players!");
            return false;
        }

        Player player = (Player) sender;

        // Verificar permissão
        if (!player.hasPermission("fulldevcoins.admin")) {
            player.sendMessage("§cVocê não tem permissão para usar este comando!");
            return false;
        }

        // Se não houver argumentos, mostrar ajuda
        if (args.length == 0) {
            showHelp(player);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "reload":
                return reloadCommand(player);
            case "info":
                return infoCommand(player);
            case "help":
                showHelp(player);
                return true;
            default:
                player.sendMessage("§cComando desconhecido! Use /fulldevcoins help");
                return false;
        }
    }

    /**
     * Recarrega a configuração do plugin
     */
    private boolean reloadCommand(Player player) {
        plugin.reloadConfig();
        player.sendMessage("§a[FulldevCoins] Configuração recarregada com sucesso!");
        return true;
    }

    /**
     * Mostra informações do plugin
     */
    private boolean infoCommand(Player player) {
        player.sendMessage("");
        player.sendMessage("§6===== FulldevCoins =====");
        player.sendMessage("§7Versão: §e" + plugin.getDescription().getVersion());
        player.sendMessage("");
        player.sendMessage("§7Configurações atuais:");
        player.sendMessage("§7- Drop Chance: §e" + plugin.getConfig().getDouble("drop-chance", 0.5) * 100 + "%");
        player.sendMessage("§7- Min Coins: §e" + plugin.getConfig().getInt("min-coins", 1));
        player.sendMessage("§7- Max Coins: §e" + plugin.getConfig().getInt("max-coins", 5));
        player.sendMessage("§7- Status: §e" + (plugin.getConfig().getBoolean("enabled", true) ? "Ativado" : "Desativado"));
        player.sendMessage("§6=======================");
        player.sendMessage("");
        return true;
    }

    /**
     * Mostra a ajuda do plugin
     */
    private void showHelp(Player player) {
        player.sendMessage("");
        player.sendMessage("§6===== FulldevCoins - Ajuda =====");
        player.sendMessage("§7Comandos disponíveis:");
        player.sendMessage("§e/fulldevcoins help §7- Mostra esta mensagem");
        player.sendMessage("§e/fulldevcoins info §7- Mostra informações do plugin");
        player.sendMessage("§e/fulldevcoins reload §7- Recarrega a configuração");
        player.sendMessage("§6==================================");
        player.sendMessage("");
    }
}

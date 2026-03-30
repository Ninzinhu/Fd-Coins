package org.konpeki_estudios;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.konpeki_estudios.database.DatabaseManager;
import org.konpeki_estudios.gui.CoinsGUI;
import org.konpeki_estudios.shop.ShopManager;

public class GUIListener implements Listener {

    private final FulldevCoinsPlugin plugin;
    private final DatabaseManager database;
    private final ShopManager shopManager;

    public GUIListener(FulldevCoinsPlugin plugin, DatabaseManager database, ShopManager shopManager) {
        this.plugin = plugin;
        this.database = database;
        this.shopManager = shopManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Verificar se é um player
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        // GUI de FulldevCoins
        if (title.equals("§6FulldevCoins")) {
            event.setCancelled(true);

            int slot = event.getRawSlot();

            // Botão de loja (slot 29)
            if (slot == 29) {
                shopManager.openShop(player);
            }

            // Botão de Discord (slot 33)
            else if (slot == 33) {
                String discordLink = plugin.getConfig().getString("links.discord", "https://discord.gg/seu-servidor");
                player.sendMessage("§5Discord: §7" + discordLink);
            }

            // Botão de Website (slot 37)
            else if (slot == 37) {
                String website = plugin.getConfig().getString("links.website", "https://seu-website.com");
                player.sendMessage("§bWebsite: §7" + website);
            }

            // Botão de fechar (slot 44)
            else if (slot == 44) {
                player.closeInventory();
            }

            // Cabeça do player (slot 22) - atualizar
            else if (slot == 22) {
                DatabaseManager.PlayerData playerData = database.getOrCreatePlayer(player.getUniqueId(), player.getName());
                if (playerData != null) {
                    CoinsGUI.openCoinsGUI(player, playerData.getCoins(), playerData.getLastLogin());
                }
            }
        }

        // GUI de Loja
        else if (title.equals("§6Loja de Coins")) {
            event.setCancelled(true);

            int slot = event.getRawSlot();
            ItemStack clicked = event.getCurrentItem();

            if (clicked == null || clicked.getType() == Material.BLACK_STAINED_GLASS_PANE) {
                return;
            }

            // Diamante (slot 11)
            if (slot == 11) {
                shopManager.buyItem(player, "Diamante", Material.DIAMOND, 50, 1);
                shopManager.openShop(player); // Reabrir loja
            }

            // Esmeralda (slot 13)
            else if (slot == 13) {
                shopManager.buyItem(player, "Esmeralda", Material.EMERALD, 40, 1);
                shopManager.openShop(player);
            }

            // Barra de Ouro (slot 15)
            else if (slot == 15) {
                shopManager.buyItem(player, "Barra de Ouro (x5)", Material.GOLD_INGOT, 30, 5);
                shopManager.openShop(player);
            }

            // Barra de Ferro (slot 20)
            else if (slot == 20) {
                shopManager.buyItem(player, "Barra de Ferro (x10)", Material.IRON_INGOT, 20, 10);
                shopManager.openShop(player);
            }

            // Botão de voltar (slot 26)
            else if (slot == 26) {
                DatabaseManager.PlayerData playerData = database.getOrCreatePlayer(player.getUniqueId(), player.getName());
                if (playerData != null) {
                    CoinsGUI.openCoinsGUI(player, playerData.getCoins(), playerData.getLastLogin());
                }
            }
        }
    }
}

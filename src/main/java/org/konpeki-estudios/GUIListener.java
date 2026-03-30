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
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        // ============ GUI PRINCIPAL ============
        if (title.equals("§6§l☆ FulldevCoins ☆")) {
            event.setCancelled(true);
            int slot = event.getRawSlot();

            // Validar slot válido
            if (slot < 0 || slot >= 54) return;

            // Botão de Loja (slot 29)
            if (slot == 29) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
                shopManager.openShop(player);
            }

            // Botão de Discord (slot 31)
            else if (slot == 31) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
                String discordLink = plugin.getConfig().getString("links.discord", "https://discord.gg/seu-servidor");
                player.sendMessage(" ");
                player.sendMessage("§5§l【 DISCORD 】");
                player.sendMessage("§7Clique para copiar: §a" + discordLink);
                player.sendMessage(" ");
            }

            // Botão de Website (slot 33)
            else if (slot == 33) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
                String website = plugin.getConfig().getString("links.website", "https://seu-website.com");
                player.sendMessage(" ");
                player.sendMessage("§9§l【 WEBSITE 】");
                player.sendMessage("§7Clique para copiar: §a" + website);
                player.sendMessage(" ");
            }

            // Botão de Fechar (slot 35)
            else if (slot == 35) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                player.closeInventory();
                player.sendMessage("§7Você fechou o menu de coins.");
            }

            // Cabeça do player (slot 10) - atualizar GUI
            else if (slot == 10) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
                DatabaseManager.PlayerData playerData = database.getOrCreatePlayer(player.getUniqueId(), player.getName());
                if (playerData != null) {
                    CoinsGUI.openCoinsGUI(player, playerData.getCoins(), playerData.getLastLogin());
                }
            }
        }

        // ============ GUI DA LOJA ============
        else if (title.equals("§6§l💰 LOJA DE COINS 💰")) {
            event.setCancelled(true);
            int slot = event.getRawSlot();

            if (slot < 0 || slot >= 54) return;

            // Diamante (slot 29)
            if (slot == 29) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
                shopManager.buyItem(player, "Diamante", Material.DIAMOND, 50, 1);
            }

            // Esmeralda (slot 31)
            else if (slot == 31) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
                shopManager.buyItem(player, "Esmeralda", Material.EMERALD, 40, 1);
            }

            // Ouro (slot 33)
            else if (slot == 33) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
                shopManager.buyItem(player, "Barra de Ouro", Material.GOLD_INGOT, 30, 5);
            }

            // Ferro (slot 35)
            else if (slot == 35) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.2f);
                shopManager.buyItem(player, "Barra de Ferro", Material.IRON_INGOT, 20, 10);
            }

            // Botão Voltar (slot 47)
            else if (slot == 47) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                DatabaseManager.PlayerData playerData = database.getOrCreatePlayer(player.getUniqueId(), player.getName());
                if (playerData != null) {
                    CoinsGUI.openCoinsGUI(player, playerData.getCoins(), playerData.getLastLogin());
                }
            }

            // Botão Fechar (slot 51)
            else if (slot == 51) {
                player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                player.closeInventory();
                player.sendMessage("§7Você fechou a loja.");
            }
        }
    }
}

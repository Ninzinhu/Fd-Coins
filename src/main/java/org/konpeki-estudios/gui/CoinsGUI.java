package org.konpeki_estudios.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoinsGUI {

    /**
     * Abrir GUI de coins para o player
     */
    public static void openCoinsGUI(Player player, long coins, long lastLogin) {
        Inventory gui = Bukkit.createInventory(null, 45, "§6FulldevCoins");

        // Preenchimento com vidro preto
        for (int i = 0; i < 45; i++) {
            gui.setItem(i, createGlassPane());
        }

        // Cabeça do player no centro (slot 22)
        gui.setItem(22, createPlayerHead(player));

        // Informações do player
        gui.setItem(11, createInfoItem(player, coins, lastLogin));

        // Botão de loja
        gui.setItem(29, createShopButton());

        // Botão de Discord
        gui.setItem(33, createDiscordButton());

        // Botão de Website
        gui.setItem(37, createWebsiteButton());

        // Botão de fechar
        gui.setItem(44, createCloseButton());

        player.openInventory(gui);
    }

    /**
     * Criar vidro preto para preenchimento
     */
    private static ItemStack createGlassPane() {
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            glass.setItemMeta(meta);
        }
        return glass;
    }

    /**
     * Criar cabeça do player
     */
    private static ItemStack createPlayerHead(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta != null) {
            meta.setOwningPlayer(player);
            meta.setDisplayName("§6" + player.getName());

            List<String> lore = new ArrayList<>();
            lore.add("§7UUID: §e" + player.getUniqueId().toString().substring(0, 8) + "...");
            lore.add("");
            lore.add("§7Clique para atualizar");

            meta.setLore(lore);
            head.setItemMeta(meta);
        }

        return head;
    }

    /**
     * Criar item de informações do player
     */
    private static ItemStack createInfoItem(Player player, long coins, long lastLogin) {
        ItemStack info = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = info.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§6Informações do Player");

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§eMoedas: §6" + formatNumber(coins));
            lore.add("");
            lore.add("§eÚltimo Login: §7" + formatDate(lastLogin));
            lore.add("");
            lore.add("§7Ganhe moedas:");
            lore.add("§7• Matando mobs");
            lore.add("§7• Login automático (a cada 15 min)");
            lore.add("");
            lore.add("§eClique para abrir a loja!");

            meta.setLore(lore);
            info.setItemMeta(meta);
        }

        return info;
    }

    /**
     * Criar botão de loja
     */
    private static ItemStack createShopButton() {
        ItemStack shop = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = shop.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§aLoja de Coins");

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§7Troque seus coins por items!");
            lore.add("");
            lore.add("§e• Diamante - 50 moedas");
            lore.add("§e• Esmeralda - 40 moedas");
            lore.add("§e• Barra de Ouro - 30 moedas");
            lore.add("§e• Barra de Ferro - 20 moedas");
            lore.add("");
            lore.add("§aClique para abrir!");

            meta.setLore(lore);
            shop.setItemMeta(meta);
        }

        return shop;
    }

    /**
     * Criar botão de Discord
     */
    private static ItemStack createDiscordButton() {
        ItemStack discord = new ItemStack(Material.PURPLE_CONCRETE);
        ItemMeta meta = discord.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§5Discord");

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§7Junte-se ao nosso servidor!");
            lore.add("§7discord.gg/seu-servidor");
            lore.add("");
            lore.add("§eClique para copiar o link");

            meta.setLore(lore);
            discord.setItemMeta(meta);
        }

        return discord;
    }

    /**
     * Criar botão de Website
     */
    private static ItemStack createWebsiteButton() {
        ItemStack website = new ItemStack(Material.BLUE_CONCRETE);
        ItemMeta meta = website.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§bWebsite");

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§7Visite nosso website!");
            lore.add("§7www.seu-website.com");
            lore.add("");
            lore.add("§eClique para copiar o link");

            meta.setLore(lore);
            website.setItemMeta(meta);
        }

        return website;
    }

    /**
     * Criar botão de fechar
     */
    private static ItemStack createCloseButton() {
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta meta = close.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§cFechar");
            close.setItemMeta(meta);
        }

        return close;
    }

    /**
     * Formatar número com separador de milhares
     */
    public static String formatNumber(long number) {
        return String.format("%,d", number).replace(",", ".");
    }

    /**
     * Formatar data em formato legível
     */
    public static String formatDate(long timestamp) {
        if (timestamp == 0) {
            return "Nunca";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date(timestamp));
    }
}

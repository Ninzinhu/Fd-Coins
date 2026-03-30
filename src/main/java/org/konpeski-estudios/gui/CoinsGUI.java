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

    public static void openCoinsGUI(Player player, long coins, long lastLogin, long joinDate) {
        Inventory gui = Bukkit.createInventory(null, 27, "§6★ Meu Saldo ★");

        for (int i = 0; i < 9; i++) {
            gui.setItem(i, createGlassPane());
        }

        gui.setItem(9, createPlayerHead(player, joinDate));
        gui.setItem(10, createCoinsDisplay(coins));
        gui.setItem(11, createLastLoginDisplay(lastLogin));
        gui.setItem(12, createMemberSinceDisplay(joinDate));

        for (int i = 13; i < 18; i++) {
            gui.setItem(i, createGlassPane());
        }

        gui.setItem(18, createLoja());
        gui.setItem(19, createGlassPane());
        gui.setItem(20, createDiscordButton());
        gui.setItem(21, createGlassPane());
        gui.setItem(22, createWebsiteButton());
        gui.setItem(23, createGlassPane());
        gui.setItem(24, createCloseButton());
        gui.setItem(25, createGlassPane());
        gui.setItem(26, createGlassPane());

        player.openInventory(gui);
    }

    private static ItemStack createGlassPane() {
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            glass.setItemMeta(meta);
        }
        return glass;
    }

    private static ItemStack createPlayerHead(Player player, long joinDate) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta != null) {
            meta.setOwningPlayer(player);
            meta.setDisplayName("§e" + player.getName());

            List<String> lore = new ArrayList<>();
            lore.add("§7Nível: §6" + player.getLevel());
            lore.add("§7Saúde: §c" + (int) player.getHealth() + "§7/§c20");

            meta.setLore(lore);
            head.setItemMeta(meta);
        }

        return head;
    }

    private static ItemStack createCoinsDisplay(long coins) {
        ItemStack item = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§6Moedas");

            List<String> lore = new ArrayList<>();
            lore.add("§e" + formatNumber(coins));

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    private static ItemStack createLastLoginDisplay(long lastLogin) {
        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§bÚltimo Login");

            List<String> lore = new ArrayList<>();
            lore.add("§b" + formatDate(lastLogin));

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    private static ItemStack createMemberSinceDisplay(long joinDate) {
        ItemStack item = new ItemStack(Material.CALENDAR);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§dMembro Desde");

            List<String> lore = new ArrayList<>();
            lore.add("§d" + formatDate(joinDate));

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    private static ItemStack createLoja() {
        ItemStack loja = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = loja.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§a🏪 Loja");
            loja.setItemMeta(meta);
        }

        return loja;
    }

    private static ItemStack createDiscordButton() {
        ItemStack discord = new ItemStack(Material.PURPLE_CONCRETE);
        ItemMeta meta = discord.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§5💬 Discord");
            discord.setItemMeta(meta);
        }

        return discord;
    }

    private static ItemStack createWebsiteButton() {
        ItemStack website = new ItemStack(Material.BLUE_CONCRETE);
        ItemMeta meta = website.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§9🌐 Website");
            website.setItemMeta(meta);
        }

        return website;
    }

    private static ItemStack createCloseButton() {
        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = close.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§c✖ Fechar");
            close.setItemMeta(meta);
        }

        return close;
    }

    public static String formatNumber(long number) {
        return String.format("%,d", number).replace(",", ".");
    }

    public static String formatDate(long timestamp) {
        if (timestamp == 0) {
            return "Nunca";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date(timestamp));
    }
}

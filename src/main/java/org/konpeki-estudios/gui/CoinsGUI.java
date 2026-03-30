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
        Inventory gui = Bukkit.createInventory(null, 54, "§6§l☆ FulldevCoins ☆");

        // ============ LINHA 1: Barra decorativa ============
        for (int i = 0; i < 9; i++) {
            gui.setItem(i, createDecorativeBlock());
        }

        // ============ LINHA 2-3: Informações do Player ============
        // Cabeça do player (slot 10)
        gui.setItem(10, createPlayerHead(player));

        // Info do player (slots 11-16)
        gui.setItem(11, createInfoItem("§e§lMOEDAS", coins));
        gui.setItem(12, createGoldBlock());
        gui.setItem(13, createLevelItem(1)); // Implementar level depois
        gui.setItem(14, createPlayTimeItem(lastLogin));
        gui.setItem(15, createGoldBlock());
        gui.setItem(16, createDecorativeBlock());

        // Linha 3 - Decoração
        for (int i = 18; i < 27; i++) {
            if (i == 20 || i == 24) {
                gui.setItem(i, createDecorativeBlock());
            } else {
                gui.setItem(i, createGlassPane());
            }
        }

        // ============ LINHA 4: Botões de Ações ============
        gui.setItem(29, createLoja());        // Loja
        gui.setItem(30, createGoldBlock());   // Separador
        gui.setItem(31, createDiscordButton());
        gui.setItem(32, createGoldBlock());   // Separador
        gui.setItem(33, createWebsiteButton());
        gui.setItem(34, createGoldBlock());   // Separador
        gui.setItem(35, createCloseButton());

        // ============ LINHA 5: Decoração ============
        for (int i = 36; i < 45; i++) {
            gui.setItem(i, createGlassPane());
        }

        // ============ LINHA 6: Barra decorativa final ============
        for (int i = 45; i < 54; i++) {
            gui.setItem(i, createDecorativeBlock());
        }

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
     * Criar bloco decorativo
     */
    private static ItemStack createDecorativeBlock() {
        ItemStack block = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = block.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            block.setItemMeta(meta);
        }
        return block;
    }

    /**
     * Criar bloco de ouro simples
     */
    private static ItemStack createGoldBlock() {
        ItemStack block = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = block.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            block.setItemMeta(meta);
        }
        return block;
    }

    /**
     * Criar cabeça do player
     */
    private static ItemStack createPlayerHead(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta != null) {
            meta.setOwningPlayer(player);
            meta.setDisplayName("§e§l" + player.getName());

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Clique para atualizar");
            lore.add(" ");

            meta.setLore(lore);
            head.setItemMeta(meta);
        }

        return head;
    }

    /**
     * Item de moedas
     */
    private static ItemStack createInfoItem(String name, long coins) {
        ItemStack info = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = info.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§6" + formatNumber(coins));
            lore.add(" ");

            meta.setLore(lore);
            info.setItemMeta(meta);
        }

        return info;
    }

    /**
     * Item de level
     */
    private static ItemStack createLevelItem(int level) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§d§lLEVEL");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§d" + level);
            lore.add(" ");

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Item de tempo de jogo
     */
    private static ItemStack createPlayTimeItem(long lastLogin) {
        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§b§lÚLTIMO LOGIN");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§b" + formatDate(lastLogin));
            lore.add(" ");

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Criar botão de loja
     */
    private static ItemStack createLoja() {
        ItemStack loja = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = loja.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§a§l🏪 LOJA");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Troque coins por items");
            lore.add(" ");
            lore.add("§e• Diamante - 50 moedas");
            lore.add("§e• Esmeralda - 40 moedas");
            lore.add("§e• Ouro - 30 moedas");
            lore.add("§e• Ferro - 20 moedas");
            lore.add(" ");
            lore.add("§a⚡ CLIQUE PARA ABRIR ⚡");
            lore.add(" ");

            meta.setLore(lore);
            loja.setItemMeta(meta);
        }

        return loja;
    }

    /**
     * Criar botão de Discord
     */
    private static ItemStack createDiscordButton() {
        ItemStack discord = new ItemStack(Material.PURPLE_CONCRETE);
        ItemMeta meta = discord.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§5§l💬 DISCORD");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Junte-se ao nosso servidor!");
            lore.add("§7discord.gg/seu-servidor");
            lore.add(" ");
            lore.add("§5⚡ CLIQUE PARA COPIAR ⚡");
            lore.add(" ");

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
            meta.setDisplayName("§9§l🌐 WEBSITE");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Visite nosso website!");
            lore.add("§7www.seu-website.com");
            lore.add(" ");
            lore.add("§9⚡ CLIQUE PARA COPIAR ⚡");
            lore.add(" ");

            meta.setLore(lore);
            website.setItemMeta(meta);
        }

        return website;
    }

    /**
     * Criar botão de fechar
     */
    private static ItemStack createCloseButton() {
        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = close.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§c§l✖ FECHAR");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Clique para fechar");
            lore.add(" ");

            meta.setLore(lore);
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

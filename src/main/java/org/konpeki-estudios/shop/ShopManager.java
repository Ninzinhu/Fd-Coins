package org.konpeki_estudios.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.konpeki_estudios.FulldevCoinsPlugin;
import org.konpeki_estudios.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class ShopManager {
    private final FulldevCoinsPlugin plugin;
    private final DatabaseManager database;

    public ShopManager(FulldevCoinsPlugin plugin, DatabaseManager database) {
        this.plugin = plugin;
        this.database = database;
    }

    /**
     * Abrir loja de coins para o player
     */
    public void openShop(Player player) {
        Inventory shop = Bukkit.createInventory(null, 54, "§6§l💰 LOJA DE COINS 💰");

        // ============ LINHA 1: Barra decorativa ============
        for (int i = 0; i < 9; i++) {
            shop.setItem(i, createDecorativeBlock());
        }

        // ============ LINHA 2-3: Info do player + Saldo ============
        shop.setItem(10, createGoldBlock());
        shop.setItem(11, createBalanceItem(player));
        shop.setItem(12, createGoldBlock());
        shop.setItem(13, createGoldBlock());
        shop.setItem(14, createGoldBlock());
        shop.setItem(15, createGoldBlock());
        shop.setItem(16, createGoldBlock());
        shop.setItem(17, createGoldBlock());

        // Linha vazia
        for (int i = 18; i < 27; i++) {
            shop.setItem(i, createGlassPane());
        }

        // ============ LINHA 4: Items da Loja ============
        shop.setItem(29, createShopItemStack(Material.DIAMOND, "§b§lDIAMANTE", 1, 50));
        shop.setItem(31, createShopItemStack(Material.EMERALD, "§2§lESMERALDA", 1, 40));
        shop.setItem(33, createShopItemStack(Material.GOLD_INGOT, "§6§lOURO", 5, 30));
        shop.setItem(35, createShopItemStack(Material.IRON_INGOT, "§7§lFERRO", 10, 20));

        // Separadores
        shop.setItem(30, createGoldBlock());
        shop.setItem(32, createGoldBlock());
        shop.setItem(34, createGoldBlock());

        // ============ LINHA 5: Decoração ============
        for (int i = 36; i < 45; i++) {
            shop.setItem(i, createGlassPane());
        }

        // ============ LINHA 6: Botões de volta e info ============
        shop.setItem(45, createDecorativeBlock());
        shop.setItem(46, createGoldBlock());
        shop.setItem(47, createBackButton());
        shop.setItem(48, createGoldBlock());
        shop.setItem(49, createInfoButton());
        shop.setItem(50, createGoldBlock());
        shop.setItem(51, createCloseButton());
        shop.setItem(52, createGoldBlock());
        shop.setItem(53, createDecorativeBlock());

        player.openInventory(shop);
    }

    /**
     * Comprar item da loja
     */
    public void buyItem(Player player, String itemName, Material material, long price, int quantity) {
        long playerCoins = database.getCoins(player.getUniqueId());

        if (playerCoins < price) {
            player.sendMessage("§c❌ Você não tem moedas suficientes!");
            player.sendMessage("§cVocê precisa de §6" + (price - playerCoins) + "§c moedas a mais.");
            player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.5f);
            return;
        }

        // Remover coins
        if (!database.removeCoins(player.getUniqueId(), price, "Compra na loja: " + itemName)) {
            player.sendMessage("§c❌ Erro ao processar a compra!");
            return;
        }

        // Adicionar item ao inventário
        ItemStack item = new ItemStack(material, quantity);
        player.getInventory().addItem(item);

        // Notificar sucesso
        player.sendMessage("§a✅ Compra realizada com sucesso!");
        player.sendMessage("§aVocê comprou: §e" + itemName + " §apor §6" + price + "§a moedas");
        player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);

        // Reabrir loja
        openShop(player);
    }

    /**
     * Criar item de shop com preço
     */
    private ItemStack createShopItemStack(Material material, String name, int quantity, long price) {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Quantidade: §e" + quantity);
            lore.add("§7Preço: §6" + price + " moedas");
            lore.add(" ");
            lore.add("§a⚡ CLIQUE PARA COMPRAR ⚡");
            lore.add(" ");

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Item de saldo
     */
    private ItemStack createBalanceItem(Player player) {
        long coins = database.getCoins(player.getUniqueId());
        ItemStack item = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§6§lSEU SALDO");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§e" + formatNumber(coins) + " moedas");
            lore.add(" ");

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Botão de informações
     */
    private ItemStack createInfoButton() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§b§l📖 INFORMAÇÕES");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Clique nos items para");
            lore.add("§7comprar com suas moedas!");
            lore.add(" ");

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Botão de volta
     */
    private ItemStack createBackButton() {
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta meta = back.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§c§l← VOLTAR");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Voltar ao menu principal");
            lore.add(" ");

            meta.setLore(lore);
            back.setItemMeta(meta);
        }

        return back;
    }

    /**
     * Botão de fechar
     */
    private ItemStack createCloseButton() {
        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = close.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§c§l✖ FECHAR");

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Fechar a loja");
            lore.add(" ");

            meta.setLore(lore);
            close.setItemMeta(meta);
        }

        return close;
    }

    /**
     * Bloco decorativo
     */
    private ItemStack createDecorativeBlock() {
        ItemStack block = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = block.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            block.setItemMeta(meta);
        }
        return block;
    }

    /**
     * Bloco de ouro
     */
    private ItemStack createGoldBlock() {
        ItemStack block = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = block.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            block.setItemMeta(meta);
        }
        return block;
    }

    /**
     * Vidro preto
     */
    private ItemStack createGlassPane() {
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            glass.setItemMeta(meta);
        }
        return glass;
    }

    /**
     * Formatar números
     */
    private String formatNumber(long number) {
        return String.format("%,d", number).replace(",", ".");
    }
}

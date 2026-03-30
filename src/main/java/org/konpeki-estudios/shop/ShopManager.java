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
        Inventory shop = Bukkit.createInventory(null, 27, "§6Loja de Coins");

        // Vidro preto para preenchimento
        for (int i = 0; i < 27; i++) {
            shop.setItem(i, createGlassPane());
        }

        // Item 1: Diamante
        shop.setItem(11, createShopItem(Material.DIAMOND, "§bDiamante",
            new String[]{"§7Preço: §650 moedas", "", "§eClique para comprar!"}));

        // Item 2: Esmeralda
        shop.setItem(13, createShopItem(Material.EMERALD, "§2Esmeralda",
            new String[]{"§7Preço: §640 moedas", "", "§eClique para comprar!"}));

        // Item 3: Barra de Ouro
        shop.setItem(15, createShopItem(Material.GOLD_INGOT, "§6Barra de Ouro (x5)",
            new String[]{"§7Preço: §630 moedas", "", "§eClique para comprar!"}));

        // Item 4: Barra de Ferro
        ItemStack iron = createShopItem(Material.IRON_INGOT, "§7Barra de Ferro (x10)",
            new String[]{"§7Preço: §620 moedas", "", "§eClique para comprar!"});
        iron.setAmount(10);
        shop.setItem(20, iron);

        // Botão de voltar
        shop.setItem(26, createBackButton());

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
    }

    /**
     * Criar item de loja
     */
    private ItemStack createShopItem(Material material, String name, String[] lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);

            List<String> loreList = new ArrayList<>();
            loreList.add("");
            for (String line : lore) {
                loreList.add(line);
            }

            meta.setLore(loreList);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Criar vidro preto
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
     * Criar botão de voltar
     */
    private ItemStack createBackButton() {
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta meta = back.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§cVoltar");
            back.setItemMeta(meta);
        }

        return back;
    }
}

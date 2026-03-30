package org.konpeki_estudios;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.konpeki_estudios.database.DatabaseManager;
import org.konpeki_estudios.gui.CoinsGUI;

import java.util.Random;

public class MobDeathListener implements Listener {

    private final FulldevCoinsPlugin plugin;
    private final DatabaseManager database;
    private final Random random = new Random();

    public MobDeathListener(FulldevCoinsPlugin plugin, DatabaseManager database) {
        this.plugin = plugin;
        this.database = database;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();

        // Verificar se é um mob e se foi morto por um player
        if (!(entity instanceof Mob) || killer == null) {
            return;
        }

        // Verificar se o plugin está habilitado
        if (!plugin.getConfig().getBoolean("enabled", true)) {
            return;
        }

        // Verificar se o drop de mobs está habilitado
        if (!plugin.getConfig().getBoolean("coins-drop.mob-drop.enabled", true)) {
            return;
        }

        // Verificar se o mob está na lista de permitidos
        if (!isAllowedMob(entity.getType().name())) {
            return;
        }

        // Verificar chance de dropar
        double dropChance = plugin.getConfig().getDouble("coins-drop.mob-drop.chance", 0.5);
        if (random.nextDouble() > dropChance) {
            return;
        }

        // Calcular quantidade de moedas
        int minCoins = plugin.getConfig().getInt("coins-drop.mob-drop.min-coins", 1);
        int maxCoins = plugin.getConfig().getInt("coins-drop.mob-drop.max-coins", 5);
        int amount = minCoins + random.nextInt(maxCoins - minCoins + 1);

        // Adicionar coins ao banco de dados
        database.addCoins(killer.getUniqueId(), amount, "Matou mob: " + entity.getType().name());

        // Dropar a moeda
        dropCoin(killer, entity, amount);

        // Enviar mensagem no chat
        String message = plugin.getConfig().getString("messages.coins-received",
            "§a[§6Fulldev§a] +§e%amount%§a moeda(s)! Total: §6%total%");
        message = message.replace("%amount%", String.valueOf(amount));
        message = message.replace("%total%", String.valueOf(database.getCoins(killer.getUniqueId())));
        killer.sendMessage(message);
    }

    /**
     * Verifica se o mob está na lista de permitidos
     */
    private boolean isAllowedMob(String mobType) {
        java.util.List<String> allowedMobs = plugin.getConfig().getStringList("coins-drop.mob-drop.allowed-mobs");

        // Se a lista está vazia, permite todos
        if (allowedMobs.isEmpty()) {
            return true;
        }

        return allowedMobs.contains(mobType);
    }

    /**
     * Dropa a moeda para o player
     */
    private void dropCoin(Player player, LivingEntity mob, int amount) {
        // Criar um item de moeda (usando GOLD_NUGGET como representação)
        ItemStack coin = createCoinItem(amount);

        // Dropar a moeda no local onde o mob morreu
        mob.getWorld().dropItemNaturally(mob.getLocation(), coin);
    }

    /**
     * Cria um item de moeda personalizado
     */
    private ItemStack createCoinItem(int amount) {
        ItemStack coin = new ItemStack(Material.GOLD_NUGGET, amount);

        // Personalizar o nome da moeda
        org.bukkit.inventory.meta.ItemMeta meta = coin.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6FulldevCoin");
            coin.setItemMeta(meta);
        }

        return coin;
    }
}

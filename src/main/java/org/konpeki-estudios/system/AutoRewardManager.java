package org.konpeki_estudios.system;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.konpeki_estudios.FulldevCoinsPlugin;
import org.konpeki_estudios.database.DatabaseManager;

import java.util.*;

public class AutoRewardManager {
    private final FulldevCoinsPlugin plugin;
    private final DatabaseManager database;
    private BukkitTask task;
    private final Map<UUID, Long> lastRewardTime = new HashMap<>();

    public AutoRewardManager(FulldevCoinsPlugin plugin, DatabaseManager database) {
        this.plugin = plugin;
        this.database = database;
    }

    /**
     * Iniciar sistema de recompensas automáticas
     */
    public void start() {
        // Pegar intervalo da configuração (em minutos)
        int intervalMinutes = plugin.getConfig().getInt("coins-drop.interval-minutes", 15);
        long tickInterval = intervalMinutes * 60L * 20L; // Converter para ticks (20 ticks = 1 segundo)

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            rewardOnlinePlayers();
        }, 0, tickInterval);

        plugin.getLogger().info("✅ Sistema de recompensas automáticas iniciado (a cada " + intervalMinutes + " minutos)");
    }

    /**
     * Parar sistema de recompensas automáticas
     */
    public void stop() {
        if (task != null) {
            task.cancel();
            plugin.getLogger().info("⏹️ Sistema de recompensas automáticas parado");
        }
    }

    /**
     * Recompensar todos os players online
     */
    private void rewardOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            rewardPlayer(player);
        }
    }

    /**
     * Recompensar um player específico
     */
    public void rewardPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        long coinsPerDrop = plugin.getConfig().getLong("coins-drop.coins-per-drop", 1);

        // Adicionar coins
        database.addCoins(uuid, coinsPerDrop, "Auto-reward");

        // Atualizar tempo
        lastRewardTime.put(uuid, System.currentTimeMillis());

        // Notificar player
        String message = plugin.getConfig().getString("messages.auto-reward",
            "§a[Fulldev] Você recebeu §e1 moeda§a automática!");
        player.sendMessage(message);
    }

    /**
     * Obter tempo até próxima recompensa para um player
     */
    public String getTimeUntilNextReward(Player player) {
        UUID uuid = player.getUniqueId();
        long lastTime = lastRewardTime.getOrDefault(uuid, System.currentTimeMillis());
        int intervalMinutes = plugin.getConfig().getInt("coins-drop.interval-minutes", 15);
        long nextRewardTime = lastTime + (intervalMinutes * 60 * 1000L);

        long timeLeft = nextRewardTime - System.currentTimeMillis();

        if (timeLeft <= 0) {
            return "§aPronto agora!";
        }

        long minutes = timeLeft / (60 * 1000);
        long seconds = (timeLeft % (60 * 1000)) / 1000;

        return String.format("§e%02d:%02d", minutes, seconds);
    }
}

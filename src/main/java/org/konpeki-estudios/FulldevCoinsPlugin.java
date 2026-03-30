package org.konpeki_estudios;

import org.bukkit.plugin.java.JavaPlugin;

public class FulldevCoinsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Salvar a configuração padrão
        saveDefaultConfig();

        // Registrar o listener de eventos
        getServer().getPluginManager().registerEvents(new MobDeathListener(this), this);

        // Registrar o comando
        getCommand("fulldevcoins").setExecutor(new CoinCommand(this));

        getLogger().info("§a[FulldevCoins] Plugin ativado com sucesso!");
        getLogger().info("§a[FulldevCoins] Versão: " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        getLogger().info("§c[FulldevCoins] Plugin desativado!");
    }
}

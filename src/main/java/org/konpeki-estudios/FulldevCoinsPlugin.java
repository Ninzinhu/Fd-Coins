package org.konpeki_estudios;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.konpeki_estudios.database.DatabaseManager;
import org.konpeki_estudios.shop.ShopManager;
import org.konpeki_estudios.system.AutoRewardManager;

import java.io.File;

public class FulldevCoinsPlugin extends JavaPlugin implements Listener {

    private DatabaseManager database;
    private AutoRewardManager autoRewardManager;
    private ShopManager shopManager;

    @Override
    public void onEnable() {
        // Salvar a configuração padrão
        saveDefaultConfig();

        // Inicializar banco de dados
        File dbFile = new File(getDataFolder(), "fulldev-coins.db");
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        database = new DatabaseManager(dbFile.getAbsolutePath());
        database.connect();

        // Inicializar gerenciador de loja
        shopManager = new ShopManager(this, database);

        // Inicializar sistema de recompensas automáticas
        autoRewardManager = new AutoRewardManager(this, database);
        autoRewardManager.start();

        // Registrar o listener de eventos
        getServer().getPluginManager().registerEvents(new MobDeathListener(this, database), this);
        getServer().getPluginManager().registerEvents(new GUIListener(this, database, shopManager), this);

        // Registrar os comandos
        getCommand("coins").setExecutor(new CoinCommand(this, database, shopManager));
        getCommand("fulldevcoins").setExecutor(new CoinCommand(this, database, shopManager));

        getLogger().info("§a[FulldevCoins] Plugin ativado com sucesso!");
        getLogger().info("§a[FulldevCoins] Versão: " + getDescription().getVersion());
        getLogger().info("§a[FulldevCoins] Banco de dados: " + (database != null ? "Conectado" : "Erro"));
    }

    @Override
    public void onDisable() {
        // Parar sistema de recompensas
        if (autoRewardManager != null) {
            autoRewardManager.stop();
        }

        // Desconectar banco de dados
        if (database != null) {
            database.disconnect();
        }

        getLogger().info("§c[FulldevCoins] Plugin desativado!");
    }

    /**
     * Obter instância do DatabaseManager
     */
    public DatabaseManager getDatabase() {
        return database;
    }

    /**
     * Obter instância do AutoRewardManager
     */
    public AutoRewardManager getAutoRewardManager() {
        return autoRewardManager;
    }

    /**
     * Obter instância do ShopManager
     */
    public ShopManager getShopManager() {
        return shopManager;
    }
}

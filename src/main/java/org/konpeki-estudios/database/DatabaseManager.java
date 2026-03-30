package org.konpeki_estudios.database;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Logger;

public class DatabaseManager {
    private Connection connection;
    private String databasePath;
    private static final Logger logger = Logger.getLogger("FulldevCoins");

    public DatabaseManager(String databasePath) {
        this.databasePath = databasePath;
    }

    /**
     * Conectar ao banco de dados SQLite
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            createTables();
            logger.info("✅ Conectado ao banco de dados SQLite");
        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("❌ Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    /**
     * Criar tabelas se não existirem
     */
    private void createTables() throws SQLException {
        String playerTableSQL = "CREATE TABLE IF NOT EXISTS players (" +
                "uuid TEXT PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "coins LONG DEFAULT 0," +
                "last_login LONG," +
                "total_coins_earned LONG DEFAULT 0," +
                "level INT DEFAULT 1," +
                "created_at LONG," +
                "booster_multiplier DOUBLE DEFAULT 1.0," +
                "booster_until LONG DEFAULT 0" +
                ")";

        String transactionTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player_uuid TEXT NOT NULL," +
                "type TEXT NOT NULL," +  // EARN, SPEND, BOOSTER, BONUS
                "amount LONG NOT NULL," +
                "reason TEXT," +
                "timestamp LONG," +
                "FOREIGN KEY(player_uuid) REFERENCES players(uuid)" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(playerTableSQL);
            stmt.execute(transactionTableSQL);
        }
    }

    /**
     * Obter ou criar player
     */
    public PlayerData getOrCreatePlayer(UUID uuid, String name) {
        try {
            // Verificar se existe
            String selectSQL = "SELECT * FROM players WHERE uuid = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
                pstmt.setString(1, uuid.toString());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Update last login
                        updateLastLogin(uuid);
                        return new PlayerData(
                            uuid,
                            rs.getString("name"),
                            rs.getLong("coins"),
                            rs.getLong("last_login"),
                            rs.getLong("total_coins_earned"),
                            rs.getInt("level"),
                            rs.getLong("created_at")
                        );
                    }
                }
            }

            // Criar novo player
            String insertSQL = "INSERT INTO players (uuid, name, coins, last_login, created_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                long now = System.currentTimeMillis();
                pstmt.setString(1, uuid.toString());
                pstmt.setString(2, name);
                pstmt.setLong(3, 0);
                pstmt.setLong(4, now);
                pstmt.setLong(5, now);
                pstmt.executeUpdate();
            }

            return new PlayerData(uuid, name, 0, System.currentTimeMillis(), 0, 1, System.currentTimeMillis());

        } catch (SQLException e) {
            logger.severe("❌ Erro ao obter/criar player: " + e.getMessage());
            return null;
        }
    }

    /**
     * Adicionar coins ao player
     */
    public void addCoins(UUID uuid, long amount, String reason) {
        try {
            String updateSQL = "UPDATE players SET coins = coins + ?, total_coins_earned = total_coins_earned + ? WHERE uuid = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
                pstmt.setLong(1, amount);
                pstmt.setLong(2, amount);
                pstmt.setString(3, uuid.toString());
                pstmt.executeUpdate();
            }

            // Registrar transação
            recordTransaction(uuid, "EARN", amount, reason);

        } catch (SQLException e) {
            logger.severe("❌ Erro ao adicionar coins: " + e.getMessage());
        }
    }

    /**
     * Remover coins do player
     */
    public boolean removeCoins(UUID uuid, long amount, String reason) {
        try {
            // Verificar se tem coins suficientes
            PlayerData player = getOrCreatePlayer(uuid, "");
            if (player.getCoins() < amount) {
                return false;
            }

            String updateSQL = "UPDATE players SET coins = coins - ? WHERE uuid = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
                pstmt.setLong(1, amount);
                pstmt.setString(2, uuid.toString());
                pstmt.executeUpdate();
            }

            // Registrar transação
            recordTransaction(uuid, "SPEND", amount, reason);

            return true;

        } catch (SQLException e) {
            logger.severe("❌ Erro ao remover coins: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registrar transação
     */
    private void recordTransaction(UUID uuid, String type, long amount, String reason) {
        try {
            String insertSQL = "INSERT INTO transactions (player_uuid, type, amount, reason, timestamp) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setString(1, uuid.toString());
                pstmt.setString(2, type);
                pstmt.setLong(3, amount);
                pstmt.setString(4, reason);
                pstmt.setLong(5, System.currentTimeMillis());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.severe("❌ Erro ao registrar transação: " + e.getMessage());
        }
    }

    /**
     * Atualizar último login
     */
    private void updateLastLogin(UUID uuid) {
        try {
            String updateSQL = "UPDATE players SET last_login = ? WHERE uuid = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
                pstmt.setLong(1, System.currentTimeMillis());
                pstmt.setString(2, uuid.toString());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.severe("❌ Erro ao atualizar último login: " + e.getMessage());
        }
    }

    /**
     * Obter coins do player
     */
    public long getCoins(UUID uuid) {
        try {
            String selectSQL = "SELECT coins FROM players WHERE uuid = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
                pstmt.setString(1, uuid.toString());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getLong("coins");
                    }
                }
            }
        } catch (SQLException e) {
            logger.severe("❌ Erro ao obter coins: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Fechar conexão
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("✅ Desconectado do banco de dados");
            }
        } catch (SQLException e) {
            logger.severe("❌ Erro ao desconectar: " + e.getMessage());
        }
    }

    /**
     * Classe de dados do player
     */
    public static class PlayerData {
        private UUID uuid;
        private String name;
        private long coins;
        private long lastLogin;
        private long totalCoinsEarned;
        private int level;
        private long joinDate;

        public PlayerData(UUID uuid, String name, long coins, long lastLogin, long totalCoinsEarned, int level) {
            this.uuid = uuid;
            this.name = name;
            this.coins = coins;
            this.lastLogin = lastLogin;
            this.totalCoinsEarned = totalCoinsEarned;
            this.level = level;
            this.joinDate = System.currentTimeMillis();
        }

        public PlayerData(UUID uuid, String name, long coins, long lastLogin, long totalCoinsEarned, int level, long joinDate) {
            this.uuid = uuid;
            this.name = name;
            this.coins = coins;
            this.lastLogin = lastLogin;
            this.totalCoinsEarned = totalCoinsEarned;
            this.level = level;
            this.joinDate = joinDate;
        }

        public UUID getUuid() { return uuid; }
        public String getName() { return name; }
        public long getCoins() { return coins; }
        public long getLastLogin() { return lastLogin; }
        public long getTotalCoinsEarned() { return totalCoinsEarned; }
        public int getLevel() { return level; }
        public long getJoinDate() { return joinDate; }
    }
}

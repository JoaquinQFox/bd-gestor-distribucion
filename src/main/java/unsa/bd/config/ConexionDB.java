package unsa.bd.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexionDB {
    public static Connection getConnection() {
        Properties props = new Properties();

        try (InputStream input = ConexionDB.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontro archivo database.properties");
            }

            props.load(input);
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String  password = props.getProperty("db.password");

            Connection con = DriverManager.getConnection(url, user, password);

            String driver = con.getMetaData().getDatabaseProductName();
            if (driver.equalsIgnoreCase("MySQL")) {
                Statement st = con.createStatement();
                st.execute("SET SESSION sql_mode = CONCAT(@@sql_mode, ',ANSI_QUOTES')");
            }
            return con;
        } catch (IOException | SQLException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = ConexionDB.getConnection();
        if (connection != null) {
            System.out.println("¡Conexión exitosa!");
            connection.close();
        } else {
            System.out.println("Falló la conexión.");
        }
    }
}

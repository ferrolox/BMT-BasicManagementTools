package taskmanager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnection {
	private static final String URL = setting("DB_URL", "jdbc:postgresql://localhost:5432/taskmanager");
	private static final String USER = setting("DB_USER", "taskmanager_user");
	private static final String PASSWORD = setting("DB_PASSWORD", "taskmanager_password");

	private DatabaseConnection() {}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void initializeSchema() throws SQLException, IOException {
		String schema;

		try (InputStream input = DatabaseConnection.class.getResourceAsStream("/schema.sql")) {
			if (input == null) {
				throw new IOException("Could not find schema.sql on the classpath.");
			}

			schema = new String(input.readAllBytes(), StandardCharsets.UTF_8);
		}

		try (Connection connection = getConnection();
		     Statement statement = connection.createStatement()) {
			statement.execute(schema);
		}
	}

	private static String setting(String name, String defaultValue) {
		String value = System.getenv(name);
		return value == null || value.isBlank() ? defaultValue : value;
	}
}
package taskmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository {

	public Task create(String title, String description) throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			return create(connection, title, description);
		}
	}

	public Optional<Task> findById(long id) throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			return findById(connection, id);
		}
	}

	public List<Task> findAll() throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			return findAll(connection);
		}
	}

	public boolean update(long id, String title, String description, boolean completed)
		throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			return update(connection, id, title, description, completed);
		}
	}

	public boolean delete(long id) throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			return delete(connection, id);
		}
	}

	Task create(Connection connection, String title, String description) throws SQLException {
		String sql = """
            INSERT INTO tasks (title, description)
            VALUES (?, ?)
            RETURNING id, title, description, completed, created_at
            """;

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, title);
			statement.setString(2, description);

			try (ResultSet result = statement.executeQuery()) {
				result.next();
				return mapTask(result);
			}
		}
	}

	Optional<Task> findById(Connection connection, long id) throws SQLException {
		String sql = """
            SELECT id, title, description, completed, created_at
            FROM tasks
            WHERE id = ?
            """;

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);

			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					return Optional.of(mapTask(result));
				}

				return Optional.empty();
			}
		}
	}

	List<Task> findAll(Connection connection) throws SQLException {
		String sql = """
            SELECT id, title, description, completed, created_at
            FROM tasks
            ORDER BY id
            """;

		List<Task> tasks = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(sql);
		     ResultSet result = statement.executeQuery()) {

			while (result.next()) {
				tasks.add(mapTask(result));
			}
		}

		return tasks;
	}

	boolean update(
		Connection connection,
		long id,
		String title,
		String description,
		boolean completed
	) throws SQLException {
		String sql = """
            UPDATE tasks
            SET title = ?, description = ?, completed = ?
            WHERE id = ?
            """;

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, title);
			statement.setString(2, description);
			statement.setBoolean(3, completed);
			statement.setLong(4, id);

			return statement.executeUpdate() > 0;
		}
	}

	boolean delete(Connection connection, long id) throws SQLException {
		String sql = "DELETE FROM tasks WHERE id = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			return statement.executeUpdate() > 0;
		}
	}

	private Task mapTask(ResultSet result) throws SQLException {
		return new Task(
			result.getLong("id"),
			result.getString("title"),
			result.getString("description"),
			result.getBoolean("completed"),
			result.getObject("created_at", java.time.OffsetDateTime.class)
		);
	}
}
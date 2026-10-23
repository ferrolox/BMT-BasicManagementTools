package taskmanager;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionDemo {
	private final TaskRepository repository = new TaskRepository();

	public void demonstrateCommit() throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			connection.setAutoCommit(false);

			try {
				repository.create(
					connection,
					"Committed task",
					"This task should remain in the database."
				);

				connection.commit();
				IO.println("Transaction committed.");
			} catch (SQLException exception) {
				connection.rollback();
				throw exception;
			}
		}
	}

	public void demonstrateRollback() throws SQLException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			connection.setAutoCommit(false);

			try {
				repository.create(
					connection,
					"Rolled-back task",
					"This task should not remain in the database."
				);

				connection.rollback();
				IO.println("Transaction rolled back.");
			} catch (SQLException exception) {
				connection.rollback();
				throw exception;
			}
		}
	}
}
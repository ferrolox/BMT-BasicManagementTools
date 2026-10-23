package taskmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

	static void main() {
		try {
			DatabaseConnection.initializeSchema();

			TaskRepository repository = new TaskRepository();

			demonstrateCrud(repository);

			TransactionDemo transactions = new TransactionDemo();
			transactions.demonstrateCommit();
			transactions.demonstrateRollback();

			IO.println("\nTasks after transactions:");
			repository.findAll().forEach(IO::println);

			demonstrateInvalidQuery();
			demonstrateInvalidData(repository);

		} catch (SQLException exception) {
			IO.println("Database operation failed: " + exception.getMessage());
			IO.println("SQL state: " + exception.getSQLState());
		} catch (java.io.IOException exception) {
			IO.println("Could not load schema: " + exception.getMessage());
		}
	}

	private static void demonstrateCrud(TaskRepository repository) throws SQLException {
		IO.println("=== CREATE ===");

		Task first = repository.create(
			"Learn Java",
			"Practise JDBC and SQL."
		);

		Task second = repository.create(
			"Learn Docker",
			"Understand containers and volumes."
		);

		IO.println(first);
		IO.println(second);

		IO.println("\n=== READ ===");
		repository.findAll().forEach(IO::println);

		IO.println("\n=== UPDATE ===");
		boolean updated = repository.update(
			first.id(),
			"Learn Java JDBC",
			"Practise prepared statements and transactions.",
			true
		);
		IO.println("Updated: " + updated);
		IO.println(repository.findById(first.id()).orElseThrow());

		IO.println("\n=== DELETE ===");
		boolean deleted = repository.delete(second.id());
		IO.println("Deleted: " + deleted);
		IO.println("Remaining tasks:");
		repository.findAll().forEach(IO::println);
	}

	private static void demonstrateInvalidQuery() {
		IO.println("\n=== INVALID QUERY ===");

		try (Connection connection = DatabaseConnection.getConnection();
		     PreparedStatement statement =
			     connection.prepareStatement("SELECT column_that_does_not_exist FROM tasks")) {

			statement.executeQuery();
		} catch (SQLException exception) {
			IO.println("Expected SQL error: " + exception.getMessage());
			IO.println("SQL state: " + exception.getSQLState());
		}
	}

	private static void demonstrateInvalidData(TaskRepository repository) {
		IO.println("\n=== INVALID DATA ===");

		try {
			repository.create("   ", "This title violates the CHECK constraint.");
		} catch (SQLException exception) {
			IO.println("Expected data error: " + exception.getMessage());
			IO.println("SQL state: " + exception.getSQLState());
		}
	}
}
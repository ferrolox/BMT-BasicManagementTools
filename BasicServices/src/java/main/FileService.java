import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

private static final Path OUTPUT_FILE = Path.of("output.txt");

void main() throws IOException {
	List<String> inputs = readUserInput();

	writeToFile(inputs);

	List<String> savedLines = readFromFile();

	IO.println("\nContent of output.txt:");

	for (String line : savedLines) {
		IO.println(line);
	}
}

private static List<String> readUserInput() {
	List<String> inputs = new ArrayList<>();

	IO.println("Enter text. Submit an empty line to finish:");

	while (true) {
		String line = IO.readln();

		if (line.isEmpty()) {
			break;
		}

		inputs.add(line);
	}

	return inputs;
}

private static void writeToFile(List<String> inputs) throws IOException {
	Files.write(
		OUTPUT_FILE,
		inputs
	);

	IO.println(
		"\nWrote " + inputs.size() +
			" lines to " + OUTPUT_FILE
	);
}

private static List<String> readFromFile() throws IOException {
	return Files.readAllLines(OUTPUT_FILE);
}
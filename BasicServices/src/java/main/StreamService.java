private static final Path INPUT_FILE = Path.of("texts.txt");

void main() throws IOException {
	createInputFile();

	List<String> lines = readLines();

	IO.println("All lines:");
	lines.forEach(IO::println);

	IO.println("\nFirst 5 matching lines:");

	lines.stream()
	     .filter(line -> line.toLowerCase().contains("free"))
	     .sorted()
	     .limit(5)
	     .forEach(IO::println);

	demonstrateAdditionalStreamOperations(lines);
}

private static void createInputFile() throws IOException {
	List<String> lines = List.of(
		"Java is free and open source.",
		"This line does not contain the keyword.",
		"Free software gives users freedom.",
		"Learning Java can be completely free.",
		"Programming requires practice.",
		"You are free to choose your programming language.",
		"Free tools can be useful for development.",
		"This is another normal line.",
		"Many free resources are available online.",
		"Free software encourages collaboration.",
		"This line is unrelated.",
		"There are many free programming courses.",
		"Open source software is often free.",
		"You can freely experiment with Java.",
		"Free documentation is available for Java."
	);

	Files.write(INPUT_FILE, lines);
}

private static List<String> readLines() throws IOException {
	return Files.readAllLines(INPUT_FILE);
}

private static void demonstrateAdditionalStreamOperations(List<String> lines) {
	long matchingLines = lines.stream()
	                          .filter(line -> line.toLowerCase().contains("free"))
	                          .count();

	IO.println("\nNumber of matching lines: " + matchingLines);

	List<String> matchingLinesList = lines.stream()
	                                      .filter(line -> line.toLowerCase().contains("free"))
	                                      .sorted()
	                                      .toList();

	IO.println("\nAll matching lines sorted:");

	matchingLinesList.forEach(IO::println);

	IO.println("\nAny line contains 'collaboration': " + lines.stream().anyMatch(line -> line.toLowerCase().contains("collaboration")));
	IO.println("No lines are blank: " + lines.stream().noneMatch(String::isBlank));
}
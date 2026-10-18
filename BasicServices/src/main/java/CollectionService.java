void main() {
	List<String> entries = createCollection();

	printCollection(entries);

	removeEverySecondEntry(entries);

	IO.println("\nCollection after removal:");
	printCollection(entries);

	demonstrateCollectionOperations(entries);
}

private static List<String> createCollection() {
	List<String> entries = new ArrayList<>();

	for (int i = 1; i <= 50; i++) {
		entries.add("Entry " + i);
	}

	return entries;
}

private static void printCollection(List<String> entries) {
	IO.println("Collection contains " + entries.size() + " entries:");

	for (String entry : entries) {
		IO.println(entry);
	}
}

private static void removeEverySecondEntry(List<String> entries) {
	Iterator<String> iterator = entries.iterator();
	int position = 1;

	IO.println("\nIterating through collection:");

	while (iterator.hasNext()) {
		String entry = iterator.next();

		if (position % 2 == 0) {
			iterator.remove();
			IO.println(entry + " <== removed");
		} else {
			IO.println(entry);
		}

		position++;
	}
}

private static void demonstrateCollectionOperations(List<String> entries) {
	IO.println("\nCollection operations:");

	entries.add("New Entry");
	IO.println("After add: " + entries.getLast());

	entries.set(0, "Modified Entry");
	IO.println("After modification: " + entries.getFirst());

	IO.println("Contains 'Entry 10': " + entries.contains("Entry 10"));
	IO.println("First entry: " + entries.getFirst());
	IO.println("Last entry: " + entries.getLast());

	entries.remove("New Entry");
	IO.println("After removing 'New Entry': " + entries.size());

	IO.println("Collection is empty: " + entries.isEmpty());
}
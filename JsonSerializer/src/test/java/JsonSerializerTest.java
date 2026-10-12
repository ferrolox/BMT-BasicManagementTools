import com.fasterxml.jackson.core.JsonProcessingException;

void main() throws JsonProcessingException {
	JsonSerializer parser = new JsonSerializer();

	JsonSerializer.Item item = new JsonSerializer.Item(
		16,
		"Papa",
		"https://example.com/item/16",
		"Electronics",
		249.99,
		25
	);

	String serializedJson = parser.serialize(item);

	IO.println("Serialized JSON:");
	IO.println(serializedJson);

	String json = """
		[
		  { "id": 1, "label": "Alpha", "url": "https://example.com/item/1" },
		  { "id": 2, "label": "Bravo", "url": "https://example.com/item/2" },
		  { "id": 3, "label": "Charlie", "url": "https://example.com/item/3" },
		  { "id": 4, "label": "Delta", "url": "https://example.com/item/4" },
		  { "id": 5, "label": "Echo", "url": "https://example.com/item/5" },
		  { "id": 6, "label": "Foxtrot", "url": "https://example.com/item/6" },
		  { "id": 7, "label": "Golf", "url": "https://example.com/item/7" },
		  { "id": 8, "label": "Hotel", "url": "https://example.com/item/8" },
		  { "id": 9, "label": "India", "url": "https://example.com/item/9" },
		  { "id": 10, "label": "Juliet", "url": "https://example.com/item/10" },
		  { "id": 11, "label": "Kilo", "url": "https://example.com/item/11" },
		  { "id": 12, "label": "Lima", "url": "https://example.com/item/12" },
		  { "id": 13, "label": "Mike", "url": "https://example.com/item/13" },
		  { "id": 14, "label": "November", "url": "https://example.com/item/14" },
		  { "id": 15, "label": "Oscar", "url": "https://example.com/item/15" }
		]
		""";

	List<JsonSerializer.Item> items = parser.deserialize(json);

	IO.println("\nDeserialized items:");

	for (JsonSerializer.Item parsedItem : items) {
		IO.println("ID: %d | Label: %-10s | URL: %s".formatted(parsedItem.id(), parsedItem.label(), parsedItem.url()));
	}
}
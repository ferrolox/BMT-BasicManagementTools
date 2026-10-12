import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonSerializer {

	private final ObjectMapper mapper = new ObjectMapper();

	public record Item(
		int id,
		String label,
		String url,
		String category,
		double price,
		int stock
	) {}

	public String serialize(Item item) throws JsonProcessingException {
		return mapper.writeValueAsString(item);
	}

	public List<Item> deserialize(String json) throws JsonProcessingException {
		return mapper.readValue(json, new TypeReference<List<Item>>() {});
	}
}
package taskmanager;

import java.time.OffsetDateTime;

public record Task(
	long id,
	String title,
	String description,
	boolean completed,
	OffsetDateTime createdAt
) {
}
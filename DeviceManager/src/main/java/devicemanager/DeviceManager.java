package devicemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DeviceManager {

	// =========================================================
	// BEFORE: VIOLATION OF SRP
	// =========================================================

	static class BadDeviceManager {

		private final List<String> devices = new ArrayList<>();

		private void validate(String name) {
			if (name == null || name.isBlank()) {
				throw new IllegalArgumentException("Invalid device name");
			}

			if (devices.contains(name)) {
				throw new IllegalArgumentException("Device already exists");
			}
		}

		public void addDevice(String name) {
			validate(name);
			devices.add(name);
		}

		public void removeDevice(String name) {
			if (!devices.remove(name)) {
				throw new IllegalArgumentException("Device not found");
			}
		}

		public List<String> getDevices() {
			return List.copyOf(devices);
		}

		public String generateReport() {
			return String.join("\n", devices);
		}

		public void exportReport(Path path) throws IOException {
			Files.writeString(path, generateReport());
		}
	}

	// =========================================================
	// AFTER: APPLYING SRP
	// =========================================================

	static class DeviceValidator {

		public void validate(String name) {
			if (name == null || name.isBlank()) {
				throw new IllegalArgumentException("Invalid device name");
			}
		}
	}

	static class DeviceRepository {

		private final List<String> devices = new ArrayList<>();

		public void save(String name) {
			devices.add(name);
		}

		public boolean exists(String name) {
			return devices.contains(name);
		}

		public boolean delete(String name) {
			return devices.remove(name);
		}

		public List<String> findAll() {
			return List.copyOf(devices);
		}
	}

	static class DeviceExporter {

		public String generateReport(List<String> devices) {
			return String.join("\n", devices);
		}

		public void export(List<String> devices, Path path)
			throws IOException {
			Files.writeString(path, generateReport(devices));
		}
	}

	static class GoodDeviceManager {

		private final DeviceRepository repository;
		private final DeviceValidator validator;
		private final DeviceExporter exporter;

		public GoodDeviceManager(
			DeviceRepository repository,
			DeviceValidator validator,
			DeviceExporter exporter
		) {
			this.repository = repository;
			this.validator = validator;
			this.exporter = exporter;
		}

		public void addDevice(String name) {
			validator.validate(name);

			if (repository.exists(name)) {
				throw new IllegalArgumentException("Device already exists");
			}

			repository.save(name);
		}

		public void removeDevice(String name) {
			if (!repository.delete(name)) {
				throw new IllegalArgumentException("Device not found");
			}
		}

		public List<String> getDevices() {
			return repository.findAll();
		}

		public void exportReport(Path path) throws IOException {
			exporter.export(repository.findAll(), path);
		}
	}

	// =========================================================
	// DEMONSTRATION
	// =========================================================

	void main() throws IOException {
		IO.println("========== BEFORE: SRP ==========");

		BadDeviceManager badManager = new BadDeviceManager();

		badManager.addDevice("Smartphone");
		badManager.addDevice("Laptop");

		IO.println(badManager.generateReport());
		IO.println("\n========== AFTER: SRP ==========");

		GoodDeviceManager goodManager = new GoodDeviceManager(
			new DeviceRepository(),
			new DeviceValidator(),
			new DeviceExporter()
		);

		goodManager.addDevice("Smartphone");
		goodManager.addDevice("Laptop");

		IO.println(goodManager.getDevices());

		Path exportPath = Path.of("device-report.txt");
		goodManager.exportReport(exportPath);

		IO.println("Report exported to: " + exportPath);
	}
}
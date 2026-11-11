
package devicemanager;

import java.util.ArrayList;
import java.util.List;

public class DeviceController {

	// =========================================================
	// BEFORE: FAT CONTROLLER
	// =========================================================

	static class BadDeviceController {

		private final List<String> devices = new ArrayList<>();

		public String addDevice(String name) {

			if (name == null || name.isBlank()) {
				return "Invalid device name";
			}

			if (devices.stream().anyMatch(existing -> existing.equalsIgnoreCase(name))) {
				return "Device already exists";
			}

			devices.add(name);

			return "Successfully registered: " + name;
		}

		public String searchDevices(String searchTerm) {

			if (searchTerm == null || searchTerm.isBlank()) {
				return "Invalid search term";
			}

			List<String> results = devices.stream()
			                              .filter(name -> name.toLowerCase()
			                                                  .contains(searchTerm.toLowerCase()))
			                              .toList();

			if (results.isEmpty()) {
				return "No devices found";
			}

			return String.join(", ", results);
		}

		public String removeDevice(String name) {

			boolean removed = devices.removeIf(existing ->
				                                   existing.equalsIgnoreCase(name));

			return removed ? "Device removed" : "Device not found";
		}
	}


	// =========================================================
	// AFTER: SEPARATION OF RESPONSIBILITIES
	// =========================================================

	static class DeviceRepository {

		private final List<String> devices = new ArrayList<>();

		public void save(String name) {
			devices.add(name);
		}

		public boolean exists(String name) {
			return devices.stream()
			              .anyMatch(existing ->
				                        existing.equalsIgnoreCase(name));
		}

		public List<String> findAll() {
			return List.copyOf(devices);
		}

		public List<String> search(String searchTerm) {
			return devices.stream()
			              .filter(name -> name.toLowerCase()
			                                  .contains(searchTerm.toLowerCase()))
			              .toList();
		}

		public boolean delete(String name) {
			return devices.removeIf(existing ->
				                        existing.equalsIgnoreCase(name));
		}
	}

	static class DeviceService {

		private final DeviceRepository repository;

		public DeviceService(DeviceRepository repository) {
			this.repository = repository;
		}

		public void registerDevice(String name) {

			if (name == null || name.isBlank()) {
				throw new IllegalArgumentException("Invalid device name");
			}

			if (repository.exists(name)) {
				throw new IllegalArgumentException("Device already exists");
			}

			repository.save(name);
		}

		public List<String> searchDevices(String searchTerm) {

			if (searchTerm == null || searchTerm.isBlank()) {
				throw new IllegalArgumentException("Invalid search term");
			}

			return repository.search(searchTerm);
		}

		public void removeDevice(String name) {

			if (!repository.delete(name)) {
				throw new IllegalArgumentException("Device not found");
			}
		}

		public List<String> getDevices() {
			return repository.findAll();
		}
	}

	static class GoodDeviceController {

		private final DeviceService service;

		public GoodDeviceController(DeviceService service) {
			this.service = service;
		}

		public String addDevice(String name) {
			try {
				service.registerDevice(name);
				return "Successfully registered: " + name;
			} catch (IllegalArgumentException exception) {
				return "Could not register device: " + exception.getMessage();
			}
		}

		public List<String> searchDevices(String searchTerm) {
			return service.searchDevices(searchTerm);
		}

		public String removeDevice(String name) {
			try {
				service.removeDevice(name);
				return "Device removed";
			} catch (IllegalArgumentException exception) {
				return "Could not remove device: " + exception.getMessage();
			}
		}

		public List<String> getDevices() {
			return service.getDevices();
		}
	}


	// =========================================================
	// DEMONSTRATION
	// =========================================================

	void main() {
		IO.println("========== BEFORE: FAT CONTROLLER ==========");

		BadDeviceController badController = new BadDeviceController();

		IO.println(badController.addDevice("iPhone"));
		IO.println(badController.addDevice("MacBook"));
		IO.println(badController.addDevice("iPhone"));
		IO.println(badController.searchDevices("phone"));


		IO.println("\n========== AFTER: REFACTORED CONTROLLER ==========");

		DeviceRepository repository = new DeviceRepository();
		DeviceService service = new DeviceService(repository);
		GoodDeviceController goodController =
			new GoodDeviceController(service);

		IO.println(goodController.addDevice("iPhone"));
		IO.println(goodController.addDevice("MacBook"));
		IO.println(goodController.addDevice("iPhone"));
		IO.println(goodController.searchDevices("phone"));
	}
}
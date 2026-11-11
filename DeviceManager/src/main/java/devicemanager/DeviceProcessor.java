
package devicemanager;

import java.util.ArrayList;
import java.util.List;

public class DeviceProcessor {

	record Device(String name, int powerConsumption) {}

	// =========================================================
	// BEFORE: MULTIPLE RESPONSIBILITIES
	// =========================================================

	static class BadDeviceProcessor {

		private final List<Device> devices = new ArrayList<>();
		private int availablePower = 1000;

		public void registerDevice(Device device) {

			if (device == null) {
				throw new IllegalArgumentException("Device cannot be null");
			}

			if (device.name() == null || device.name().isBlank()) {
				throw new IllegalArgumentException("Invalid device name");
			}

			if (device.powerConsumption() <= 0) {
				throw new IllegalArgumentException(
					"Invalid power consumption"
				);
			}

			if (device.powerConsumption() > availablePower) {
				throw new IllegalArgumentException(
					"Insufficient power capacity"
				);
			}

			availablePower -= device.powerConsumption();

			// Data storage
			devices.add(device);

			// Notification
			IO.println("Device registered: " + device.name());
		}

		public List<Device> getDevices() {
			return List.copyOf(devices);
		}
	}

	// =========================================================
	// AFTER: APPLYING SOLID
	// =========================================================

	static class DeviceValidator {

		public void validate(Device device) {

			if (device == null) {
				throw new IllegalArgumentException("Device cannot be null");
			}

			if (device.name() == null || device.name().isBlank()) {
				throw new IllegalArgumentException("Invalid device name");
			}

			if (device.powerConsumption() <= 0) {
				throw new IllegalArgumentException(
					"Invalid power consumption"
				);
			}
		}
	}

	static class PowerManager {

		private int availablePower;

		public PowerManager(int availablePower) {
			this.availablePower = availablePower;
		}

		public void reserve(int powerConsumption) {

			if (powerConsumption > availablePower) {
				throw new IllegalArgumentException(
					"Insufficient power capacity"
				);
			}

			availablePower -= powerConsumption;
		}

		public int getAvailablePower() {
			return availablePower;
		}
	}

	static class DeviceRepository {

		private final List<Device> devices = new ArrayList<>();

		public void save(Device device) {
			devices.add(device);
		}

		public List<Device> findAll() {
			return List.copyOf(devices);
		}
	}

	static class DeviceNotifier {

		public void notifyRegistration(Device device) {
			IO.println("Device registered: " + device.name());
		}
	}

	static class GoodDeviceProcessor {

		private final DeviceValidator validator;
		private final PowerManager powerManager;
		private final DeviceRepository repository;
		private final DeviceNotifier notifier;

		public GoodDeviceProcessor(
			DeviceValidator validator,
			PowerManager powerManager,
			DeviceRepository repository,
			DeviceNotifier notifier
		) {
			this.validator = validator;
			this.powerManager = powerManager;
			this.repository = repository;
			this.notifier = notifier;
		}

		public void registerDevice(Device device) {

			validator.validate(device);
			powerManager.reserve(device.powerConsumption());
			repository.save(device);
			notifier.notifyRegistration(device);
		}

		public List<Device> getDevices() {
			return repository.findAll();
		}
	}

	/*
	 * Metrics (Before -> After):
	 * - Classes: 2 -> 6
	 * - Files: 1 -> 1
	 * - LOC: Lower -> Higher
	 * - Cyclomatic Complexity: 5 -> 1
	 * - Coupling: Higher -> More structured
	 * - Cohesion: Lower -> Higher
	 */

	// =========================================================
	// DEMONSTRATION
	// =========================================================

	void main() {

		IO.println("========== BEFORE: SOLID IMPACT ==========");

		BadDeviceProcessor badProcessor = new BadDeviceProcessor();

		badProcessor.registerDevice(new Device("Smart TV", 150));
		badProcessor.registerDevice(new Device("Laptop", 100));

		IO.println("Devices: " + badProcessor.getDevices());


		IO.println("\n========== AFTER: SOLID IMPACT ==========");

		GoodDeviceProcessor goodProcessor = new GoodDeviceProcessor(
			new DeviceValidator(),
			new PowerManager(1000),
			new DeviceRepository(),
			new DeviceNotifier()
		);

		goodProcessor.registerDevice(new Device("Smart TV", 150));
		goodProcessor.registerDevice(new Device("Laptop", 100));

		IO.println("Devices: " + goodProcessor.getDevices());
	}
}
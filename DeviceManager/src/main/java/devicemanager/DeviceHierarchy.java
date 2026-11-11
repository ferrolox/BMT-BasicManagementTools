package devicemanager;

import java.util.List;

public class DeviceHierarchy {

	// =========================================================
	// BEFORE: VIOLATION OF LSP
	// =========================================================

	static abstract class BadDevice {

		protected final String name;

		protected BadDevice(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public abstract void charge();
	}

	static class BadSmartphone extends BadDevice {

		public BadSmartphone(String name) {
			super(name);
		}

		@Override
		public void charge() {
			IO.println(name + " is charging.");
		}
	}

	static class BadSmartTV extends BadDevice {

		public BadSmartTV(String name) {
			super(name);
		}

		@Override
		public void charge() {
			throw new UnsupportedOperationException(
				"Smart TVs do not support this charging operation"
			);
		}
	}

	// =========================================================
	// AFTER: APPLYING LSP
	// =========================================================

	static abstract class GoodDevice {

		private final String name;

		protected GoodDevice(String name) {
			if (name == null || name.isBlank()) {
				throw new IllegalArgumentException("Invalid device name");
			}

			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	interface Chargeable {

		void charge();
	}

	static class GoodSmartphone extends GoodDevice implements Chargeable {

		public GoodSmartphone(String name) {
			super(name);
		}

		@Override
		public void charge() {
			IO.println(getName() + " is charging.");
		}
	}

	static class GoodSmartTV extends GoodDevice {

		public GoodSmartTV(String name) {
			super(name);
		}
	}

	static class ChargingService {

		public void chargeAll(List<? extends Chargeable> devices) {
			for (Chargeable device : devices) {
				device.charge();
			}
		}
	}

	// =========================================================
	// DEMONSTRATION
	// =========================================================

	void main() {
		IO.println("========== BEFORE: LSP ==========");

		List<BadDevice> badDevices = List.of(
			new BadSmartphone("iPhone"),
			new BadSmartTV("Samsung TV")
		);

		for (BadDevice device : badDevices) {
			try {
				device.charge();
			} catch (UnsupportedOperationException exception) {
				IO.println("Error: " + exception.getMessage());
			}
		}

		IO.println("\n========== AFTER: LSP ==========");

		List<GoodDevice> devices = List.of(
			new GoodSmartphone("iPhone"),
			new GoodSmartTV("Samsung TV")
		);

		for (GoodDevice device : devices) {
			IO.println("Registered device: " + device.getName());
		}

		ChargingService chargingService = new ChargingService();

		List<GoodSmartphone> smartphones = List.of(
			new GoodSmartphone("iPhone"),
			new GoodSmartphone("Pixel")
		);

		chargingService.chargeAll(smartphones);
	}
}
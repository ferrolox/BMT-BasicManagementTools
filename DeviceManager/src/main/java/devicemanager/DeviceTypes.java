
package devicemanager;

import java.util.List;

public class DeviceTypes {

	// =========================================================
	// BEFORE: UNRESTRICTED DEVICE HIERARCHY
	// =========================================================

	interface BadDeviceType {

		String getName();
	}

	record BadSmartphone(String getName) implements BadDeviceType {}

	record BadLaptop(String getName) implements BadDeviceType {}

	record BadSmartTV(String getName) implements BadDeviceType {}

	record UnknownDevice(String getName) implements BadDeviceType {}

	// =========================================================
	// AFTER: SEALED DEVICE HIERARCHY
	// =========================================================

	sealed interface DeviceType
		permits Smartphone, Laptop, SmartTV {

		String getName();
	}

	record Smartphone(String getName) implements DeviceType {}

	record Laptop(String getName) implements DeviceType {}

	record SmartTV(String getName) implements DeviceType {}


	// =========================================================
	// EXHAUSTIVE PATTERN MATCHING
	// =========================================================

	static String describe(DeviceType device) {
		return switch (device) {
			case Smartphone smartphone -> smartphone.getName() + " is a smartphone.";
			case Laptop laptop -> laptop.getName() + " is a laptop.";
			case SmartTV smartTV -> smartTV.getName() + " is a smart TV.";
		};
	}

	// =========================================================
	// DEMONSTRATION
	// =========================================================

	void main() {

		IO.println("========== BEFORE: UNRESTRICTED ==========");

		List<BadDeviceType> unrestrictedDevices = List.of(
			new BadSmartphone("iPhone"),
			new BadLaptop("MacBook"),
			new UnknownDevice("Unknown device")
		);

		for (BadDeviceType device : unrestrictedDevices) {
			IO.println(device.getName());
		}


		IO.println("\n========== AFTER: SEALED ==========");

		List<DeviceType> devices = List.of(
			new Smartphone("iPhone"),
			new Laptop("MacBook"),
			new SmartTV("Samsung TV")
		);

		for (DeviceType device : devices) {
			IO.println(describe(device));
		}
	}
}
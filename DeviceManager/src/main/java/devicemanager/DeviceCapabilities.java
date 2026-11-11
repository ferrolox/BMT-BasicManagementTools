
package devicemanager;

public class DeviceCapabilities {

	// =========================================================
	// BEFORE: VIOLATION OF ISP
	// =========================================================

	interface BadSmartDevice {

		void playMusic();

		void manageMediaLibrary();

		void cleanRoom();

		void controlLights();

		void adjustTemperature();
	}

	static class BadRobotVacuum implements BadSmartDevice {

		@Override
		public void playMusic() {
			throw new UnsupportedOperationException(
				"Robot vacuum cannot play music"
			);
		}

		@Override
		public void manageMediaLibrary() {
			throw new UnsupportedOperationException(
				"Robot vacuum cannot manage a media library"
			);
		}

		@Override
		public void cleanRoom() {
			IO.println("Robot vacuum is cleaning.");
		}

		@Override
		public void controlLights() {
			throw new UnsupportedOperationException(
				"Robot vacuum cannot control lights"
			);
		}

		@Override
		public void adjustTemperature() {
			throw new UnsupportedOperationException(
				"Robot vacuum cannot adjust temperature"
			);
		}
	}

	// =========================================================
	// AFTER: APPLYING ISP
	// =========================================================

	interface MediaPlayer {
		void playMusic();
	}

	interface LibraryManager {
		void manageMediaLibrary();
	}

	interface RoomCleaner {
		void cleanRoom();
	}

	interface LightController {
		void controlLights();
	}

	interface TemperatureController {
		void adjustTemperature();
	}

	static class RobotVacuum implements RoomCleaner {

		@Override
		public void cleanRoom() {
			IO.println("Robot vacuum is cleaning.");
		}
	}

	static class SmartSpeaker implements MediaPlayer, LibraryManager {

		@Override
		public void playMusic() {
			IO.println("Smart speaker is playing music.");
		}

		@Override
		public void manageMediaLibrary() {
			IO.println("Smart speaker is managing its media library.");
		}
	}

	static class SmartHomeHub
		implements LightController, TemperatureController {

		@Override
		public void controlLights() {
			IO.println("Smart home hub is controlling the lights.");
		}

		@Override
		public void adjustTemperature() {
			IO.println("Smart home hub is adjusting the temperature.");
		}
	}

	// =========================================================
	// DEMONSTRATION
	// =========================================================

	void main() {
		IO.println("========== BEFORE: ISP ==========");

		BadRobotVacuum badVacuum = new BadRobotVacuum();

		badVacuum.cleanRoom();

		try {
			badVacuum.playMusic();
		} catch (UnsupportedOperationException exception) {
			IO.println("Error: " + exception.getMessage());
		}


		IO.println("\n========== AFTER: ISP ==========");

		RobotVacuum vacuum = new RobotVacuum();
		SmartSpeaker speaker = new SmartSpeaker();
		SmartHomeHub hub = new SmartHomeHub();

		vacuum.cleanRoom();

		speaker.playMusic();
		speaker.manageMediaLibrary();

		hub.controlLights();
		hub.adjustTemperature();
	}
}
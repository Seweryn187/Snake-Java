package game.snake.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.snake.Screen.SnakeGame;

public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static void createApplication() {
		new Lwjgl3Application(new SnakeGame(), getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("Snake");
		configuration.setWindowedMode(1440, 810);
		configuration.setResizable(false);
		configuration.setWindowIcon("Img/snake_icon.png");
		configuration.setForegroundFPS(60);
		return configuration;
	}
}
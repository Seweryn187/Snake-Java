package game.snake.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.snake.SnakeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1440, 810);
		config.setResizable(false);
		config.setTitle("Snake");
		config.setWindowIcon("Img/snake_icon.png");
		config.setForegroundFPS(60);
		new Lwjgl3Application(new SnakeGame(), config);
	}
}

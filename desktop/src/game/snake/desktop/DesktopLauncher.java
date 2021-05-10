package game.snake.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.snake.SnakeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1440;
		config.height = 810;
		config.resizable = false;
		config.title = "Snake";
		config.addIcon("Img/snake_icon.png", Files.FileType.Internal);
		new LwjglApplication(new SnakeGame(), config);
	}
}

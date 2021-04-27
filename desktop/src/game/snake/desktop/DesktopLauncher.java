package game.snake.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.snake.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=800;
		config.height=600;
		config.resizable=false;
		config.title="Snake";
		config.addIcon("Img/snake_icon.png", Files.FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}

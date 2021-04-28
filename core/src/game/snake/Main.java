package game.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import static java.lang.System.exit;

public class Main extends ApplicationAdapter {


	private SpriteBatch batch;
	private Texture snakeImg;
	private Texture appleImg;
	private Snake snake;
	private Apple apple;
	private boolean gameOver;
	//private final BitmapFont font = new BitmapFont();

	@Override
	public void create () {
		batch = new SpriteBatch();
		snakeImg = new Texture("img/snake.png");
		appleImg = new Texture("img/apple.png");
		snake = new Snake(snakeImg);
		apple = new Apple(appleImg);
		initializeNewGame();
	}

	@Override
	public void render () {
		updateGame();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		apple.draw(batch);
		snake.draw(batch);
		if(!gameOver){
			//font.draw(batch, "You lost", 720, 405);
		}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		snakeImg.dispose();
		appleImg.dispose();
	}

	private void initializeNewGame() {
		snake.initialize();
		apple.randomizeApplePosition();
		gameOver = false;
	}

	private void updateGame() {
		if (!gameOver) {
			snake.act(Gdx.graphics.getDeltaTime());
			if (snake.isAppleFound(apple.getPosition())) {
				snake.extendSnake();
				apple.randomizeApplePosition();
			}
			if (snake.hasHitHimself()) {
				gameOver = true;
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				dispose();
				exit(0);
			}
		} else {
			if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
				initializeNewGame();
			}
		}
	}
}

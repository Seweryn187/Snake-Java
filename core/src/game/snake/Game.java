package game.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

import java.util.List;
import static java.lang.System.exit;

public class Game extends ApplicationAdapter {


	private SpriteBatch batch;
	private Texture snakeBodyImg, snakeHeadUpImg, snakeHeadDownImg, snakeHeadLeftImg, snakeHeadRightImg, snakeTailUpImg,
			snakeTailDownImg, snakeTailLeftImg, snakeTailRightImg;
	private Texture appleImg;
	private Snake snake;
	private Apple apple;
	private FoodPositionRandomizer foodPositionRandomizer;
	private boolean gameOver;
	private BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("font\\arial.fnt"), Gdx.files.internal("font\\arial.png"),false);
		font.setColor(Color.RED);
		snakeBodyImg = new Texture("img/snakeBody.png");
		snakeHeadUpImg = new Texture("img/snakeHeadUp.png");
		snakeHeadDownImg = new Texture("img/snakeHeadDown.png");
		snakeHeadRightImg = new Texture("img/snakeHeadRight.png");
		snakeHeadLeftImg = new Texture("img/snakeHeadLeft.png");
		snakeTailUpImg = new Texture("img/snakeTailUp.png");
		snakeTailDownImg = new Texture("img/snakeTailDown.png");
		snakeTailRightImg = new Texture("img/snakeTailRight.png");
		snakeTailLeftImg = new Texture("img/snakeTailLeft.png");
		appleImg = new Texture("img/apple.png");
		snake = new Snake(snakeHeadUpImg, snakeHeadDownImg, snakeHeadLeftImg, snakeHeadRightImg,snakeBodyImg,
				snakeTailUpImg, snakeTailDownImg, snakeTailRightImg, snakeTailLeftImg);
		apple = new Apple(appleImg);
		foodPositionRandomizer = new FoodPositionRandomizer();
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
		if(gameOver){
			font.draw(batch, "GAME OVER", 645, 405);
			font.draw(batch, "(Press \"N\" to start new game, )", 500, 370);
		}
		String scoreS = String.valueOf(Score.getScore());
		font.draw(batch, "Score: " + scoreS , 1250, 780);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		snakeHeadUpImg.dispose();
		snakeHeadDownImg.dispose();
		snakeHeadLeftImg.dispose();
		snakeHeadRightImg.dispose();
		snakeBodyImg.dispose();
		snakeTailUpImg.dispose();
		snakeTailDownImg.dispose();
		snakeTailLeftImg.dispose();
		snakeTailRightImg.dispose();
		appleImg.dispose();
	}

	private void initializeNewGame() {
		snake.initialize();
		gameOver = false;
		randomizeApplePosition();
		Score.resetScore();
	}


	private void randomizeApplePosition() {
		List<GridPoint2> occupiedPositions =
				snake.getSnakeSegmentPositions();

		try {
			apple.setPosition(
					foodPositionRandomizer.getRandomAvailablePosition(
							occupiedPositions
					)
			);
		} catch (NoMorePositionsAvailable e) {
			gameOver = true;
		}
	}

	private void updateGame() {
		if (!gameOver) {
			snake.act(Gdx.graphics.getDeltaTime());
			if (snake.isAppleFound(apple.getPosition())) {
				Score.score();
				snake.extendSnake();
				randomizeApplePosition();
			}
			if (snake.hasHitHimself()) {
				gameOver = true;
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				dispose();
				exit(0);
			}
		} else {
			if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
				initializeNewGame();
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				dispose();
				exit(0);
			}
		}
	}
}

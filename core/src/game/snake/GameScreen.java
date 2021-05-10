package game.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

import static java.lang.System.exit;

public class GameScreen implements Screen {

    final SnakeGame game;
    private final Texture snakeBodyImg, snakeHeadUpImg, snakeHeadDownImg, snakeHeadLeftImg, snakeHeadRightImg, snakeTailUpImg,
            snakeTailDownImg, snakeTailLeftImg, snakeTailRightImg, grassImg, appleImg;
    private final Snake snake;
    private final Apple apple;
    private final FoodPositionRandomizer foodPositionRandomizer;
    private boolean gameOver;
    private final Music gameMusic;
    private boolean paused = false;

    public GameScreen(final SnakeGame game){
        this.game = game;
        game.font.setColor(Color.RED);
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
        grassImg = new Texture("img/grass.png");
        snake = new Snake(snakeHeadUpImg, snakeHeadDownImg, snakeHeadLeftImg, snakeHeadRightImg,snakeBodyImg,
                snakeTailUpImg, snakeTailDownImg, snakeTailRightImg, snakeTailLeftImg);
        apple = new Apple(appleImg);
        foodPositionRandomizer = new FoodPositionRandomizer();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/snakeJazz.mp3"));
        gameMusic.setLooping(true);
        gameMusic.play();
        initializeNewGame();
    }

    @Override
    public void render (float delta) {
        if(paused) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                paused = false;
            }
        }
        else{
            updateGame();
        }

        if(!gameMusic.isPlaying()){
            gameMusic.play();
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        for(int i = 0; i <= 1440; i+=30){
            for(int j = 0; j <= 810; j+=30) {
                game.batch.draw(grassImg, i, j);
            }
        }
        apple.draw(game.batch);
        snake.draw(game.batch);
        if(gameOver){
            game.font.draw(game.batch, "GAME OVER", 645, 405);
            game.font.draw(game.batch, "(Press \"N\" to start new game, )", 500, 370);
        }
        if(paused){
            gameMusic.stop();
            game.font.draw(game.batch, "PAUSE", 645, 405);
        }
        String scoreS = String.valueOf(Score.getScore());
        game.font.draw(game.batch, "Score: " + scoreS , 1250, 780);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose () {
        game.batch.dispose();
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
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !paused) {
                paused = true;
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                initializeNewGame();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            dispose();
            exit(0);
        }
    }
}

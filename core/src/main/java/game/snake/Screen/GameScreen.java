package game.snake.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import game.snake.Objects.Apple;
import game.snake.Objects.Snake;
import game.snake.Utility.FoodPositionRandomizer;
import game.snake.Utility.Score;

import java.util.Arrays;
import java.util.List;

import static game.snake.Utility.Score.getScore;

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
    private final float screenWidth = Gdx.graphics.getWidth();
    private final float screenHeight = Gdx.graphics.getHeight();
    private static boolean continued = false;
    private static final Preferences prefs = Gdx.app.getPreferences("myPreferences");

    public GameScreen(final SnakeGame game){
        this.game = game;
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

        for(int i = 0; i <= screenWidth; i+=30){
            for(int j = 0; j <= screenHeight; j+=30) {
                game.batch.draw(grassImg, i, j);
            }
        }

        apple.draw(game.batch);
        snake.draw(game.batch);

        if(gameOver){
            game.font.draw(game.batch, "GAME OVER", screenWidth/2 - 80, screenHeight/2 + 60);
            game.font.draw(game.batch, "(Press \"N\" to start new game)", screenWidth/2 - 170, screenHeight/2 + 30);
        }

        if(paused){
            gameMusic.stop();
            game.font.draw(game.batch, "PAUSE", screenWidth/2 - 30, screenHeight/2 + 60);
        }

        String scoreS = String.valueOf(getScore());
        game.font.draw(game.batch, "Your score: " + scoreS , screenWidth - 250, screenHeight - 30);

        game.font.draw(game.batch, "Best score: " + prefs.getInteger("bestScore", 0), screenWidth/15, screenHeight - 30);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void hide() {
        saveGame();
        gameMusic.stop();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        saveGame();
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
        gameMusic.dispose();
    }

    private void initializeNewGame() {
        snake.initialize();
        gameOver = false;
        if(getContinued()) {
            Score.setScore(prefs.getInteger("score"));
            GridPoint2 oldApplePosition= new GridPoint2(prefs.getInteger("appleX"), prefs.getInteger("appleY"));
            apple.setPosition(oldApplePosition);
        }
        else{
            randomizeApplePosition();
            Score.resetScore();
        }
    }


    private void randomizeApplePosition() {
        List<GridPoint2> occupiedPositions = snake.getSnakeSegmentPositions();
        apple.setPosition(foodPositionRandomizer.getRandomAvailablePosition(occupiedPositions));
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
                continued = false;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !paused) {
                paused = true;
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                initializeNewGame();
            }
        }
    }

    public void saveGame(){
        int[] snakeSegmentsPositions = new int[snake.getSnakeSegmentPositions().size()*2];
        final int[] j = {0};
        final int[] i = {0};

        prefs.putInteger("score", getScore());

        snake.getSnakeSegmentPositions().forEach(body -> {
            snakeSegmentsPositions[j[0]] = body.x;
            snakeSegmentsPositions[j[0] +1] = body.y;
            j[0] += 2;
        });

        j[0] = 0;

        snake.getSnakeSegmentPositions().forEach(x -> {
            prefs.putInteger("snakeX" + Arrays.toString(i), snakeSegmentsPositions[j[0]]);
            prefs.putInteger("snakeY" + Arrays.toString(i), snakeSegmentsPositions[j[0] + 1]);
            j[0] += 2;
            i[0] += 1;
        });

        GridPoint2 appleCor = apple.getPosition();

        prefs.putInteger("appleX", appleCor.x);
        prefs.putInteger("appleY", appleCor.y);
        prefs.putInteger("numberOfSegments", snake.getSnakeSegmentPositions().size());
        prefs.putString("movementDirection", snake.getDirection().toString());
        prefs.putString("tailMovementDirection", snake.getTailDirection().toString());

        saveBestScore();
        prefs.flush();
    }

    public static boolean getContinued(){
        return continued;
    }

    public static void setContinued(boolean val){
        continued = val;
    }

    public static Preferences getPrefs(){
        return prefs;
    }

    public void saveBestScore() {
        if(!prefs.contains("bestScore")){
            prefs.putInteger("bestScore", Score.getScore());
        }
        if(prefs.getInteger("bestScore") < Score.getScore()){
                prefs.putInteger("bestScore", Score.getScore());
        }
    }

}

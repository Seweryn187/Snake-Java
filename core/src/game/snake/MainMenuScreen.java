package game.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final SnakeGame game;
    private final Texture menuScreenImg;
    private Stage stage;
    private final float screenWidth = Gdx.graphics.getWidth();
    private final float screenHeight = Gdx.graphics.getHeight();

    public MainMenuScreen(final SnakeGame gam) {
        game = gam;
        menuScreenImg = new Texture("img/MenuImg.jpeg");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        game.batch.begin();
        game.batch.draw(menuScreenImg, 0, 0);
        game.font.draw(game.batch, "Snake game", screenWidth - 810, screenHeight - 130);
        game.batch.end();
        stage.act();
        stage.draw();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.setScreen(new GameScreen(game));
            dispose();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        Table table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Button buttonPlay = new TextButton("PLAY", skin,"small");
        buttonPlay.setSize(screenWidth/15,screenHeight/13);
        buttonPlay.setPosition(screenWidth/2 - 52, screenHeight/2 + 150);
        buttonPlay.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        final Button buttonExit = new TextButton("EXIT", skin,"small");
        buttonExit.setSize(screenWidth/15,screenHeight/13);
        buttonExit.setPosition(screenWidth/2 - 52, screenHeight/2 + 50);
        buttonExit.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonExit);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        menuScreenImg.dispose();
        stage.dispose();
    }
}

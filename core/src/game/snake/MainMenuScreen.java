package game.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final SnakeGame game;
    private final Texture menuScreenImg;

    public MainMenuScreen(final SnakeGame gam) {
        game = gam;
        menuScreenImg = new Texture("img/MenuImg.jpeg");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        game.batch.begin();
        game.batch.draw(menuScreenImg, 0, 0);
        game.font.draw(game.batch, "Snake game", 630, 750);
        game.font.draw(game.batch, "Click anywhere to begin!", 560, 700);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
    public void dispose() {
        menuScreenImg.dispose();
    }
}

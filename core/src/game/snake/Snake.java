package game.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.List;

enum MovementDirection { LEFT, UP, RIGHT, DOWN }

public class Snake {

    private final Texture texture;
    private final List<GridPoint2> snakeSegments;
    private MovementDirection direction;
    private float timeElapsedSinceLastMove;
    private boolean canChangeDirection;

    public Snake(Texture texture) {

        this.texture = texture;
        snakeSegments = new ArrayList<>();
    }

    public void initialize() {

        int startPositionX = 720;
        int startPositionY = 420;
        timeElapsedSinceLastMove = 0;
        direction = MovementDirection.RIGHT;
        snakeSegments.clear();
        snakeSegments.add(new GridPoint2(startPositionX + 150, startPositionY));
        snakeSegments.add(new GridPoint2(startPositionX + 120, startPositionY));
        snakeSegments.add(new GridPoint2(startPositionX + 90, startPositionY));
        snakeSegments.add(new GridPoint2(startPositionX + 60, startPositionY));
        snakeSegments.add(new GridPoint2(startPositionX + 30, startPositionY));
    }

    public void act(float deltaTime) {

        if (canChangeDirection) {
            handleDirectionChange();
        }

        timeElapsedSinceLastMove += deltaTime;

        if (timeElapsedSinceLastMove >= 0.1) {
            timeElapsedSinceLastMove = 0;
            canChangeDirection = true;
            move();
        }
    }

    public boolean isAppleFound(GridPoint2 applePosition) {

        return head().equals(applePosition);
    }

    public void extendSnake() {

        snakeSegments.add(new GridPoint2(snakeSegments.get(snakeSegments.size() - 1)));
    }

    public boolean hasHitHimself() {

        for (int i = 1; i < snakeSegments.size(); i++) {
            if (snakeSegments.get(i).equals(head())) {
                return true;
            }
        }
        return false;
    }

    public void draw(Batch batch) {

        for (GridPoint2 pos : snakeSegments) {
            batch.draw(texture, pos.x, pos.y);
        }
    }

    private void handleDirectionChange() {

        MovementDirection newDirection = direction;

        if ((Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) &&
                direction != MovementDirection.RIGHT ) {
            newDirection = MovementDirection.LEFT;
        }
        if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) &&
                direction != MovementDirection.LEFT) {
            newDirection = MovementDirection.RIGHT;
        }
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) &&
                direction != MovementDirection.DOWN) {
            newDirection = MovementDirection.UP;
        }
        if ((Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) &&
                direction != MovementDirection.UP) {
            newDirection = MovementDirection.DOWN;
        }
        if (direction != newDirection) {
            direction = newDirection;
            canChangeDirection = false;
        }
    }

    private void move() {

        for (int i = snakeSegments.size() - 1; i > 0; i--) {
            snakeSegments.get(i).set(snakeSegments.get(i - 1));
        }

        int segmentWidth = texture.getWidth();
        int segmentHeight = texture.getWidth();

        int lastWindowSegmentX = Gdx.graphics.getWidth() - segmentWidth;
        int lastWindowSegmentY = Gdx.graphics.getHeight() - segmentHeight;
        GridPoint2 head = head();

        switch (direction) {
            case LEFT:
                head.x = (head.x == 0) ? lastWindowSegmentX : head.x - segmentWidth;
                break;
            case UP:
                head.y = (head.y == lastWindowSegmentY) ? 0 : head.y + segmentHeight;
                break;
            case RIGHT:
                head.x = (head.x == lastWindowSegmentX) ? 0 : head.x + segmentWidth;
                break;
            case DOWN:
                head.y = (head.y == 0) ? lastWindowSegmentY : head.y - segmentHeight;
                break;
        }
    }

    private GridPoint2 head() {
        return snakeSegments.get(0);
    }

}

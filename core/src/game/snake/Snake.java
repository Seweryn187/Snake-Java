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

    private static final int SEGMENT_WIDTH = 30;
    private static final int SEGMENT_HEIGHT = 30;

    private static final int LAST_POSSIBLE_X_POSITION
            = Gdx.graphics.getWidth() - SEGMENT_WIDTH;
    private static final int LAST_POSSIBLE_Y_POSITION
            = Gdx.graphics.getHeight() - SEGMENT_HEIGHT;

    private final Texture tHeadUp;
    private final Texture tHeadDown;
    private final Texture tHeadLeft;
    private final Texture tHeadRight;
    private final Texture tBody;
    private final Texture tTailUp;
    private final Texture tTailDown;
    private final Texture tTailRight;
    private final Texture tTailLeft;
    private final List<GridPoint2> snakeSegments;
    private MovementDirection direction;
    private MovementDirection tailDirection;
    private float timeElapsedSinceLastMove;
    private boolean canChangeDirection;

    public Snake(Texture tHeadUp, Texture tHeadDown, Texture tHeadLeft, Texture tHeadRight, Texture tBody, Texture tTailUp,
                 Texture tTailDown, Texture tTailRight, Texture tTailLeft) {

        this.tHeadUp = tHeadUp;
        this.tHeadDown= tHeadDown;
        this.tHeadLeft = tHeadLeft;
        this.tHeadRight = tHeadRight;
        this.tBody = tBody;
        this.tTailUp = tTailUp;
        this.tTailDown = tTailDown;
        this.tTailRight = tTailRight;
        this.tTailLeft = tTailLeft;
        snakeSegments = new ArrayList<>();
    }

    public void initialize() {

        if(GameScreen.getContinued()){

            timeElapsedSinceLastMove = 0;
            direction = Enum.valueOf(MovementDirection.class,GameScreen.getPrefs().getString("movementDirection"));
            tailDirection = Enum.valueOf(MovementDirection.class,GameScreen.getPrefs().getString("tailMovementDirection"));
            snakeSegments.clear();
            for(int i = 0; i < GameScreen.getPrefs().getInteger("numberOfSegments"); i++){
                snakeSegments.add(new GridPoint2(GameScreen.getPrefs().getInteger("x"+i),
                        GameScreen.getPrefs().getInteger("y"+i)));
            }

        }
        else{

            int startPositionX = 720;
            int startPositionY = 420;
            timeElapsedSinceLastMove = 0;
            direction = MovementDirection.RIGHT;
            tailDirection = MovementDirection.RIGHT;
            snakeSegments.clear();
            snakeSegments.add(new GridPoint2(startPositionX + 150, startPositionY));
            snakeSegments.add(new GridPoint2(startPositionX + 120, startPositionY));
            snakeSegments.add(new GridPoint2(startPositionX + 90, startPositionY));
            snakeSegments.add(new GridPoint2(startPositionX + 60, startPositionY));
            snakeSegments.add(new GridPoint2(startPositionX + 30, startPositionY));
        }
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

        determineTailDirection();
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

        for (int i = 1; i < snakeSegments.size() - 1; i++) {
            GridPoint2 body = snakeSegments.get(i);
            batch.draw(tBody, body.x, body.y);
        }

        GridPoint2 tail = snakeSegments.get(tailIndex());
        switch(tailDirection){
            case UP:{
                batch.draw(tTailDown, tail.x, tail.y);
                break;
            }
            case DOWN:{
                batch.draw(tTailUp, tail.x, tail.y);
                break;
            }
            case LEFT:{
                batch.draw(tTailRight, tail.x, tail.y);
                break;
            }
            case RIGHT:{
                batch.draw(tTailLeft, tail.x, tail.y);
                break;
            }
        }

        switch(direction){
            case UP:{
                batch.draw(tHeadUp, head().x, head().y);
                break;
            }
            case DOWN:{
                batch.draw(tHeadDown, head().x, head().y);
                break;
            }
            case LEFT:{
                batch.draw(tHeadLeft, head().x, head().y);
                break;
            }
            case RIGHT:{
                batch.draw(tHeadRight, head().x, head().y);
                break;
            }
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

        GridPoint2 head = head();

        switch (direction) {
            case LEFT:
                head.x = (head.x == 0) ? LAST_POSSIBLE_X_POSITION : head.x - SEGMENT_WIDTH;
                break;
            case UP:
                head.y = (head.y == LAST_POSSIBLE_Y_POSITION) ? 0 : head.y + SEGMENT_HEIGHT;
                break;
            case RIGHT:
                head.x = (head.x == LAST_POSSIBLE_X_POSITION) ? 0 : head.x + SEGMENT_WIDTH;
                break;
            case DOWN:
                head.y = (head.y == 0) ? LAST_POSSIBLE_Y_POSITION : head.y - SEGMENT_HEIGHT;
                break;
        }
    }

    private void determineTailDirection() {

        GridPoint2 segmentBeforeTail = snakeSegments.get(tailIndex() - 1);
        GridPoint2 tail = snakeSegments.get(tailIndex());

        if (tail.x == 0 && segmentBeforeTail.x == LAST_POSSIBLE_X_POSITION) {

            tailDirection = MovementDirection.LEFT;

        } else if (tail.x == LAST_POSSIBLE_X_POSITION && segmentBeforeTail.x == 0) {

            tailDirection = MovementDirection.RIGHT;

        } else if (tail.y == 0 && segmentBeforeTail.y == LAST_POSSIBLE_Y_POSITION) {

            tailDirection = MovementDirection.DOWN;

        } else if (tail.y == LAST_POSSIBLE_Y_POSITION && segmentBeforeTail.y == 0) {

            tailDirection = MovementDirection.UP;

        }
        else if (segmentBeforeTail.x > tail.x) {

            tailDirection = MovementDirection.RIGHT;

        } else if (segmentBeforeTail.x < tail.x) {

            tailDirection = MovementDirection.LEFT;

        } else if (segmentBeforeTail.y > tail.y) {

            tailDirection = MovementDirection.UP;

        } else if (segmentBeforeTail.y < tail.y) {

            tailDirection = MovementDirection.DOWN;
        }
    }

    private GridPoint2 head() {
        return snakeSegments.get(0);
    }

    private int tailIndex() { return snakeSegments.size() - 1; }

    public List<GridPoint2> getSnakeSegmentPositions() {
        return snakeSegments;
    }

    public MovementDirection getDirection(){
        return direction;
    }

    public MovementDirection getTailDirection(){
        return tailDirection;
    }
}

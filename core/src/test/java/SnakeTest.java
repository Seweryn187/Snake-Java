import com.badlogic.gdx.math.GridPoint2;
import game.snake.Objects.Apple;
import game.snake.Objects.Snake;
import game.snake.Utility.MovementDirection;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnakeTest {

    Apple apple = new Apple();

    Snake snake = new Snake();

    @Test
    public void isAppleFoundTest() {
        snake.snakeSegments.add(new GridPoint2(10, 10));
        snake.snakeSegments.add(new GridPoint2(40, 10));
        snake.snakeSegments.add(new GridPoint2(70, 10));
        snake.snakeSegments.add(new GridPoint2(100, 10));
        snake.snakeSegments.add(new GridPoint2(130, 10));
        apple.setPosition(new GridPoint2(10,50));
        assertFalse(snake.isAppleFound(apple.getPosition()));
        apple.setPosition(new GridPoint2(10,10));
        assertTrue(snake.isAppleFound(apple.getPosition()));
    }

    @Test
    public void extendSnakeTest() {
        snake.snakeSegments.add(new GridPoint2(10, 10));
        snake.snakeSegments.add(new GridPoint2(40, 10));
        snake.snakeSegments.add(new GridPoint2(70, 10));
        snake.snakeSegments.add(new GridPoint2(100, 10));
        snake.snakeSegments.add(new GridPoint2(130, 10));
        snake.extendSnake();
        assertEquals(snake.getSnakeSegmentPositions().size(), 6);
    }

    @Test
    public void hasHitHimselfTest() {
        snake.snakeSegments.add(new GridPoint2(10, 10));
        snake.snakeSegments.add(new GridPoint2(40, 10));
        snake.snakeSegments.add(new GridPoint2(70, 10));
        snake.snakeSegments.add(new GridPoint2(10, 10));
        snake.snakeSegments.add(new GridPoint2(130, 10));
        assertTrue(snake.hasHitHimself());
    }

    @Test
    public void moveTest() {
        snake.snakeSegments.add(new GridPoint2(10, 10));
        snake.snakeSegments.add(new GridPoint2(40, 10));
        snake.snakeSegments.add(new GridPoint2(70, 10));
        snake.snakeSegments.add(new GridPoint2(100, 10));
        snake.snakeSegments.add(new GridPoint2(130, 10));
        snake.direction = MovementDirection.DOWN;
        snake.move();
        assertEquals(snake.head(), new GridPoint2(10, -20));
    }

    @Test
    public void determineTailDirectionTest() {
        snake.snakeSegments.add(new GridPoint2(10, 10));
        snake.snakeSegments.add(new GridPoint2(40, 10));
        snake.snakeSegments.add(new GridPoint2(70, 10));
        snake.snakeSegments.add(new GridPoint2(100, 10));
        snake.snakeSegments.add(new GridPoint2(130, 10));
        snake.determineTailDirection();
        assertEquals(snake.getTailDirection(), MovementDirection.LEFT);
    }
}

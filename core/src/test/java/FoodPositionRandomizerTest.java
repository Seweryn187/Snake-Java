import com.badlogic.gdx.math.GridPoint2;
import game.snake.Objects.Apple;
import game.snake.Objects.Snake;
import game.snake.Utility.FoodPositionRandomizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertNotNull;

public class FoodPositionRandomizerTest {

    Snake snake = new Snake();
    Apple apple = new Apple();
    FoodPositionRandomizer fpr = new FoodPositionRandomizer();

    @Test
    public void FoodPositionRandomizerActionTest() {
        snake.snakeSegments.add(new GridPoint2(10, 10));
        snake.snakeSegments.add(new GridPoint2(40, 10));
        snake.snakeSegments.add(new GridPoint2(70, 10));
        snake.snakeSegments.add(new GridPoint2(100, 10));
        snake.snakeSegments.add(new GridPoint2(130, 10));

        List<GridPoint2> occupiedPositions = snake.getSnakeSegmentPositions();
        apple.setPosition(fpr.getRandomAvailablePosition(occupiedPositions));
        assertNotNull(apple.getPosition());
    }

}

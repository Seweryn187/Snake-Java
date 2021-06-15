
import org.junit.Test;
import org.testng.Assert;

import static game.snake.Utility.Score.*;

public class ScoreTest {

    @Test
    public void testGet() {
        int score = 0;
        Assert.assertEquals(0, getScore());
    }

    @Test
    public void testSet() {
        int score = 0;
        setScore(10);
        Assert.assertEquals(10, getScore());
    }

    @Test
    public void testReset() {
        int score = 0;
        setScore(10);
        resetScore();
        Assert.assertEquals(0, getScore());
    }

}

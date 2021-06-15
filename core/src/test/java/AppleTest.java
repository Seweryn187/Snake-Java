import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import game.snake.Screen.SnakeGame;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class AppleTest {

    private final GridPoint2 position;
    private SpriteBatch batch;

    private SnakeGame game;

    public AppleTest() {
        this.position = new GridPoint2();
    }

    public void setPosition(GridPoint2 position) {
        this.position.set(position);
    }

    public GridPoint2 getPosition() {
        return this.position;
    }

    @Test
    public void appleCreationTest() {
        AppleTest appleTest = new AppleTest();
        assertNotNull(appleTest);
    }

    @Test
    public void appleSetPositionTest() {
        AppleTest appleTest = new AppleTest();
        GridPoint2 appleCoordinates = new GridPoint2(2,2);
        appleTest.setPosition(appleCoordinates);
        assertEquals(appleTest.position.x, 2);
        assertEquals(appleTest.position.y, 2);
    }

    @Test
    public void appleGetPositionTest() {
        AppleTest appleTest = new AppleTest();
        GridPoint2 appleCoordinates = new GridPoint2(2,2);
        appleTest.setPosition(appleCoordinates);
        assertEquals(appleTest.getPosition(), appleCoordinates);
    }


}

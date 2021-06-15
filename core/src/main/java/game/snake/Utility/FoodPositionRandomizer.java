package game.snake.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FoodPositionRandomizer {

    private static final int SEGMENT_WIDTH = 30;
    private static final int SEGMENT_HEIGHT = 30;

    private final List<GridPoint2> allPossiblePositions;

    public FoodPositionRandomizer() {

        int numberOfXPositions = 1440 / SEGMENT_WIDTH;
        int numberOfYPositions = 810 / SEGMENT_HEIGHT;

        allPossiblePositions = new ArrayList<>();

        for (int i = 0; i < numberOfXPositions; i++) {
            for (int j = 0; j < numberOfYPositions; j++) {
                allPossiblePositions.add(new GridPoint2(i * SEGMENT_WIDTH, j * SEGMENT_HEIGHT)
                );
            }
        }
    }

    public GridPoint2 getRandomAvailablePosition(List<GridPoint2> occupiedPositions){

        HashSet<GridPoint2> unavailablePositions = new HashSet<>(occupiedPositions);

        List<GridPoint2> availablePositions = new ArrayList<>();

        for (GridPoint2 position : allPossiblePositions) {
            if (!unavailablePositions.contains(position)) {
                availablePositions.add(position);
            }
        }

        return availablePositions.get((int) (Math.random() * availablePositions.size()));
    }
}



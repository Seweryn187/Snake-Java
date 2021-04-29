package game.snake;

public class Score {

    private static int score;

    public static void score() {
        score += 10;
    }

    public static int getScore() {
        return score;
    }

    public static void resetScore() {
        score = 0;
    }
}

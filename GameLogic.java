import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
import java.util.Map;

// This is our "Backend" class.
// It knows NOTHING about the UI (no JFrame, no JButton).
// It just manages the game's rules and state.
public class GameLogic {

    private int secretNumber;
    private int attemptsLeft;
    private int totalScore;
    private final int MAX_ATTEMPTS = 10;

    public GameLogic() {
        this.totalScore = 0;
        startNewGame();
    }

    public void startNewGame() {
        this.secretNumber = ThreadLocalRandom.current().nextInt(1, 101);
        this.attemptsLeft = MAX_ATTEMPTS;
    }

    // This is the main method the UI will call.
    // It returns a Map, which is a simple way to send
    // multiple pieces of information back to the UI.
    public Map<String, Object> checkGuess(String guessText) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int guess = Integer.parseInt(guessText);
            attemptsLeft--;

            if (guess == secretNumber) {
                totalScore += (attemptsLeft + 1) * 10;
                result.put("message", "Congratulations! You guessed it!");
                result.put("color", new java.awt.Color(34, 139, 34)); // Dark Green
                result.put("gameOver", true);

            } else if (attemptsLeft <= 0) {
                result.put("message", "Game Over! The number was " + secretNumber);
                result.put("color", java.awt.Color.GRAY);
                result.put("gameOver", true);

            } else if (guess < secretNumber) {
                result.put("message", "Too low!");
                result.put("color", new java.awt.Color(255, 165, 0)); // Orange
                result.put("gameOver", false);

            } else {
                result.put("message", "Too high!");
                result.put("color", java.awt.Color.RED);
                result.put("gameOver", false);
            }

        } catch (NumberFormatException ex) {
            result.put("message", "Please enter a valid number.");
            result.put("color", java.awt.Color.RED);
            result.put("gameOver", false);
        }
        
        // Add the current game state to the result
        result.put("attemptsLeft", attemptsLeft);
        result.put("totalScore", totalScore);
        
        return result;
    }

    // --- Getter Methods ---
    // The UI needs these to get the initial values when a new game starts.
    
    public int getAttemptsLeft() {
        return this.attemptsLeft;
    }

    public int getTotalScore() {
        return this.totalScore;
    }
}

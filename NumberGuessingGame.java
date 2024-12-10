import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private int numberToGuess;
    private int attemptsLeft;
    private int currentRound;
    private int totalScore;
    private final int MAX_ROUNDS = 3;
    private final int MAX_ATTEMPTS = 7;
    private final int MAX_NUMBER = 100;

    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JLabel roundLabel;
    private JButton guessButton;
    private JButton nextRoundButton;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        guessField = new JTextField(10);
        messageLabel = new JLabel("Guess a number between 1 and " + MAX_NUMBER);
        attemptsLabel = new JLabel("Attempts left: " + MAX_ATTEMPTS);
        scoreLabel = new JLabel("Score: 0");
        roundLabel = new JLabel("Round: 1/" + MAX_ROUNDS);
        guessButton = new JButton("Guess");
        nextRoundButton = new JButton("Next Round");
        nextRoundButton.setEnabled(false);

        JPanel inputPanel = new JPanel();
        inputPanel.add(guessField);
        inputPanel.add(guessButton);

        add(messageLabel);
        add(inputPanel);
        add(attemptsLabel);
        add(scoreLabel);
        add(roundLabel);
        add(nextRoundButton);

        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        nextRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentRound++;
                if (currentRound <= MAX_ROUNDS) {
                    startNewRound();
                } else {
                    endGame();
                }
            }
        });

        startNewGame();
    }

    private void startNewGame() {
        currentRound = 1;
        totalScore = 0;
        startNewRound();
    }

    private void startNewRound() {
        numberToGuess = new Random().nextInt(MAX_NUMBER) + 1;
        attemptsLeft = MAX_ATTEMPTS;
        updateLabels();
        guessField.setText("");
        messageLabel.setText("Guess a number between 1 and " + MAX_NUMBER);
        guessButton.setEnabled(true);
        nextRoundButton.setEnabled(false);
        // Removed: currentRound++;
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attemptsLeft--;

            if (guess == numberToGuess) {
                int roundScore = attemptsLeft + 1;
                totalScore += roundScore;
                messageLabel.setText("Correct! You guessed it in " + (MAX_ATTEMPTS - attemptsLeft) + " attempts.");
                guessButton.setEnabled(false);
                if (currentRound < MAX_ROUNDS) {
                    nextRoundButton.setEnabled(true);
                } else {
                    endGame();
                }
            } else if (attemptsLeft == 0) {
                messageLabel.setText("Round Over! The number was " + numberToGuess);
                guessButton.setEnabled(false);
                if (currentRound < MAX_ROUNDS) {
                    nextRoundButton.setEnabled(true);
                } else {
                    endGame();
                }
            } else if (guess < numberToGuess) {
                messageLabel.setText("Too low! Try again.");
            } else {
                messageLabel.setText("Too high! Try again.");
            }

            updateLabels();
        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid number.");
        }
    }

    private void updateLabels() {
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        scoreLabel.setText("Score: " + totalScore);
        roundLabel.setText("Round: " + currentRound + "/" + MAX_ROUNDS);
    }

    private void endGame() {
        messageLabel.setText("Game Over! Your final score is " + totalScore);
        guessField.setEnabled(false);
        guessButton.setEnabled(false);
        nextRoundButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame().setVisible(true);
            }
        });
    }
}


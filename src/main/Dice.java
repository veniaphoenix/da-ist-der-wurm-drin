package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Dice extends JFrame {
    private static final long serialVersionUID = -3862432823066101583L;
	private JPanel diePanel;

    public Dice() {
        // Set up the JFrame
        super("Colored Dice Roller Visual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        // Create components
        JButton rollButton = new JButton("Roll");
        diePanel = new JPanel();
        diePanel.setPreferredSize(new Dimension(100, 100));

        // Create an instance of the DiceRoller class
        DiceRoller diceRoller = new DiceRoller();

        // Add action listener to the roll button
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the rollDie method of the DiceRoller instance
                diceRoller.rollDie();

                // Update the die panel color based on the result
                diePanel.setBackground(diceRoller.getCurrentColor());

                // Perform additional actions after rolling the dice
                performAdditionalActions(diceRoller.getResult());
            }
        });

        // Set up layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(rollButton);
        add(diePanel);

        // Set frame visibility
        setVisible(true);
    }

    private void performAdditionalActions(int result) {
        // Example: Display a message in the console
        System.out.println("You rolled a " + result);
        // Add more actions based on the result as needed
    }

    // public static void main(String[] args) {
    // // Create an instance of the Dice class
    // SwingUtilities.invokeLater(Dice::new);
    // }
    public class DiceRoller {
        private final Color[] colors = { Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA };
        private Color currentColor;
        private int result;

        public void rollDie() {
            // Generate a random number between 0 and 5 to select a color
            Random random = new Random();
            int colorIndex = random.nextInt(colors.length);

            // Set the current color based on the random index
            currentColor = colors[colorIndex];

            // Set the result to the selected color index + 1
            result = colorIndex + 1;
        }

        public Color getCurrentColor() {
            return currentColor;
        }

        public int getResult() {
            return result;
        }
    }
}

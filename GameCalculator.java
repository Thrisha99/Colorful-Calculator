import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class GameCalculator extends JFrame implements ActionListener {

    JTextField display;
    String operator = "";
    double num1, num2, result;

    public GameCalculator() {
        setTitle("Game-Style Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 60)); // Dark blue-gray background

        // Display box
        display = new JTextField();
        display.setBounds(30, 30, 320, 50);
        display.setFont(new Font("Courier New", Font.BOLD, 26));
        display.setEditable(false);
        display.setBackground(new Color(255, 255, 240)); // Ivory
        display.setForeground(Color.DARK_GRAY);
        display.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        add(display);

        // Button labels
        String[] buttonLabels = {
            "7", "8", "9", "/", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            "0", "C", "=", "+"
        };

        int x = 30, y = 100;

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton btn = new JButton(buttonLabels[i]);
            btn.setBounds(x, y, 70, 50);
            btn.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            btn.setFocusPainted(false);

            // Color coding
            String label = buttonLabels[i];
            if (label.matches("[0-9]")) {
                btn.setBackground(new Color(173, 216, 230)); // Light Blue for digits
                btn.setForeground(Color.BLACK);
            } else if (label.equals("C")) {
                btn.setBackground(new Color(255, 99, 71)); // Tomato for Clear
                btn.setForeground(Color.WHITE);
            } else if (label.equals("=")) {
                btn.setBackground(new Color(60, 179, 113)); // MediumSeaGreen for Equal
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(new Color(255, 215, 0)); // Gold for operators
                btn.setForeground(Color.BLACK);
            }

            btn.addActionListener(this);
            add(btn);

            x += 80;
            if ((i + 1) % 4 == 0) {
                x = 30;
                y += 60;
            }
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        playClickSound(); // 🔊 Play sound on every click

        String cmd = e.getActionCommand();

        if (cmd.matches("[0-9]")) {
            display.setText(display.getText() + cmd);
        } else if (cmd.equals("C")) {
            display.setText("");
            operator = "";
        } else if (cmd.equals("=")) {
            try {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/":
                        if (num2 == 0) {
                            display.setText("Error");
                            return;
                        }
                        result = num1 / num2;
                        break;
                }
                display.setText("" + result);
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else {
            try {
                num1 = Double.parseDouble(display.getText());
                operator = cmd;
                display.setText("");
            } catch (Exception ex) {
                display.setText("Error");
            }
        }
    }

    // 🔊 Sound method
    public void playClickSound() {
        try {
            File soundFile = new File("click.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error playing sound: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new GameCalculator();
    }
}

package net.hyperiongames.calculator;

import lombok.Getter;
import net.hyperiongames.calculator.listener.ButtonListener;
import net.hyperiongames.calculator.utils.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

@Getter
public class Calculator extends JFrame {

    @Getter private static Calculator instance;

    private JTextField textField;

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            Calculator calculatorApp = new Calculator();
            calculatorApp.setVisible(true);
        });
    }

    public Calculator(){
        instance = this;

        System.out.println("Starting Calculator...");

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        setupButtons();

        stopwatch.stop();
        System.out.println("Calculator has started in " + stopwatch.elapsedTime(TimeUnit.MILLISECONDS) + "ms.");
    }

    public void setupButtons(){
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("images/img.png");
        Image image = icon.getImage();
        setIconImage(image);

        textField = new JTextField();
        textField.setEditable(false);

        textField.setFont(new Font(Font.DIALOG_INPUT, textField.getFont().getStyle(), 50));

        add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));

        String[] buttonLabels = {
                "", "", "^", "C",
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for(String label : buttonLabels){
            JButton button = new JButton(label);
            button.addActionListener(new ButtonListener());
            button.setFont(new Font(Font.DIALOG_INPUT, button.getFont().getStyle(), 50));

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }
}

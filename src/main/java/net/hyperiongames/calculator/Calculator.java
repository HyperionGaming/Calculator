package net.hyperiongames.calculator;

import net.hyperiongames.calculator.utils.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Calculator extends JFrame implements ActionListener {

    public static Calculator instance;

    public JTextField textField;
    private String operator = "";
    private double firstOperand;

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
        textField.setEditable(true);

        textField.setFont(new Font(Font.SANS_SERIF, textField.getFont().getStyle(), 50));

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
            button.addActionListener(this);
            button.setFont(new Font(Font.DIALOG_INPUT, button.getFont().getStyle(), 50));

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        String command = event.getActionCommand();
        if(command.equals("C")){
            textField.setText("");

        } else if(!(textField.getText().equalsIgnoreCase("NaN") || textField.getText().equalsIgnoreCase("Infinity"))){
            if(command.matches("[0-9]")){
                textField.setText(textField.getText() + command);

            } else if(command.equals(".") && !textField.getText().isEmpty()){
                if(!textField.getText().contains(".")){
                    textField.setText(textField.getText() + ".");
                }

            } else if(command.matches("[×÷\\-+^]") && !textField.getText().isEmpty()){
                firstOperand = Double.parseDouble(textField.getText());
                operator = command;
                textField.setText("");

            } else if(command.equals("=") && !textField.getText().isEmpty()){
                double secondOperand = Double.parseDouble(textField.getText());
                double result = (Double.isInfinite(performOperation(firstOperand, secondOperand, operator)) || (Double.isNaN(performOperation(firstOperand, secondOperand, operator))) ? Double.NaN : performOperation(firstOperand, secondOperand, operator));
                textField.setText(String.valueOf(result).replace(".0", ""));
            }
        }
    }

    private double performOperation(double operand1, double operand2, String operator){
        return switch (operator){
            case ("+") -> operand1 + operand2;
            case ("-") -> operand1 - operand2;
            case ("×") -> operand1 * operand2;
            case ("^") -> Math.pow(operand1, operand2);
            case ("÷") -> {
                if(operand2 == 0){
                    JOptionPane.showMessageDialog(Calculator.instance, "Cannot divide by zero");
                    yield 0;
                }
                yield operand1 / operand2;
            }
            default -> 0;
        };
    }
}

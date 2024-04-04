package net.hyperiongames.calculator.listener;

import net.hyperiongames.calculator.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    private double firstOperand = 0;
    private String operator = "";

    @Override
    public void actionPerformed(ActionEvent event){
        JTextField textField = Calculator.getInstance().getTextField();
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
                double result = ((Double.isInfinite(performOperation(firstOperand, secondOperand, operator)) || (Double.isNaN(performOperation(firstOperand, secondOperand, operator))) ? Double.NaN : performOperation(firstOperand, secondOperand, operator)));
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
                    JOptionPane.showMessageDialog(Calculator.getInstance(), "Cannot divide by zero");
                    yield 0;
                }
                yield operand1 / operand2;
            }
            default -> 0;
        };
    }
}

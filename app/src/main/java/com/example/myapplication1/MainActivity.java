package com.example.myapplication1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private String currentInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);

        // Number Buttons
        Button button0 = findViewById(R.id.buttonZero);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);

        // Operator Buttons
        Button buttonAddPlus = findViewById(R.id.buttonAdditionPlus);
        Button buttonSub = findViewById(R.id.buttonSubtractionMinus);
        Button buttonMul = findViewById(R.id.buttonMultiplicationX);
        Button buttonDiv = findViewById(R.id.buttonDivisionSlash);
        Button buttonPercentage = findViewById(R.id.buttonPercentage);
        Button buttonEqualTo = findViewById(R.id.buttonEqual);

        // Special Buttons
        Button buttonAllClear = findViewById(R.id.buttonAllClear);
        Button buttonPointDot = findViewById(R.id.buttonPointDot);
        Button buttonBackspaceDelete = findViewById(R.id.buttonBackSpaceDelete);
        Button buttonParenthesis = findViewById(R.id.buttonParenthesis);
        Button buttonPi = findViewById(R.id.buttonPi);
        Button buttonSqrRoot = findViewById(R.id.buttonSqr);
        Button buttonFactorial = findViewById(R.id.buttonFactorial);
        Button buttonExponent = findViewById(R.id.buttonExponent);

        // Set OnClickListener for Buttons
        button0.setOnClickListener(view -> appendToInput("0"));
        button1.setOnClickListener(view -> appendToInput("1"));
        button2.setOnClickListener(view -> appendToInput("2"));
        button3.setOnClickListener(view -> appendToInput("3"));
        button4.setOnClickListener(view -> appendToInput("4"));
        button5.setOnClickListener(view -> appendToInput("5"));
        button6.setOnClickListener(view -> appendToInput("6"));
        button7.setOnClickListener(view -> appendToInput("7"));
        button8.setOnClickListener(view -> appendToInput("8"));
        button9.setOnClickListener(view -> appendToInput("9"));

        buttonAddPlus.setOnClickListener(view -> appendToInput("+"));
        buttonSub.setOnClickListener(view -> appendToInput("-"));
        buttonMul.setOnClickListener(view -> appendToInput("*"));
        buttonDiv.setOnClickListener(view -> appendToInput("/"));
        buttonPercentage.setOnClickListener(view -> appendToInput("%"));
        buttonPointDot.setOnClickListener(view -> appendToInput("."));
        buttonParenthesis.setOnClickListener(view -> appendToInput("()"));
        buttonSqrRoot.setOnClickListener(view -> appendToInput("√"));
        buttonPi.setOnClickListener(view -> appendToInput(String.valueOf(Math.PI)));
        buttonFactorial.setOnClickListener(view -> appendToInput("!"));
        buttonExponent.setOnClickListener(view -> appendToInput("^"));

        buttonAllClear.setOnClickListener(view -> clearInput());
        buttonBackspaceDelete.setOnClickListener(view -> deleteLastChar());
        buttonEqualTo.setOnClickListener(view -> calculateResult());
    }

    // Enhanced appendToInput method
    private void appendToInput(String value) {
        // Check if the input is a dot
        if (value.equals(".")) {
            // Get the last segment of input
            String[] parts = currentInput.split("[+\\-*/]");
            String lastPart = parts[parts.length - 1];

            // Check if there's already a dot in the last part
            if (lastPart.contains(".")) {
                // If so, do not add another dot
                return;
            }

            // If the input is empty or ends with an operator, add "0" before the dot
            if (currentInput.isEmpty() || currentInput.matches(".*[+\\-*/]$")) {
                currentInput += "0";
            }
        }

        // Append the value to the current input
        currentInput += value;
        editText.setText(currentInput);
    }

    private void clearInput() {
        currentInput = "";
        editText.setText(currentInput);
    }

    private void deleteLastChar() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            editText.setText(currentInput);
        }
    }

    private void calculateResult() {
        try {
            // Replace square root symbol
            if (currentInput.contains("√")) {
                currentInput = currentInput.replace("√", "sqrt");
            }

            // Evaluate expression
            double result = evaluate(currentInput);

            // Set the result to the EditText
            editText.setText(String.valueOf(result));
            // Clear the currentInput
            currentInput = String.valueOf(result);
        } catch (Exception e) {
            e.printStackTrace();
            editText.setText("Error");
            currentInput = "";
        }
    }

    private double evaluate(String expression) {
        // Handle factorial
        while (expression.contains("!")) {
            int index = expression.indexOf("!");
            int numberStart = index - 1;

            while (numberStart >= 0 && Character.isDigit(expression.charAt(numberStart))) {
                numberStart--;
            }
            numberStart++;
            int number = Integer.parseInt(expression.substring(numberStart, index));
            expression = expression.substring(0, numberStart) + factorial(number) + expression.substring(index + 1);
        }

        // Handle exponentiation
        while (expression.contains("^")) {
            int index = expression.indexOf("^");
            double base = Double.parseDouble(expression.substring(0, index));
            double exponent = Double.parseDouble(expression.substring(index + 1));
            expression = String.valueOf(Math.pow(base, exponent));
        }

        // Parse and calculate the expression
        // Note: This is a very simple parser; a more robust implementation would be needed for complex expressions
        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");

        double result = Double.parseDouble(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double value = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += value;
                    break;
                case "-":
                    result -= value;
                    break;
                case "*":
                    result *= value;
                    break;
                case "/":
                    result /= value;
                    break;
            }
        }
        return result;
    }

    private int factorial(int n) {
        if (n == 0) return 1;
        return n * factorial(n - 1);
    }
}

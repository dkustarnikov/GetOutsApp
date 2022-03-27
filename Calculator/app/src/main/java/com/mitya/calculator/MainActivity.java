package com.mitya.calculator;

import static com.mitya.calculator.TopDisplayFragment.isAlreadyAnExpression;
import static com.mitya.calculator.TopDisplayFragment.isOperator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


public class MainActivity extends AppCompatActivity implements ButtonsGrid.OnButtonsGridListener {

    private static final String DISPLAY_TAG = "display";
    private static final String GRID_TAG = "buttonsGrid";

    TopDisplayFragment mDisplayFragment;
    ButtonsGrid mButtonsGridFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();
//        mButtonsGridFragment = (ButtonsGrid) fragmentManager.findFragmentByTag(GRID_TAG);
        if (mButtonsGridFragment == null) {
            mButtonsGridFragment = new ButtonsGrid();
            fragmentManager.beginTransaction().add(R.id.buttonsGridFragment, mButtonsGridFragment, GRID_TAG).commit();
        }

//        mDisplayFragment = (TopDisplayFragment) fragmentManager.findFragmentByTag(DISPLAY_TAG);
        if (mDisplayFragment == null) {
            mDisplayFragment = new TopDisplayFragment();
            fragmentManager.beginTransaction().add(R.id.topDisplayFragment, mDisplayFragment, DISPLAY_TAG).commit();
        }
    }

    public void numberClicked(View view) {
        TextView textView = (TextView) view;

        mDisplayFragment.addText(textView.getText().toString());
    }

    public void operatorClicked(View view) {
        TextView textView = (TextView) view;

        if (textView.getText().toString().equalsIgnoreCase("CE")) {
            //This is where we remove the last thing we entered
            String currentEntry = mDisplayFragment.mdisplayTextView.getText().toString();

            //If there is only 1 thing in the display, set it to 0. If it has a negation sign, remove that too
            if (currentEntry.contains("—") && currentEntry.length() == 2) {
                mDisplayFragment.mdisplayTextView.setText("0");
                return;
            } else if (currentEntry.length() == 1) {
                mDisplayFragment.mdisplayTextView.setText("0");
                return;
            }
            //Just remove the last entry
            currentEntry = currentEntry.substring(0, currentEntry.length() - 1);
            mDisplayFragment.mdisplayTextView.setText(currentEntry);
            return;

        } else if (textView.getText().toString().equalsIgnoreCase("C")) {
            //This is where we reset
            mDisplayFragment.mdisplayTextView.setText("0");
            return;
        }

        mDisplayFragment.addText(textView.getText().toString());
    }

    public void equalsClicked(View view) {
        //This is when we do all the calculations

        //Before anything, we check if its even an expression yet. If its not, we don't do anything

        //This is going to check if the user is trying to calculate a square root. If they are, I am going to just do it here.


        //If its not an expression
        if (!isAlreadyAnExpression(mDisplayFragment.mdisplayTextView.getText().toString())) {
            Toast.makeText(getApplicationContext(), "This is not an expression yet. Use an operator first", Toast.LENGTH_SHORT).show();
        } else {

            //First parse the expression.
            Expression expression = new Expression(mDisplayFragment.mdisplayTextView.getText().toString());

            Double answer = expression.calculate();

            if (answer < 0) {
                answer *= -1;
                mDisplayFragment.mdisplayTextView.setText("—" + answer);
            } else {
                mDisplayFragment.mdisplayTextView.setText(String.valueOf(answer));
            }

        }

    }

    @Override
    public void inputFromButtons(String text) {

    }

    private static class Expression {

        private double firstNumber;
        private double secondNumber;
        private char operator;

        public Expression(String str) {
            //This is where the parsing happens

            //Long dash symbol: '—'

            //This will work with the square root number
            if (str.charAt(0) == '√') {
                operator = '√';
                firstNumber = Double.parseDouble(str.substring(1));
                return;
            }


            //This is a multiplier for the first number. The second number has to be positive for now
            int multiplier = 1;
            if (str.charAt(0) == '—') {
                multiplier = -1;
            }

            int counter = 0;
            String numberString = "";

            if (multiplier == -1) counter++;
            while (true) {
                if (isOperator(str.charAt(counter + 1)) || counter == str.length() - 1) {

                    numberString += str.charAt(counter);
                    firstNumber = Double.parseDouble(numberString);
                    counter++;
                    break;
                }
                numberString += str.charAt(counter);
                counter++;
            }

            firstNumber *= multiplier;

            //Now we get the operator
            operator = str.charAt(counter);
            counter++;

            //Reset
            numberString = "";
            //Now we get the second number
            while (true) {
                if (counter == str.length() - 1) {

                    numberString += str.charAt(counter);
                    secondNumber = Double.parseDouble(numberString);
                    break;
                }
                numberString += str.charAt(counter);
                counter++;
            }

        }


        public double calculate() {
            switch (this.operator) {
                case '%':
                    return firstNumber % secondNumber;
                case '/':
                    return firstNumber / secondNumber;
                case 'X':
                    return firstNumber * secondNumber;
                case '-':
                    return firstNumber - secondNumber;
                case '+':
                    return firstNumber + secondNumber;
                case '√':
                    return Math.sqrt(firstNumber);
            }

            return 0.0;
        }

    }
}
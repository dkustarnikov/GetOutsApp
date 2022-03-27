package com.mitya.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopDisplayFragment extends Fragment {

    String TAG = "Logging";

    TextView mdisplayTextView;

    public TopDisplayFragment() {
        // Required empty public constructor
    }

    public static TopDisplayFragment newInstance(String param1, String param2) {
        TopDisplayFragment fragment = new TopDisplayFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "TopDisplayFragment onCreate method");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top_display, container, false);

        Log.i(TAG, "TopDisplayFragment inflate inside OnCreateView");

        mdisplayTextView = v.findViewById(R.id.numberDisplayTextView);

        return v;
    }

    public static boolean isOperator(Character c) {
        return c == '%' || c == '√' || c == '/' || c == 'X' || c == '-' || c == '+';
    }

    public static boolean isAlreadyAnExpression(String str) {
        if (str.charAt(0) == '√') {
            return true;
        }
        for (char c : str.toCharArray()) {
            if (isOperator(c)) {
                return true;
            }
        }
        return false;
    }

    public void addText(String str) {
        String currentText = mdisplayTextView.getText().toString();

        //If current Text has an operator, don't allow more operators. Or if its a negative number and the operator added is a squareRoot
        if (isOperator(str.charAt(0)) && isAlreadyAnExpression(currentText) || (currentText.charAt(0) == '—' && str.charAt(0) == '√')) {
            return;
        }

        //If we are trying to add a square root, add it to the front.
        if (str.charAt(0) == '√') {
            currentText = "√" + currentText;
            mdisplayTextView.setText(currentText);
            return;
        }


        if (str.equalsIgnoreCase("±")) {
            if (currentText.equalsIgnoreCase("0")) {
                //Do nothing
                return;
            } else if (currentText.charAt(0) == '—') {
                currentText = currentText.substring(1);
                mdisplayTextView.setText(currentText);
                return;
            } else {
                currentText = "—" + currentText;
                mdisplayTextView.setText(currentText);
                return;
            }
        }


        if (str.equalsIgnoreCase("•")) {
            str = ".";
            currentText += str;
            mdisplayTextView.setText(currentText);
            return;
        }

        if (!isOperator(str.charAt(0)) && currentText.equalsIgnoreCase("0")) {
            currentText = "";
        }

        currentText += str;

        mdisplayTextView.setText(currentText);
    }
}

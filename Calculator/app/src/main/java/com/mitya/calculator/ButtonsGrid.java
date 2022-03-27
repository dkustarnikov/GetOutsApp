package com.mitya.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ButtonsGrid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonsGrid extends Fragment {

    private OnButtonsGridListener mCallback;

    public ButtonsGrid() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ButtonsGrid.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonsGrid newInstance(String param1, String param2) {
        ButtonsGrid fragment = new ButtonsGrid();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buttons_grid, container, false);


        return v;
    }


    public interface OnButtonsGridListener {
        void inputFromButtons(String text);
    }

    // This method insures that the Activity has actually implemented our
    // listener and that it isn't null.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnButtonsGridListener) {
//            mCallback = (OnButtonsGridListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnButtonsGridListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


}
package com.willblaschko.android.avs.actions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.willblaschko.android.avs.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author will on 5/30/2016.
 */

public class DisplayCodeFragment extends Fragment {

    private final static String ARG_TITLE = "title";
    private final static String ARG_RAW_CODE = "raw_code";

    String title;
    int rawCode;

    public static DisplayCodeFragment getInstance(String title, @RawRes int rawCode){
        Bundle b = new Bundle();
        b.putString(ARG_TITLE, title);
        b.putInt(ARG_RAW_CODE, rawCode);
        DisplayCodeFragment fragment = new DisplayCodeFragment();
        fragment.setArguments(b);
        return fragment;
    }

    public DisplayCodeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() == null){
            return;
        }
        title = getArguments().getString(ARG_TITLE);
        rawCode = getArguments().getInt(ARG_RAW_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() != null) {
            getActivity().setTitle(title);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_code, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView text = (TextView) view.findViewById(R.id.code);

        StringBuilder builder = new StringBuilder();

        InputStream in = getResources().openRawResource(rawCode);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            while (line != null) {
                builder.append(line).append("\r\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        text.setText(builder.toString());

    }
}

package com.willblaschko.android.avs.actions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.willblaschko.android.avs.R;

/**
 * @author will on 5/30/2016.
 */

public class SendTextActionFragment extends BaseListenerFragment {

    EditText search;
    View button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_type, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search = (EditText) view.findViewById(R.id.search);
        button = view.findViewById(R.id.button);

        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    search();
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    private void search(){
        String text = search.getText().toString();
        alexaManager.sendTextRequest(text, getRequestCallback());
    }

    @Override
    public void startListening() {
        search.setText("");
        search.requestFocus();
    }

    @Override
    protected String getTitle() {
        return getString(R.string.fragment_action_send_text);
    }

    @Override
    protected int getRawCode() {
        return R.raw.code_text;
    }
}

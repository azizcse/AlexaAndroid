package com.willblaschko.android.avs.actions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.willblaschko.android.avs.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author will on 5/30/2016.
 */

public class SendPrerecordedActionFragment extends BaseListenerFragment {

    View button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_prerecorded, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    private void search(){
        try {
            InputStream is = getActivity().getAssets().open("intros/joke.raw");
            byte[] fileBytes=new byte[is.available()];
            is.read(fileBytes);
            is.close();
            alexaManager.sendAudioRequest(fileBytes, getRequestCallback());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void startListening() {

    }

    @Override
    protected String getTitle() {
        return getString(R.string.fragment_action_send_prerecorded);
    }

    @Override
    protected int getRawCode() {
        return R.raw.code_prerecorded;
    }
}

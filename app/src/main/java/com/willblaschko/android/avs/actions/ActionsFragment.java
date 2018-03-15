package com.willblaschko.android.avs.actions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.willblaschko.android.avs.R;
import com.willblaschko.android.avs.actions.adapter.ActionFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author will on 5/30/2016.
 */

public class ActionsFragment extends BaseListenerFragment {


    @Override
    public void startListening() {

    }

    @Override
    protected String getTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected int getRawCode() {
        return R.raw.code_base;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recycler);
        ActionFragmentAdapter adapter = new ActionFragmentAdapter(getItems());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private List<ActionFragmentAdapter.ActionFragmentItem> getItems(){
        List<ActionFragmentAdapter.ActionFragmentItem> items = new ArrayList<>();

        items.add(new ActionFragmentAdapter.ActionFragmentItem(getString(R.string.fragment_action_send_audio),
                R.drawable.ic_stat_microphone,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFragment(new SendAudioActionFragment());
                    }
                }));


        items.add(new ActionFragmentAdapter.ActionFragmentItem(getString(R.string.fragment_action_send_prerecorded),
                android.R.drawable.ic_menu_compass,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFragment(new SendPrerecordedActionFragment());
                    }
                }));
        items.add(new ActionFragmentAdapter.ActionFragmentItem(getString(R.string.fragment_action_send_text),
                android.R.drawable.ic_menu_edit,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFragment(new SendTextActionFragment());
                    }
                }));


        return items;
    }


    public interface ActionFragmentInterface{
        void loadFragment(Fragment fragment, boolean addToBackstack);
    }
}

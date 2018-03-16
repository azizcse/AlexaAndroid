package com.willblaschko.android.avs.actions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.willblaschko.android.alexa.AlexaManager;
import com.willblaschko.android.alexa.callbacks.AsyncCallback;
import com.willblaschko.android.alexa.interfaces.AvsResponse;
import com.willblaschko.android.avs.R;

import static com.willblaschko.android.avs.global.Constants.PRODUCT_ID;

/**
 * @author will on 5/30/2016.
 */

public abstract class BaseListenerFragment extends Fragment {

    protected AlexaManager alexaManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get our AlexaManager instance for convenience
        alexaManager = AlexaManager.getInstance(getActivity());

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() != null) {
            getActivity().setTitle(getTitle());
        }
    }

    protected AsyncCallback<AvsResponse, Exception> getRequestCallback(){
        if(getActivity() != null && getActivity() instanceof AvsListenerInterface){
            return ((AvsListenerInterface) getActivity()).getRequestCallback();
        }
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.code_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.view_code:
                DisplayCodeFragment fragment = DisplayCodeFragment.getInstance(getTitle(), getRawCode());
                loadFragment(fragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract void startListening();
    protected abstract String getTitle();
    @RawRes
    protected abstract int getRawCode();

    public interface AvsListenerInterface{
        AsyncCallback<AvsResponse, Exception> getRequestCallback();
    }

    protected void loadFragment(Fragment fragment){
        if(getActivity() != null && getActivity() instanceof ActionsFragment.ActionFragmentInterface){
            ((ActionsFragment.ActionFragmentInterface) getActivity()).loadFragment(fragment, true);
        }
    }
}

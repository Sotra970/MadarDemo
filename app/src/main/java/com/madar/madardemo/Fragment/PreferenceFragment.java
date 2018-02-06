package com.madar.madardemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madar.madardemo.Adapter.PreferenceListAdapter;
import com.madar.madardemo.Fragment.Abstract.TitledFragment;
import com.madar.madardemo.R;
import com.madar.madardemo.Util.PreferenceManager;

/**
 * Created by Ahmed on 8/21/2017.
 */

public class PreferenceFragment extends TitledFragment {

    public static PreferenceFragment getInstance() {
        return new PreferenceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);

        RecyclerView recyclerView = (RecyclerView)
                v.findViewById(R.id.fragment_preferences_recycler_view);

        initRecyclerView(recyclerView);

        return v;
    }

    private void initRecyclerView(RecyclerView recyclerView){
        if(recyclerView != null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            PreferenceListAdapter adapter =
                    new PreferenceListAdapter(
                            PreferenceManager.getPreferences(getContext()),
                            getContext()
                    );

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(R.string.fragment_title_preferences);
    }
}

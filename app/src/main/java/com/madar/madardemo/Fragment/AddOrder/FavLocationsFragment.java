package com.madar.madardemo.Fragment.AddOrder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.madar.madardemo.Adapter.FavsArrayAdapter;
import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.Fragment.Abstract.AddOrderBaseFragment;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavLocationsFragment extends AddOrderBaseFragment {


    public FavLocationsFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.progressView)
    View progrssView;
    @BindView(R.id.container)
    View containerHolder;

    View layout_res ;

    @BindView(R.id.fav_location_list_view)
    ListView listView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layout_res == null){
           layout_res =  inflater.inflate(R.layout.fragment_fav_locations, container, false);
            ButterKnife.bind(this, layout_res) ;
            initLoading(this.progrssView, this.containerHolder);
            showLoading(true);
        }
        
        return layout_res ;
    }


    @Override
    public void onResume() {
        super.onResume();
        get_favs( new FavFetch(){

            @Override
            public void onFavFetch(ArrayList<FavLocationModel> favs) {

                if (favs !=null){
                    no_items.setVisibility(View.GONE);
                    update(favs);
                }else {
                    no_items.setVisibility(View.VISIBLE);
                }
                showLoading(false);
            }
        }, false);
    }

    private void update(final ArrayList<FavLocationModel> data) {
        showLoading(false);
         FavsArrayAdapter<FavLocationModel> arrayAdapter = new FavsArrayAdapter<FavLocationModel>(getContext() , data ) {
            @Override
            public void drawText(TextView textView, FavLocationModel object) {
                textView.setText(object.getDetails());
                Log.e("draw" , object.getDetails()+"") ;
            }

            @Override
            public void delete(final FavLocationModel object , int pos) {
                showLoading(true);
                delete_fav(object.getID(), new DeleteFav() {
                    @Override
                    public void onFavDelete() {
                       get_favs(new FavFetch() {
                           @Override
                           public void onFavFetch(ArrayList<FavLocationModel> favs) {
                               if (favs !=null){
                                   no_items.setVisibility(View.GONE);
                                   update(favs);
                               }else {
                                   no_items.setVisibility(View.VISIBLE);
                               }
                               showLoading(false);
                           }
                       } , true);
                    }
                });
            }


        };
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FavLocationModel favLocationModel = data.get(position) ;
                Intent intent = new Intent(Config.FAV_UPDATE_ACTION) ;
                intent.putExtra("fav",favLocationModel);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                getActivity().onBackPressed();
            }
        });
    }


    @BindView(R.id.no_items)
    TextView no_items ;


    @Override
    public void onPageSelected(int pos) {

    }
}

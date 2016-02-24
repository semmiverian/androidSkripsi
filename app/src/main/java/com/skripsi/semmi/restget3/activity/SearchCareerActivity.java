package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.skripsi.semmi.restget3.Interface.SearchCareerInterface;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.AllCareerAdapater;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 24/02/2016.
 */
public class SearchCareerActivity extends AppCompatActivity {

    private SearchView searchView;
    private AllCareerAdapater mAdapater;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_career);
        mAdapater = new AllCareerAdapater(this,1);
        listView = (ListView) findViewById(R.id.listSearchCareer);

        searchView= (SearchView) findViewById(R.id.searchCareer);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(SearchCareerActivity.this, ""+query, Toast.LENGTH_SHORT).show();
                mAdapater.clear();
                listView.invalidate();
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                SearchCareerInterface searchCareerInterface = restAdapter.create(SearchCareerInterface.class);
                searchCareerInterface.searchCareer(query, new Callback<List<AllCareer>>() {
                    @Override
                    public void success(List<AllCareer> allCareers, Response response) {
                        if(allCareers.isEmpty() ){
                            Toast.makeText(SearchCareerActivity.this, "There is no data with that Query", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (AllCareer allCareer : allCareers){
                            Log.d("SSS", "success: "+allCareer.getKarirnama());
                            mAdapater.add(allCareer);
                        }
                        listView.setAdapter(mAdapater);
                        mAdapater.notifyDataSetChanged();

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent CareerDetailIntent= new Intent(SearchCareerActivity.this,CareerDetailActivity.class);
                                CareerDetailIntent.putExtra(CareerDetailActivity.EXTRA,mAdapater.getItem(position));
                                startActivity(CareerDetailIntent);
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}

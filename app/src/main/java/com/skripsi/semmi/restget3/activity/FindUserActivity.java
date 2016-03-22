package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.skripsi.semmi.restget3.Font.RobotoTextView;
import com.skripsi.semmi.restget3.Helper.ListViewHelper;
import com.skripsi.semmi.restget3.Interface.SearchUserInterface;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.SearchUserAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 11/12/2015.
 */
public class FindUserActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RobotoTextView resultSearch;
    private ListView mListView;
    private SearchUserAdapter mAdapter;
    private SharedPreferences sharedPreferences;
    private FloatingSearchView floatingSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mAdapter = new SearchUserAdapter(FindUserActivity.this, 0);
        resultSearch = (RobotoTextView) findViewById(R.id.querySearchResult);
        mListView = (ListView) findViewById(R.id.FindUserListView);

//        ListViewHelper listViewHelper = new ListViewHelper();
//        listViewHelper.googleCardslistViewDesign(getResources(),mListView);
//
//        sharedPreferences = this.getSharedPreferences("Session Check", Context.MODE_PRIVATE);
//
//        resultSearch.setText("# no query defined");
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(!query.equals("")){
//                    resultSearch.setText(query);
//                    String currentUser =sharedPreferences.getString("usernameSession", "Username");
//                    findUser(query,currentUser);
//                }
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });


        // Testing predictive text
        floatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if(!oldQuery.equals("") && newQuery.equals("")){
                    floatingSearchView.clearSuggestions();
                    return;
                }
                floatingSearchView.showProgress();
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                SearchUserInterface sui = restAdapter.create(SearchUserInterface.class);
                sui.findUser(newQuery, new Callback<List<AllUser>>() {
                    @Override
                    public void success(List<AllUser> allUsers, Response response) {

                        floatingSearchView.swapSuggestions(allUsers);
                        floatingSearchView.hideProgress();
                        for(AllUser allUser : allUsers){
                            //  set adapter for the exist data
                            Log.d("data exist", allUser.getUsername());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });

        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                AllUser userClicked = (AllUser) searchSuggestion;
                Log.d("onclick", "onSuggestionClicked: "+userClicked.getNama());
                Intent UserProfileIntent = new Intent(FindUserActivity.this, AllUserProfile.class);
                UserProfileIntent.putExtra(AllUserProfile.extra,userClicked);
                startActivity(UserProfileIntent);
//                mAdapter.clear();
//                mListView.invalidate();
//
//                mAdapter.add(userClicked);
//                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onSearchAction() {

            }
        });


    }
    // fungsi untuk mencari data user berdasarkan nama atau username yang didapat dari query yang dilakukan oleh user
    private void findUser(String query, final String currentUser) {


        if(query.equals("") || query == null){
            new MaterialDialog.Builder(this)
                    .title("null")
                    .content("Silahkan isi dlu query nya ")
                    .positiveText("Ulangi")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            materialDialog.dismiss();
                        }
                    })
                    .show();
            return;
        }


        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        SearchUserInterface sui = restAdapter.create(SearchUserInterface.class);
        sui.findUser(query, new Callback<List<AllUser>>() {
            @Override
            public void success(List<AllUser> allUsers, Response response) {
                mAdapter = new SearchUserAdapter(FindUserActivity.this, 0);
                if(allUsers.isEmpty() || allUsers == null){
                    //  Set custom adapter for no data return
                    // Still use the same adapter as the existing data
                    Log.d("no data", "ga ada data dengan query tersebut");
                    String kosong = "kosong";
                    String noneImage ="http://i.imgur.com/0hi2ZKN.png";
                    AllUser allUserNone = new AllUser(kosong,noneImage,kosong,kosong,1);
                    mAdapter.add(allUserNone);
                    mListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    return;
                }

                for(AllUser allUser : allUsers){
                    //  set adapter for the exist data
                    Log.d("data exist", allUser.getUsername());
                if(!allUser.getUsername().equals(currentUser)){
                    if (!allUser.getStatus().equals("unverified")){
                        mAdapter.add(allUser);
                        }
                    }
                }
                Log.d("currUser",currentUser);

                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent UserProfileIntent = new Intent(FindUserActivity.this, AllUserProfile.class);
                        UserProfileIntent.putExtra(AllUserProfile.extra,mAdapter.getItem(position));
                        startActivity(UserProfileIntent);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Error","retrofit search"+error.getMessage());
            }
        });
    }
}

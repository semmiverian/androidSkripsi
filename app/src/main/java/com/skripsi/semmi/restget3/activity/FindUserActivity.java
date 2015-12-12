package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arlib.floatingsearchview.FloatingSearchView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        resultSearch = (RobotoTextView) findViewById(R.id.querySearchResult);
        mListView = (ListView) findViewById(R.id.FindUserListView);

        ListViewHelper listViewHelper = new ListViewHelper();
        listViewHelper.googleCardslistViewDesign(getResources(),mListView);


        resultSearch.setText("# no query defined");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals("")){
                    resultSearch.setText(query);
                    findUser(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
    // fungsi untuk mencari data user berdasarkan nama atau username yang didapat dari query yang dilakukan oleh user
    private void findUser(String query) {
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
                    // TODO Set custom adapter for no data return
                    Log.d("no data", "ga ada data dengan query tersebut");
                    return;
                }

                for(AllUser allUser : allUsers){
                    // TODO set adapter for the exist data
                    Log.d("data exist", allUser.getNama());
                    if(!allUser.getStatus().equals("unverified")){
                        mAdapter.add(allUser);
                    }
                }
                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Error","retrofit search"+error.getMessage());
            }
        });
    }
}

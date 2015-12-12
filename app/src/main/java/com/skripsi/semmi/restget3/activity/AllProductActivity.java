package com.skripsi.semmi.restget3.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.skripsi.semmi.restget3.Helper.ListViewHelper;
import com.skripsi.semmi.restget3.Interface.AllProductInterface;
import com.skripsi.semmi.restget3.Interface.SearchProductInterface;
import com.skripsi.semmi.restget3.Model.AllProduct;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.AllProductAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 17/11/2015.
 */
public class AllProductActivity extends AppCompatActivity implements OnDismissCallback, View.OnClickListener {
    private AllProductAdapter mAdapter;
    private ListView listView;
    private static final int INITIAL_DELAY_MILLIS = 300;
    public static String refresh_code="1";
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        // pengen nge cek setiap kali create bakal di invalidate dlu list view nya

        mAdapter= new AllProductAdapter(this,0);
        fetchProductData();


        // define design
        listView = (ListView) findViewById(R.id.ProductListView);
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(mAdapter);
        swingBottomInAnimationAdapter.setAbsListView(listView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                INITIAL_DELAY_MILLIS);

        // Google card design
        ListViewHelper listViewHelper = new ListViewHelper();
        listViewHelper.googleCardslistViewDesign(getResources(),listView);

        //fab related code
        mFloatingActionButton= (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

        // define on click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ProdukDetailIntent = new Intent (AllProductActivity.this, ProductDetailActivity.class);
                ProdukDetailIntent.putExtra(ProductDetailActivity.extra, mAdapter.getItem(position));
                startActivity(ProdukDetailIntent);
                Log.d("onClick",""+position);
            }
        });

    }

    // Ambil data produk dari server
    private void fetchProductData() {
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        AllProductInterface allProductInterface = restAdapter.create(AllProductInterface.class);
        allProductInterface.getProduct(new Callback<List<AllProduct>>() {
            @Override
            public void success(List<AllProduct> allProducts, Response response) {

                if(allProducts == null || allProducts.isEmpty()){
                    return;
                }

                for(AllProduct allProduct : allProducts){
                    mAdapter.add(allProduct);
                }
                listView.invalidate();
                listView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("get Error", "from Retrofit" + error.getMessage());
                new MaterialDialog.Builder(AllProductActivity.this)
                        .title("Something went wrong")
                        .content("Mohon maaf terjadi kesalahan pada penampilan data")
                        .positiveText("Muat ulang")
//                        .icon(Drawable.createFromPath(String.valueOf(R.drawable.ic_media_play)))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                fetchProductData();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull final ViewGroup listView,
                          @NonNull final int[] reverseSortedPositions) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab :
                    addNewProduct();
                break;
        }
    }

    private void addNewProduct() {
        Intent newProduct = new Intent(this, AddNewProdukActivity.class);
        startActivity(newProduct);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(this,home_activity.class);
        startActivity(backIntent);
    }


    // Search Action
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        // Search related code
        // TODO find how to create button back after search
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search_product));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // pengennya kalau di close bakal balik fetch semua datanya lagi
                listView.invalidate();
                mAdapter.clear();
                fetchProductData();
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("search", query);
                //  add call to server to respond the query
                showSearchResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void showSearchResult(String querySearch) {
        // bikin method buat tampilin list view baru ketika search di lakukan
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        final String query = querySearch;
        SearchProductInterface spi = restAdapter.create(SearchProductInterface.class);
        spi.fetchSearch(querySearch, new Callback<List<AllProduct>>() {
            @Override
            public void success(List<AllProduct> allProducts, Response response) {
                // TODO SET adapter to reset LIST VIEW
                Log.d("query",query);
                listView.invalidate();
                mAdapter.clear();
                if(allProducts == null || allProducts.isEmpty()){
                    Toast.makeText(AllProductActivity.this, "Tidak ada barang dengan query tersebut", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(AllProductActivity.this, "Ada data dengan " +query, Toast.LENGTH_LONG).show();
                for(AllProduct allProduct : allProducts){
                    mAdapter.add(allProduct);
                }
                listView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("query Error", "from Retrofit" + error.getMessage());

            }
        });
    }
}

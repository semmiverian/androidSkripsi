package com.skripsi.semmi.restget3.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.skripsi.semmi.restget3.Interface.AllProductInterface;
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
public class AllProductActivity extends AppCompatActivity implements OnDismissCallback {
    private AllProductAdapter mAdapter;
    private ListView listView;
    private static final int INITIAL_DELAY_MILLIS = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        mAdapter= new AllProductAdapter(this,0);
        fetchProductData();

        listView = (ListView) findViewById(R.id.ProductListView);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(mAdapter);
        swingBottomInAnimationAdapter.setAbsListView(listView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                INITIAL_DELAY_MILLIS);

        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = getResources();

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setAdapter(swingBottomInAnimationAdapter);

    }

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
}

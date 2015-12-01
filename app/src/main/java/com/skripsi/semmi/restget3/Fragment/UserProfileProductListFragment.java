package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.UserProductInterface;
import com.skripsi.semmi.restget3.Model.Product;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.ProductDetailActivity;
import com.skripsi.semmi.restget3.adapter.UserSaleAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 29/11/2015.
 */
public class UserProfileProductListFragment extends Fragment {
    private GridView gridView;
    private UserSaleAdapter mAdapater;
    private  String user;
    private SharedPreferences sharedPreferences;

    public static UserProfileProductListFragment getInstance(){
        UserProfileProductListFragment fragment = new UserProfileProductListFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_product,container,false);
        gridView = (GridView) view.findViewById(R.id.UserProductGrid);
        sharedPreferences= this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        user=sharedPreferences.getString("usernameSession","Username");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapater = new UserSaleAdapter(this.getContext(),2);
        fetchUserProduct();
        gridView.setClipToPadding(false);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        gridView.setFadingEdgeLength(0);
        gridView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        gridView.setPadding(px, px, px, px);
        gridView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        gridView.setAdapter(mAdapater);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void fetchUserProduct() {
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserProductInterface userProductInterface=restAdapter.create(UserProductInterface.class);
        userProductInterface.getUserProduct(user, new Callback<List<Product>>() {
            @Override
            public void success(List<Product> products, Response response) {
                Log.d("berhasil catch", "berhasil gan");
                if (products == null || products.isEmpty()) {
                    Toast.makeText(getActivity(), "Ga ada Produk yang di Jual", Toast.LENGTH_SHORT).show();
                }
                for (Product product : products) {
                    mAdapater.add(product);
                }
                mAdapater.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Retrofit" + error.getMessage());
                showFailureDialog();
            }
        });
    }

    private void showFailureDialog() {
        new MaterialDialog.Builder(getActivity())
                .title("Error")
                .content("Mohon maaf terjadi kesalahan pada penampilan data")
                .positiveText("Muat ulang")
//                        .icon(Drawable.createFromPath(String.valueOf(R.drawable.ic_media_play)))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        fetchUserProduct();
                    }
                })
                .show();
    }
}

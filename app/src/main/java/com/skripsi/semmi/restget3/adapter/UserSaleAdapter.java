package com.skripsi.semmi.restget3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.Product;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 25/10/2015.
 */
public class UserSaleAdapter extends ArrayAdapter<Product>{
    public UserSaleAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            holder = new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.sale_product_user,parent,false);
            holder.nama= (TextView) convertView.findViewById(R.id.UserProdukName);
            holder.image = (ImageView) convertView.findViewById(R.id.UserProdukImage);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.nama.setText(getItem(position).getProdukNama());
        Picasso.with(getContext())
                .load(getItem(position).getProdukImage())
                .into(holder.image);
        return convertView;
    }
    private class ViewHolder{
        TextView nama;
        ImageView image;
    }
}

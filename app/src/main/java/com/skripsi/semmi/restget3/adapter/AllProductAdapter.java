package com.skripsi.semmi.restget3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.AllProduct;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 18/11/2015.
 */
public class AllProductAdapter extends ArrayAdapter<AllProduct> implements View.OnClickListener {
    public AllProductAdapter(Context context, int resource) {
        super(context, resource);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_all_product,parent,false);
            holder.produkImage= (ImageView) convertView.findViewById(R.id.ProdukImage);
            holder.produkJudul = (TextView) convertView.findViewById(R.id.ProdukId);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(getContext())
                .load(getItem(position).getProdukImage())
                .into(holder.produkImage);
        holder.produkJudul.setText(getItem(position).getProduknama());
        return convertView;

    }

    @Override
    public void onClick(View v) {

    }

    class ViewHolder{
        ImageView produkImage;
        TextView produkJudul;

    }
}

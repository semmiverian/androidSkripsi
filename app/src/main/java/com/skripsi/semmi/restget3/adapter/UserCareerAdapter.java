package com.skripsi.semmi.restget3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.Career;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 27/10/2015.
 */
public class UserCareerAdapter extends ArrayAdapter<Career> {
    public UserCareerAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.sale_career_user,parent,false);
            holder.nama= (TextView) convertView.findViewById(R.id.UserCareerName);
            holder.image = (ImageView) convertView.findViewById(R.id.UserCareerImage);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.nama.setText(getItem(position).getKarirNama());
        Picasso.with(getContext())
                .load(getItem(position).getKarirImage())
                .into(holder.image);
        return convertView;
    }
    private class ViewHolder{
        TextView nama;
        ImageView image;
    }
}

package com.skripsi.semmi.restget3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 09/11/2015.
 */
public class AllUserAdapter extends ArrayAdapter<AllUser> {

    public AllUserAdapter(Context context, int resource) {
        super(context, resource);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder= new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.all_user_list, parent, false);
            holder.userImage= (ImageView) convertView.findViewById(R.id.userImage);
            holder.userNama= (TextView) convertView.findViewById(R.id.usernameList);
            convertView.setTag(holder);
        }else{
           holder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(getContext())
                .load(getItem(position).getImage())
                .into(holder.userImage);
        holder.userNama.setText(getItem(position).getNama());
        return convertView;
    }

    class ViewHolder{
        ImageView userImage;
        TextView userNama;
    }
}

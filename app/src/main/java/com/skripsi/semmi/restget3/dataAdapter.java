package com.skripsi.semmi.restget3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by semmi on 11/10/2015.
 */
public class dataAdapter extends ArrayAdapter<Data> {
    public dataAdapter(Context context, int resource) {

        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.view_data_get,parent, false);
            holder.judul= (TextView) convertView.findViewById(R.id.judul);
            holder.deskripsi= (TextView) convertView.findViewById(R.id.deskripsi);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.judul.setText(getItem(position).getJudul());
        holder.deskripsi.setText(getItem(position).getDeskripsi());

        return convertView;
    }
     class ViewHolder{
        TextView judul;
        TextView deskripsi;
    }
}

package com.skripsi.semmi.restget3.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.AllProduct;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 25/11/2015.
 */
public class ContactDialogAdapter extends BaseAdapter {
    private String email;
    private String telepon;
    private LayoutInflater layoutInflater;
    public ContactDialogAdapter(Context context, String email , String telepon) {
        layoutInflater =LayoutInflater.from(context);
        this.email = email;
        this.telepon = telepon;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;

        if(convertView  == null ){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.contact_dialog, parent, false);
            holder.emailImage = (ImageView) convertView.findViewById(R.id.emailImage);
            holder.teleponImage = (ImageView) convertView.findViewById(R.id.teleponImage);
            holder.emailText = (TextView) convertView.findViewById(R.id.emailDialog);
            holder.teleponDialog = (TextView) convertView.findViewById(R.id.teleponDialog);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.emailText.setText(getItem(position).getProdukEmail());
//        holder.teleponDialog.setText(getItem(position).getProdukTelepon());
        holder.emailImage.setImageResource(R.drawable.ic_search);
        holder.teleponImage.setImageResource(R.drawable.ic_search);
        holder.emailText.setText(email);
        holder.teleponDialog.setText(telepon);
        return convertView;
    }

    class ViewHolder{
        ImageView emailImage;
        ImageView teleponImage;
        TextView emailText;
        TextView teleponDialog;
    }
}
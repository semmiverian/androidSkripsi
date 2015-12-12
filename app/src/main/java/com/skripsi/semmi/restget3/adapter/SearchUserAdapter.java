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
import com.vstechlab.easyfonts.EasyFonts;

/**
 * Created by semmi on 12/12/2015.
 */
public class SearchUserAdapter extends ArrayAdapter<AllUser> {

    public SearchUserAdapter(Context context, int resource) {
        super(context, resource);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder= new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_all_user,parent,false);
            holder.userImage = (ImageView) convertView.findViewById(R.id.UserImage);
            holder.namaUser = (TextView) convertView.findViewById(R.id.FindNamaUser);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(getContext())
                .load(getItem(position).getImage())
                .into(holder.userImage);

        holder.namaUser.setText(getItem(position).getNama());
        holder.namaUser.setTypeface(EasyFonts.droidSerifRegular(getContext()));


        return convertView;
    }

    class ViewHolder{
        ImageView userImage;
        TextView namaUser;
    }
}

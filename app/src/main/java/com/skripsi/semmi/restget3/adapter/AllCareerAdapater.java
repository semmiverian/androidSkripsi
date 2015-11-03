package com.skripsi.semmi.restget3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 01/11/2015.
 */
public class AllCareerAdapater extends ArrayAdapter<AllCareer> {
    public AllCareerAdapater(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ambil variabel yang sudah di define agar bisa dipakai pada fungsi ini
        ViewHolder holder;
        // fungsi ini akan berjalan kalau sebelumnya belum pernah dipanggil
        if(convertView == null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.career_fragment,parent,false);
            holder.careerImage= (ImageView) convertView.findViewById(R.id.careerImage);
            holder.judulCareer= (TextView) convertView.findViewById(R.id.judulCareer);
            holder.deskripsiCareer= (TextView) convertView.findViewById(R.id.deskripsiCareer);
            // set tag biar kaya simpen flag
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        // masukin data yang diambil biar bisa ditampilin di aplikasi
        holder.judulCareer.setText(getItem(position).getKarirnama());
        holder.deskripsiCareer.setText(getItem(position).getKarirdetail());
        Picasso.with(getContext())
                .load(getItem(position).getKarirImage())
                .into(holder.careerImage);
        return convertView;
    }

    class ViewHolder{
        // Define variabel
        ImageView careerImage;
        TextView judulCareer;
        TextView deskripsiCareer;
    }
}

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
import com.skripsi.semmi.restget3.Model.Dialog;
import com.skripsi.semmi.restget3.R;
import com.vstechlab.easyfonts.EasyFonts;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semmi on 25/11/2015.
 */
public class ContactDialogAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<Dialog> items;


    public ContactDialogAdapter(Context context, List<Dialog> items){
        layoutInflater =LayoutInflater.from(context);
        this.mContext=context;
        this.items=items;
    }




    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;

        if(convertView  == null ){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.contact_dialog, parent, false);
            holder.emailImage = (MaterialIconView) convertView.findViewById(R.id.emailImage);
            holder.emailText = (TextView) convertView.findViewById(R.id.emailDialog);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.emailImage.setIcon(items.get(position).getIcon());
        holder.emailText.setText(items.get(position).getContent());
        holder.emailText.setTypeface(EasyFonts.droidSerifRegular(mContext));
        return convertView;
    }

    class ViewHolder{
        TextView emailText;
        MaterialIconView emailImage;
    }
}

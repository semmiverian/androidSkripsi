package com.skripsi.semmi.restget3.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.Message;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 09/11/2015.
 */
public class MessageAdapter extends ArrayAdapter<Message> {
    private int mUserID;

    public MessageAdapter(Context context, int resource, int currentUserId ) {
        super(context, resource, 0);
        mUserID=currentUserId;

    }



        @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder= new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_lis,parent, false);
            holder.sendMessageImage= (ImageView) convertView.findViewById(R.id.userImageSend);
            holder.receiveMessageImage= (ImageView) convertView.findViewById(R.id.userImageReceive);
            holder.messageContent= (TextView) convertView.findViewById(R.id.messageContent);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        // nge cek siapa yang nulis pesan
        // kalau yang user lagi login gambar kanan muncul
        // kalau user lain gambar kanan yang muncul
        // tempat message diatur kanan kiri tergantung user
        if(mUserID == getItem(position).getFrom_id()){
            holder.receiveMessageImage.setVisibility(View.GONE);
            holder.sendMessageImage.setVisibility(View.VISIBLE);
            holder.messageContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            Picasso.with(getContext())
                    .load(getItem(position).getFrom_image())
                    .into(holder.sendMessageImage);
        }else{
            holder.receiveMessageImage.setVisibility(View.VISIBLE);
            holder.sendMessageImage.setVisibility(View.GONE);
            holder.messageContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            Picasso.with(getContext())
                    .load(getItem(position).getTo_image())
                    .into(holder.receiveMessageImage);
        }
            holder.messageContent.setText(getItem(position).getPesan());
        return convertView;
    }

    private class ViewHolder {
        ImageView sendMessageImage;
        ImageView receiveMessageImage;
        TextView messageContent;
    }
}

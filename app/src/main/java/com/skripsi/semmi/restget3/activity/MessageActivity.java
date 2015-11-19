package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.skripsi.semmi.restget3.Interface.PostMessageInterface;
import com.skripsi.semmi.restget3.Interface.getMessageInterface;
import com.skripsi.semmi.restget3.Model.Message;
import com.skripsi.semmi.restget3.Model.PostMessage;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 10/11/2015.
 */
public class MessageActivity extends AppCompatActivity  {
    private ListView listView;
    private EditText messageInput;
    private Button buttonSend;
    private MessageAdapter mAdapater;
    private ArrayList<Message> mMessage;
    public static final String to_id= "0";
    private int from_id;
    private SharedPreferences sharedPreferences;
    private Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedPreferences=getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        from_id= sharedPreferences.getInt("idSession", 0);
        int to_id_user= getIntent().getExtras().getInt(to_id);
        mAdapater= new MessageAdapter(this,from_id);
        listView= (ListView) findViewById(R.id.listviewChat);
        listView.setAdapter(mAdapater);
        messageInput= (EditText) findViewById(R.id.messageInput);
        buttonSend = (Button) findViewById(R.id.sendMessage);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMessage();
            }
        });
        receiveMessage();

        handler = new Handler();
        handler.postDelayed(runnable,1000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            receiveMessage();
            handler.postDelayed(this,1000);
        }
    };



    private void postMessage() {
        //   Send message to Server and Ask to refresh the list view
        if(!messageInput.getText().toString().equals("")){
            //   Set some callback after post message
            int to_id_user= getIntent().getExtras().getInt(to_id);
            int from_id_user=sharedPreferences.getInt("idSession", 61);
            String messageContent = messageInput.getText().toString();
            RestAdapter restAdapter=new RestAdapter.Builder()
                    .setEndpoint(getString(R.string.api))
                    .build();
            PostMessageInterface pmi = restAdapter.create(PostMessageInterface.class);
            pmi.postMessage(from_id_user, to_id_user, messageContent, new Callback<PostMessage>() {
                @Override
                public void success(PostMessage postMessage, Response response) {
                    // TODO: 11/11/2015 Notified the receiver when receiving a new message
                    receiveMessage();
                    messageInput.setText("");
                    mAdapater.clear();
                    Log.d("berhasil","post"+postMessage.getInfo());
                }
                @Override
                public void failure(RetrofitError error) {
                    Log.d("message post Error", "from Retrofit" + error.getMessage());
                }
            });

        }else{
            Toast.makeText(this,"Empty Message",Toast.LENGTH_SHORT).show();
        }

    }



    private void receiveMessage() {
        // Ngambil data yang ada di server dengan syarat yang sudah ditentukan
        // bakal nge reset data yang sudah ada di listview dengan barisan data yang baru

        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(to_id)){
                int to_id_user= getIntent().getExtras().getInt(to_id);
                int from_id_user=sharedPreferences.getInt("idSession", 0);
                Log.d("to_id", ""+getIntent().getExtras().getInt(to_id));
                Log.d("from_id", String.valueOf(from_id_user));
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                getMessageInterface gmi = restAdapter.create(getMessageInterface.class);
                gmi.getMessage(from_id_user, to_id_user, new Callback<List<Message>>() {
                    @Override
                    public void success(List<Message> messages, Response response) {
                        mAdapater.clear();
                        for(Message message : messages){
                            mAdapater.add(message);
                            mAdapater.notifyDataSetChanged();
                            listView.invalidate();
                            // biar bisa nampilin data yang paling baru
                            listView.setSelection(mAdapater.getCount() -1);
//                            Log.d("message",message.getPesan());
//                            Log.d("to Image",message.getTo_image());
//                            Log.d("from Image",message.getFrom_image());
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("message Error", "from Retrofit" + error.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

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

import com.skripsi.semmi.restget3.Interface.getMessageInterface;
import com.skripsi.semmi.restget3.Model.Message;
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
    private Handler handler = new Handler();
    public static final String to_id= "0";
    private int from_id;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedPreferences=getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        from_id= sharedPreferences.getInt("idSession", 0);
        mAdapater= new MessageAdapter(this,0,61);
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
    }



    private void postMessage() {
        // TODO: 10/11/2015  Send message to Server and Ask to refresh the list view
        if(!messageInput.getText().toString().equals("")){
            String messageContent = messageInput.getText().toString();
            // TODO: 10/11/2015  PUT some Callback to retrofit with post method
        }else{
            Toast.makeText(this,"Empty Message",Toast.LENGTH_SHORT).show();
        }
        receiveMessage();
    }

    private void receiveMessage() {
        // TODO: 10/11/2015 Reset LISTVIEW with the latest data get from the server
        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(to_id)){
                int to_id_user= getIntent().getExtras().getInt(to_id);
                int from_id_user=sharedPreferences.getInt("idSession", 61);
                Log.d("to_id", ""+getIntent().getExtras().getInt(to_id));
                Log.d("from_id", String.valueOf(from_id_user));
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                getMessageInterface gmi = restAdapter.create(getMessageInterface.class);
                gmi.getMessage(from_id_user, to_id_user, new Callback<List<Message>>() {
                    @Override
                    public void success(List<Message> messages, Response response) {

                        for(Message message : messages){
                            mAdapater.clear();
                            mAdapater.add(message);
                            mAdapater.notifyDataSetChanged();
                            listView.invalidate();
                            Log.d("message",message.getPesan());
                        }
                        Log.d("tag", "Not Doing Anything");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("message Error", "from Retrofit" + error.getMessage());
                    }
                });
            }
        }
    }
}

package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.skripsi.semmi.restget3.Interface.PostMessageInterface;
import com.skripsi.semmi.restget3.Interface.getMessageInterface;
import com.skripsi.semmi.restget3.Model.LocalMessage;
import com.skripsi.semmi.restget3.Model.Message;
import com.skripsi.semmi.restget3.Model.PostMessage;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
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
    private Realm myRealm;
    private int flag =0;
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
        handler.postDelayed(runnable, 1000);

        // set realm starter
        myRealm = Realm.getInstance(MessageActivity.this);
        checkMessageDataCompability();
    }

    private void checkMessageDataCompability() {
        // Ngambil data yang ada di server dengan syarat yang sudah ditentukan
        // bakal nge reset data yang sudah ada di listview dengan barisan data yang baru
        final String[] same = {"salah"};
        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(to_id)){
                final int to_id_user= getIntent().getExtras().getInt(to_id);
                final int from_id_user=sharedPreferences.getInt("idSession", 0);
                Log.d("to_id", ""+getIntent().getExtras().getInt(to_id));
                Log.d("from_id", String.valueOf(from_id_user));
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                getMessageInterface gmi = restAdapter.create(getMessageInterface.class);
                gmi.getMessage(from_id_user, to_id_user, new Callback<List<Message>>() {
                    @Override
                    public void success(List<Message> messages, Response response) {

//                        mAdapater.clear();
//                        for (Message message : messages) {
//                            mAdapater.add(message);
//                            mAdapater.notifyDataSetChanged();
//                            listView.invalidate();
//                            // biar bisa nampilin data yang paling baru
//                            listView.setSelection(mAdapater.getCount() - 1);
//                        }
                        RealmResults<LocalMessage> localMessages = myRealm.where(LocalMessage.class)
                                .beginGroup()
                                .equalTo("to_id", to_id_user)
                                .or()
                                .equalTo("to_id", from_id_user)
                                .endGroup()
                                .beginGroup()
                                .equalTo("from_id", to_id_user)
                                .or()
                                .equalTo("from_id", from_id_user)
                                .endGroup()
                                .findAll();
                        Log.d("count start","local message"+ localMessages.size());
                        Log.d("size start", "success: " + messages.size());
                        // server size data
                        int serverSize = messages.size();
                        // local size data
                        int localSize = localMessages.size();
                        // IF local database and data sent by server had same size will not do anything
                        if(serverSize == localSize){
                             flag = 1;
                        }
                        if(localSize < serverSize){
                            // delete all local data
                            deleteLocalData();
                            // Refresh the data with the latest version
                            for (Message message : messages) {
                                refreshLocalMessageList(message);
                            }
                            flag = 0;
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

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkMessageDataCompability();
            if(flag == 1){
             Log.d("flag testing", "run: flag 1");
            }
            if(flag == 0){
                receiveMessage();
            }
            handler.postDelayed(this, 1000);

        }
    };



    private void postMessage() {
        //   Send message to Server and Ask to refresh the list view
        if(!messageInput.getText().toString().equals("")){
            //   Set some callback after post message
            final int to_id_user= getIntent().getExtras().getInt(to_id);
            int from_id_user=sharedPreferences.getInt("idSession", 61);
            final String messageContent = messageInput.getText().toString();
            RestAdapter restAdapter=new RestAdapter.Builder()
                    .setEndpoint(getString(R.string.api))
                    .build();
            PostMessageInterface pmi = restAdapter.create(PostMessageInterface.class);
            pmi.postMessage(from_id_user, to_id_user, messageContent, new Callback<PostMessage>() {
                @Override
                public void success(PostMessage postMessage, Response response) {
                    // TODO: 11/11/2015 Notiflag =0;fied the receiver when receiving a new message
                    receiveMessage();
                    mAdapater.clear();
                    Log.d("berhasil","post"+postMessage.getInfo());
//                     final LocalMessage dataMessage = new LocalMessage( to_id_user,messageContent, from_id);
//
//                    // Asynchronous transaction to local database
//                    RealmAsyncTask transaction = myRealm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            LocalMessage localMessage = realm.copyToRealm(dataMessage);
//                        }
//                    }, null);
                    messageInput.setText("");
                    flag= 0;

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

//        Log.d("compability", "receiveMessage: " + checkMessageDataCompability());
//        if(checkMessageDataCompability()){
//            return;
//        }
        // Ngambil data yang ada di server dengan syarat yang sudah ditentukan
        // bakal nge reset data yang sudah ada di listview dengan barisan data yang baru
        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(to_id)){
                final int to_id_user= getIntent().getExtras().getInt(to_id);
                final int from_id_user=sharedPreferences.getInt("idSession", 0);
                Log.d("to_id", ""+getIntent().getExtras().getInt(to_id));
                Log.d("from_id", String.valueOf(from_id_user));
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                getMessageInterface gmi = restAdapter.create(getMessageInterface.class);
                gmi.getMessage(from_id_user, to_id_user, new Callback<List<Message>>() {
                    @Override
                    public void success(List<Message> messages, Response response) {

                        RealmResults<LocalMessage> localMessages = myRealm.where(LocalMessage.class)
                                .beginGroup()
                                    .equalTo("to_id", to_id_user)
                                    .or()
                                    .equalTo("to_id", from_id_user)
                                .endGroup()
                                .beginGroup()
                                    .equalTo("from_id", to_id_user)
                                    .or()
                                    .equalTo("from_id", from_id_user)
                                .endGroup()
                                .findAll();

                        for (LocalMessage localMessage : localMessages) {
                            Log.d("Query", "success() returned: " + localMessage.getMessage());
                        }
                        Log.d("count loop ", "local message" + localMessages.size());
                        Log.d("size loop ", "success: " + messages.size());
                        // server size data
                        int serverSize = messages.size();
                        // local size data
                        int localSize = localMessages.size();

                        // IF local database and data sent by server had same size will not do anything
//                        if (serverSize == localSize) {
//                            flag = 1;
//                            return;
//                        }

                        // IF local database had less size than the data server remove all local database and replace it with the latest one.




                        // IF local database had greater size than the data server remove all local database and replace it with the latest one. ( will this happen? )

                            mAdapater.clear();
                            for (Message message : messages) {
                                // TODO Try doing this only if there is new data
                                // TODO if not don't try to refresh this every time
                                mAdapater.add(message);
                                mAdapater.notifyDataSetChanged();
                                listView.invalidate();
                                // biar bisa nampilin data yang paling baru
                                listView.setSelection(mAdapater.getCount() - 1);
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

    private void refreshLocalMessageList(Message message) {
        final LocalMessage dataMessage = new LocalMessage( message.getTo_id(), message.getPesan(), message.getFrom_id());
        // Asynchronous transaction to local database
        RealmAsyncTask transaction = myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LocalMessage localMessage = realm.copyToRealm(dataMessage);
            }
        },null);
    }

    private void deleteLocalData() {
        myRealm.beginTransaction();
        RealmResults<LocalMessage> results = myRealm.where(LocalMessage.class).findAll();
        results.clear();
        myRealm.commitTransaction();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
        finish();
    }


}

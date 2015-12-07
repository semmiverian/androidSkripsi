package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Fragment.CareerListFragment;
import com.skripsi.semmi.restget3.Interface.NewCareerInterface;
import com.skripsi.semmi.restget3.Model.NewCareer;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.Helper.GetDataPathHelper;
import com.skripsi.semmi.restget3.adapter.AllCareerAdapater;

import java.io.File;
import java.io.IOException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by semmi on 03/11/2015.
 */
public class AddNewCareerActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView addJudulKarir;
    private TextView addDeskKarir;
    private TextView addEmailContact;
    private TextView addPhoneContact;
    private Button addImageKarir;
    private ImageView previewImageUpload;
    private Button submit;
    private int upload_code=1;
    private  Uri uri;
    private  TypedFile typedFile;
    private SharedPreferences sharedPreferences;
    private  MaterialDialog dialog;
    private AllCareerAdapater mAdapater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_career);

        // define xml file biar bisa di pasang logik
        addJudulKarir= (TextView) findViewById(R.id.addJudulKarir);
        addDeskKarir= (TextView) findViewById(R.id.addDeskKarir);
        addEmailContact = (TextView) findViewById(R.id.kontakEmail);
        addPhoneContact = (TextView) findViewById(R.id.kontakTelepon);
        addImageKarir= (Button) findViewById(R.id.addImageKarir);
        previewImageUpload= (ImageView) findViewById(R.id.previewImageUpload);
        submit= (Button) findViewById(R.id.submitNewCareer);
        sharedPreferences= this.getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        // set onclick listener
        addImageKarir.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String judul=addJudulKarir.getText().toString();
        String detail=addDeskKarir.getText().toString();
        String email = addEmailContact.getText().toString();
        String phone = addPhoneContact.getText().toString();
        switch(v.getId()){
            case R.id.addImageKarir:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/* ");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, upload_code);
                break;
            case R.id.submitNewCareer:
                // pasang dialog
                 dialog = new MaterialDialog.Builder(this)
                        .title("Proses")
                        .content("Connecting to server")
                        .progress(true,0)
                        .show();
                // ambil real path dari image yang dipilih
                String imagePath=null;
                if(uri != null){
                    Cursor cursor = this.getContentResolver().query(
                            uri, null, null, null, null);
                    if (cursor == null) {
                        imagePath = uri.getPath();
                    } else {
                        cursor.moveToFirst();
                        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        imagePath = cursor.getString(idx);
                    }
                    typedFile=new TypedFile("multipart/form-data",new File(imagePath ));
                }else{
                    // kalau ga ada gambar yang dipilih maka bakal set jadi null
                    // gambar bakal dipasang gambar default yang sebelumnya udah di simpan di server
                    if(imagePath==null){
                        typedFile=null;
                    }
                }

                if(judul.equals("") || detail.equals("") || email.equals("") || phone.equals("")){
                    dialog.dismiss();
                    new MaterialDialog.Builder(this)
                            .title("Gagal")
                            .content("Harus Di Isi Semua")
                            .positiveText("Kembali")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                    materialDialog.dismiss();
                                }
                            })
                            .show();
                    return;
                }

                // define adapter
                mAdapater=new AllCareerAdapater(this,0);

                String username=sharedPreferences.getString("usernameSession", "Username");

                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                NewCareerInterface newCareerInterface=restAdapter.create(NewCareerInterface.class);
                newCareerInterface.postCareer(username,judul,detail,email,phone,typedFile,new Callback<NewCareer>() {
                    @Override
                    public void success(NewCareer newCareer, Response response) {
                        dialog.setContent("Sukses Add Data");
                        Toast.makeText(AddNewCareerActivity.this,""+newCareer.getInfo(),Toast.LENGTH_SHORT).show();
                        Intent intentCareer= new Intent(AddNewCareerActivity.this, CareerActivity.class);
                        intentCareer.putExtra(CareerActivity.refresh_code, "3");
                        startActivity(intentCareer);
                        CareerListFragment clf =  new CareerListFragment();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("insert Error", "from Retrofit" + error.getMessage());
                    }
                });
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            uri=data.getData();
            // kalau ga ada image yang dipilih bakal tampilin null
            if(uri== null)
                return;

            if(requestCode==upload_code){
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    previewImageUpload.setImageBitmap(bitmap);
                    GetDataPathHelper gdph = new GetDataPathHelper();
                    gdph.getPath(getApplicationContext(), uri);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }



}

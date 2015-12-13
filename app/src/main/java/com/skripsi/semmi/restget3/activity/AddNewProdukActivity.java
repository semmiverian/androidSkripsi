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

import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.NewProductInterface;
import com.skripsi.semmi.restget3.Model.NewProduk;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.Helper.GetDataPathHelper;

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
public class AddNewProdukActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView addJudulProduk;
    private TextView addDeskProduk;
    private TextView addKontakEmail;
    private TextView addKontakTelepon;
    private Button addImageProduk;
    private Button captureImageProduk;
    private ImageView previewImageUpload;
    private Button submit;
    private int upload_code=1;
    private int camera_code=2;
    public static final int MEDIA_TYPE_IMAGE = 10;
    private  Uri uri;
    private  TypedFile typedFile;
    private SharedPreferences sharedPreferences;
    private  MaterialDialog dialog;
    private   GetDataPathHelper gdph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_product);

        // define xml file biar bisa di pasang logik
        addJudulProduk= (TextView) findViewById(R.id.addJudulProduk);
        addDeskProduk= (TextView) findViewById(R.id.addDeskProduk);
        addKontakEmail = (TextView) findViewById(R.id.addKontakEmail);
        addKontakTelepon = (TextView) findViewById(R.id.addKontakTelepon);
        addImageProduk= (Button) findViewById(R.id.addImageProduk);
        captureImageProduk = (Button) findViewById(R.id.captureImageProduk);
        previewImageUpload= (ImageView) findViewById(R.id.previewImageUpload);
        submit= (Button) findViewById(R.id.submitNewProduk);
        sharedPreferences= this.getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        // set onclick listener
        addImageProduk.setOnClickListener(this);
        captureImageProduk.setOnClickListener(this);
        submit.setOnClickListener(this);

        gdph = new GetDataPathHelper();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addImageProduk:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/* ");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, upload_code);
                break;
            case R.id.captureImageProduk:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                uri = gdph.getOutputMediaFileUri(10);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(cameraIntent, camera_code);
                break;
            case R.id.submitNewProduk:
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

                // define adapter
//                mAdapater=new AllCareerAdapater(this,0);

                // Proses upload ke server
                String username=sharedPreferences.getString("usernameSession", "Username");
                String judul=addJudulProduk.getText().toString();
                String detail=addDeskProduk.getText().toString();
                String email=addKontakEmail.getText().toString();
                String telepon =addKontakTelepon.getText().toString();
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                NewProductInterface newProductInterface=restAdapter.create(NewProductInterface.class);
                newProductInterface.postProduk(username, judul, detail, typedFile,email,telepon, new Callback<NewProduk>() {
                    @Override
                    public void success(NewProduk newProduk, Response response) {
                        dialog.setContent("Sukses Add Data");
                        Toast.makeText(AddNewProdukActivity.this,""+newProduk.getInfo(),Toast.LENGTH_SHORT).show();
                        Intent intentCareer= new Intent(AddNewProdukActivity.this, AllProductActivity.class);
                        intentCareer.putExtra(CareerActivity.refresh_code, "3");
                        startActivity(intentCareer);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", uri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        uri = savedInstanceState.getParcelable("file_uri");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // kalau ga ada image yang dipilih bakal tampilin null
        if(uri== null)
            return;
        if(resultCode == RESULT_OK){
            if(requestCode==upload_code){
                uri=data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    previewImageUpload.setImageBitmap(bitmap);

                    gdph.getPath(getApplicationContext(), uri);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(requestCode==camera_code){

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    previewImageUpload.setImageBitmap(bitmap);
                    gdph.getPath(getApplicationContext(), uri);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }



}

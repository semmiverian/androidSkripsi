package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Helper.GetDataPathHelper;
import com.skripsi.semmi.restget3.Interface.UpdateCareerInterface;
import com.skripsi.semmi.restget3.Interface.UpdateProdukInterface;
import com.skripsi.semmi.restget3.Model.DeleteData;
import com.skripsi.semmi.restget3.R;

import java.io.File;
import java.io.IOException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by semmi on 02/12/2015.
 */
public class ProductEditActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String produkNama = "Extra produk nama";
    public final static String produkDetail = "Extra produk detail";
    public final static String produkImage = "Extra produk Image";
    public final static String produkId = "Extra produk ID";
    public final static String produkTelepon = "Extra produk Telepon";
    public final static String produkEmail = "Extra produk Email";
    private EditText produkNamaText;
    private EditText produkDetailText;
    private EditText produkEmailText;
    private EditText produkTeleponText;
    private Button updateButton;
    private  MaterialDialog dialog;
    private String idProduk;
    private ImageView previewImage;
    private Button changeImageButton;
    private int upload_code=1;
    private Uri uri;
    private  TypedFile typedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_activity);
        produkNamaText = (EditText) findViewById(R.id.editProdukNama);
        produkDetailText = (EditText) findViewById(R.id.editProdukDetail);
        updateButton = (Button) findViewById(R.id.updateProduk);
        previewImage = (ImageView) findViewById(R.id.previewImageKarir);
        changeImageButton = (Button) findViewById(R.id.updateImage);
        produkEmailText = (EditText) findViewById(R.id.produkEmail);
        produkTeleponText = (EditText) findViewById(R.id.produkTelepon);

        if(getIntent()!= null && getIntent().getExtras()!=null) {
            if (getIntent().getExtras().containsKey(produkNama)) {
                produkNamaText.setText(getIntent().getExtras().getString(produkNama));
            }if(getIntent().getExtras().containsKey(produkDetail)) {
                produkDetailText.setText(getIntent().getExtras().getString(produkDetail));
            }
            if(getIntent().getExtras().containsKey(produkId)) {
                idProduk=getIntent().getExtras().getString(produkId);
            }
            if(getIntent().getExtras().containsKey(produkEmail)) {
                produkEmailText.setText(getIntent().getExtras().getString(produkEmail));
            }
            if(getIntent().getExtras().containsKey(produkTelepon)) {
                produkTeleponText.setText(getIntent().getExtras().getString(produkTelepon));
            }
        }

        updateButton.setOnClickListener(this);
        changeImageButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.updateProduk:
                String nama = produkNamaText.getText().toString();
                String detail = produkDetailText.getText().toString();
                String email = produkEmailText.getText().toString();
                String telepon = produkTeleponText.getText().toString();
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

                dialog = new MaterialDialog.Builder(this)
                        .title("Proses")
                        .content("Connecting to server")
                        .progress(true,0)
                        .show();
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                UpdateProdukInterface upi = restAdapter.create(UpdateProdukInterface.class);
                upi.updateProduk(idProduk, nama, detail, email, telepon, typedFile, new Callback<DeleteData>() {
                    @Override
                    public void success(DeleteData deleteData, Response response) {
                        dialog.setContent(deleteData.getInfo());
                        dialog.dismiss();
                        Intent successUpdateProdukIntent = new Intent(ProductEditActivity.this, home_activity.class);
                        successUpdateProdukIntent.putExtra("code3","code from delete produk");
                        startActivityForResult(successUpdateProdukIntent, 101);
                        Log.d("produk Id",idProduk);
                        Log.d("update Career",deleteData.getInfo());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Edit Error", "from Career " + error.getMessage());
                    }
                });
//
                break;
            case R.id.updateImage:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/* ");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, upload_code);
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
                    previewImage.setImageBitmap(bitmap);
                    GetDataPathHelper gdph = new GetDataPathHelper();
                    gdph.getPath(getApplicationContext(), uri);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


}

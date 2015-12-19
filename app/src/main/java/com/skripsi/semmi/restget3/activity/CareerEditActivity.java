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
import com.skripsi.semmi.restget3.Interface.UpdateCareerInterface;
import com.skripsi.semmi.restget3.Model.DeleteData;
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
 * Created by semmi on 02/12/2015.
 */
public class CareerEditActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String karirNama = "Extra karir nama";
    public final static String karirDetail = "Extra karir detail";
    public final static String karirId = "Extra karir ID";
    public final static String karirEmail = "Extra karir Email";
    public final static String karirTelepon = "Extra karir telepon";
    private EditText karirNamaText;
    private EditText karirDetailText;
    private EditText karirEmailText;
    private EditText karirTeleponText;
    private Button updateButton;
    private  MaterialDialog dialog;
    private String idKarir;
    private ImageView previewImage;
    private Button changeImageButton;
    private int upload_code=1;
    private Uri uri;
    private  TypedFile typedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_career_activity);
        karirNamaText = (EditText) findViewById(R.id.editKarirNama);
        karirDetailText = (EditText) findViewById(R.id.editKarirDetail);
        karirEmailText = (EditText) findViewById(R.id.karirEmailEdit);
        karirTeleponText = (EditText) findViewById(R.id.karirTeleponEdit);

        updateButton = (Button) findViewById(R.id.updateKarir);
        previewImage = (ImageView) findViewById(R.id.previewImageKarir);
        changeImageButton = (Button) findViewById(R.id.updateImage);

        showOldData();

        updateButton.setOnClickListener(this);
        changeImageButton.setOnClickListener(this);
    }

    private void showOldData() {

        if(getIntent()!= null && getIntent().getExtras()!=null) {
            if (getIntent().getExtras().containsKey(karirNama)) {
                karirNamaText.setText(getIntent().getExtras().getString(karirNama));
            }if(getIntent().getExtras().containsKey(karirDetail)) {
                karirDetailText.setText(getIntent().getExtras().getString(karirDetail));
            }if(getIntent().getExtras().containsKey(karirId)) {
                idKarir=getIntent().getExtras().getString(karirId);
            }if(getIntent().getExtras().containsKey(karirEmail)) {

                karirEmailText.setText(getIntent().getExtras().getString(karirEmail));
            }if(getIntent().getExtras().containsKey(karirTelepon)) {
                karirTeleponText.setText(getIntent().getExtras().getString(karirTelepon));
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.updateKarir:
                String nama = karirNamaText.getText().toString();
                String detail = karirDetailText.getText().toString();
                String email = karirEmailText.getText().toString();
                String telepon = karirTeleponText.getText().toString();
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
                UpdateCareerInterface updateCareerInterface = restAdapter.create(UpdateCareerInterface.class);
                updateCareerInterface.postUpdateCareer(idKarir, nama, detail, typedFile,email,telepon,new Callback<DeleteData>() {
                    @Override
                    public void success(DeleteData deleteData, Response response) {
                        dialog.setContent(deleteData.getInfo());
                        dialog.dismiss();
                        Intent successUpdateCareerIntent = new Intent(CareerEditActivity.this, home_activity.class);
                        successUpdateCareerIntent.putExtra("code","code from career");
                        startActivityForResult(successUpdateCareerIntent, 101);
                        Log.d("career id",idKarir);
                        Log.d("update Career",deleteData.getInfo());
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Edit Error", "from Career " + error.getMessage());
                    }
                });
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

package com.skripsi.semmi.restget3.activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Fragment.CareerListFragment;
import com.skripsi.semmi.restget3.Interface.NewCareerInterface;
import com.skripsi.semmi.restget3.Model.NewCareer;
import com.skripsi.semmi.restget3.R;
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

                // define adapter
                mAdapater=new AllCareerAdapater(this,0);

                String username=sharedPreferences.getString("usernameSession", "Username");
                String judul=addJudulKarir.getText().toString();
                String detail=addDeskKarir.getText().toString();
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                NewCareerInterface newCareerInterface=restAdapter.create(NewCareerInterface.class);
                newCareerInterface.postCareer(username,judul,detail,typedFile,new Callback<NewCareer>() {
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
                    getPath(getApplicationContext(), uri);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }


    }


    // TODO Refactor and make this ugly thing in one class
    // Dont Bother this part
    // Just a way to do something
    // ngambil nama data yang dipilih oleh user jika user menggunakan API diatas 19
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}

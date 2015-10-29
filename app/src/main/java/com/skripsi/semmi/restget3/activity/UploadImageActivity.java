package com.skripsi.semmi.restget3.activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.semmi.restget3.Interface.UploadImageInterface;
import com.skripsi.semmi.restget3.Model.UploadImage;
import com.skripsi.semmi.restget3.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by semmi on 28/10/2015.
 */
public class UploadImageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton;
    private Button mButton2;
    private File mFile;
    private ImageView mImageView;
    private TextView mTextView;
    private final static int upload_code= 1;
    private SharedPreferences sharedPreferences;
    private  Uri uri;
    private Spinner msSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // define layout sama button yang ada di view ini
        setContentView(R.layout.upload_profile_image_activity);
        mButton= (Button) findViewById(R.id.UploadImage);
        mButton.setOnClickListener(this);
        mImageView= (ImageView) findViewById(R.id.previewImage);
        mTextView= (TextView) findViewById(R.id.pathImage);
        mButton2= (Button) findViewById(R.id.postImage);
        mButton2.setVisibility(View.INVISIBLE);
        msSpinner= (Spinner) findViewById(R.id.spinner);
        msSpinner.setVisibility(View.INVISIBLE);
        sharedPreferences= this.getSharedPreferences("Session Check", Context.MODE_PRIVATE);

    }


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            uri=data.getData();

       try {
           Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
           mImageView.setImageBitmap(bitmap);
           getPath(getApplicationContext(), uri);
           mButton2.setVisibility(View.VISIBLE);
           mButton2.setOnClickListener(this);




       }catch (IOException e){
           e.printStackTrace();
       }
//        if (requestCode == GALLERY_INTENT_CALLED) {
//            uri = data.getData();
//        }else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {
//            uri = data.getData();
//            takeFlags = data.getFlags()
//                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//
//
//        }
//        getContentResolver().takePersistableUriPermission(uri, takeFlags);
        mTextView.setText(uri.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.UploadImage:
//                if (Build.VERSION.SDK_INT <19){
//                    Intent intent = new Intent();
//                    intent.setType("image/* ");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)),GALLERY_INTENT_CALLED);
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.setType("image/* ");
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                     intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
//                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/* ");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(intent, upload_code);
                break;
            case R.id.postImage:
                // tampilin spineer ketika user klik upload image
                msSpinner.setVisibility(View.VISIBLE);
                // script untuk ambil path dari image yang dipilih
                String imagePath=null;
                Cursor cursor = this.getContentResolver().query(
                        uri, null, null, null, null);
                if (cursor == null) {
                    imagePath = uri.getPath();
                } else {
                    cursor.moveToFirst();
                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    imagePath = cursor.getString(idx);
                }
                //String filename = uri.getLastPathSegment();
                TypedFile typedFile=new TypedFile("multipart/form-data",new File(imagePath ));
                String user=sharedPreferences.getString("usernameSession", "Username");

                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                UploadImageInterface uploadImageInterface=restAdapter.create(UploadImageInterface.class);
                uploadImageInterface.postImage(typedFile, user, new Callback<UploadImage>() {
                    @Override
                    public void success(UploadImage uploadImage, Response response) {
                        // ilangin spinner ketika udah sukses
                        msSpinner.setVisibility(View.GONE);
                       Toast.makeText(UploadImageActivity.this,"Berhasil Mengganti Image",Toast.LENGTH_SHORT).show();
                        Log.d("sukses","berhasil post image");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("post Error","from Retrofit"+error.getMessage());
                    }
                });
                break;
        }

    }

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

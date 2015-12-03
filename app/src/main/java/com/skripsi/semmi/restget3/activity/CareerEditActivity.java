package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.UpdateCareerInterface;
import com.skripsi.semmi.restget3.Model.DeleteData;
import com.skripsi.semmi.restget3.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 02/12/2015.
 */
public class CareerEditActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String karirNama = "Extra karir nama";
    public final static String karirDetail = "Extra karir detail";
    public final static String karirImage = "Extra karir Image";
    public final static String karirId = "Extra karir ID";
    private EditText karirNamaText;
    private EditText karirDetailText;
    private Button updateButton;
    private  MaterialDialog dialog;
    private String idKarir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_career_activity);
        karirNamaText = (EditText) findViewById(R.id.editKarirNama);
        karirDetailText = (EditText) findViewById(R.id.editKarirDetail);
        updateButton = (Button) findViewById(R.id.updateKarir);
        if(getIntent()!= null && getIntent().getExtras()!=null) {
            if (getIntent().getExtras().containsKey(karirNama)) {
                karirNamaText.setText(getIntent().getExtras().getString(karirNama));
            }if(getIntent().getExtras().containsKey(karirDetail)) {
                karirDetailText.setText(getIntent().getExtras().getString(karirDetail));
            }
            if(getIntent().getExtras().containsKey(karirId)) {
                idKarir=getIntent().getExtras().getString(karirId);
            }
        }

        updateButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.updateKarir:
                String nama = karirNamaText.getText().toString();
                String detail = karirDetailText.getText().toString();
                dialog = new MaterialDialog.Builder(this)
                        .title("Proses")
                        .content("Connecting to server")
                        .progress(true,0)
                        .show();
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                UpdateCareerInterface updateCareerInterface = restAdapter.create(UpdateCareerInterface.class);
                updateCareerInterface.postUpdateCareer(idKarir, nama, detail, new Callback<DeleteData>() {
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
        }
    }
}

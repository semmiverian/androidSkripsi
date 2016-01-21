package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Fragment.LoginFragment;
import com.skripsi.semmi.restget3.Fragment.RegisterFragment;
import com.skripsi.semmi.restget3.Interface.RegisterApiInterface;
import com.skripsi.semmi.restget3.MainActivity;
import com.skripsi.semmi.restget3.Model.Register;
import com.skripsi.semmi.restget3.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 15/10/2015.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button mButton;
    private EditText mUsername;
    //    private EditText mDob;
    private EditText mEmail;
    private EditText mName;
    private EditText mJurusan;
    private CheckBox mCheckBox;
    private Button mDob;
    private String username;
    private String dob;
    private String email;
    private String name;
    private String jurusan;
    private MaterialDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_register_page);
        mButton= (Button) findViewById(R.id.btnRegister);
        mUsername= (EditText) findViewById(R.id.usernameRegis);
        mDob= (Button) findViewById(R.id.btnBOD);
        mEmail= (EditText) findViewById(R.id.emailRegis);
        mName= (EditText) findViewById(R.id.nameRegis);
        mJurusan = (EditText) findViewById(R.id.jurusanRegis);
        mCheckBox= (CheckBox) findViewById(R.id.checkBoxTOS);
        mButton.setOnClickListener(this);
        mDob.setOnClickListener(this);
//        displayRegisterScene();
    }


    private void displayRegisterScene() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container, RegisterFragment.getInstance()).commit();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnRegister:
                dialog = new MaterialDialog.Builder(this)
                        .title("Proses")
                        .content("Connecting to server")
                        .progress(true, 0)
                        .show();
                username=mUsername.getText().toString();
//                dob=mDob.getText().toString();
                email=mEmail.getText().toString();
                name=mName.getText().toString();
                jurusan=mJurusan.getText().toString();

                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                RegisterApiInterface registerApiInterface=restAdapter.create(RegisterApiInterface.class);
                registerApiInterface.postRegister(username,dob,email, name,jurusan,new Callback<Register>() {
                    @Override
                    public void success(Register register, Response response) {
                        //  Log.d("sukses","alhamdulillah");
                        if(!mCheckBox.isSelected()&& username.equals("") && dob.equals("")&&email.equals("")&&name.equals("")){
                            Toast.makeText(RegisterActivity.this, "Isi semua field dan check TOS", Toast.LENGTH_SHORT).show();
                            dialog.setContent("Gagal Register");
                            dialog.dismiss();
                        }
                        else if(register.getKode().equals("1")){
                            mUsername.setText("");
//                            mDob.setText("");
                            mEmail.setText("");
                            mName.setText("");
                            dialog.setContent("Gagal Register");
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this,""+register.getStatus(),Toast.LENGTH_LONG).show();
                        }else{
                            Log.d("status", register.getStatus());
                            dialog.setContent("Sukses Register");
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this,""+register.getStatus(),Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("register Error","from Retrofit"+error.getMessage());
                    }
                });
                break;
            case R.id.btnBOD:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RegisterActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(true);
                dpd.setAccentColor(Color.parseColor("#0064a5"));
                dpd.setTitle("Date of Birth");
                dpd.show(getFragmentManager(), "Birth of Date ");
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dob = ""+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        mDob.setText(dob);
    }
}

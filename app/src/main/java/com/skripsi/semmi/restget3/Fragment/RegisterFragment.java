package com.skripsi.semmi.restget3.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.RegisterApiInterface;
import com.skripsi.semmi.restget3.MainActivity;
import com.skripsi.semmi.restget3.Model.Register;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.home_activity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 13/10/2015.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private Button mButton;
    private EditText mUsername;
    private EditText mDob;
    private EditText mEmail;
    private EditText mName;
    private EditText mJurusan;
    private EditText mTahunulus;
    private CheckBox mCheckBox;
    private String username;
    private String dob;
    private String email;
    private String name;
    private String jurusan;
    private String tahunlulus;
    private MaterialDialog dialog;
    public static RegisterFragment getInstance(){
        RegisterFragment fragment=new RegisterFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.simple_register_page, container, false);
        mButton= (Button) view.findViewById(R.id.btnRegister);
        mUsername= (EditText) view.findViewById(R.id.usernameRegis);
        mDob= (EditText) view.findViewById(R.id.dobRegis);
        mEmail= (EditText) view.findViewById(R.id.emailRegis);
        mName= (EditText) view.findViewById(R.id.nameRegis);
        mJurusan = (EditText) view.findViewById(R.id.jurusanRegis);
        mTahunulus= (EditText) view.findViewById(R.id.tahunLulus);
        mCheckBox= (CheckBox) view.findViewById(R.id.checkBoxTOS);
        mButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {

        dialog = new MaterialDialog.Builder(getActivity())
                .title("Proses")
                .content("Connecting to server")
                .progress(true,0)
                .show();
        username=mUsername.getText().toString();
        dob=mDob.getText().toString();
        email=mEmail.getText().toString();
        name=mName.getText().toString();
        jurusan=mJurusan.getText().toString();
        tahunlulus=mTahunulus.getText().toString();

        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        RegisterApiInterface registerApiInterface=restAdapter.create(RegisterApiInterface.class);
        registerApiInterface.postRegister(username,dob,email, name,jurusan,tahunlulus,new Callback<Register>() {
            @Override
            public void success(Register register, Response response) {
               //  Log.d("sukses","alhamdulillah");
                if(!mCheckBox.isSelected()&& username.equals("") && dob.equals("")&&email.equals("")&&name.equals("")){
                    Toast.makeText(getActivity(),"Isi semua field dan check TOS",Toast.LENGTH_SHORT).show();
                    dialog.setContent("Gagal Register");
                    dialog.dismiss();
                }
                else if(register.getKode().equals("1")){
                    mUsername.setText("");
                    mDob.setText("");
                    mEmail.setText("");
                    mName.setText("");
                    dialog.setContent("Gagal Register");
                    dialog.dismiss();
                    Toast.makeText(getActivity(),""+register.getStatus(),Toast.LENGTH_LONG).show();
                }else{
                    Log.d("status",register.getStatus());
                    dialog.setContent("Sukses Register");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(),""+register.getStatus(),Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error","from Retrofit"+error.getMessage());
            }
        });
    }
}

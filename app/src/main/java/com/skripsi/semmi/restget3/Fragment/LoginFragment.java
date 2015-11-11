package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.LoginApiInterface;
import com.skripsi.semmi.restget3.Model.Login;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.ForgotPassActivity;
import com.skripsi.semmi.restget3.activity.RegisterActivity;
import com.skripsi.semmi.restget3.activity.home_activity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 14/10/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button mRegister;
    private Button mLogin;
    private EditText mUsername;
    private EditText mPassword;
    private TextView mForgotPass;
    private  SharedPreferences sharedPreferences;
    private  MaterialDialog dialog;
    private SharedPreferences.Editor editor;
    public static LoginFragment getInstance(){
        LoginFragment fragment=new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // panggil View
        View view=inflater.inflate(R.layout.simple_login_page,container,false);
        mUsername= (EditText) view.findViewById(R.id.username);
        mPassword= (EditText) view.findViewById(R.id.password);
        mForgotPass= (TextView) view.findViewById(R.id.forgotGo);
        sharedPreferences = this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        // kalau udah pernah login ga usah masuk ke tampilan login lagi
        if(sharedPreferences.getString("usernameSession",null)!= null){
            Intent intent1 = new Intent(getActivity(), home_activity.class);
            intent1.putExtra(home_activity.username, sharedPreferences.getString("usernameSession",null));
            startActivity(intent1);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRegister= (Button) view.findViewById(R.id.registerGo);
        mRegister.setOnClickListener(this);
        mLogin= (Button) view.findViewById(R.id.loginGo);
        mLogin.setOnClickListener(this);
        mForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerGo:
                Toast.makeText(getActivity(),"Register Click",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, 102);
                break;
            case R.id.loginGo:
                // buat sebuah dialog bar biar bisa ngasih tau user apa yang sedang terjadi

                 dialog = new MaterialDialog.Builder(getActivity())
                        .title("Proses")
                        .content("Connecting to server")
                        .progress(true,0)
                        .show();
                String username=mUsername.getText().toString();
                String password=mPassword.getText().toString();
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                LoginApiInterface loginApiInterface = restAdapter.create(LoginApiInterface.class);
                loginApiInterface.cekLogin(username, password, new Callback<Login>() {
                    @Override
                    public void success(Login login, Response response) {
                        // Log.d("sukses",""+login.getKode());
                        if(login.getKode().equals("4")) {
//                             dialog.dismiss();

                            dialog.setContent("Berhasil Log in");
                            // kalau sukses bakal simpen kaya season ke device

                            editor=sharedPreferences.edit();
                            editor.putString("usernameSession",login.getUsername());
                            editor.putString("statusSession",login.getStatus());
                            editor.putString("imageSession",login.getImage());
                            editor.putInt("idSession",login.getId());
                            editor.apply();
                            Intent intent1 = new Intent(getActivity(), home_activity.class);
                            intent1.putExtra(home_activity.username, login.getUsername());
                            intent1.putExtra(home_activity.status, login.getStatus());
                            startActivity(intent1);
                        }else{
                            dialog.dismiss();
                             Toast.makeText(getActivity(),"Error"+login.getInfo(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("post Error","from Retrofit"+error.getMessage());
                    }
                });

                break;
            case  R.id.forgotGo:
                    Intent intent3=new Intent(getActivity(), ForgotPassActivity.class);
                    startActivity(intent3);
                break;
        }

    }
    
}

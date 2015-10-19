package com.skripsi.semmi.restget3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skripsi.semmi.restget3.Interface.ForgotPassInterface;
import com.skripsi.semmi.restget3.MainActivity;
import com.skripsi.semmi.restget3.Model.ForgotPass;
import com.skripsi.semmi.restget3.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 19/10/2015.
 */
public class ForgotPassFragment extends Fragment implements View.OnClickListener {
    private EditText mUsername;
    private EditText mEmail;
    private Button mButton;
    private String email;
    private String username;

    public static ForgotPassFragment getInstance(){
        ForgotPassFragment fragment=new ForgotPassFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.simple_forgot_password_page,container,false);
        mUsername= (EditText) view.findViewById(R.id.userForgot);
        mEmail= (EditText) view.findViewById(R.id.emailForgot);
        mButton= (Button) view.findViewById(R.id.btnForgot);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        username=mUsername.getText().toString();
        email=mEmail.getText().toString();
        RestAdapter restAdapter=new RestAdapter.Builder()
                                .setEndpoint(getString(R.string.api))
                                .build();
        ForgotPassInterface forgotPassInterface=restAdapter.create(ForgotPassInterface.class);
        forgotPassInterface.postForgotPassword(username, email, new Callback<ForgotPass>() {
            @Override
            public void success(ForgotPass forgotPass, Response response) {
                if(forgotPass.getKode().equals("7")){
                    Toast.makeText(getActivity(),""+forgotPass.getStatus(),Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(),""+forgotPass.getStatus(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Retrofit" + error.getMessage());
            }
        });

    }
}

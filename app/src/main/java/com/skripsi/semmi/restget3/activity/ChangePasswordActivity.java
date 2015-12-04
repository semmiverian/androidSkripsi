package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 03/12/2015.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button confirm;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        confirm = (Button) findViewById(R.id.changePasswordForm);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmNewPassword = (EditText) findViewById(R.id.confirmNewPassword);

        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String oldPasswordText = oldPassword.getText().toString();
        String newPasswordText = newPassword.getText().toString();
        String confirmNewPasswordText= confirmNewPassword.getText().toString();

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Proses")
                .content("Connecting to server")
                .progress(true,0)
                .show();
        switch(id){
            case R.id.changePasswordForm:
                if(oldPasswordText.equals("") || newPasswordText.equals("") || confirmNewPasswordText.equals("")  ){
                    dialog.dismiss();
                    new MaterialDialog.Builder(this)
                            .title("Gagal")
                            .content("Harus Di Isi Semua")
                            .positiveText("Kembali")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                    materialDialog.dismiss();
                                }
                            })
                            .show();
                    return;
                }
                if(!confirmNewPasswordText.equals(newPasswordText)  ){
                    dialog.dismiss();
                     new MaterialDialog.Builder(this)
                            .title("Gagal")
                            .content("New Password dan Confirm Password harus lah sama")
                            .positiveText("Kembali")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                       materialDialog.dismiss();

                                }
                            })
                            .show();
                    return;
                }

                // TODO Send data to server and set Callback
                break;
        }
    }
}

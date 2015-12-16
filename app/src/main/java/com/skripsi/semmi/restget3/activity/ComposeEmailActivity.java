package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 15/12/2015.
 */
public class ComposeEmailActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String emailAdressExtra = "extra";
    private TextView EmailAdress;
    private EditText SubjectEmail;
    private EditText ContentEmail;
    private Button SendEmaill;
    private int Send_Email_code = 121;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_email);

        EmailAdress= (TextView) findViewById(R.id.sendAdress);
        SubjectEmail = (EditText) findViewById(R.id.subjectEmail);
        ContentEmail = (EditText) findViewById(R.id.contentEmail);
        SendEmaill = (Button) findViewById(R.id.sendEmail);

        SendEmaill.setOnClickListener(this);

        // ambil data intent sent to email address
        if(getIntent()!= null && getIntent().getExtras()!=null) {
            if (getIntent().getExtras().containsKey(emailAdressExtra)) {
                EmailAdress.setText(getIntent().getExtras().getString(emailAdressExtra));
            }
        }

        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendEmail:
                // Set Dialog
//                dialog = new MaterialDialog.Builder(this)
//                        .title("Proses")
//                        .content("Connecting to server")
//                        .progress(true, 0)
//                        .show();


                String sendAdress = EmailAdress.getText().toString();
                String[] addresses= new String[1];
                addresses[0] = sendAdress;
                String subject = SubjectEmail.getText().toString();
                String content = ContentEmail.getText().toString();
                // Send Email
                composeEmail(addresses, subject, content);
                break;
        }
    }

    private void composeEmail(String[] addresses, String subject, String content) {
        Intent SendEmailIntent = new Intent(Intent.ACTION_SENDTO);
        SendEmailIntent.setType("*/*");
        SendEmailIntent.setData(Uri.parse("mailto:"));
        SendEmailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
        SendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        SendEmailIntent.putExtra(Intent.EXTRA_TEXT,content);
        startActivityForResult(SendEmailIntent,Send_Email_code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == Send_Email_code){
                dialog.setContent("Berhasil mengirimkan pesan");
                Intent ProductDetailIntent = new Intent(this,AllProductActivity.class);
                startActivity(ProductDetailIntent);
            }
        }else{
//           showErrorDialog();
        }
    }

    private void showErrorDialog() {
        new MaterialDialog.Builder(this)
                .title("Something went wrong")
                .content("Mohon maaf terjadi kesalahan ketika mengirim email")
                .positiveText("Ulanig")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }
}

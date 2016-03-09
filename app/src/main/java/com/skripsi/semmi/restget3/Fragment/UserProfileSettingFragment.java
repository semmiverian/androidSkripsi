package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.ChangeStatusInterface;
import com.skripsi.semmi.restget3.Interface.UserImageInterface;
import com.skripsi.semmi.restget3.Model.DeleteData;
import com.skripsi.semmi.restget3.Model.UserImage;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.ChangePasswordActivity;
import com.skripsi.semmi.restget3.activity.UploadImageActivity;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 29/11/2015.
 */
public class UserProfileSettingFragment extends Fragment implements View.OnClickListener {

    private TextView Username;
    private TextView Status;
    private ImageView profileImage;
    private String imageLink;
    private  String user;
    private SharedPreferences sharedPreferences;
    private Button mButton;
    private Button changePasswordButton;
    private TextView usernameDetail;
    private TextView namaDetail;
    private TextView emailDetail;
    private TextView dobDetail;
    private TextView majorDetail;
    private TextView statusDetail;
    private Button changeStatusButton;
    private MaterialDialog dialog;

    public static  UserProfileSettingFragment getInstance(){
        UserProfileSettingFragment fragment = new UserProfileSettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_profile_setting,container,false);

        Username= (TextView) view.findViewById(R.id.usernameProfile);
        Status= (TextView) view.findViewById(R.id.usernameStatus);
        profileImage= (ImageView) view.findViewById(R.id.UserProfileImage);
        mButton= (Button) view.findViewById(R.id.changeProfileImage);
        changePasswordButton = (Button) view.findViewById(R.id.changePassword);
        usernameDetail = (TextView) view.findViewById(R.id.usernameDetail);
        emailDetail = (TextView) view.findViewById(R.id.emailDetail);
        namaDetail = (TextView) view.findViewById(R.id.namaDetail);
        dobDetail = (TextView) view.findViewById(R.id.bodDetail);
        majorDetail = (TextView) view.findViewById(R.id.majorDetail);
        statusDetail = (TextView) view.findViewById(R.id.statusDetail);
        changeStatusButton  = (Button) view.findViewById(R.id.changeStatus);
        mButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
        changeStatusButton.setOnClickListener(this);

        String defaultImage=getString(R.string.avatarDefaultString);
        sharedPreferences= this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);

        String currentName =sharedPreferences.getString("namaSession", "nama");
        String currentUser = sharedPreferences.getString("usernameSession","user");
        String currentEmail = sharedPreferences.getString("emailSession","email");
        String bod = sharedPreferences.getString("dobSession","1111111");
        String currentMajor = sharedPreferences.getString("jurusanSession","IT");
        String currentStatus = sharedPreferences.getString("statusSession","alumni");
        usernameDetail.setText(currentUser);
        emailDetail.setText(currentEmail);
        namaDetail.setText(currentName);
        dobDetail.setText(bod);

        majorDetail.setText(currentMajor);
        statusDetail.setText(currentStatus);



        Username.setText(currentName.toUpperCase());
        Status.setText(sharedPreferences.getString("statusSession", "Status"));
        user=sharedPreferences.getString("usernameSession","Username");
        fetchUserImage();

        if(currentStatus.equals("alumni")){
            changeStatusButton.setVisibility(View.INVISIBLE);
        }
        return view;
    }


    // Ngambil avatar user dari server
    private void fetchUserImage() {
        RestAdapter restAdapter3=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserImageInterface userImageInterface=restAdapter3.create(UserImageInterface.class);
        userImageInterface.getUserImage(user, new Callback<UserImage>() {
            @Override
            public void success(UserImage userImage, Response response) {
                Log.d("sukses", "berhasil feed image");
                imageLink = userImage.getImage();
                Log.d("image", userImage.getImage());
                Picasso.with(getContext())
                        .load(imageLink)
                        .into(profileImage);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "feed image error" + error.getMessage());
                showFailureDialog();
            }
        });
    }

    private void showFailureDialog() {
        new MaterialDialog.Builder(getActivity())
                .title("Error")
                .content("Mohon maaf terjadi kesalahan pada penampilan data")
                .positiveText("Muat ulang")
//                        .icon(Drawable.createFromPath(String.valueOf(R.drawable.ic_media_play)))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        fetchUserImage();
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeProfileImage:
                Intent uploadImageIntent = new Intent(getActivity(), UploadImageActivity.class);
                startActivity(uploadImageIntent);
                break;
            case R.id.changePassword:
                Intent changePasswordIntent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                break;
            case R.id.changeStatus:
                new MaterialDialog.Builder(getActivity())
                        .title("Change My Status")
                        .content("I Have Been Graduated, Please change my status from Student to Alumni")
                        .positiveText("confirm")
                        .negativeText("dismiss")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                materialDialog.dismiss();
                                dialog = new MaterialDialog.Builder(getActivity())
                                        .title("Proses")
                                        .content("Connecting to server")
                                        .progress(true,0)
                                        .show();
                                changeMyStatusRequest(dialog);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                materialDialog.dismiss();

                            }
                        })
                        .positiveColorRes(R.color.teal_400)
                        .negativeColorRes(R.color.teal_400)
                        .show();
                break;
        }
    }

    private void changeMyStatusRequest(final MaterialDialog dialog2) {
        int id = sharedPreferences.getInt("idSession", 0);
        if(id == 0){
            Toast.makeText(getActivity(), "ID ERROR", Toast.LENGTH_SHORT).show();
            return;
        }
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        ChangeStatusInterface changeStatusInterface = restAdapter.create(ChangeStatusInterface.class);
        changeStatusInterface.changeStatus(id, new Callback<DeleteData>() {
            @Override
            public void success(DeleteData deleteData, Response response) {
                dialog2.dismiss();
                new MaterialDialog.Builder(getActivity())
                        .title("Success")
                        .content("Congratulations your request is being process by our admin please wait our confirmation email.")
                        .positiveText("got it")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                materialDialog.dismiss();
                            }
                        })
                        .positiveColorRes(R.color.teal_400)
                        .show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("error", "failure: "+error.getMessage());
            }
        });

    }
}

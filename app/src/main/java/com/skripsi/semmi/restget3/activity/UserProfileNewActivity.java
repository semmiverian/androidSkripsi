package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.skripsi.semmi.restget3.Fragment.UserProfileCareerFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileProductListFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileSettingFragment;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.UserProfileTabsAdapter;

/**
 * Created by semmi on 29/11/2015.
 */
public class UserProfileNewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private UserProfileTabsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_new);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.user_profile);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new UserProfileTabsAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new UserProfileSettingFragment(), "Setting");
        mAdapter.addFragment(new UserProfileCareerFragment(), "Career");
        mAdapter.addFragment(new UserProfileProductListFragment(), "Produk");
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this, home_activity.class);
        startActivity(a);
    }
}

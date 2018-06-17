package com.example.redent0r.ethernal4;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

/**
 * @author redent0r
 *
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    BottomNavigationView bottomNavigationView;
    ProfileFragment profileFragment = new ProfileFragment();
    SearchFragment searchFragment = new SearchFragment();
    LotteryFragment lotteryFragment = new LotteryFragment();

    public  static final String KEY_USER_ID = TAG + ".KEY_USER_ID";
    public static String userId;

    public static final String serverUrl = "http://159.65.161.113:5000";
    public static final String serverUrlLogin = serverUrl + "/login";
    public static final String serverUrlRegister = serverUrl + "/register";
    public static final String serverUrlStartLottery = serverUrl + "/startlottery";
    public static final String serverUrlPlayLottery = serverUrl + "/playlottery";
    public static final String serverUrlRefresh = serverUrl + "/getcontracts";
    public static final String serverUrlGetUserContracts = serverUrl + "/getusercontracts";
    public static final String serverUrlGetUserInfo = serverUrl + "/getuserinfo";
    public static final String serverUrlWithdraw = serverUrl + "/withdraw";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userId = intent.getStringExtra(KEY_USER_ID);

        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY_USER_ID, userId);
        profileFragment.setArguments(bundle);

        Log.d(TAG, "onCreate: userId:" + userId);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        setUpBottomNav(bottomNavigationView);

        // initial fragment transaction. default = home
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.home_container, lotteryFragment).commit();
    }

    private void setUpBottomNav(BottomNavigationView bmv) {
        bmv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragmentManager.beginTransaction().replace(R.id.home_container, lotteryFragment).commit();
                        return true;
                    case R.id.action_search:
                        fragmentManager.beginTransaction().replace(R.id.home_container, searchFragment).commit();
                        return true;
                    case R.id.action_profile:
                        fragmentManager.beginTransaction().replace(R.id.home_container, profileFragment).commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

}
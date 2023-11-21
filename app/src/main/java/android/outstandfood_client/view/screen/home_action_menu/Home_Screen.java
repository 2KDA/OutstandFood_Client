package android.outstandfood_client.view.screen.home_action_menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.R;

import android.outstandfood_client.databinding.ActivityHomeScreenBinding;
import android.outstandfood_client.view.screen.fragment.CartFragment;
import android.outstandfood_client.view.screen.fragment.HistoryFragment;
import android.outstandfood_client.view.screen.fragment.home.HomeFragment;
import android.outstandfood_client.view.screen.fragment.ProfileFragment;
import android.view.MenuItem;

import android.outstandfood_client.models.User;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Screen extends AppCompatActivity {

    private ActivityHomeScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home_menu_Home) {
                    replaceFragment(new HomeFragment());
                } else if (id == R.id.home_menu_Orders) {
                    replaceFragment(new CartFragment());
                } else if (id == R.id.home_menu_Wallet) {
                    replaceFragment(new HistoryFragment());
                } else if (id == R.id.home_menu_Profile) {
                    replaceFragment(new ProfileFragment());
                }
                return true;
            }
        });
    }


//    private BottomNavigationView btnNavigation_home_ActionMenu_Main_layout;
//    User user;
//    private String userId;
//    private String username;
//    private String password;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_screen);
//
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("userData")) {
//            user = intent.getParcelableExtra("userData");
//            if (user != null) {
//                username = user.getUsername();
//                password = user.getPassword();
//                userId = user.get_id();
//            }
//        }





    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

}
package android.outstandfood_client.view.screen.home_action_menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;


import android.outstandfood_client.databinding.ActivityHomeScreenBinding;
import android.outstandfood_client.view.screen.fragment.CartFragment;
import android.outstandfood_client.view.screen.fragment.HistoryFragment;
import android.outstandfood_client.view.screen.fragment.MenuFragment;
import android.outstandfood_client.view.screen.fragment.ProfileFragment;
import android.outstandfood_client.view.screen.fragment.home.HomeFragment;
import android.view.MenuItem;

import android.outstandfood_client.models.User;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Screen extends OutstandActivity {

    private ActivityHomeScreenBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home_menu_trangchu) {
                    replaceFragment(new HomeFragment());
                } else if (id == R.id.home_menu_muasam) {
                    replaceFragment(new MenuFragment());
                } else if (id == R.id.home_menu_giohang) {
                    replaceFragment(new CartFragment());
                } else if (id == R.id.home_menu_lichsu) {
                    replaceFragment(new HistoryFragment());
                } else if (id == R.id.home_menu_taikhoan) {
                    replaceFragment(new ProfileFragment());
                }
                return true;
            }
        });
    }




    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

 }
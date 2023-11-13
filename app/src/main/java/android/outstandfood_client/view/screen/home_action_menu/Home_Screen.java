package android.outstandfood_client.view.screen.home_action_menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityHomeScreenBinding;
import android.outstandfood_client.view.screen.fragment.CartFragment;
import android.outstandfood_client.view.screen.fragment.HistoryFragment;
import android.outstandfood_client.view.screen.fragment.home.HomeFragment;
import android.outstandfood_client.view.screen.fragment.ProfileFragment;
import android.view.MenuItem;

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

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}
package android.outstandfood_client.view.screen.home_action_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.models.User;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Screen extends AppCompatActivity {

    private BottomNavigationView btnNavigation_home_ActionMenu_Main_layout;
    User user;
    private String userId;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userData")) {
            user = intent.getParcelableExtra("userData");
            if (user != null) {
                username = user.getUsername();
                password = user.getPassword();
                userId = user.get_id();
            }
        }


    }
}
package android.outstandfood_client.view.screen.home_action_menu;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.R;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.SharedPrefsManager;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Screen extends AppCompatActivity {

    private BottomNavigationView btnNavigation_home_ActionMenu_Main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);



        Context context = getApplicationContext(); // Hoặc sử dụng 'this' nếu bạn đang ở trong một hoạt động

        User user = null;
        if (context != null) {
            user = SharedPrefsManager.getUser(context);
        }
        if (user != null) {
//            txtusernameProfile.setText(user.getUsername());
            Toast.makeText(this, " tên tk là:  "+user.getUsername(), Toast.LENGTH_SHORT).show();
        }



    }
}
package android.outstandfood_client.view.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_screen extends OutstandActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash_screen.this, Onboarding_screen.class));
                finish();
            }
        },2000);
    }
}
package android.outstandfood_client.view.screen.MyDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SetUpDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_detail);

        LinearLayout btnmydetail = findViewById(R.id.rtr9q7jhkn2s);
        LinearLayout btnpass = findViewById(R.id.setnewpass);
        ImageView imgback = findViewById(R.id.imgback_detail);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnmydetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetUpDetailActivity.this, MydetailActivity.class)
                        ; startActivity(intent);
            }
        });
        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetUpDetailActivity.this, SetNewPassActivity.class)
                        ; startActivity(intent);
            }
        });

    }
}
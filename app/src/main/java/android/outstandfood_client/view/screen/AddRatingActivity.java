package android.outstandfood_client.view.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.interfaceApi.ApiRating;
import android.outstandfood_client.models.Rating;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.CommonActivity;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.adapter.ListRatingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRatingActivity extends OutstandActivity {

    TextView tv_name, tv_email;
    TextInputEditText ed_comment;
    Button btn_comment;
    ImageView imgback;

    ListRatingAdapter listRatingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);
        tv_name = findViewById(R.id.tv_userComment);
        tv_email = findViewById(R.id.tv_emailComment);
        ed_comment = findViewById(R.id.ed_comment);
        btn_comment = findViewById(R.id.btn_submitComment);
        imgback = findViewById(R.id.imgback);

        User user = SharedPrefsManager.getUser(getApplicationContext());

        tv_name.setText("Tên người dùng: " + user.getUsername());
        tv_email.setText("Email: " + user.getUserEmail());

        Intent i = getIntent();
        String id_product = i.getStringExtra("id_product");
        String product_name = i.getStringExtra("product_name");

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_comment.getText().toString().length() == 0){
                    show("Outstand'Food","Không để trống đánh giá" );
                } else {
                    Rating rating = new Rating();

                    rating.setUser_name(user.getName());
                    rating.setUser_username(user.getUsername());
                    rating.setProduct_name(product_name);
                    rating.setId_user(user.get_id());
                    rating.setId_product(id_product);
                    rating.setRating(ed_comment.getText().toString());

                    postComment(rating);
                }


            }
        });
        imgback.setOnClickListener(v ->{
            onBackPressed();
            finish();
        });
    }
    void postComment (Rating objRating){
        ApiRating.apiRating.addRating(objRating).enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                CommonActivity.createAlertDialog(AddRatingActivity.this,
                        "Đăng thành công",
                        "Outstand'Food", v -> {
                            onBackPressed();
                            finish();
                        }).show();
            }
            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                show("Outstand'Food", "Đăng  thất bại");
            }
        });
    }
}
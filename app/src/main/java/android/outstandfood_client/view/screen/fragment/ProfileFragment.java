package android.outstandfood_client.view.screen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.outstandfood_client.databinding.FragmentCartBinding;
import android.outstandfood_client.databinding.FragmentHistoryBinding;
import android.outstandfood_client.databinding.FragmentProfileBinding;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.Address.ListAddressActivity;
import android.outstandfood_client.view.screen.Login_screen;
import android.outstandfood_client.view.screen.MyDetail.SetUpDetailActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.outstandfood_client.R;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);

        binding.rvf0hwvbv8pq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User savedUser = SharedPrefsManager.getUser(getContext());
                if (savedUser == null) {
                    Toast.makeText(getContext(), "Bạn cần đăng nhập mới có thể sử dụng", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login_screen.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), ListAddressActivity.class);
                    startActivity(intent);
                }

            }
        });
        User savedUser = SharedPrefsManager.getUser(getContext());
        if (savedUser != null) {

            binding.txtname.setText(savedUser.getUsername());
            binding.txtemail.setText(savedUser.getUserEmail());
            RequestOptions requestOptions = new RequestOptions().transform(new CircleCrop());

            String baseUrl = "https://example.com";
            // Sử dụng Glide để tải và hiển thị hình ảnh từ URL
            Glide.with(binding.getRoot().getContext())
                    .load(baseUrl + savedUser.getImage()) // Đặt URL hình ảnh từ HTTP call
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_person_outline_24) // Ảnh mặc định nếu không tải được
                    .into(binding.imgavtprofile); // ImageView để hiển thị hình ảnh
            binding.txtlogin.setVisibility(View.GONE);

            Log.d("Lỗi" , "Lỗi " +savedUser.getImage());
            Toast.makeText(getContext(), " " +savedUser.getImage(), Toast.LENGTH_SHORT).show();
        }else{
            binding.txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Login_screen.class);
                startActivity(intent);
            }
        });

        }
        binding.txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = getContext();
                if (context != null) {

                    SharedPrefsManager.clearUser(context);

                    Intent intent = new Intent(getContext(), Login_screen.class);
                    startActivity(intent);
                }
            }
        });
        binding.rbvdpmsf5ndm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User savedUser = SharedPrefsManager.getUser(getContext());
                if (savedUser == null) {
                    Toast.makeText(getContext(), "Bạn cần đăng nhập mới có thể sử dụng", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login_screen.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), SetUpDetailActivity.class);
                    startActivity(intent);
                }

            }
        });

        return binding.getRoot();
    }
}
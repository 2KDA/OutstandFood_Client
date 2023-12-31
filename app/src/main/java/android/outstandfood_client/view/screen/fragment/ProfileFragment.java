package android.outstandfood_client.view.screen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.outstandfood_client.OutstandFragment;
import android.outstandfood_client.data.CartDatabase;
import android.outstandfood_client.databinding.FragmentCartBinding;
import android.outstandfood_client.databinding.FragmentHistoryBinding;
import android.outstandfood_client.databinding.FragmentProfileBinding;
import android.outstandfood_client.models.User;
import android.outstandfood_client.object.CommonActivity;
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
import com.facebook.crypto.BuildConfig;

public class ProfileFragment extends OutstandFragment {
    private FragmentProfileBinding binding;


    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        // Kiểm tra xem có sự thay đổi dữ liệu của User không
        User savedUser = SharedPrefsManager.getUser(getContext());

        if(SharedPrefsManager.getUser(getContext()) != null){
            binding.layoutLogout.setVisibility(View.VISIBLE);
            binding.layoutLogined.setVisibility(View.VISIBLE);
            binding.layoutNoLogin.setVisibility(View.GONE);
        }else{
            binding.layoutLogout.setVisibility(View.GONE);
            binding.layoutLogined.setVisibility(View.GONE);
            binding.layoutNoLogin.setVisibility(View.VISIBLE);
        }

        if (savedUser != null) {
            // Cập nhật thông tin người dùng
            binding.txtname.setText(savedUser.getUsername());
            binding.txtemail.setText(savedUser.getUserEmail());
            RequestOptions requestOptions = new RequestOptions().transform(new CircleCrop());


            Glide.with(binding.getRoot().getContext())
                    .load(savedUser.getImage()) // Đặt URL hình ảnh từ HTTP call
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_person_outline_24) // Ảnh mặc định nếu không tải được
                    .into(binding.imgavtprofile); // ImageView để hiển thị hình ảnh
//            binding.txtlogin.setVisibility(View.GONE);
        } else {
            binding.Logined.setVisibility(View.GONE);
            binding.txtname.setText("");
            binding.txtemail.setText("");
            binding.imgavtprofile.setImageResource(R.drawable.ic_person_outline_24);
//            binding.txtlogin.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);

        binding.layoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ListAddressActivity.class);
                    startActivity(intent);
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Login_screen.class);
                    startActivity(intent);
            }
        });

        binding.txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonActivity.createDialog(getActivity(),
                        "Bạn muốn đăng xuất?",
                        "Outstand'Food","Hủy","Đồng ý",
                        null,
                        v -> {
                                SharedPrefsManager.clearUser(getContext());
                                Intent intent = new Intent(getContext(), Login_screen.class);
                                startActivity(intent);
                                CartDatabase.getInstance(getActivity()).cartDao().DeleteAll();
                        }
                        ).show();
            }
        });
        binding.layoutUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getContext(), SetUpDetailActivity.class);
                    startActivity(intent);
            }
        });

        binding.layoutShare.setOnClickListener(v ->{
            share(getContext());
        });

        return binding.getRoot();
        }


    public void share(Context context) {
        String appPackageName = BuildConfig.APPLICATION_ID;
        String appName = context.getString(R.string.app_name);
        String appDesc = context.getString(R.string.facebook_app_id);
        String shareBodyText = appDesc + "\n https://play.google.com/store/apps/details?id=" + appPackageName;

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TITLE, appName);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);

        // Check if there's an activity that can handle the intent
        if (sendIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(sendIntent, null));
        } else {
            // Handle the case where no activity can handle the intent
            Toast.makeText(context, "No app found to handle the share action", Toast.LENGTH_SHORT).show();
        }
    }
}
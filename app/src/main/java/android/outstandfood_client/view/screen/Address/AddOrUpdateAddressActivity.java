package android.outstandfood_client.view.screen.Address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.databinding.ActivityAddOrUpdateAddressBinding;
import android.outstandfood_client.databinding.ActivityListAddressBinding;
import android.outstandfood_client.interfaceApi.ApiServiceAddress;
import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.AddressResponse;
import android.outstandfood_client.models.User;
import android.outstandfood_client.models.address.CityAddress;
import android.outstandfood_client.models.address.DistrictAddress;
import android.outstandfood_client.models.address.WardsAddress;
import android.outstandfood_client.object.CommonActivity;
import android.outstandfood_client.object.SharedPrefsManager;
import android.outstandfood_client.view.screen.MyDetail.MydetailActivity;
import android.outstandfood_client.view.screen.adapter.AddressAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddOrUpdateAddressActivity extends OutstandActivity {
    private ActivityAddOrUpdateAddressBinding binding;
    static final String BASE_URL = "https://outstanfood-com.onrender.com/api/";
    private List<CityAddress> cityList;
    private List<DistrictAddress> districtList;
    private List<WardsAddress> wardList;

    private ArrayAdapter<CityAddress> cityAdapter;
    private ArrayAdapter<DistrictAddress> districtAdapter;
    private ArrayAdapter<WardsAddress> wardAdapter;
    private String selectedCity;
    private String selectedDistrict;
    private String selectedWard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOrUpdateAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AddressModel addressModel = (AddressModel) getIntent().getSerializableExtra("address_model");
        User savedUser = SharedPrefsManager.getUser(this);
        String addressId = getIntent().getStringExtra("_idAddress");

        if (addressId == null || addressId.isEmpty()) {
          binding.txtman.setText("Thêm địa chỉ");
          binding.btnaddress.setText("Tạo mới");
            binding.btnaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String address = binding.edtaddress.getText().toString().trim();
                    String phone = binding.edtphone.getText().toString().trim();
                    if (address.isEmpty() || phone.isEmpty()) {
                        show("Outstand'Food", "Vui lòng nhập đầy đủ thông tin.");
                    }
                    else if (!isValidPhoneNumber(phone)) {
                        show("Outstand'Food", "Số điện thoại không hợp lệ");
                    }
                    else {
                        CommonActivity.createDialog(AddOrUpdateAddressActivity.this,
                                "\nĐịa chỉ: " + printResult() +"\n " + "\nSố điện thoại: "+ phone + "\n",
                                "Xác nhận","Đồng ý", "Hủy",
                                v -> {
                                    showWaitProgress(AddOrUpdateAddressActivity.this);
                                    addAddress(savedUser.get_id(), printResult(), phone);
                                },null).show();
                    }
                }
            });

        } else if (addressModel.getId() != " " || addressModel.getAddress() != null) {
            binding.txtman.setText("Sửa địa chỉ");
            binding.btnaddress.setText("Sửa địa chỉ");

            binding.edtphone.setText(addressModel.getPhone());
            binding.edtaddress.setText(addressModel.getAddress());
            binding.btnaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String address = binding.edtaddress.getText().toString().trim();
                    String phone = binding.edtphone.getText().toString().trim();
                    if (address.isEmpty() || phone.isEmpty()) {
                        show("Outstand'Food", "Vui lòng nhập đầy đủ thông tin.");
                    }else if (!isValidPhoneNumber(phone)) {
                        show("Outstand'Food", "Số điện thoại không hợp lệ.");
                    }
                    else {
                        CommonActivity.createDialog(AddOrUpdateAddressActivity.this,
                                "Địa chỉ: " + printResult() +"\n" + "Số điện thoại: "+ phone,
                                "Xác nhận","Đồng ý", "Hủy",
                                v -> {
                                    showWaitProgress(AddOrUpdateAddressActivity.this);
                                    updateAddress(addressModel.getId(), printResult(), phone);
                                },null).show();
                    }
                }
            });
        }

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initView();
    }
    private void initView() {
        fetchCities();
        cityList = new ArrayList<>();
        districtList = new ArrayList<>();
        wardList = new ArrayList<>();

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtList);
        wardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wardList);

        binding.spinnerCity.setAdapter(cityAdapter);
        binding.spinnerDistrict.setAdapter(districtAdapter);
        binding.spinnerWard.setAdapter(wardAdapter);


        binding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CityAddress selected = cityList.get(position);
                int cityCode = selected.getCode();
                selectedCity = selected.getName();
                fetchDistricts(cityCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        binding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictAddress selected = districtList.get(position);
                selectedDistrict = selected.getName();
                int districtCode = selected.getCode();
                fetchWards(districtCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WardsAddress selected = wardList.get(position);
                selectedWard = selected.getName();
                printResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void addAddress(String id_user, String address, String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress addresInterface = retrofit.create(ApiServiceAddress.class);

        Call<AddressModel> call = addresInterface.addAddress(id_user, address, phone);
        call.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                hideWaitProgress();
                if (response.isSuccessful() && response.body() != null) {
                    CommonActivity.createAlertDialog(AddOrUpdateAddressActivity.this,"Thêm địa chỉ thành công",
                            "Outstand'Food",new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("result", RESULT_OK);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            }
                    ).show();
                } else {
                    show("Outsand'Food", "Lỗi thêm địa chỉ.");
                }
            }
            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {
                hideWaitProgress();
                show("Outstand'Food", "Lỗi : " + t.getMessage());
            }
        });
    }
    private void updateAddress(String id, String address, String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServiceAddress addressInterface = retrofit.create(ApiServiceAddress.class);

        Call<AddressModel> call = addressInterface.updateAddress(id, address, phone);
        call.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                hideWaitProgress();
                if (response.isSuccessful() && response.body() != null) {
                    CommonActivity.createAlertDialog(AddOrUpdateAddressActivity.this,"Sửa địa chỉ thành công.",
                            "Outstand'Food",new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("result", RESULT_OK);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            }
                    ).show();
                } else {
                    show("Outsand'Food", "Lỗi sửa địa chỉ.");
                }
            }

            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {
                hideWaitProgress();
                show("Outstand'Food", "Lỗi : " + t.getMessage());
             }
        });
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0[1-9][0-9]{8}";
        return phoneNumber.matches(regex);
    }

    private String printResult() {
        String result = binding.edtaddress.getText().toString() + ", "+
                binding.spinnerWard.getSelectedItem().toString() + ", " +
                binding.spinnerDistrict.getSelectedItem().toString() + ", " +
                binding.spinnerCity.getSelectedItem().toString();
        return result;
    }

    private void fetchCities() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url("https://provinces.open-api.vn/api/?depth=1")
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONArray cityArray = new JSONArray(jsonData);
                    cityList.clear();
                    for (int i = 0; i < cityArray.length(); i++) {
                        JSONObject cityObj = cityArray.getJSONObject(i);
                        CityAddress cityAddress = new CityAddress();
                        String cityName = cityObj.getString("name");
                        cityAddress.setName(cityName != null ? cityName : "");

                        int cityCode = cityObj.getInt("code");
                        cityAddress.setCode(cityCode != 0 ? cityCode : 0);

                        String cityCodeName = cityObj.getString("codename");
                        cityAddress.setCodename(cityCodeName != null ? cityCodeName : "");

                        String cityDivisionType = cityObj.getString("division_type");
                        cityAddress.setDivision_type(cityDivisionType != null ? cityDivisionType : "");

                        String cityPhoneCode = cityObj.getString("phone_code");
                        cityAddress.setPhone_code(cityPhoneCode != null ? cityPhoneCode : "");

                        cityList.add(cityAddress);
                    }
                    Log.d("TAG", "run: LISTCITY" + cityList);
                    runOnUiThread(() -> cityAdapter.notifyDataSetChanged());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void fetchDistricts(int code) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url("https://provinces.open-api.vn/api/p/" + code + "?depth=2")
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray districtArray = jsonObject.getJSONArray("districts");
                    districtList.clear();
                    for (int i = 0; i < districtArray.length(); i++) {
                        JSONObject districtObj = districtArray.getJSONObject(i);
                        DistrictAddress districtAddress = new DistrictAddress();

                        String districtName = districtObj.getString("name");
                        districtAddress.setName(districtName != null ? districtName : "");

                        int districtCode= districtObj.getInt("code");
                        districtAddress.setCode(districtCode != 0 ? districtCode : 0);

                        String districtDivisionType = districtObj.getString("division_type");
                        districtAddress.setDivision_type(districtDivisionType != null ? districtDivisionType : "");

                        String districtCodeName = districtObj.getString("codename");
                        districtAddress.setCodename(districtCodeName != null ? districtCodeName : "");

                        int districtProvinceCode = districtObj.getInt("province_code");
                        districtAddress.setProvince_code(districtProvinceCode != 0 ? districtProvinceCode : 0);
                        districtList.add(districtAddress);
                    }
                    Log.d("TAG", "run: LISTDISTRIC" + districtList);
                    runOnUiThread(() -> {
                        districtAdapter.notifyDataSetChanged();
                        binding.spinnerDistrict.setSelection(0);
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    private void fetchWards(int district) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    String url = "https://provinces.open-api.vn/api/d/" + district + "?depth=2";
                    Log.d("TAG", "run: " + url);
                    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray wardArray = jsonObject.getJSONArray("wards");
                    wardList.clear();
                    for (int i = 0; i < wardArray.length(); i++) {
                        JSONObject wardObj = wardArray.getJSONObject(i);
                        WardsAddress wardsAddress = new WardsAddress();

                        String wardsName = wardObj.getString("name");
                        wardsAddress.setName(wardsName != null ? wardsName : "");

                        int wardsCode= wardObj.getInt("code");
                        wardsAddress.setCode(wardsCode != 0 ? wardsCode : 0);

                        String wardsDivisionType = wardObj.getString("division_type");
                        wardsAddress.setDivision_type(wardsDivisionType != null ? wardsDivisionType : "");

                        String wardsCodeName = wardObj.getString("codename");
                        wardsAddress.setCodename(wardsCodeName != null ? wardsCodeName : "");

                        int wardsDistrictCode = wardObj.getInt("district_code");
                        wardsAddress.setCode(wardsDistrictCode != 0 ? wardsDistrictCode : 0);

                        wardList.add(wardsAddress);
                    }
                    Log.d("TAG", "run: LISTWARD" +  wardList);
                    runOnUiThread(() -> {
                        wardAdapter.notifyDataSetChanged();
                        binding.spinnerWard.setSelection(0);
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
package android.outstandfood_client;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.CountDownTimer;
import android.outstandfood_client.object.CommonActivity;


import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;

public class OutstandFragment extends Fragment {
    private static final long CONNECTION_TIMEOUT = 5000;
    private CountDownTimer connectionTimer;
    public void showWaitProgress(Context context) {
        hideWaitProgress();

        OutstandActivity.waitProgress = new ProgressDialog(context);
        OutstandActivity.waitProgress.setMessage("Vui lòng chờ…");
        OutstandActivity.waitProgress.setCancelable(false);
        OutstandActivity.waitProgress.show();
    }

    public void showWaitProgress(Context context, String msg) {
        hideWaitProgress();
        OutstandActivity.waitProgress = new ProgressDialog(context);
        OutstandActivity.waitProgress.setMessage(msg);
        OutstandActivity.waitProgress.setCancelable(false);
        OutstandActivity.waitProgress.show();
    }

    public void hideWaitProgress() {
        if (OutstandActivity.waitProgress != null) {
            OutstandActivity.waitProgress.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startConnectionTimer();
    }

    private void startConnectionTimer() {
        stopConnectionTimer();
        connectionTimer = new CountDownTimer(CONNECTION_TIMEOUT, CONNECTION_TIMEOUT) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (!checkInternetConnection()) {
                    CommonActivity.createAlertDialog(getActivity(), "Lỗi", "Không có kết nối mạng.",
                            v -> {
                                getActivity().finishAffinity();
                            });
                }
            }
        };

        connectionTimer.start();
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopConnectionTimer();
    }

    private void stopConnectionTimer() {
        if (connectionTimer != null) {
            connectionTimer.cancel();
            connectionTimer = null;
        }
    }

}

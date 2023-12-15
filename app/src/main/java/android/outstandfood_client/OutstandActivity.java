package android.outstandfood_client;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.CountDownTimer;
import android.outstandfood_client.object.CommonActivity;


import androidx.appcompat.app.AppCompatActivity;

public abstract class OutstandActivity extends AppCompatActivity {
    public static ProgressDialog waitProgress;
    private static final long CONNECTION_TIMEOUT = 5000;
    private CountDownTimer connectionTimer;
    public void show(String tittle, String message) {
        Dialog dialog;
        if (tittle != null) {
            dialog = CommonActivity.createAlertDialog(this,
                    message, tittle
            );
        } else {
            dialog = CommonActivity.createAlertDialog(this,
                    message, "ERROR"
            );
        }
        if (dialog != null) {
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
        }

    }
    public void showWaitProgress(Context context) {
        if (isFinishing()) {
            return;
        }
        hideWaitProgress();

        waitProgress = new ProgressDialog(context);
        waitProgress.setMessage("Vui lòng chờ…");
        waitProgress.setCancelable(false);
        waitProgress.show();
    }

    public void showWaitProgress(Context context, String message) {
        if (isFinishing()) {
            return;
        }
        hideWaitProgress();

        waitProgress = new ProgressDialog(context);
        waitProgress.setMessage(message);
        waitProgress.setCancelable(false);
        waitProgress.show();

    }

    public void hideWaitProgress() {
        if (waitProgress != null && waitProgress.isShowing()) {
            waitProgress.cancel();
        }
    }
    public void showNoInternet(){
        CommonActivity.createAlertDialog(OutstandActivity.this, "Lỗi", "Không có kết nối mạng.",
                v -> {
                    finishAffinity();
                });
    }

    public void dissmissProgressDialog() {
        if (waitProgress != null && waitProgress.isShowing()) {
            waitProgress.dismiss();
        }
    }

    @Override
    protected void onResume() {
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
                    showNoInternet();
                }
            }
        };
        connectionTimer.start();
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    @Override
    protected void onPause() {
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

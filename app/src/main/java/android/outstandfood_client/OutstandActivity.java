package android.outstandfood_client;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.outstandfood_client.object.CommonActivity;


import androidx.appcompat.app.AppCompatActivity;

public abstract class OutstandActivity extends AppCompatActivity {
    public static ProgressDialog waitProgress;
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

    public void dissmissProgressDialog() {
        if (waitProgress != null && waitProgress.isShowing()) {
            waitProgress.dismiss();
        }
    }

}

package android.outstandfood_client;

import android.app.ProgressDialog;
import android.content.Context;


import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;

public class OutstandFragment extends Fragment {
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

}

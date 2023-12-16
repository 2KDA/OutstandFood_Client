package android.outstandfood_client.object;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.CountDownTimer;
import android.outstandfood_client.OutstandActivity;
import android.outstandfood_client.R;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class CommonActivity extends OutstandActivity {

    private static Dialog dialog;
    private static final long CONNECTION_TIMEOUT = 5000;
    private CountDownTimer connectionTimer;

    public static Dialog createAlertDialog(Activity act, String message, String title) {
        try {
            if (dialog != null) {
                dialog.cancel();
            }
            dialog = new Dialog(act);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_common);
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
            tvTitle.setText(title);
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvDialogContent);
            tvMessage.setMaxLines(5);
            tvMessage.setText(message);
            Button btnLeft = (Button) dialog.findViewById(R.id.btnLeft);
            btnLeft.setText("Đồng ý");
            dialog.findViewById(R.id.btnRight).setVisibility(View.GONE);
            dialog.findViewById(R.id.btnView).setVisibility(View.GONE);
            dialog.setCancelable(false);
            btnLeft.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });

            return dialog;
        } catch (Exception e) {
            return null;
        }
    }

    public static Dialog createDialog(Activity act, String message,
                                      String title, String leftButtonText, String rightButtonText,
                                      final View.OnClickListener leftClick, final View.OnClickListener rightClick) {
        try {
            final Dialog dialog = new Dialog(act);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_common);
            dialog.setCancelable(false);
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
            tvTitle.setText(title);
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvDialogContent);
            tvMessage.setText(message);

            // dialog.setPositiveButton(mActivity.getString(R.string.ok), null);
            Button btnLeft = (Button) dialog.findViewById(R.id.btnLeft);
            Button btnRight = (Button) dialog.findViewById(R.id.btnRight);
            btnLeft.setText(leftButtonText);
            btnRight.setText(rightButtonText);
            dialog.show();
            View.OnClickListener leftClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (leftClick != null) {
                        leftClick.onClick(arg0);
                    }
                    dialog.dismiss();
                }
            };
            View.OnClickListener rightClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (rightClick != null) {
                        rightClick.onClick(arg0);
                    }
                    dialog.dismiss();
                }
            };
            btnLeft.setOnClickListener(leftClickListener);
            btnRight.setOnClickListener(rightClickListener);
            return dialog;
        } catch (Exception e) {
            return null;
        }
    }

    public static Dialog createAlertDialog(Activity act, String message, String title, final View.OnClickListener onClick) {
        try {

            final Dialog dialog = new Dialog(act);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_common);
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
            tvTitle.setText(title);
            dialog.setCancelable(false);
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvDialogContent);
            tvMessage.setText(message);

            Button btnLeft = (Button) dialog.findViewById(R.id.btnLeft);
            btnLeft.setText("Đồng ý");
            dialog.findViewById(R.id.btnRight).setVisibility(View.GONE);
            dialog.findViewById(R.id.btnView).setVisibility(View.GONE);
            View.OnClickListener onClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (onClick != null) {
                        onClick.onClick(arg0);
                    }
                    dialog.dismiss();
                }
            };

            btnLeft.setOnClickListener(onClickListener);
            return dialog;
        } catch (Exception e) {
            return null;
        }
    }

}

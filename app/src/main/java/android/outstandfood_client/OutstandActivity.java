package android.outstandfood_client;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.outstandfood_client.object.CommonActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class OutstandActivity extends AppCompatActivity {

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


}

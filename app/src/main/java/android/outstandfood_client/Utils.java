package android.outstandfood_client;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {
    public static void showCustomToast(Context context, String message) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // Customize the content of the Toast layout here if needed
        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);

        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0); // Set gravity to center

        toast.show();
    }
}

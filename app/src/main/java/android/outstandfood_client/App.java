package android.outstandfood_client;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.getkeepsafe.relinker.BuildConfig;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                "AcjH_OcXyM4TYqlCk-8hrJRH-Vxll0b8clOftVS10ODOjfEuEUqBzmKA16WQ-iEhAUaCU7XWcc66rAso",
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                ""+"android.outstandfood.client"+"://paypalpay"
        ));
        Log.d("Paypal",getApplicationContext().getPackageName()  + "://paypalpay");

    }
}

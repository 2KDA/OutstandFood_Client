package android.outstandfood_client.view.screen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.outstandfood_client.MainActivity;
import android.outstandfood_client.R;
import android.outstandfood_client.view.Adapter_onboarding;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;



public class Onboarding_screen extends AppCompatActivity {

    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button nextbtn, skipbtn;

    TextView[] dots;
    Adapter_onboarding viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        nextbtn = findViewById(R.id.nextbtn);
        skipbtn = findViewById(R.id.skipButton);


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) < 2) {
                    mSLideViewPager.setCurrentItem(getitem(1), true);
                }else{
                    Intent i = new Intent(Onboarding_screen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Onboarding_screen.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new Adapter_onboarding(this);

        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
    }

    public void setUpindicator(int position) {

        dots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            }
            mDotLayout.addView(dots[i]);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));
        }

    }

    private int getitem(int i) {

        return mSLideViewPager.getCurrentItem() + i;
    }
}
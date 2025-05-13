package com.ashikurrahmandev.sobee.Activitys;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ashikurrahmandev.sobee.R;
import com.ashikurrahmandev.sobee.Utill.StatusBarCreateAccount;
import com.ashikurrahmandev.sobee.Utill.StatusBarUtil;
import com.ashikurrahmandev.sobee.databinding.ActivityProfileBinding;

import java.util.Random;

public class CreateAccountActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean isDarkMode = (getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        StatusBarCreateAccount.setStatusBarIconColor(this, isDarkMode);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        editor = sharedPreferences.edit();



          String titleText = "Trusted <font color ='#CDDC39'>Shopping Made</font> Easy";

          binding.titleText.setText(Html.fromHtml(titleText));

        String[] messages = {"Smart Shopping", "Best Deals Everyday", "Fast & Secure", "Easy Payments", "Shop With Confidence"};
        int[] index = {0};

        Handler handler = new Handler();
        Runnable updateText = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int randomWidth = 200 + random.nextInt(101);

                ValueAnimator lineAnim = ValueAnimator.ofInt(0, randomWidth);
                lineAnim.setDuration(400);
                lineAnim.addUpdateListener(animation -> {
                    int value = (int) animation.getAnimatedValue();
                    ViewGroup.LayoutParams params = binding.animatedLine.getLayoutParams();
                    params.width = value;
                    binding.animatedLine.setLayoutParams(params);
                });

                // Text fade out, change, fade in
                binding.animatedText.animate().alpha(0f).setDuration(300).withEndAction(() -> {
                    binding.animatedText.setText(messages[index[0] % messages.length]);
                    binding.animatedText.animate().alpha(1f).setDuration(300).start();
                }).start();

                lineAnim.start();

                index[0]++;
                handler.postDelayed(this, 3000);

            }
        };
        handler.post(updateText);




    }//OnCreate bundle end here
}
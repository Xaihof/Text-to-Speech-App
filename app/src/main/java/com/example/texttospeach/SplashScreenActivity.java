package com.example.texttospeach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    //Variables.
    Animation top_anim_var, bottom_amin_var;
    TextView logo_var, rights_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Writing a code to remove Status Bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);


        //Animations.
        top_anim_var = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom_amin_var = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks.
        logo_var = findViewById(R.id.Logo);
        rights_var = findViewById(R.id.rights);

        //Assigining the Animations.
        logo_var.setAnimation(top_anim_var);
        rights_var.setAnimation(bottom_amin_var);

        //Making the Handler.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Making an Intenet;
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);

                //Using finish method to restrict the application from going back to splash screen when is closed.
                finish();
            }
            //Giving value before parenthesis to give delay in milli seconds.
        }, 2000);
    }
}

//Go to AndroidManifest and follow the steps.
package edu.ptit.vhlee.galleryplus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(getPhotoIntent(this));
        finish();
    }

    public static Intent getPhotoIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}

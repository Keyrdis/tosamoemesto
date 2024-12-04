package com.example.tosamoemesto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.example.tosamoemesto.R;

public class ScreenActivity extends BaseActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 2000L;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            // Transition to the main activity
            Intent mainIntent = new Intent(ScreenActivity.this, SignInActivity.class);
            startActivity(mainIntent);
            // Close the splash screen
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove callbacks to prevent memory leaks
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}

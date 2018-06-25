package com.jinke.housekeeper.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RestartStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}

package com.example.rex.xmcg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.Doctor;
import com.orhanobut.logger.Logger;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Doctor doctor = (Doctor) bundle.getSerializable("doctor");
        Logger.d(doctor.doctorName);
    }
}

package com.example.lotogether;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,mFragment.OnFragmentInteractionListener{

    Button b1,b2,b3,b4;
    mFragment mf1,mf2,mf3,mf4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mf1=mFragment.newInstance(R.layout.m1_layout +"","1");
        mf2=mFragment.newInstance(R.layout.m2_layout +"","1");
        mf3=mFragment.newInstance(R.layout.m3_layout +"","1");
        mf4=mFragment.newInstance(R.layout.m4_layout +"","1");
        getSupportFragmentManager().beginTransaction().add(R.id.ll,mf4).commitAllowingStateLoss();

        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button1:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll,mf1).commitAllowingStateLoss();
                break;
            case R.id.button2:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll,mf2).commitAllowingStateLoss();
                break;
            case R.id.button3:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll,mf3).commitAllowingStateLoss();
                break;
            case R.id.button4:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll,mf4).commitAllowingStateLoss();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

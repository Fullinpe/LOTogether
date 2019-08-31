package com.example.lotogether;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,mFragment.OnFragmentInteractionListener{

    public static String S_ID,MGR;
    private String[][] strings;
    private Handler handler =new Handler();
    Button b1,b2,b3,b4;
    mFragment mf1,mf2,mf3,mf4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        S_ID=intent.getStringExtra("S_ID");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                strings=null;
//                strings=DBUtils.select_DB("SELECT * FROM members WHERE S_ID='"
//                        +S_ID+"'","MGR");
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(strings!=null)
//                        {
//                            if(strings.length==1)
//                            {
//                                MGR=strings[0][0];
//                                if(MGR == null)
//                                {
//                                    b2.setVisibility(View.GONE);
//                                    b3.setVisibility(View.GONE);
//                                }else if(MGR.equals("1")||MGR.equals("2"))
//                                {
//                                    b2.setVisibility(View.GONE);
//                                }else if(MGR.equals("3"))
//                                {
//                                    b2.setVisibility(View.GONE);
//                                }
//                            }
//                            else if(strings.length>1)
//                            {
//                                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
//                                builder.setTitle("提示：");
//                                builder.setMessage("请联系管理员，账号异常");
//                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        Objects.requireNonNull(MainActivity.this).finish();
//                                    }
//                                });
//                                builder.show();
//                            }
//                        }
//                        else
//                        {
//                            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
//                            builder.setTitle("提示：");
//                            builder.setMessage("请联系管理员，账号异常");
//                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Objects.requireNonNull(MainActivity.this).finish();
//                                }
//                            });
//                            builder.show();
//                        }
//                    }
//                });
//            }
//        }).start();
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

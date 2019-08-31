package com.example.lotogether;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LogActivity extends AppCompatActivity {

    String[][] strings;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        final EditText ed1=findViewById(R.id.s_id);
        final EditText ed2=findViewById(R.id.password);
        Button sign_up=findViewById(R.id.sign_up);
        Button sign_in=findViewById(R.id.sign_in);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Objects.requireNonNull(LogActivity.this),SignUpActivity.class);
                startActivity(intent);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        strings=null;
                        strings=DBUtils.select_DB("SELECT * FROM admin WHERE S_ID='"
                                +ed1.getText().toString()+"' AND Password='"
                                +ed2.getText().toString()+"'","S_ID");

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(strings!=null)
                                {
                                    if(strings.length>0)
                                    {
                                        Intent intent=new Intent();
                                        intent.putExtra("S_ID",ed1.getText().toString());
                                        intent.setClass(Objects.requireNonNull(LogActivity.this),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(LogActivity.this);
                                        builder.setTitle("提示：");
                                        builder.setMessage("请确认填写学号或密码是否有误");
                                        builder.setPositiveButton("确定", null);
                                        builder.show();
                                    }
                                }
                                else
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(LogActivity.this);
                                    builder.setTitle("提示：");
                                    builder.setMessage("请确认网络链路正确");
                                    builder.setPositiveButton("确定", null);
                                    builder.show();
                                }
                            }
                        });

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}

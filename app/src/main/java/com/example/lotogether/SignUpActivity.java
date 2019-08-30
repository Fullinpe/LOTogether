package com.example.lotogether;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    String[][] temp;
    int status=0;
    boolean exit_test=false,global=false;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final ImageView status1=findViewById(R.id.status1);
        final ImageView status2=findViewById(R.id.status2);
        final ImageView status3=findViewById(R.id.status3);
        final ImageView status4=findViewById(R.id.status4);
        final ImageView status5=findViewById(R.id.status5);
        final ImageView status6=findViewById(R.id.status6);
        final EditText ed1=findViewById(R.id.ed1_signup);
        final EditText ed2=findViewById(R.id.ed2_signup);
        final EditText ed3=findViewById(R.id.ed3_signup);
        final EditText ed4=findViewById(R.id.ed4_signup);
        final EditText ed5=findViewById(R.id.ed5_signup);
        final EditText ed6=findViewById(R.id.ed6_signup);
        Button signup=findViewById(R.id.signup_signup);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!exit_test)
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(!ed1.getText().toString().equals(""))
                                status1.setVisibility(View.VISIBLE);
                            else
                                status1.setVisibility(View.INVISIBLE);
                            if(!ed2.getText().toString().equals("")&&ed2.getCurrentTextColor()==Color.BLACK)
                                status2.setVisibility(View.VISIBLE);
                            else
                                status2.setVisibility(View.INVISIBLE);
                            if(!ed3.getText().toString().equals(""))
                                status3.setVisibility(View.VISIBLE);
                            else
                                status3.setVisibility(View.INVISIBLE);
                            if(!ed4.getText().toString().equals(""))
                                status4.setVisibility(View.VISIBLE);
                            else
                                status4.setVisibility(View.INVISIBLE);
                            if(!ed5.getText().toString().equals(""))
                                status5.setVisibility(View.VISIBLE);
                            else
                                status5.setVisibility(View.INVISIBLE);
                            if(ed6.getText().toString().equals(ed5.getText().toString()))
                                status6.setVisibility(View.VISIBLE);
                            else
                                status6.setVisibility(View.INVISIBLE);
                            global = status1.getVisibility() == View.VISIBLE && status2.getVisibility() == View.VISIBLE &&
                                    status3.getVisibility() == View.VISIBLE && status4.getVisibility() == View.VISIBLE &&
                                    status5.getVisibility() == View.VISIBLE && status6.getVisibility() == View.VISIBLE;
//                            Log.e("TAGG","查看："+global);
                        }
                    });


                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!exit_test)
                {
                    temp=DBUtils.select_DB("SELECT * FROM members WHERE S_ID='"+ed2.getText().toString()+"'","S_ID");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(temp!=null)
                            {
                                if(temp.length>0)
                                    ed2.setTextColor(Color.RED);
                                else
                                    ed2.setTextColor(Color.BLACK);
                            }
                            else
                                ed2.setTextColor(Color.BLACK);
                        }
                    });

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        signup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        status=0;
                        if(global)
                        {
                            status = DBUtils._DB("INSERT INTO members (S_ID,`NAME`,QQ,TEL) VALUES ('"
                                    +ed2.getText().toString() +"','"
                                    +ed1.getText().toString() +"','"
                                    +ed3.getText().toString()+"','"
                                    +ed4.getText().toString()+"')");
                            if(status==1)
                                status=DBUtils._DB("INSERT INTO admin VALUES ('"
                                        +ed2.getText().toString()+"','"
                                        +ed5.getText().toString()+"')");
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder=new AlertDialog.Builder(SignUpActivity.this);
                                builder.setTitle("提示：");
                                String string;

                                if(status==1)
                                {
                                    string="恭喜你，欢迎加入";
                                    builder.setIcon(R.mipmap.ic_launcher);
                                }
                                else if(global)
                                    string="链路或服务器故障，稍后再试";
                                else
                                    string="请确认填写信息是否有误";

                                builder.setMessage(string);
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(status==1)
                                            finish();
                                    }
                                });
                                builder.show();
                            }
                        });

                    }
                }).start();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exit_test=true;
    }
}

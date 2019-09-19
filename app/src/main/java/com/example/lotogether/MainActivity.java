package com.example.lotogether;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,mFragment.OnFragmentInteractionListener{

    public static String S_ID,MGR="0";
    private boolean exit_d=false;
    private String[][] strings;
    private Handler handler =new Handler();
    Button b1,b2,b3,b4;
    mFragment mf1,mf2,mf3,mf4;
    private boolean check_update=true;
    private boolean exit_out=true;
    private boolean m2_onscreen =false;
    static int temp,temp1;
    static boolean pri1_able=false,pri2_able=false;
    private mFragment current_mf;
    private boolean mf1_b=false,mf2_b=false,mf3_b=false,mf4_b=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.CALL_PHONE
        }, 0x11);

        Intent intent=getIntent();
        S_ID=intent.getStringExtra("S_ID");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("123", "测试通知组",
                    NotificationManager.IMPORTANCE_HIGH);
            // 设置渠道描述
            notificationChannel.setDescription("测试通知组");
            // 是否绕过请勿打扰模式
            notificationChannel.canBypassDnd();
            // 设置绕过请勿打扰模式
            notificationChannel.setBypassDnd(true);
            // 桌面Launcher的消息角标
            notificationChannel.canShowBadge();
            // 设置显示桌面Launcher的消息角标
            notificationChannel.setShowBadge(true);
            // 设置通知出现时声音，默认通知是有声音的
            notificationChannel.setSound(null, null);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400,
                    300, 200, 400});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notification = new NotificationCompat.Builder(this, "123").setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("注目提醒！")
                //设置通知图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                //设置通知内容标题
                .setContentTitle("LOT："+S_ID)
                //设置通知内容
                .setContentText("LOT正在后台运行")
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR|Notification.FLAG_ONGOING_EVENT;
        assert notificationManager != null;
        notificationManager.notify(0,notification);

        new Thread(new Runnable() {
            @Override
            public void run() {
                strings=null;
                strings=DBUtils.select_DB("SELECT * FROM members WHERE S_ID='"
                        +S_ID+"'","MGR");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(strings!=null)
                        {
                            if(strings.length==1)
                            {
                                MGR=strings[0][0];
                                if(MGR == null)
                                {
                                    b2.setVisibility(View.GONE);
                                    b3.setVisibility(View.GONE);
                                }else if(MGR.equals("1")||MGR.equals("2"))
                                {
                                    b2.setVisibility(View.GONE);
                                }
                            }
                            else if(strings.length>1)
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("提示：");
                                builder.setMessage("请联系管理员，账号异常");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Objects.requireNonNull(MainActivity.this).finish();
                                    }
                                });
                                builder.show();
                            }
                        }
                        else
                        {
                            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("提示：");
                            builder.setMessage("请联系管理员，账号异常");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Objects.requireNonNull(MainActivity.this).finish();
                                }
                            });
                            builder.show();
                        }
                    }
                });
            }
        }).start();

        TextView textView=findViewById(R.id.main_bug);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.bug_dia);
                Button button=dialog.findViewById(R.id.commit_bug);
                final EditText editText=dialog.findViewById(R.id.bug_ed_dia);
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if(editText.getText().toString().length()>5)
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    int temp=DBUtils._DB("INSERT INTO bug_report (bug_com,bug)VALUES ('"+(MainActivity.S_ID==null?LogActivity.device_mac:MainActivity.S_ID)+"','"+editText.getText().toString()+"')");
                                    if(temp!=1)
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MainActivity.this,"提交失败，请检查网络",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    else
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MainActivity.this,"提交成功，感谢反馈",Toast.LENGTH_LONG).show();
                                            }
                                        });

                                }
                            }).start();
                    }
                });
            }
        });

        mf1=mFragment.newInstance(R.layout.m1_layout +"","1");
        mf2=mFragment.newInstance(R.layout.m2_layout +"","1");
        mf3=mFragment.newInstance(R.layout.m3_layout +"","1");
        mf4=mFragment.newInstance(R.layout.m4_layout +"","1");
        getSupportFragmentManager().beginTransaction().add(R.id.ll, mf4).commitAllowingStateLoss();
        current_mf=mf4;

        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        b4.setTypeface(ResourcesCompat.getFont(this, R.font.font_fill));
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean onetime=true,onetime2=true;
                while (check_update){
                    String[][] trash_query=null;
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(MainActivity.S_ID!=null)
                        trash_query = DBUtils.select_DB("SELECT MAX(version_id) version_id FROM version UNION ALL " +
                                "SELECT OPER_device FROM `logs` WHERE `KEY`=(SELECT MAX(`KEY`) " +
                                "OPER_device FROM `logs` WHERE S_ID='"+MainActivity.S_ID
                                +"' AND TYPE_operation='登录账户') UNION ALL SELECT PRI FROM members WHERE S_ID='"+MainActivity.S_ID
                                +"' UNION ALL SELECT PRI1 FROM members WHERE S_ID='"+MainActivity.S_ID
                                +"' UNION ALL SELECT PRI2 FROM members WHERE S_ID='"+MainActivity.S_ID+"'","version_id");
                    if(trash_query!=null) {
                        LogActivity.onlineversion_id = trash_query[0][0];
                        if (!LogActivity.onlineversion_id.equals(LogActivity.version_id)&&onetime) {
                            onetime=false;
                            LogActivity.trash=false;
                            handler.post(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                                    dialog.setTitle("更新");
                                    dialog.setMessage("版本已更新，请退出后重新打开APP");
                                    dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    });
                                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialogInterface) {
                                            finish();
                                        }
                                    });
                                    dialog.show();

                                }
                            });
                        }
                        if(!trash_query[1][0].equals(LogActivity.device_mac)&&onetime2) {
                            onetime2=false;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                                    dialog.setTitle("注销");
                                    dialog.setMessage("你的账号在别处登录");
                                    dialog.setNegativeButton("注销", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                            if(exit_out) {
                                                exit_out=false;
                                                finish();
                                                startActivity(new Intent(MainActivity.this,LogActivity.class));
                                            }
                                            MainActivity.S_ID=null;
                                        }
                                    });
                                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialogInterface) {
                                            finish();
                                            if(exit_out) {
                                                exit_out=false;
                                                finish();
                                                startActivity(new Intent(MainActivity.this,LogActivity.class));
                                            }
                                            MainActivity.S_ID=null;
                                        }
                                    });
                                    dialog.show();
                                }
                            });
                        }
                        if(trash_query.length>2) {
                            temp=Integer.parseInt(trash_query[2][0]);
                            handler.post(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.P)
                                @Override
                                public void run() {
                                    if(m2_onscreen) {
                                        ProgressBar progressBar=findViewById(R.id.pb_m2);
                                        TextView textView=findViewById(R.id.raise_hands);
                                        progressBar.setProgress(temp);
                                        if(temp>=getResources().getInteger(R.integer.beg_pro)) {
                                            textView.setVisibility(View.VISIBLE);
                                        }
                                        else{
                                            textView.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }
                            });
                        }
                        if(trash_query.length>3) {
                            temp1=Integer.parseInt(trash_query[3][0]);
                            pri1_able=temp1==1;
                            pri2_able=temp1==1;
                        }
                        if(trash_query.length>4) {
                            temp1=Integer.parseInt(trash_query[4][0]);
                            pri2_able=temp1==1;
                        }
//                        for(int i=0;i<trash_query.length;i++)
//                            Log.e("TG",""+trash_query[i][0]);
                    }
                }
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                boolean onetime=true,onetime2=true;
//                while (check_update){
//                    String[][] trash_query;
//                    trash_query = DBUtils.select_DB("SELECT MAX(version_id) version_id FROM version","version_id");
//                    if(trash_query!=null) {
//                        LogActivity.onlineversion_id = trash_query[0][0];
//                        if (!LogActivity.onlineversion_id.equals(LogActivity.version_id)&&onetime) {
//                            onetime=false;
//                            LogActivity.trash=false;
//                            handler.post(new Runnable() {
//                                @SuppressLint("SetTextI18n")
//                                @Override
//                                public void run() {
//                                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
//                                    dialog.setTitle("更新");
//                                    dialog.setMessage("版本已更新，请退出后重新打开APP");
//                                    dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            finish();
//                                        }
//                                    });
//                                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                        @Override
//                                        public void onCancel(DialogInterface dialogInterface) {
//                                            finish();
//                                        }
//                                    });
//                                    dialog.show();
//
//                                }
//                            });
//                        }
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    trash_query=null;
//                    if(MainActivity.S_ID!=null)
//                        trash_query = DBUtils.select_DB("SELECT OPER_device FROM `logs` WHERE `KEY`=(SELECT MAX(`KEY`) OPER_device FROM `logs` WHERE S_ID='"+MainActivity.S_ID+"' AND TYPE_operation='登录账户')","OPER_device");
//                    if(trash_query!=null) {
//                        if(!trash_query[0][0].equals(LogActivity.device_mac)&&onetime2) {
//                            onetime2=false;
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
//                                    dialog.setTitle("注销");
//                                    dialog.setMessage("你的账号在别处登录");
//                                    dialog.setNegativeButton("注销", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            finish();
//                                            if(exit_out)
//                                            {
//                                                exit_out=false;
//                                                finish();
//                                                startActivity(new Intent(MainActivity.this,LogActivity.class));
//                                            }
//                                            MainActivity.S_ID=null;
//                                        }
//                                    });
//                                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                        @Override
//                                        public void onCancel(DialogInterface dialogInterface) {
//                                            finish();
//                                            if(exit_out)
//                                            {
//                                                exit_out=false;
//                                                finish();
//                                                startActivity(new Intent(MainActivity.this,LogActivity.class));
//                                            }
//                                            MainActivity.S_ID=null;
//                                        }
//                                    });
//                                    dialog.show();
//                                }
//                            });
//                        }
//                    }
//                }
//            }
//        }).start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                m2_onscreen =false;
                b1.setTypeface(ResourcesCompat.getFont(this, R.font.font_fill));
                b2.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b3.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b4.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                if(current_mf.equals(mf1)){
                    final TextView count_m1=findViewById(R.id.count_m1);
                    final ListView listView=findViewById(R.id.member_m1);
                    final List<Map<String,Object>> list=new ArrayList<>();
                    final m1_Adapter adapter=new m1_Adapter(MainActivity.this);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            strings=null;
                            strings=DBUtils.select_DB("SELECT MAJOR,`NAME`,QQ,TEL,mgr_name FROM members LEFT JOIN mgr_table ON members.MGR=mgr_table.mgr_id WHERE MGR>1 ORDER BY MGR DESC","MAJOR","NAME","mgr_name","QQ","TEL");
                            if(strings!=null)
                            {
                                Map<String, Object> map = new HashMap<>();
                                for (int i=0;i<strings.length;i++)
                                {
                                    if(i>0)
                                        map =new HashMap<>();
                                    map.put("num",strings[i][0]);
                                    map.put("name",strings[i][1]);
                                    map.put("job",strings[i][2]);
                                    map.put("qq",strings[i][3]);
                                    map.put("tel",strings[i][4]);
                                    list.add(map);
                                }
                                handler.post(new Runnable() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void run() {
                                        count_m1.setText("当前人数："+list.size());
                                        adapter.setList(list);
                                        listView.setAdapter(adapter);
                                        Toast.makeText(MainActivity.this,"已刷新！",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }).start();
                }
                else{
                    if (!mf1.isAdded()) {
                        // 先判断是否被add过
                        if (mf1_b) {
                            mf1_b = false;
                            getSupportFragmentManager().beginTransaction().add(R.id.ll, mf1).commitAllowingStateLoss();
                        } else {
                            getSupportFragmentManager().beginTransaction().hide(current_mf).add(R.id.ll, mf1).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                        }
                    } else {
                        getSupportFragmentManager().beginTransaction().hide(current_mf).show(mf1).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                    }
                    current_mf=mf1;
                }
                
                break;
            case R.id.button2:
                m2_onscreen =true;
                b1.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b2.setTypeface(ResourcesCompat.getFont(this, R.font.font_fill));
                b3.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b4.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                if(current_mf.equals(mf2)){
                    final TextView none_m2=findViewById(R.id.none_m2);
                    final ListView listView2=findViewById(R.id.m2_list);
                    final List<Map<String,Object>> list_m2=new ArrayList<>();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            strings=null;
                            strings=DBUtils.select_DB("SELECT * FROM members WHERE MGR>2 AND COMN>0 ORDER BY COMN DESC","NAME","ARRIVE","RECN","COMN");
                            if(strings!=null) {
                                Map<String, Object> map = new HashMap<>();
                                for (int i=0;i<strings.length;i++) {
                                    if(i>0)
                                        map =new HashMap<>();
                                    map.put("name",strings[i][0]);
                                    map.put("arrive",strings[i][1]);
                                    map.put("recn",strings[i][2]);
                                    map.put("comn",strings[i][3]);
                                    list_m2.add(map);
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        m2_Adapter adapter_m2=new m2_Adapter(MainActivity.this);
                                        int visi=View.INVISIBLE;
                                        if(list_m2.size()==0)
                                            visi=View.VISIBLE;
                                        none_m2.setVisibility(visi);
                                        adapter_m2.setList(list_m2);
                                        listView2.setAdapter(adapter_m2);
                                        Toast.makeText(MainActivity.this,"已刷新！",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }).start();
                }else{
                    if (!mf2.isAdded()) {
                        // 先判断是否被add过
                        if (mf2_b) {
                            mf2_b = false;
                            getSupportFragmentManager().beginTransaction().add(R.id.ll, mf2).commitAllowingStateLoss();
                        } else {
                            getSupportFragmentManager().beginTransaction().hide(current_mf).add(R.id.ll, mf2).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                        }
                    } else {
                        getSupportFragmentManager().beginTransaction().hide(current_mf).show(mf2).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                    }
                    current_mf=mf2;
                }
                break;
            case R.id.button3:
                m2_onscreen =false;
                b1.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b2.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b3.setTypeface(ResourcesCompat.getFont(this, R.font.font_fill));
                b4.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                if(current_mf.equals(mf3)){
                    final LinearLayout ll_m3=findViewById(R.id.ll_m3);
                    final ListView listView_m3=findViewById(R.id.newmember_m3);
                    final List<Map<String,Object>> list_m3=new ArrayList<>();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            strings=null;
                            strings=DBUtils.select_DB("SELECT * FROM members WHERE MGR=1 AND RECN>0","S_ID","NAME","RECN","MAJOR");
                            if(strings!=null) {
                                Map<String, Object> map = new HashMap<>();
                                for (int i=0;i<strings.length;i++) {
                                    if(i>0)
                                        map =new HashMap<>();
                                    map.put("num",strings[i][0]);
                                    map.put("name",strings[i][1]);
                                    map.put("accept",strings[i][2]);
                                    map.put("major_n",strings[i][3]);
                                    list_m3.add(map);
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        m3_Adapter adapter_m3=new m3_Adapter(MainActivity.this);
                                        int visi=View.INVISIBLE;
                                        if(list_m3.size()>0)
                                            visi=View.VISIBLE;
                                        ll_m3.setVisibility(visi);
                                        adapter_m3.setList(list_m3);
                                        listView_m3.setAdapter(adapter_m3);
                                        Toast.makeText(MainActivity.this,"已刷新！",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }).start();
                }else{
                    if (!mf3.isAdded()) {
                        // 先判断是否被add过
                        if (mf3_b) {
                            mf3_b = false;
                            getSupportFragmentManager().beginTransaction().add(R.id.ll, mf3).commitAllowingStateLoss();
                        } else {
                            getSupportFragmentManager().beginTransaction().hide(current_mf).add(R.id.ll, mf3).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                        }
                    } else {
                        getSupportFragmentManager().beginTransaction().hide(current_mf).show(mf3).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                    }
                    current_mf=mf3;
                }
                break;
            case R.id.button4:
                m2_onscreen =false;
                b1.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b2.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b3.setTypeface(ResourcesCompat.getFont(this, R.font.font_blank));
                b4.setTypeface(ResourcesCompat.getFont(this, R.font.font_fill));
                if(current_mf.equals(mf4)){
                    final TextView tv_1=findViewById(R.id.name_m4);
                    final TextView tv_2=findViewById(R.id.s_id_m4);
                    final TextView tv_3=findViewById(R.id.major_m4);
                    final TextView tv_4=findViewById(R.id.mgr_m4);
                    final TextView tv_5=findViewById(R.id.QQ_m4);
                    final TextView tv_6=findViewById(R.id.TEL_m4);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            strings=null;
                            strings=DBUtils.select_DB("SELECT * FROM members LEFT JOIN mgr_table ON members.MGR=mgr_table.mgr_id WHERE S_ID='"
                                    +MainActivity.S_ID+"'","NAME","S_ID","MAJOR","mgr_name","QQ","TEL");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(strings!=null) {
                                        if (strings.length == 1) {
                                            tv_1.setText(strings[0][0]);
                                            tv_2.setText(strings[0][1]);
                                            tv_3.setText(strings[0][2]);
                                            tv_4.setText(strings[0][3]);
                                            tv_5.setText(strings[0][4]);
                                            tv_6.setText(strings[0][5]);
                                            Toast.makeText(MainActivity.this, "已刷新！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    }).start();
                }else{
                    if (!mf4.isAdded()) {
                        // 先判断是否被add过
                        if (mf4_b) {
                            mf4_b = false;
                            getSupportFragmentManager().beginTransaction().add(R.id.ll, mf4).commitAllowingStateLoss();
                        } else {
                            getSupportFragmentManager().beginTransaction().hide(current_mf).add(R.id.ll, mf4).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                        }
                    } else {
                        getSupportFragmentManager().beginTransaction().hide(current_mf).show(mf4).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                    }
                    current_mf=mf4;
                }
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    //安卓重写返回键事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(exit_d)
                finish();
            else
            {
                Toast.makeText(this,"再按一次返回键退出程序",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        exit_d=true;
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            exit_d=false;
                        }
                    }
                }).start();

            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        check_update=false;
    }
}

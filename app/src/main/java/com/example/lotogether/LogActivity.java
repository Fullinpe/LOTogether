package com.example.lotogether;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Objects;

public class LogActivity extends AppCompatActivity {

    public static String device_mac;
    String[][] strings,strings2;
    Handler handler=new Handler();
    boolean trouble=true;
    //TODO
    static String version_id="4";

    static String onlineversion_id="e";
    int down_percent=0;
    private String mSavePath;
    private String mVersion_name="temp.apk";
    private boolean mIsCancel=false;
    private ProgressBar progressBar;
    private Dialog dialog;
    private File apkFile;

    @SuppressLint("HandlerLeak")
    Handler handler_log=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setProgress(down_percent);
                    break;
                case 2:
                    dialog.dismiss();
                    installAPK();
            }
        }
    };
    static boolean trash=true;
    private String[][] trash_query;

    @SuppressLint("StaticFieldLeak")
    public static LogActivity finish_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        device_mac=getLocalMac();
        Log.e("MAC",device_mac);
        final EditText ed1=findViewById(R.id.s_id);
        final EditText ed2=findViewById(R.id.password);
        Button sign_up=findViewById(R.id.sign_up);
        Button sign_in=findViewById(R.id.sign_in);
        ActivityCompat.requestPermissions(LogActivity.this, new String[]{
                Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 0x11);

        dialog =new Dialog(this);
        progressBar=new ProgressBar(this, null,android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        dialog.setContentView(progressBar);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mIsCancel=true;
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                strings2=null;
                strings2 = DBUtils.select_DB("SELECT MAX(version_id) version_id FROM version","version_id");
                InputStream[] is;
                if(strings2!=null) {
                    onlineversion_id=strings2[0][0];
                    if(!onlineversion_id.equals(version_id)) {
                        int version_len=
                                Integer.parseInt(DBUtils.select_DB("SELECT OCTET_LENGTH(version_blob) datesize from version " +
                                        "WHERE version_id=(SELECT MAX(version_id) FROM version)","datesize")[0][0]);
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
                        mSavePath = sdPath + "LOTogether";

                        File dir = new File(mSavePath);
                        if (!dir.exists()){
                            if(dir.mkdir())
                                Log.e("LogActivity","成功创建文件夹");
                            else
                                Log.e("LogActivity","创建文件夹失败");
                        }
                        else
                            Log.e("LogActivity","文件夹已存在");

                        is = DBUtils.selectBLOB("SELECT * from version WHERE version_id="+strings2[0][0],"version_blob");

                        try {
                            apkFile = new File(mSavePath, mVersion_name);
                            FileOutputStream fos = new FileOutputStream(apkFile);
                            int count = 0;
                            byte[] buffer = new byte[1024];
                            while (!mIsCancel){
                                int numread = is[0].read(buffer);
                                count += numread;
                                // 计算进度条的当前位置
                                down_percent = (int) (((float)count/version_len) * 100);
                                // 更新进度条
                                handler_log.sendEmptyMessage(1);

                                // 下载完成
                                if (numread < 0){
                                    handler_log.sendEmptyMessage(2);
                                    Log.e("LogActivity","apk_len:"+version_len);
                                    break;
                                }
                                fos.write(buffer, 0, numread);
                            }
                            fos.close();
                            is[0].close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        trash_query=null;
                        trash_query = DBUtils.select_DB("SELECT OPER_device,S_ID FROM `logs` WHERE `KEY`="
                                +"(SELECT MAX(`KEY`) FROM `logs` WHERE S_ID=(SELECT S_ID FROM `logs` WHERE `KEY`="
                                +"(SELECT MAX(`KEY`) FROM `logs` WHERE OPER_device='"+device_mac+"' "
                                +"AND TYPE_operation='登录账户')) AND TYPE_operation='登录账户')","OPER_device","S_ID");
                        if(trash_query!=null) {
                            if(trash_query[0][0].equals(device_mac)) {
                                MainActivity.S_ID=trash_query[0][1];
                                Intent intent=new Intent();
                                intent.putExtra("S_ID",trash_query[0][1]);
                                intent.setClass(Objects.requireNonNull(LogActivity.this),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
            }
        }).start();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Objects.requireNonNull(LogActivity.this),SignUpActivity.class);
                startActivity(intent);
                finish_=LogActivity.this;
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(trouble)
                {
                    trouble=false;
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
                                            mFragment.logs_thread(ed1.getText().toString(),"登录账户","你好啊！");
                                        }
                                        else
                                        {
                                            AlertDialog.Builder builder=new AlertDialog.Builder(LogActivity.this);
                                            builder.setTitle("提示：");
                                            builder.setMessage("请确认填写学号或密码是否有误");
                                            builder.setPositiveButton("确定", null);
                                            builder.show();

                                            trouble=true;
                                        }
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(LogActivity.this);
                                        builder.setTitle("提示：");
                                        builder.setMessage("请确认网络链路正确");
                                        builder.setPositiveButton("确定", null);
                                        builder.show();

                                        trouble=true;
                                    }
                                }
                            });
                        }
                    }).start();
                }else{
                    Toast.makeText(LogActivity.this,"疯狂加载中...",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    protected void installAPK() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String filePath= apkFile.getAbsolutePath();
        Log.e("安装文件路径：",filePath);
        File file = new File(filePath);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {//大于7.0使用此方法
            Uri apkUri = FileProvider.getUriForFile(LogActivity.this, "com.example.lotogether.fileprovider", file);///-----ide文件提供者名
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else {//小于7.0就简单了
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        }
        startActivity(intent);
    }
    /**
     * 获取设备MAC 地址 由于 6.0 以后 WifiManager 得到的 MacAddress得到都是 相同的没有意义的内容
     * 所以采用以下方法获取Mac地址
     */
    private static String getLocalMac() {
//        WifiManager wifi = (WifiManager) context
//                .getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifi.getConnectionInfo();
//        return info.getMacAddress();


        String macAddress;
        StringBuilder buf = new StringBuilder();
        NetworkInterface networkInterface;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "";
            }
            byte[] addr = networkInterface.getHardwareAddress();


            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
        return macAddress;


    }
}

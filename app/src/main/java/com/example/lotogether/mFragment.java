package com.example.lotogether;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[][] strings;
    static boolean[] cheched;
    int num_checked=0;

    private Handler handler=new Handler();

    private OnFragmentInteractionListener mListener;
    private String[][] temp;

    public mFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mFragment.
     */
    // TODO: Rename and change types and number of parameters
    static mFragment newInstance(String param1, String param2) {
        mFragment fragment = new mFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Handler handler_dia=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //在需要弹出软键盘的地方发送msg
                if (msg.what==1001){
                    //使用以下代码来弹出软键盘
                    InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert inputMethodManager != null;
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
        switch (Integer.parseInt(mParam1))
        {
            case R.layout.m1_layout:

                final ListView listView=view.findViewById(R.id.member_m1);
                final List<Map<String,Object>> list=new ArrayList<>();
                final m1_Adapter adapter=new m1_Adapter(getActivity());
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Map<String,Object> map=list.get(i);
                        if (isGotoable(getActivity(), "com.tencent.mobileqq"))
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+map.get("qq")+"&version=1")));
                        else if(isGotoable(getActivity() ,"com.tencent.tim"))
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+map.get("qq")+"&version=1")));
                        else
                            Toast.makeText(getActivity(),"本机未安装QQ应用",Toast.LENGTH_SHORT).show();
                    }
                });
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Map<String,Object> map=list.get(i);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + map.get("tel")));
                        startActivity(intent);
                        return true;
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        strings=null;
                        strings=DBUtils.select_DB("SELECT * FROM members LEFT JOIN mgr_table ON members.MGR=mgr_table.mgr_id","S_ID","NAME","mgr_name","QQ","TEL");
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
                                @Override
                                public void run() {
                                    adapter.setList(list);
                                    listView.setAdapter(adapter);
                                }
                            });
                        }

                    }
                }).start();

                break;
            case R.layout.m2_layout:
                Button complaint=view.findViewById(R.id.complaint_m2);
                Button recommend=view.findViewById(R.id.recommend_m2);
                ImageView imageView=view.findViewById(R.id.LOGO_m2);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO
                    }
                });
                complaint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog=new Dialog(Objects.requireNonNull(getActivity()));
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setContentView(R.layout.choose_dia);
                        final ListView listView=dialog.findViewById(R.id.choose_list);
                        TextView textView=dialog.findViewById(R.id.choose_title);
                        Button cancel =dialog.findViewById(R.id.cancel_choose);
                        Button ok=dialog.findViewById(R.id.ok_choose);
                        textView.setText("请选择你想投诉的同学：");
                        final List<Map<String,Object>> list=new ArrayList<>();
                        cheched=null;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                temp = null;
                                temp=DBUtils.select_DB("","S_ID","NAME");

                                cheched=new boolean[temp.length];
                                if(temp!=null)
                                {
                                    Map<String, Object> map = new HashMap<>();
                                    for (int i=0;i<temp.length;i++)
                                    {
                                        if(i>0)
                                            map =new HashMap<>();
                                        map.put("num",temp[i][0]);
                                        map.put("name",temp[i][1]);
                                        list.add(map);
                                    }
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            choose_Adapter adapter=new choose_Adapter(getActivity());
                                            adapter.setList(list);
                                            listView.setAdapter(adapter);
                                            dialog.show();
                                        }
                                    });
                                }
                            }
                        }).start();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                final StringBuilder checked_= new StringBuilder();
                                num_checked=0;
                                boolean flag=false;
                                if(listView.getCount()==cheched.length)
                                    for(int i=0;i<listView.getCount();i++)
                                    {
                                        ConstraintLayout constraintLayout= (ConstraintLayout) listView.getAdapter().getView(i,null,null);
                                        TextView textView1=constraintLayout.findViewById(R.id.s_id_choose);
                                        if (cheched[i])
                                        {
                                            if(flag)
                                                checked_.append(",");
                                            flag=true;
                                            checked_.append(textView1.getText().toString());
                                            num_checked++;
                                        }
                                    }
                                final Dialog dialog=new Dialog(Objects.requireNonNull(getActivity()));
                                dialog.setContentView(R.layout.mdialog);
                                final EditText key_dia=dialog.findViewById(R.id.key_dia);
                                Button button=dialog.findViewById(R.id.dialog_button);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run()
                                            {
                                                strings=null;
                                                strings=DBUtils.select_DB("SELECT * FROM admin WHERE S_ID='"
                                                        +MainActivity.S_ID+"' AND Password='"
                                                        +key_dia.getText().toString()+"'","S_ID");
                                                if(strings!=null)
                                                {
                                                    if(strings.length==1)
                                                    {
                                                        final int rows=DBUtils._DB("UPDATE members SET COMN=COMN+"+MainActivity.MGR+" WHERE S_ID IN ("+checked_.toString()+")");
                                                        handler.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                if(num_checked==rows)
                                                                    Toast.makeText(getActivity(),"操作成功！",Toast.LENGTH_LONG).show();
                                                                else
                                                                    Toast.makeText(getActivity(),"存在自检问题，联系管理员 ",Toast.LENGTH_LONG).show();

                                                            }
                                                        });

                                                    }
                                                    else if(strings.length>1)
                                                    {
                                                        handler.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                                builder.setTitle("提示：");
                                                                builder.setMessage("请确认网络链路正确");
                                                                builder.setPositiveButton("确定", null);
                                                                builder.show();
                                                            }
                                                        });
                                                    }
                                                    else
                                                    {
                                                        handler.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                                builder.setTitle("提示：");
                                                                builder.setMessage("请确认填写密码是否有误");
                                                                builder.setPositiveButton("确定", null);
                                                                builder.show();
                                                            }
                                                        });
                                                    }
                                                }
                                                else
                                                {
                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                            builder.setTitle("提示：");
                                                            builder.setMessage("请确认网络链路正确");
                                                            builder.setPositiveButton("确定", null);
                                                            builder.show();
                                                        }
                                                    });
                                                }

                                            }
                                        }).start();

                                    }
                                });
                                dialog.show();
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(),"取消操作",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                recommend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog=new Dialog(Objects.requireNonNull(getActivity()));
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setContentView(R.layout.choose_dia);
                        final ListView listView=dialog.findViewById(R.id.choose_list);
                        TextView textView=dialog.findViewById(R.id.choose_title);
                        Button cancel =dialog.findViewById(R.id.cancel_choose);
                        Button ok=dialog.findViewById(R.id.ok_choose);
                        textView.setText("请选择你想举荐的同学：");
                        final List<Map<String,Object>> list=new ArrayList<>();
                        cheched=null;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                temp = null;
                                temp=DBUtils.select_DB("","S_ID","NAME");

                                cheched=new boolean[temp.length];
                                if(temp!=null)
                                {
                                    Map<String, Object> map = new HashMap<>();
                                    for (int i=0;i<temp.length;i++)
                                    {
                                        if(i>0)
                                            map =new HashMap<>();
                                        map.put("num",temp[i][0]);
                                        map.put("name",temp[i][1]);
                                        list.add(map);
                                    }
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            choose_Adapter adapter=new choose_Adapter(getActivity());
                                            adapter.setList(list);
                                            listView.setAdapter(adapter);
                                            dialog.show();
                                        }
                                    });
                                }
                            }
                        }).start();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                final StringBuilder checked_= new StringBuilder();
                                num_checked=0;
                                boolean flag=false;
                                if(listView.getCount()==cheched.length)
                                    for(int i=0;i<listView.getCount();i++)
                                    {
                                        ConstraintLayout constraintLayout= (ConstraintLayout) listView.getAdapter().getView(i,null,null);
                                        TextView textView1=constraintLayout.findViewById(R.id.s_id_choose);
                                        if (cheched[i])
                                        {
                                            if(flag)
                                                checked_.append(",");
                                            flag=true;
                                            checked_.append(textView1.getText().toString());
                                            num_checked++;
                                        }
                                    }
                                Log.e("",checked_.toString());
                                final Dialog dialog=new Dialog(Objects.requireNonNull(getActivity()));
                                dialog.setContentView(R.layout.mdialog);
                                final EditText key_dia=dialog.findViewById(R.id.key_dia);
                                Button button=dialog.findViewById(R.id.dialog_button);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run()
                                            {
                                                strings=null;
                                                strings=DBUtils.select_DB("SELECT * FROM admin WHERE S_ID='"
                                                        +MainActivity.S_ID+"' AND Password='"
                                                        +key_dia.getText().toString()+"'","S_ID");
                                                if(strings!=null)
                                                {
                                                    if(strings.length==1)
                                                    {
                                                        final int rows=DBUtils._DB("UPDATE members SET RECN=RECN+"+MainActivity.MGR+" WHERE S_ID IN ("+checked_.toString()+")");
                                                        handler.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                if(num_checked==rows)
                                                                    Toast.makeText(getActivity(),"操作成功！",Toast.LENGTH_LONG).show();
                                                                else
                                                                    Toast.makeText(getActivity(),"存在自检问题，联系管理员 ",Toast.LENGTH_LONG).show();

                                                            }
                                                        });

                                                    }
                                                    else if(strings.length>1)
                                                    {
                                                        handler.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                                builder.setTitle("提示：");
                                                                builder.setMessage("请确认网络链路正确");
                                                                builder.setPositiveButton("确定", null);
                                                                builder.show();
                                                            }
                                                        });
                                                    }
                                                    else
                                                    {
                                                        handler.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                                builder.setTitle("提示：");
                                                                builder.setMessage("请确认填写密码是否有误");
                                                                builder.setPositiveButton("确定", null);
                                                                builder.show();
                                                            }
                                                        });
                                                    }
                                                }
                                                else
                                                {
                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                            builder.setTitle("提示：");
                                                            builder.setMessage("请确认网络链路正确");
                                                            builder.setPositiveButton("确定", null);
                                                            builder.show();
                                                        }
                                                    });
                                                }

                                            }
                                        }).start();

                                    }
                                });
                                dialog.show();
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(),"取消操作",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case R.layout.m3_layout:
                final ListView listView_m3=view.findViewById(R.id.newmember_m3);
                final List<Map<String,Object>> list_m3=new ArrayList<>();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        strings=null;
                        strings=DBUtils.select_DB("","S_ID","NAME","RECN");
                        if(strings!=null)
                        {
                            Map<String, Object> map = new HashMap<>();
                            for (int i=0;i<strings.length;i++)
                            {
                                if(i>0)
                                    map =new HashMap<>();
                                map.put("num",strings[i][0]);
                                map.put("name",strings[i][1]);
                                map.put("accept",strings[i][2]);
                                list_m3.add(map);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    m3_Adapter adapter_m3=new m3_Adapter(getActivity());
                                    adapter_m3.setList(list_m3);
                                    listView_m3.setAdapter(adapter_m3);
                                }
                            });
                        }

                    }
                }).start();
                break;
            case R.layout.m4_layout:
                Button his_done=view.findViewById(R.id.his_done);
                final FloatingActionButton menu=view.findViewById(R.id.menu_m4);
                final TextView tv_1=view.findViewById(R.id.name_m4);
                final TextView tv_2=view.findViewById(R.id.s_id_m4);
                final TextView tv_3=view.findViewById(R.id.major_m4);
                final TextView tv_4=view.findViewById(R.id.mgr_m4);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        strings=null;
                        strings=DBUtils.select_DB("SELECT * FROM members LEFT JOIN mgr_table ON members.MGR=mgr_table.mgr_id WHERE S_ID='"
                                +MainActivity.S_ID+"'","NAME","S_ID","MAJOR","mgr_name");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(strings!=null)
                                {
                                    if(strings.length==1)
                                    {
                                        tv_1.setText(strings[0][0]);
                                        tv_2.setText(strings[0][1]);
                                        tv_3.setText(strings[0][2]);
                                        tv_4.setText(strings[0][3]);
                                    }
                                    else if(strings.length>1)
                                    {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                        builder.setTitle("提示：");
                                        builder.setMessage("请联系管理员，账号异常");
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Objects.requireNonNull(getActivity()).finish();
                                            }
                                        });
                                        builder.show();
                                    }
                                }
                                else
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                    builder.setTitle("提示：");
                                    builder.setMessage("请联系管理员，账号异常");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Objects.requireNonNull(getActivity()).finish();
                                        }
                                    });
                                    builder.show();
                                }
                            }
                        });
                    }
                }).start();
                menu.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("RtlHardcoded")
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        Dialog dialog =new Dialog(Objects.requireNonNull(getActivity()));
                        dialog.setContentView(R.layout.menu_dia);
                        Button button1=dialog.findViewById(R.id.menu1);
                        Button button2=dialog.findViewById(R.id.menu2);
                        Button button3=dialog.findViewById(R.id.menu3);
                        Button button4=dialog.findViewById(R.id.menu4);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        Window win=dialog.getWindow();
                        assert win != null;
                        win.setWindowAnimations(R.style.dialogWindowAnim);
                        win.setGravity(Gravity.LEFT | Gravity.TOP);
                        WindowManager.LayoutParams lp = win.getAttributes();
                        lp.x = win.getWindowManager().getDefaultDisplay().getWidth()-640;
                        lp.y = win.getWindowManager().getDefaultDisplay().getHeight()-1070;
                        lp.width = 500;
                        lp.height = 800;
                        lp.alpha = 0.9f;
                        win.setAttributes(lp);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();
                    }
                });

                his_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Dialog dialog=new Dialog(Objects.requireNonNull(getActivity()));
                        dialog.setContentView(R.layout.mdialog);
                        final EditText key_dia=dialog.findViewById(R.id.key_dia);
                        Button button=dialog.findViewById(R.id.dialog_button);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        strings=null;
                                        strings=DBUtils.select_DB("SELECT * FROM admin WHERE S_ID='"
                                                +MainActivity.S_ID+"' AND Password='"
                                                +key_dia.getText().toString()+"'","S_ID");

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(strings!=null)
                                                {
                                                    if(strings.length==1)
                                                    {
                                                        Toast.makeText(getActivity(),"成功！！",Toast.LENGTH_LONG).show();
                                                    }
                                                    else if(strings.length>1)
                                                    {
                                                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                        builder.setTitle("提示：");
                                                        builder.setMessage("请确认网络链路正确");
                                                        builder.setPositiveButton("确定", null);
                                                        builder.show();
                                                    }
                                                    else
                                                    {
                                                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                        builder.setTitle("提示：");
                                                        builder.setMessage("请确认填写密码是否有误");
                                                        builder.setPositiveButton("确定", null);
                                                        builder.show();
                                                    }
                                                }
                                                else
                                                {
                                                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                    builder.setTitle("提示：");
                                                    builder.setMessage("请确认网络链路正确");
                                                    builder.setPositiveButton("确定", null);
                                                    builder.show();
                                                }
                                            }
                                        });
                                    }
                                }).start();

                            }
                        });
                        dialog.show();

                    }
                });

                break;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(Integer.parseInt(mParam1),container,false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private boolean isGotoable(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
        {
            Toast.makeText(context,"休想和null联系！",Toast.LENGTH_LONG).show();
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}

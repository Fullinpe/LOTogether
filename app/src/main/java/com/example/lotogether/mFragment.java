package com.example.lotogether;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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

    private Handler handler=new Handler();

    private OnFragmentInteractionListener mListener;

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
        switch (Integer.parseInt(mParam1))
        {
            case R.layout.m1_layout:

                final ListView listView=view.findViewById(R.id.member_m1);
                final List<Map<String,Object>> list=new ArrayList<>();

                final Handler handler1=new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        strings=DBUtils.select_DB("","G_ID","NAME","MGR","QQ","TEL");
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
                            handler1.post(new Runnable() {
                                @Override
                                public void run() {
                                    m1_Adapter adapter=new m1_Adapter(getActivity());
                                    adapter.setList(list);
                                    listView.setAdapter(adapter);
                                }
                            });
                        }

                    }
                }).start();

                break;
            case R.layout.m2_layout:
                final TextView tv=view.findViewById(R.id.mysql_test);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        strings=DBUtils.select_DB("","G_ID");

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for (int i=0;i<strings.length;i++)
                                    tv.setText(strings[i][0]);
                            }
                        });
                    }
                }).start();
                break;
            case R.layout.m3_layout:
                ListView listView_m3=view.findViewById(R.id.newmember_m3);
                List<Map<String,Object>> list_m3=new ArrayList<>();
                Map<String,Object> map_m3=new HashMap<>();
                map_m3.put("num","1");
                map_m3.put("name","zhelin");
                map_m3.put("accept","负责人");
                list_m3.add(map_m3);
                map_m3=new HashMap<>();
                map_m3.put("num","2");
                map_m3.put("name","Fullinpe");
                map_m3.put("accept","接纳1");
                list_m3.add(map_m3);
                map_m3=new HashMap<>();
                map_m3.put("num","3");
                map_m3.put("name","yunqiang");
                map_m3.put("accept","接纳2");
                list_m3.add(map_m3);
                map_m3=new HashMap<>();
                map_m3.put("num","4");
                map_m3.put("name","yecong");
                map_m3.put("accept","接纳2");
                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","5");
//                map_m3.put("name","guiquan");
//                map_m3.put("accept","接纳3");
//                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","6");
//                map_m3.put("name","Fullinpe");
//                map_m3.put("accept","接纳1");
//                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","7");
//                map_m3.put("name","yunqiang");
//                map_m3.put("accept","接纳2");
//                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","8");
//                map_m3.put("name","yecong");
//                map_m3.put("accept","接纳2");
//                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","9");
//                map_m3.put("name","guiquan");
//                map_m3.put("accept","接纳3");
//                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","10");
//                map_m3.put("name","Fullinpe");
//                map_m3.put("accept","接纳1");
//                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","11");
//                map_m3.put("name","yunqiang");
//                map_m3.put("accept","接纳2");
//                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","12");
//                map_m3.put("name","yecong");
//                map_m3.put("accept","接纳2");
//                list_m3.add(map_m3);
//                map_m3=new HashMap<>();
//                map_m3.put("num","13");
//                map_m3.put("name","guiquan");
//                map_m3.put("accept","接纳3");
//                list_m3.add(map_m3);
                m3_Adapter adapter_m3=new m3_Adapter(getActivity());
                adapter_m3.setList(list_m3);
                listView_m3.setAdapter(adapter_m3);
                break;
            case R.layout.m4_layout:
                final EditText ed1=view.findViewById(R.id.s_id);
                final EditText ed2=view.findViewById(R.id.password);

                final ConstraintLayout cl1=view.findViewById(R.id.sign_m4);
                final ConstraintLayout cl2=view.findViewById(R.id.msg_m4);
                Button sign_up=view.findViewById(R.id.sign_up);
                Button sign_in=view.findViewById(R.id.sign_in);
                final Button settings=view.findViewById(R.id.settings);
                settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setTitle("Hello AlertDialog");
                        builder.setMessage("休息吗？");
                        builder.setPositiveButton("睡", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        });
                        builder.setNegativeButton("不睡了",null);
                        builder.show();
                    }
                });
                sign_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent();
                        intent.setClass(Objects.requireNonNull(getActivity()),SignUpActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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
                                                cl1.setVisibility(View.GONE);
                                                cl2.setVisibility(View.VISIBLE);
                                            }
                                            else
                                            {
                                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                builder.setTitle("提示：");
                                                builder.setMessage("请确认填写学号或密码是否有误");
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

                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
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
}

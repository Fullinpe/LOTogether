package com.example.lotogether;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String str;

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
    public static mFragment newInstance(String param1, String param2) {
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
                ListView listView=view.findViewById(R.id.member_m1);
                List<Map<String,Object>> list=new ArrayList<>();
                Map<String,Object> map=new HashMap<>();
                map.put("num","1");
                map.put("name","zhelin");
                map.put("job","负责人");
                list.add(map);
                map=new HashMap<>();
                map.put("num","2");
                map.put("name","Fullinpe");
                map.put("job","职务1");
                list.add(map);
                map=new HashMap<>();
                map.put("num","3");
                map.put("name","yunqiang");
                map.put("job","职务2");
                list.add(map);
                map=new HashMap<>();
                map.put("num","4");
                map.put("name","yecong");
                map.put("job","职务2");
                list.add(map);
                map=new HashMap<>();
                map.put("num","5");
                map.put("name","guiquan");
                map.put("job","职务3");
                list.add(map);
                map=new HashMap<>();
                map.put("num","6");
                map.put("name","Fullinpe");
                map.put("job","职务1");
                list.add(map);
                map=new HashMap<>();
                map.put("num","7");
                map.put("name","yunqiang");
                map.put("job","职务2");
                list.add(map);
                map=new HashMap<>();
                map.put("num","8");
                map.put("name","yecong");
                map.put("job","职务2");
                list.add(map);
                map=new HashMap<>();
                map.put("num","9");
                map.put("name","guiquan");
                map.put("job","职务3");
                list.add(map);
                map=new HashMap<>();
                map.put("num","10");
                map.put("name","Fullinpe");
                map.put("job","职务1");
                list.add(map);
                map=new HashMap<>();
                map.put("num","11");
                map.put("name","yunqiang");
                map.put("job","职务2");
                list.add(map);
                map=new HashMap<>();
                map.put("num","12");
                map.put("name","yecong");
                map.put("job","职务2");
                list.add(map);
                map=new HashMap<>();
                map.put("num","13");
                map.put("name","guiquan");
                map.put("job","职务3");
                list.add(map);
//                SimpleAdapter adapter=new SimpleAdapter(getActivity(),
//                        list,
//                        R.layout.m1_item,
//                        new String[]{"icon_num","num","essay","name","job"},
//                        new int[]{R.id.icon_num,R.id.textView6,R.id.textView7,R.id.textView8,R.id.textView9});
                m1_Adapter adapter=new m1_Adapter(getActivity());
                adapter.setList(list);
                listView.setAdapter(adapter);
                break;
            case R.layout.m2_layout:
                final TextView tv=view.findViewById(R.id.mysql_test);
                final Handler handler=new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        str=DBUtils.connect_mysql();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(str);
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

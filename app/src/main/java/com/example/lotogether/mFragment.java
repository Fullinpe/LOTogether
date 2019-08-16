package com.example.lotogether;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
            case R.layout.xue_layout:
                ListView listView=view.findViewById(R.id.listview1);
                List<Map<String,Object>> list=new ArrayList<>();
                Map<String,Object> map=new HashMap<>();
                map.put("icon_author",R.mipmap.ic_launcher_round);
                map.put("author","作者1");
                map.put("essay","\t这是内容前部分，1\n这是内容前部分，这是内容前部分，这是内容前部分，这是内容前部分。");
                map.put("date","2019-8-3");
                map.put("thumbs","获赞次数：12");
                list.add(map);
                map=new HashMap<>();
                map.put("icon_author",R.mipmap.ic_launcher_round);
                map.put("author","作者2");
                map.put("essay","\t这是内容前部分，2\n这是内容前部分，\n这是内容前部分，\n这是内容前部分，这是内容前部分。");
                map.put("date","2019-8-3");
                map.put("thumbs","获赞次数：12152");
                list.add(map);
                map=new HashMap<>();
                map.put("icon_author",R.mipmap.ic_launcher_round);
                map.put("author","作者3");
                map.put("essay","\t这是内容前部分，3\n这是内容前部分，\n这是内容前部分，\n这是内容前部分，\n这是内容前部分。");
                map.put("date","2019-8-3");
                map.put("thumbs","获赞次数：12123123");
                list.add(map);
                map=new HashMap<>();
                map.put("icon_author",R.mipmap.ic_launcher_round);
                map.put("author","作者4");
                map.put("essay","\t这是内容前部分，4\n这是内容前部分，这是内容前部分，这是内容前部分，这是内容前部分。");
                map.put("date","2019-8-3");
                map.put("thumbs","获赞次数：120000");
                list.add(map);
                map=new HashMap<>();
                map.put("icon_author",R.mipmap.ic_launcher_round);
                map.put("author","作者5");
                map.put("essay","\t这是内容前部分，5\n这是内容前部分，这是内容前部分，这是内容前部分，这是内容前部分。");
                map.put("date","2019-8-3");
                map.put("thumbs","获赞次数：12000");
                list.add(map);
//                SimpleAdapter adapter=new SimpleAdapter(getActivity(),
//                        list,
//                        R.layout.xue_item,
//                        new String[]{"icon_author","author","essay","date","thumbs"},
//                        new int[]{R.id.icon_author,R.id.textView6,R.id.textView7,R.id.textView8,R.id.textView9});
                mAdapter adapter=new mAdapter(getActivity());
                adapter.setList(list);
                listView.setAdapter(adapter);
                break;
            case R.layout.qin_layout:
                final TextView tv=view.findViewById(R.id.textView3);
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
            case R.layout.gao_layout:

                break;
            case R.layout.self_layout:

                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

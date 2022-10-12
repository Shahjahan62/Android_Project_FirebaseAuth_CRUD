package com.example.besmart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.besmart.R;

public class Home_Fragment extends Fragment {
    public Home_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
//        ArrayList<view_Friend> ls = new ArrayList<view_Friend>();
//        view_Friend[] frnd=new view_Friend[2];
//        frnd[0]=new view_Friend();
//        frnd[0].name="Tayyab";
//        frnd[0].user_name="tyb80";
//        frnd[1]=new view_Friend();
//        frnd[1].name="ALi";
//        frnd[1].user_name="ali980";
//        ls.addAll(Arrays.asList(frnd));
//        ListView lv=getActivity().findViewById(R.id.friend_list);
//        friend_list_adapter ad=new friend_list_adapter(ls,getActivity(),R.layout.list_user_component);
//        lv.setAdapter(ad);
    }
}

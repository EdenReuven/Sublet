package com.example.sublet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class PostFragment extends Fragment {
    EditText username_tv , phone_tv , email_tv, date_tv ,location_tv, roommate_tv, price_tv, people_tv,
            bathroom_tv, bedroom_tv ,description_tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);
        username_tv=view.findViewById(R.id.post_frag_userName_tv);
        phone_tv=view.findViewById(R.id.post_frag_phone_tv);
        email_tv=view.findViewById(R.id.post_frag_email_tv);
        date_tv=view.findViewById(R.id.post_frag_dates_tv);
        location_tv=view.findViewById(R.id.post_frag_location_tv);
        roommate_tv=view.findViewById(R.id.post_frag_roomate_tv);
        price_tv=view.findViewById(R.id.post_price_tv);
        people_tv=view.findViewById(R.id.post_frag_people_tv);
        bathroom_tv=view.findViewById(R.id.post_frag_bathroom_tv);
        bedroom_tv=view.findViewById(R.id.post_frag_bedroom_tv);
        description_tv=view.findViewById(R.id.post_frag_description_tv);
        return view;
    }
}
package com.example.sublet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sublet.model.Post;

import java.util.Calendar;
import java.util.Date;


public class AddPostFragment extends Fragment {
   EditText  dateFrom_et, dateTo_et, location_et, roommate_et, price_et, people_et, bathroom_et, bedroom_et;
   Button continue_btn;
   Date createTimeDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);

        dateFrom_et=view.findViewById(R.id.addPost_freg_dateFrom_et);
        dateTo_et=view.findViewById(R.id.addPost_freg_dateTo_et);
        location_et=view.findViewById(R.id.addPost_freg_location_et);
        roommate_et=view.findViewById(R.id.addPost_freg_rommaite_et);
        price_et=view.findViewById(R.id.addPost_freg_price_et);
        people_et=view.findViewById(R.id.addPost_freg_people_et);
        bathroom_et=view.findViewById(R.id.addPost_freg_bathroom_et);
        bedroom_et=view.findViewById(R.id.addPost_freg_bedroom_et);
        continue_btn=view.findViewById(R.id.addPost2_freg_post_btn);
        createTimeDate = Calendar.getInstance().getTime();

        Post newPost = new Post();
        newPost.setFromDate(dateFrom_et.getText().toString());
        newPost.setToDate(dateTo_et.getText().toString());
        newPost.setLocation(location_et.getText().toString());
        newPost.setNumRoommate(Integer.parseInt(roommate_et.getText().toString()));
        newPost.setPrice(Integer.parseInt(price_et.getText().toString()));
        newPost.setOverallPeople(Integer.parseInt(people_et.getText().toString()));
        newPost.setNumOfBedroom(Integer.parseInt(bedroom_et.getText().toString()));
        newPost.setNumOfBathroom(Integer.parseInt(bathroom_et.getText().toString()));
        newPost.setCreateDate(createTimeDate);

        //TODO: need to pass the newPost object to the next fragment to continue the add new post

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(AddPostFragmentDirections.actionAddPostFragmentToAddPost2());
            }
        });

        return view;
    }
}
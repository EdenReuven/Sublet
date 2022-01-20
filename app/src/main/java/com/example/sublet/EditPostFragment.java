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

import com.example.sublet.model.Model;
import com.example.sublet.model.Post;

import java.util.List;

public class EditPostFragment extends Fragment {
    EditText dateFrom_et, dateTo_et, location_et, roommate_et, price_et, people_et, bathroom_et, bedroom_et;
    Button continue_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);
        Post post = Model.instance.getPost(Model.instance.getCurrentPostId());

        dateFrom_et = view.findViewById(R.id.editPost_freg_dateFrom_et);
        dateTo_et = view.findViewById(R.id.editPost_freg_dateTo_et);
        location_et = view.findViewById(R.id.editPost_freg_location_et);
        roommate_et = view.findViewById(R.id.editPost_freg_rommaite_et);
        price_et = view.findViewById(R.id.editPost_freg_price_et);
        people_et = view.findViewById(R.id.editPost_freg_people_et);
        bathroom_et = view.findViewById(R.id.editPost_freg_bathroom_et);
        bedroom_et = view.findViewById(R.id.editPost_freg_bedroom_et);
        continue_btn = view.findViewById(R.id.editPost2_freg_post_btn);

        dateFrom_et.setText(post.getFromDate());
        dateTo_et.setText(post.getToDate());
        location_et.setText(post.getLocation());
        roommate_et.setText(Integer.toString(post.getNumRoommate()));
        price_et.setText(Integer.toString((int)post.getPrice()));
        people_et.setText(Integer.toString(post.getOverallPeople()));
        bathroom_et.setText(Integer.toString(post.getNumOfBathroom()));
        bedroom_et.setText(Integer.toString(post.getNumOfBedroom()));

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setFromDate(dateFrom_et.getText().toString());
                post.setToDate(dateTo_et.getText().toString());
                post.setLocation(location_et.getText().toString());
                post.setNumRoommate(Integer.parseInt(roommate_et.getText().toString()));
                post.setPrice(Integer.parseInt(price_et.getText().toString()));
                post.setOverallPeople(Integer.parseInt(people_et.getText().toString()));
                post.setNumOfBathroom(Integer.parseInt(bathroom_et.getText().toString()));
                post.setNumOfBedroom(Integer.parseInt(bedroom_et.getText().toString()));
                Navigation.findNavController(v).navigate(EditPostFragmentDirections.actionEditPostFragmentToEditPost2Fragment(post));
            }
        });

        return view;
    }
}
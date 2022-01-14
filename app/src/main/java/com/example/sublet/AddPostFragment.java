package com.example.sublet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddPostFragment extends Fragment {
   EditText  dateFrom_et, dateTo_et, location_et, roommate_et, price_et, people_et, bathroom_et, bedroom_et;
   Button continue_btn;

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

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(AddPostFragmentDirections.actionAddPostFragmentToAddPost2());
            }
        });

        return view;
    }
}
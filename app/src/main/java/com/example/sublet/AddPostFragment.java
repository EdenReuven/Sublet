package com.example.sublet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sublet.model.Model;
import com.example.sublet.model.Post;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AddPostFragment extends Fragment {
   EditText  dateFrom_et, dateTo_et, location_et, roommate_et, price_et, people_et, bathroom_et, bedroom_et;
   Button continue_btn;
   Date createTimeDate;
   boolean validOk;
   AddPostViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AddPostViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);

        dateFrom_et=view.findViewById(R.id.editPost_freg_dateFrom_et);
        dateTo_et=view.findViewById(R.id.editPost_freg_dateTo_et);
        location_et=view.findViewById(R.id.editPost_freg_location_et);
        roommate_et=view.findViewById(R.id.editPost_freg_rommaite_et);
        price_et=view.findViewById(R.id.editPost_freg_price_et);
        people_et=view.findViewById(R.id.editPost_freg_people_et);
        bathroom_et=view.findViewById(R.id.editPost_freg_bathroom_et);
        bedroom_et=view.findViewById(R.id.editPost_freg_bedroom_et);
        continue_btn=view.findViewById(R.id.editPost2_freg_post_btn);
        createTimeDate = Calendar.getInstance().getTime();

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validOk=true;
                CheckValid();
                if(!validOk)
                    return;

                continue_btn.setEnabled(false);
                viewModel.newPost = new Post();
                viewModel.getNewPost().setPostId(Model.instance.getGeneratePostId());
                viewModel.getNewPost().setFromDate(dateFrom_et.getText().toString());
                viewModel.getNewPost().setToDate(dateTo_et.getText().toString());
                viewModel.getNewPost().setLocation(location_et.getText().toString());
                viewModel.getNewPost().setNumRoommate(Integer.parseInt(roommate_et.getText().toString()));
                viewModel.getNewPost().setPrice(Integer.parseInt(price_et.getText().toString()));
                viewModel.getNewPost().setOverallPeople(Integer.parseInt(people_et.getText().toString()));
                viewModel.getNewPost().setNumOfBedroom(Integer.parseInt(bedroom_et.getText().toString()));
                viewModel.getNewPost().setNumOfBathroom(Integer.parseInt(bathroom_et.getText().toString()));
                Navigation.findNavController(v).navigate(AddPostFragmentDirections.actionAddPostFragmentToAddPost2(viewModel.getNewPost()));
            }
        });

        return view;

    }

    public void CheckValid(){
        EditText[] validArray = {dateFrom_et,dateTo_et,location_et,roommate_et
                ,price_et,people_et,bedroom_et,bathroom_et};

        for(int i=0;i<validArray.length;i++){
            if(validArray[i].getText().toString().length() == 0){
                validOk = false;
                Toast.makeText(MyApplication.getContext(), "All Fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
package com.example.sublet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sublet.model.Model;
import com.example.sublet.model.Post;
import com.example.sublet.model.User;

import java.util.LinkedList;
import java.util.List;


public class PostFragment extends Fragment {
    TextView username_tv , phone_tv , email_tv, date_tv ,location_tv, roommate_tv, price_tv, people_tv,
            bathroom_tv, bedroom_tv ,description_tv;
    String postId;
//    String postId;
    List<Post> postListData;
    Post post;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);
        postId = PostFragmentArgs.fromBundle(getArguments()).getPostId();

        User user = Model.instance.getCurrentUser();

        username_tv = view.findViewById(R.id.post_frag_userName_tv);
        phone_tv = view.findViewById(R.id.post_frag_phone_tv);
        email_tv = view.findViewById(R.id.post_frag_email_tv);
        date_tv = view.findViewById(R.id.post_frag_dates_tv);
        location_tv = view.findViewById(R.id.post_frag_location_tv);
        roommate_tv = view.findViewById(R.id.post_frag_roomate_tv);
        price_tv = view.findViewById(R.id.post_frag_price_tv);
        people_tv = view.findViewById(R.id.post_frag_people_tv);
        bathroom_tv = view.findViewById(R.id.post_frag_bathroom_tv);
        bedroom_tv = view.findViewById(R.id.post_frag_bedroom_tv);
        description_tv = view.findViewById(R.id.post_frag_description_tv);

        username_tv.setText(user.getUserName());
        phone_tv.setText(user.getPhone());
        email_tv.setText(user.getEmail());

        Model.instance.getPostById(postId,post1 -> {

            if(post1.getPostId().split("-")[1].equals(Model.instance.getCurrentUser().getUserName()))
                setHasOptionsMenu(true);

            date_tv.setText(post1.getFromDate()+" - "+post1.getToDate());
            location_tv.setText(post1.getLocation());
            roommate_tv.setText(Integer.toString(post1.getNumRoommate()));
            price_tv.setText(Integer.toString((int) post1.getPrice()));
            people_tv.setText(Integer.toString(post1.getOverallPeople()));
            bathroom_tv.setText(Integer.toString(post1.getNumOfBathroom()));
            bedroom_tv.setText(Integer.toString(post1.getNumOfBedroom()));
            description_tv.setText(post1.getPostContent());
        });

//        Model.instance.getPost(pos,post1 -> {
//            post = post1;
//
//            if(post.getPostId().split("-")[1].equals(Model.instance.getCurrentUser().getUserName()))
//                setHasOptionsMenu(true);
//
//            date_tv.setText(post.getFromDate()+" - "+post.getToDate());
//            location_tv.setText(post.getLocation());
//            roommate_tv.setText(Integer.toString(post.getNumRoommate()));
//            price_tv.setText(Integer.toString((int) post.getPrice()));
//            people_tv.setText(Integer.toString(post.getOverallPeople()));
//            bathroom_tv.setText(Integer.toString(post.getNumOfBathroom()));
//            bedroom_tv.setText(Integer.toString(post.getNumOfBedroom()));
//            description_tv.setText(post.getPostContent());
//        });

        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.post_info_menu, menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit_menu:
                Model.instance.setCurrentPostId(post.getPostId());
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.editPostFragment);
                return true;
            case R.id.delete_menu:
                Model.instance.setCurrentPostId(post.getPostId());
                DialogPopUp dialogPopUp = new DialogPopUp();
                dialogPopUp.show(getParentFragmentManager(),"Dialog_Popup");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
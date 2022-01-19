package com.example.sublet;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
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


public class PostFragment extends Fragment {
    TextView username_tv , phone_tv , email_tv, date_tv ,location_tv, roommate_tv, price_tv, people_tv,
            bathroom_tv, bedroom_tv ,description_tv;
    int pos;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);
        pos = PostFragmentArgs.fromBundle(getArguments()).getPosition();
        Post post = Model.instance.getPost(pos);
        User user = Model.instance.getUser(pos);

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
        date_tv.setText(post.getFromDate()+" - "+post.getToDate());
        location_tv.setText(post.getLocation());
        roommate_tv.setText(Integer.toString(post.getNumRoommate()));
        price_tv.setText(Integer.toString((int) post.getPrice()));
        people_tv.setText(Integer.toString(post.getOverallPeople()));
        bathroom_tv.setText(Integer.toString(post.getNumOfBathroom()));
        bedroom_tv.setText(Integer.toString(post.getNumOfBedroom()));
        description_tv.setText(post.getPostContent());
        setHasOptionsMenu(true);
        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.post_info_menu,menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit_menu:
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.editPostFragment);
                return true;
            case R.id.delete_menu:
                //TODO: delete post
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
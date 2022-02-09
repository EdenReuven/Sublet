package com.example.sublet;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sublet.model.Model;
import com.example.sublet.model.Post;

import java.util.List;


public class ProfileFragment extends Fragment {

    ImageView profileImg;
    TextView name_tv, phone_tv, email_tv;
    RecyclerView posts_rv;
    ProfileAdapter adapter;
    ProfileViewModel viewModel;
    String userName;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        /*userName = PostFragmentArgs.fromBundle(getArguments()).getPostId();*/
        userName= ProfileFragmentArgs.fromBundle(getArguments()).getUserName();

        profileImg=view.findViewById(R.id.profilefragment_img);
        name_tv=view.findViewById(R.id.profilefragment_name_tv);
        phone_tv=view.findViewById(R.id.profilefragment_phone_tv);
        email_tv=view.findViewById(R.id.profilefragment_email_tv);

        posts_rv=view.findViewById(R.id.profilefragment_posts_rv);
        posts_rv.setHasFixedSize(true);
        posts_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProfileAdapter();
        posts_rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int pos=position;
                Log.d("TAG", Integer.toString(pos));
            }
        });

        Model.instance.getUser(userName ,user -> {
            name_tv.setText(user.getUserName());
            phone_tv.setText(user.getPhone());
            email_tv.setText(user.getEmail());
        });

       /* Model.instance.getAllPosts(postList -> {
            viewModel.setData(postList);
            adapter.notifyDataSetChanged();
        });*/

        return view;
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView profile_img,post_img;
        TextView create_post_tv,username_tv,status_tv,location_tv,numOfPeople_tv,price_tv,dates_tv;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            profile_img = itemView.findViewById(R.id.post_profileImg_imageView);
            post_img = itemView.findViewById(R.id.post_img_imageView);
            create_post_tv = itemView.findViewById(R.id.post_createDay_tv);
            username_tv = itemView.findViewById(R.id.post_userName_tv);
            status_tv = itemView.findViewById(R.id.post_status_tv);
            location_tv = itemView.findViewById(R.id.post_location_tv);
            numOfPeople_tv = itemView.findViewById(R.id.post_numofpeople_tv);
            price_tv = itemView.findViewById(R.id.post_price_tv);
            dates_tv = itemView.findViewById(R.id.post_dates_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v,pos);
                }
            });

        }
    }

    interface OnItemClickListener {
        void onItemClick(View v,int position);
    }

    public class ProfileAdapter extends RecyclerView.Adapter<MyViewHolder> {
        OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener=listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater().inflate(R.layout.post_card, parent, false);
            MyViewHolder holder= new MyViewHolder(view ,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //TODO: add image post + image profile
            Post p = viewModel.getData().get(position);
            String userNamePost = p.getPostId().split("-")[1];

            Model.instance.getUser(userNamePost,user -> {
                holder.username_tv.setText(user.getUserName());
            });

            holder.status_tv.setText(Integer.toString(p.getNumRoommate()));
            holder.location_tv.setText(p.getLocation());
            holder.numOfPeople_tv.setText("fit for " + Integer.toString(p.getOverallPeople())+ " people");
            holder.price_tv.setText(Integer.toString((int)p.getPrice()) + " NIC");
            holder.dates_tv.setText(p.getFromDate() +" - " +p.getToDate());
            //holder.create_post_tv.setText(Integer.toString(resultDays));

        }


        @Override
        public int getItemCount() {
            if(viewModel.getData() == null)
                return 0;
            return viewModel.getData().size();
        }
    }


}
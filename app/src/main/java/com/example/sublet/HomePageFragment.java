package com.example.sublet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sublet.model.Model;
import com.example.sublet.model.Post;
import com.example.sublet.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomePageFragment extends Fragment {
    RecyclerView postList;
    List<Post> dataPost;
    List<User> dataUser;
    MyAdapter adapter;
    Date currentDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        dataPost = Model.instance.getAllPosts();
        dataUser = Model.instance.getAllUsers();
        currentDate = Calendar.getInstance().getTime();
        postList = view.findViewById(R.id.homePage_postList_rv);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        postList.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //TODO: open the current post + send the current position
                int pos = position;
                Navigation.findNavController(v).navigate(HomePageFragmentDirections.actionHomePageFragmentToPostFragment(pos));
            }
        });

        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
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

//            cb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos=getAdapterPosition();
//                    Student s = data.get(pos);
//                    s.setCheck(cb.isChecked());
//                }
//            });
        }
    }

    interface OnItemClickListener {
        void onItemClick(View v,int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener=listener;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.post_card , parent,false);
            MyViewHolder holder= new MyViewHolder(view ,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Post p = dataPost.get(position);
            User u = dataUser.get(position);
            //TODO: add image post + image profile + currentDate
            int resultDays = differenceDays(p.getCreateDate(),currentDate);
            //img
            //post img
            //holder.create_post_tv.setText(); //currentDate
            holder.create_post_tv.setText(Integer.toString(resultDays));
            holder.username_tv.setText(u.getUserName());
            holder.status_tv.setText(Integer.toString(p.getNumRoommate()));
            holder.location_tv.setText(p.getLocation());
            holder.numOfPeople_tv.setText(Integer.toString(p.getOverallPeople()));
            holder.price_tv.setText(Integer.toString((int)p.getPrice()));
            holder.dates_tv.setText(p.getFromDate() +" - " +p.getToDate());
        }

        @Override
        public int getItemCount() {
            return dataPost.size();
        }

    }

    public int differenceDays(Date fromDate,Date toDate){
        return (int) ((toDate.getTime() - fromDate.getTime())/(1000*60*60*24));
    }
}
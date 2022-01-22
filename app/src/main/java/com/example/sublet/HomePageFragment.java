package com.example.sublet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        dataUser = Model.instance.getAllUsers();
        //currentDate = Calendar.getInstance().getTime();
        postList = view.findViewById(R.id.homePage_postList_rv);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        postList.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int pos = position;
                Navigation.findNavController(v).navigate(HomePageFragmentDirections.actionHomePageFragmentToPostFragment(pos));
            }
        });
        setHasOptionsMenu(true);
        refresh();
        return view;
    }

    private void refresh() {
        Model.instance.getAllPosts(postList -> {
            dataPost = postList;
            adapter.notifyDataSetChanged();
        });
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
            //TODO: add image post + image profile
            Post p = dataPost.get(position);
            User u = Model.instance.getCurrentUser();

//            holder.create_post_tv.setText(Integer.toString(resultDays));
            holder.username_tv.setText(u.getUserName());
            holder.status_tv.setText(Integer.toString(p.getNumRoommate()));
            holder.location_tv.setText(p.getLocation());
            holder.numOfPeople_tv.setText(Integer.toString(p.getOverallPeople()));
            holder.price_tv.setText(Integer.toString((int)p.getPrice()));
            holder.dates_tv.setText(p.getFromDate() +" - " +p.getToDate());
        }

        @Override
        public int getItemCount() {
            if(dataPost == null)
                return 0;
            return dataPost.size();
        }

    }

//    public int differenceDays(Date fromDate,Date toDate){
//        return (int) ((toDate.getTime() - fromDate.getTime())/(1000*60*60*24));
//    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_page_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_menu:
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.addPostFragment);
                return true;
            case R.id.map_menu:
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.mapFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
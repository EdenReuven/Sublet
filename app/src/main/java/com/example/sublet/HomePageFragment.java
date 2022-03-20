package com.example.sublet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sublet.model.Model;
import com.example.sublet.model.Post;
import com.squareup.picasso.Picasso;
import java.util.Date;
import java.util.List;


public class HomePageFragment extends Fragment {
    RecyclerView postList;
    HomePageViewModel viewModel;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    ImageView profile_img;
    TextView userName_tv;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        Model.instance.setMapStatus("");

        swipeRefresh =view.findViewById(R.id.homePage_postList_SR);
        swipeRefresh.setOnRefreshListener(() -> Model.instance.refreshPostList());

        profile_img=view.findViewById(R.id.homePage_profile_img);
        userName_tv=view.findViewById(R.id.homePage_username_tv);
        userName_tv.setText(Model.instance.getCurrentUser().getNickName()); //show login user name

        postList = view.findViewById(R.id.homePage_postList_rv);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        postList.setAdapter(adapter);

        profile_img.setImageResource(R.drawable.woman);
        if(Model.instance.getCurrentUser().getProfileUrl() != null) {
            Picasso.get()
                    .load(Model.instance.getCurrentUser().getProfileUrl())
                    .into(profile_img);
        }

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int pos = position;
                String postId = viewModel.getDataPost().getValue().get(pos).getPostId();
                Navigation.findNavController(v).navigate(HomePageFragmentDirections.actionHomePageFragmentToPostFragment(postId));
            }
        });
        setHasOptionsMenu(true);
        viewModel.getDataPost().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                refresh();
            }
        });
        swipeRefresh.setRefreshing((Model.instance.getPostsListLoadingState().getValue()==Model.PostsListLoadingState.loading));
        Model.instance.getPostsListLoadingState().observe(getViewLifecycleOwner(), new Observer<Model.PostsListLoadingState>() {
            @Override
            public void onChanged(Model.PostsListLoadingState postsListLoadingState) {
                if(postsListLoadingState== Model.PostsListLoadingState.loading){
                    swipeRefresh.setRefreshing(true);
                }
                else{
                    swipeRefresh.setRefreshing(false);
                }

            }
        });
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileUserName =Model.instance.getCurrentUser().getUserName();
                Navigation.findNavController(profile_img).navigate(HomePageFragmentDirections.
                        actionHomePageFragmentToProfileFragment(profileUserName));
            }
        });
        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_img_Post,post_img;
        TextView username_tv,status_tv,location_tv,numOfPeople_tv,price_tv,dates_tv;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            profile_img_Post = itemView.findViewById(R.id.post_profileImgPost_imageView);
            post_img = itemView.findViewById(R.id.post_img_imageView);
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
        public void bind(Post p){

            status_tv.setText("you will have " +Integer.toString(p.getNumRoommate()) + " roommate");
            location_tv.setText(p.getLocation());
            numOfPeople_tv.setText("fit for " + Integer.toString(p.getOverallPeople())+ " people");
            price_tv.setText(Integer.toString((int)p.getPrice()) + " NIC/DAY");
            dates_tv.setText(p.getFromDate() +" - " +p.getToDate());
            post_img.setImageResource(R.drawable.room);
            if(p.getPostImgUrl() !=null) {
                Picasso.get()
                        .load(p.getPostImgUrl())
                        .into(post_img);
            }
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
            Post p = viewModel.getDataPost().getValue().get(position);
            String userNamePost = p.getPostId().split("-")[1];

            Model.instance.getUser(userNamePost,user -> {
                holder.username_tv.setText(user.getNickName());
                holder.profile_img_Post.setImageResource(R.drawable.woman);
                if(user.getProfileUrl() != null) {
                    Picasso.get()
                            .load(user.getProfileUrl())
                            .into(holder.profile_img_Post);
                }
            });
            holder.bind(p);
        }

        @Override
        public int getItemCount() {
            if(viewModel.getDataPost().getValue() == null)
                return 0;
            return viewModel.getDataPost().getValue().size();
        }

    }

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
            case R.id.logOut_menu:
                Model.instance.signOut();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
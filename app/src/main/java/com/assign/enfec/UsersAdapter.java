package com.assign.enfec;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Posts> posts;

    public UsersAdapter(List<Posts> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_posts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PostsViewHolder postsViewHolder = (PostsViewHolder) holder;
        postsViewHolder.bind(posts.get(position), position);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    private static class PostsViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtEmail;
        private TextView txtTitle;
        private TextView txtBody;

        public PostsViewHolder(View view) {
            super(view);

            txtName = view.findViewById(R.id.txtName);
            txtEmail = view.findViewById(R.id.txtEmail);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtBody = view.findViewById(R.id.txtBody);

        }

        public void bind(Posts posts, int position) {

            String userName = posts.getUserName();
            String email = posts.getEmail();
            String title = posts.getTitle();
            String body = posts.getBody();

            Log.e("userName", "userName " + userName);
            if (userName != null)
                txtName.setText(userName);

            if (email != null) {
                txtEmail.setText(email);
            }

            if (title != null)
                txtTitle.setText(title);

            if (body != null)
                txtBody.setText(body);
        }
    }
}

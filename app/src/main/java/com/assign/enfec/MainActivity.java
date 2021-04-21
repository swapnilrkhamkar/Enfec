package com.assign.enfec;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Posts> posts;
    private UsersAdapter usersAdapter;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(posts);
        recyclerView.setAdapter(usersAdapter);

        apiInterface = new APIClient().getClient().create(APIInterface.class);
        Single<List<Users>> call = apiInterface.getUsers();

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserverForUsers());

    }

    private DisposableSingleObserver<List<Users>> getObserverForUsers() {
        return new DisposableSingleObserver<List<Users>>() {
            @Override
            public void onSuccess(@NonNull List<Users> users) {
                Log.e("USERS", "USERS " + users);

                Single<List<Posts>> call = apiInterface.getPosts();

                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getObserverForPosts(users));

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("onError", "onError " + e);
            }
        };
    }

    private DisposableSingleObserver<List<Posts>> getObserverForPosts(List<Users> users) {
        return new DisposableSingleObserver<List<Posts>>() {
            @Override
            public void onSuccess(@NonNull List<Posts> posts) {
                Log.e("POSTS", "posts " + posts);
                List<Posts> postsList = new ArrayList<>();
                for (Users users1 : users) {
                    for (Posts posts2 : posts) {
                        if (users1.getId().equals(posts2.getId())) {
                            Posts posts1 = new Posts();
//                            posts1.setId(posts2.getId());
                            posts1.setTitle(posts2.getTitle());
                            posts1.setBody(posts2.getBody());
                            posts1.setUserName(users1.getUsername());
                            posts1.setEmail(users1.getEmail());

                            postsList.add(posts1);
                        }
                    }
                }

                usersAdapter.setPosts(postsList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("onError", "posts " + e);

            }
        };
    }
}
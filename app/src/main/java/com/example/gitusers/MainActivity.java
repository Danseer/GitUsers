package com.example.gitusers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView usersRecyclerView;
    private List<User> mItems = new ArrayList<>();
    ProgressBar progressBar;
   Intent intent;

    //private String method, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       usersRecyclerView = (RecyclerView) findViewById(R.id.users_gallery_recycler);
        usersRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new FetchItemTask().execute();

        setUpAdapter();
    }






    private class userHolder extends RecyclerView.ViewHolder {
        private ImageView imageItemView;
        private TextView userLogin;
        private ConstraintLayout cl;

        public userHolder(View itemView) {
            super(itemView);
            imageItemView = (ImageView) itemView.findViewById(R.id.ivUser_avatar);
            userLogin =(TextView) itemView.findViewById(R.id.tvLogin);
            cl=(ConstraintLayout) itemView.findViewById(R.id.CL);

        }
    }

    private class userAdapter extends RecyclerView.Adapter<userHolder> {

        private List<User> mGalleryItems;

        public userAdapter(List<User> items) {
            mGalleryItems = items;
        }

        @Override
        public userHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View v = inflater.inflate(R.layout.user, parent, false);
            return new userHolder(v);
        }

        @Override
        public void onBindViewHolder(userHolder holder, final int position) {
            User GalleryItem = mGalleryItems.get(position);
            Picasso.with(MainActivity.this).load(GalleryItem.getAvatarUrl()).into(holder.imageItemView);
            String login=mGalleryItems.get(position).getLogin();
            holder.userLogin.setText(login);

            holder.cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String login=mGalleryItems.get(position).getLogin();
                    String avatatUrl=mGalleryItems.get(position).getAvatarUrl();

                    //Toast.makeText(GalleryActivity.this,url, Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    intent.putExtra("login",login);
                    intent.putExtra("avatar_url",avatatUrl);


                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    private class FetchItemTask extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

        }
        @Override
        protected List<User> doInBackground(Void... voids) {
            return new Fetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            mItems = users;
            setUpAdapter();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setUpAdapter() {
        usersRecyclerView.setAdapter(new userAdapter(mItems));
    }
}

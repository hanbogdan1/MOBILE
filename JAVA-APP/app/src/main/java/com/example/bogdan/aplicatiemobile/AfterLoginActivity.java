package com.example.bogdan.aplicatiemobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import Domain.Response;
import Domain.Topic;

public class AfterLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private String Username;

    private static int page_number = 1;
    private static final int topics_by_page = 25;
    private Response response;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        Username = getIntent().getStringExtra("USER_NAME");
        response = new Response();
        customAdapter = new CustomAdapter();

        try {
            getRequest("https://www.reddit.com/r/popular/.json?");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        final ListView list = (ListView) findViewById(R.id.ListViewMostPopular);
        list.setAdapter(customAdapter);

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (list.getLastVisiblePosition() - list.getHeaderViewsCount() -
                        list.getFooterViewsCount()) >= (response.get_size() - 1)) {

                        page_number ++;
                        response.setCount(response.getCount() + topics_by_page);

                    try {
                        getRequest("https://www.reddit.com/r/popular/.json?count=" + response.getCount().toString() + "&after=" + response.getLast_id() );
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void getRequest(String urlString) throws IOException, JSONException {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarDashBoard);
        progressBar.setVisibility(View.VISIBLE);

        HttpURLConnection urlConnection = null;
        URL url = null;
        StringBuilder sb=null;
        url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(100000 /* milliseconds */ );
        urlConnection.setConnectTimeout(150000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
         sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        JSONObject jsonRestp = new JSONObject(jsonString);
        jsonRestp = jsonRestp.getJSONObject("data");
        response.setLast_id(jsonRestp.getString("after"));
        JSONArray TopicsJson = jsonRestp.getJSONArray("children");

        for(int i=0;i<TopicsJson.length();i++){
            Topic top = new Topic();
            JSONObject a = TopicsJson.getJSONObject(i);
            a = a.getJSONObject("data");
            if (a.getString("likes").equals("null"))
                top.setLikes(0);

            top.setLikes(Integer.parseInt(a.getString("likes").toString()));

            top.setId_topic(a.getString("name"));
            top.setPicture_link(a.getString("url"));
            top.setLink(a.getString("permalink"));
            top.setTitle(a.getString("title"));

            if (a.getString("num_comments").equals("null"))
                top.setLikes(0);

            top.setLikes(Integer.parseInt(a.getString("num_comments").toString()));

            response.add_topic(top);
        }

        customAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);

    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return response.get_size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_list_layout, null);
            Topic mostPopular = response.getTopics().get(position);
            TextView text = (TextView) convertView.findViewById(R.id.textView_description);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
            text.setText(mostPopular.getTitle());
            Picasso.with(AfterLoginActivity.this).load(mostPopular.getPicture_link()).resize(250, 300).into(imageView);
            return convertView;
        }
    }
}

package com.example.bogdan.aplicatiemobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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

    private CustomAdapter customAdapter= new CustomAdapter();
    ProgressBar progressBar ;
    ListView list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBarDashBoard);


        Username = getIntent().getStringExtra("USER_NAME");
        response = new Response();

        list = (ListView) findViewById(R.id.ListViewMostPopular);
        list.setAdapter(customAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void send_email(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "APLICATIE MOBILE");
        i.putExtra(Intent.EXTRA_TEXT   , "Test email ! ");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AfterLoginActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AfterLoginActivity.this, "Before call in run! ", Toast.LENGTH_LONG).show();

                getRequest();
                progressBar.setVisibility(View.INVISIBLE);
                customAdapter.notifyDataSetChanged();
                send_email();
            }
        }, 2000);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void getRequest() {

        Thread thrd = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpGet httppost = new HttpGet("https://www.reddit.com/r/popular/.json?");
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse responseHttp = httpclient.execute(httppost);

                    // StatusLine stat = response.getStatusLine();
                    int status = responseHttp.getStatusLine().getStatusCode();
                    Log.v("STATUS HTTP:", "" + status);
                    if (status == 200) {
                        HttpEntity entity = responseHttp.getEntity();
                        String data = EntityUtils.toString(entity);
                        JSONObject jsono = new JSONObject(data);
                        jsono = jsono.getJSONObject("data");
                        response.setLast_id(jsono.getString("after"));
                        JSONArray TopicsJson = jsono.getJSONArray("children");

                        for (int i = 0; i < TopicsJson.length(); i++) {
                            Log.v("da", "New topic");
                            Topic top = new Topic();
                            JSONObject a = TopicsJson.getJSONObject(i);
                            a = a.getJSONObject("data");
//                            if (a.getString("likes").equals("null") || a.getString("likes").equals(null))
//                                top.setLikes(0);
//                            else
//                                top.setLikes(Integer.parseInt(a.getString("likes").toString()));

                            top.setId_topic(a.getString("name"));
                            top.setPicture_link(a.getString("url"));
//                            top.setLink(a.getString("permalink"));
                            top.setTitle(a.getString("title"));
//
//                            if (a.getString("num_comments").equals("null") || a.getString("num_comments").equals(null))
//                                top.setComments(0);
//                            else
//                                top.setComments(Integer.parseInt(a.getString("num_comments").toString()));

                            response.add_topic(top);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }

        });

        thrd.start();

        try {
            thrd.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return response.get_size();
        }

        @Override
        public Object getItem(int position) {
            return response.getTopics().get(position);
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

package tarkalabs.com.redditreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home extends AppCompatActivity {
    RecyclerView entryList;
    OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private RedditAdapter redditAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        entryList =(RecyclerView) findViewById(R.id.my_recycler_view);
        redditAdapter = new RedditAdapter(Home.this);
        mLayoutManager = new LinearLayoutManager(this);
        entryList.setLayoutManager(mLayoutManager);
        entryList.setAdapter(redditAdapter);
        new RedditFetchTask().execute();
    }

    public class RedditFetchTask extends  AsyncTask<String, String, List<RedditEntry>> {


        @Override
        protected List<RedditEntry> doInBackground(String... params) {
            try {
                Request request = new Request.Builder()
                        .url("https://www.reddit.com/hot.json")
                        .build();
                Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                List<RedditEntry> entries = new ArrayList<>();
                JSONObject fectchreddit = new JSONObject(response.body().string());
                JSONArray jsonEntries = fectchreddit.getJSONObject("data").getJSONArray("children");
                for (int i = 0; i < jsonEntries.length(); i++) {
                    entries.add(gson.fromJson(jsonEntries.getJSONObject(i).getJSONObject("data").toString(), RedditEntry.class));
                }
                return entries;


            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }
        @Override
        protected void onPostExecute(List<RedditEntry> result) {
            redditAdapter.setEntries(result);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }
    }
}

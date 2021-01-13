package pl.chi.finalhw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideosActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        //Assign variable
        listView = findViewById(R.id.listVideos);

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.screenlife.com/api-mobile/search?channel=29";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                finish();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String videos_array = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(videos_array);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i<5; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String title = object.getString("title");
                            String description = object.getString("description");
                            String countLikes = object.getString("count_likes");
                            arrayList.add(title + " " + description + " " + countLikes );
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Initialize Array Adapter
                                arrayAdapter = new ArrayAdapter<String>(VideosActivity.this,
                                        android.R.layout.simple_list_item_1, arrayList);

                                // Set Array Adapter to ListView
                                listView.setAdapter(arrayAdapter);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}
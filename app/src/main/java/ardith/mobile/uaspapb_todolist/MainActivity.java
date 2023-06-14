package ardith.mobile.uaspapb_todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ModelTodoActivity> modelTodoActivities = new ArrayList<>();
    String  url = "https://mgm.ub.ac.id/todo.php";
    private RecyclerView rvTodo;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTodo = (RecyclerView) findViewById(R.id.rvTodo);
        rvTodo.setLayoutManager(new LinearLayoutManager( this));
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        checkInternetConnection();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Call the method to refresh your data or perform any desired actions
                refreshData();
            }
        });
    }

    /**
     * Digunakan untuk get data Api volley ke Main Activity
     * @return
     */
    private void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    // Iterate through each item in the JSON array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        ModelTodoActivity modelTodo = new ModelTodoActivity(item.getString("time"), item.getString("what"));
                        modelTodoActivities.add(modelTodo);
                    }
                    rvTodo.setAdapter(new TodoAdapter(MainActivity.this.modelTodoActivities));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
    }

    private void checkInternetConnection() {
        boolean isConnected = NetworkUtils.isInternetConnected(this);

        if (isConnected) {
            getData();
        } else {
            // No internet connection, handle accordingly
            showAlert(MainActivity.this, "Ooops !", "Tidak ada koneksi Internet");
        }
    }

    private void refreshData() {
        // Perform actions to refresh your data
        modelTodoActivities.clear();
        checkInternetConnection();
        // Call the method to stop the refresh animation when the refresh is complete
        stopRefreshing();
    }

    private void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
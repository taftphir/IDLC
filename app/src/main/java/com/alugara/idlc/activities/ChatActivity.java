package com.alugara.idlc.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.ChatAdapter;
import com.alugara.idlc.models.Admin;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Message;
import com.alugara.idlc.models.Utils;
import com.alugara.idlc.preferences.Preferences;
import com.alugara.idlc.services.ChatService;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.alugara.idlc.BuildConfig.BASE_URL;

public class ChatActivity extends AppCompatActivity {

    private boolean updateThread = true;
    private Thread chatThread;

    private ChatAdapter adapter;
    private RecyclerView rvChats;
    private View chatLoadForm;
    private MainViewModel mainViewModel;
    private Utils utils = new Utils();
    private ImageView sendChatBtn;
    private EditText messageEd;
    private Admin admin;
    private String USER_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        admin = getIntent().getParcelableExtra("selected_admin");
        if (admin != null) {
            updateThread = true;
            chatThread = new Thread(new Runnable() {
                public void run() {
                    while(updateThread){
                        try {
                            Thread.sleep(5000);
                            setListNewMessage(ChatActivity.this, admin.getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

            chatThread.start();
        }

        rvChats = findViewById(R.id.rv_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rvChats.setLayoutManager(linearLayoutManager);
        chatLoadForm = findViewById(R.id.chat_load_form);
        sendChatBtn = findViewById(R.id.iv_send_chat);
        messageEd = findViewById(R.id.ed_message);
        USER_ID = Preferences.getKeyId(this);

        if (admin != null){
            Toolbar toolbar = findViewById(R.id.toolbar_chat_list);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(admin.getName());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setListMessage(this, USER_ID, admin.getId(), 10);
        mainViewModel.getListMessages().observe(this, getMessages);

        sendChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                url = BASE_URL + "chat/send_message";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                String status = "";
                                try {
                                    JSONObject usersObj = new JSONObject(response);
                                    status = usersObj.getString("status");

                                    if (status.equals("OK")) {
                                        messageEd.setText("");
                                        adapter.clearData();
                                        adapter.notifyDataSetChanged();
                                        mainViewModel.setListMessage(ChatActivity.this, USER_ID, admin.getId(), 20);
                                        mainViewModel.getListMessages().observe(ChatActivity.this, getMessages);
                                    } else {
                                        Toast.makeText(ChatActivity.this, "Terjadi kesalahan saat mengirim pesan, coba lagi nanti", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ChatActivity.this, "Gagal mengirim pesan, periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("dari", Preferences.getKeyId(ChatActivity.this));
                        params.put("kepada", admin.getId());
                        params.put("pesan", messageEd.getText().toString());

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this);
                requestQueue.add(postRequest);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private Observer<ArrayList<Message>> getMessages = new Observer<ArrayList<Message>>() {
        @Override
        public void onChanged(ArrayList<Message> messages) {
            adapter = new ChatAdapter(messages, ChatActivity.this, USER_ID, admin.getId());
            rvChats.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            updateReadStatus();
            utils.setGone(chatLoadForm);

        }
    };

    public void setListNewMessage(final Context context, String adminID){
        String userID = Preferences.getKeyId(context);
        String url = BASE_URL+"chat/new_message?kepada="+userID+"&dari="+adminID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("chats");
                            if (jsonArray.length() > 0){
                                updateReadStatus();
                                adapter.clearData();
                                adapter.notifyDataSetChanged();
                                mainViewModel.setListMessage(ChatActivity.this, USER_ID, admin.getId(), 20);
                                mainViewModel.getListMessages().observe(ChatActivity.this, getMessages);
                            }
                            /*for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Message message = new Message(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("dari"),
                                            jsonObject.getString("kepada"),
                                            jsonObject.getString("pesan"),
                                            jsonObject.getString("sent_at"),
                                            jsonObject.getString("is_read"));

                                    messagesList.add(message);
                                    adapter = new ChatAdapter(messagesList, ChatActivity.this, USER_ID, admin.getId());
                                    rvChats.setAdapter(adapter);
                                    adapter.notifyItemInserted(messagesList.size() - 1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateThread = false;
        chatThread.interrupt();
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }

    private void updateReadStatus(){
        String url;
        url = BASE_URL + "chat";
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject usersObj = new JSONObject(response);
                            String status = usersObj.getString("status");

                            if (status.equals("OK")) {
                                //updateReadStatus();
                                Log.i("UPDATE_READ_MESSAGE", "COMPLETED");
                            } else {
                                Log.i("UPDATE_READ_MESSAGE", "ERROR");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChatActivity.this, "Gagal mengupdate, periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dari", admin.getId());
                params.put("kepada", Preferences.getKeyId(ChatActivity.this));
                params.put("is_read", "1");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this);
        requestQueue.add(postRequest);
    }

}

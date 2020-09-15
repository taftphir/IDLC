package com.alugara.idlc.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.alugara.idlc.BuildConfig;
import com.alugara.idlc.R;
import com.alugara.idlc.activities.ChatActivity;
import com.alugara.idlc.models.Admin;
import com.alugara.idlc.models.Message;
import com.alugara.idlc.preferences.Preferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.alugara.idlc.BuildConfig.BASE_URL;


public class ChatService extends Service {

    public static String CHANNEL_ID = "channel_81";
    public static final int NOTIFICATION_ID = 91;
    private NotificationCompat.Builder builder;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("chat", "Service created");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                new GetData().execute(getApplicationContext());
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        stopSelf();
    }

    private class GetData extends AsyncTask<Context, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Context... contexts) {
            String url;
            final Context context = contexts[0];
            url = BuildConfig.BASE_URL+"chat/notif?kepada="+ Preferences.getKeyId(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("chats");
                                if (jsonArray.length() <= 0){
                                    Log.v("chat_service", "no new notifiction");
                                }else{
                                    JSONObject objMsg = jsonArray.getJSONObject(jsonArray.length()-1);
                                    Admin admin = new Admin(objMsg.getString("dari"), objMsg.getString("nama"), "1");
                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    Intent goIntent = new Intent(context, ChatActivity.class);
                                    goIntent.putExtra("selected_admin", admin);
                                    goIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, goIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);

                                    builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                            .setSmallIcon(R.drawable.user_icon)
                                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.user_icon))
                                            .setContentTitle(context.getResources().getString(R.string.new_message_txt)+" "+objMsg.getString("nama"))
                                            .setContentText(objMsg.getString("pesan"))
                                            .setContentIntent(pendingIntent)
                                            .setSubText(context.getResources().getString(R.string.notifikasi_subtext))
                                            .setAutoCancel(true);
                                    builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                                    Notification notification = builder.build();
                                    if (notificationManager != null) {
                                        notificationManager.notify(NOTIFICATION_ID, notification);
                                    }
                                }

                            } catch (JSONException e) {
                                Log.v("chat_service", "failed to executed query");
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

            return true;
        }


    }
}

package com.alugara.idlc.models;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Kategori>> listKategori = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Kategori>> listSubKategori = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Files>> listFiles = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Files>> listPrevFiles = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Files>> listRelatedFiles = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Files>> listSearchFiles = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> listEmailFound = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Paket>> listPaket = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Profesi>> listProfesi = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Profesi>> listKota = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Seminar>> listSeminarOn = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Seminar>> listSeminarOff = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Artikel>> listArtikel = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Video>> listVideos = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Message>> listChat = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Admin>> listAdmin = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Profesi>> listProvinsi = new MutableLiveData<>();


    private ArrayList<Kategori> kategori = new ArrayList<>();
    private ArrayList<Kategori> subKategori = new ArrayList<>();
    private ArrayList<Files> files = new ArrayList<>();
    private ArrayList<Files> prevFiles = new ArrayList<>();
    private ArrayList<Files> relatedFiles = new ArrayList<>();
    private ArrayList<Files> searchFiles = new ArrayList<>();
    private ArrayList<String> emailFound = new ArrayList<>();
    private ArrayList<Paket> pakets = new ArrayList<>();
    private ArrayList<Profesi> profesies = new ArrayList<>();
    private ArrayList<Profesi> kotas = new ArrayList<>();
    private ArrayList<Seminar> seminarsOn = new ArrayList<>();
    private ArrayList<Seminar> seminarsOff = new ArrayList<>();
    private ArrayList<Artikel> artikels = new ArrayList<>();
    private ArrayList<Video> videos = new ArrayList<>();
    private ArrayList<Message> chats = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Profesi> provinsies = new ArrayList<>();

    private Utils utils = new Utils();

    public void setDataKategori(Context context, final View progressbar) {

        String url = BASE_URL+"kategori";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("kategori");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Kategori item = new Kategori(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("parent"),
                                            jsonObject.getString("status"),
                                            jsonObject.getString("image")
                                    );

                                    kategori.add(item);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listKategori.postValue(kategori);
                            utils.setInvisible(progressbar);
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

    public void setDataSub(Context context, String parent, final View progressbar){
        String url = BASE_URL+"kategori/sub?parent="+parent;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("sub_kategori");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Kategori item = new Kategori(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("parent"),
                                            jsonObject.getString("status"),
                                            jsonObject.getString("image")
                                    );

                                    subKategori.add(item);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listSubKategori.postValue(subKategori);
                            utils.setInvisible(progressbar);
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

    public void setListFiles(Context context, String idKategori, final View progressbar){
        String url = BASE_URL+"files?id_kategori="+idKategori;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("files");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Files file = new Files(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("id_kategori"),
                                            jsonObject.getString("nama"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("total_viewed"),
                                            jsonObject.getString("total_downloaded"),
                                            jsonObject.getString("parent"),
                                            jsonObject.getString("status"),
                                            jsonObject.getString("thumbnail")
                                    );

                                    files.add(file);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listFiles.postValue(files);
                            utils.setInvisible(progressbar);
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

    public void setListPrevFiles(Context context, String parent, final View progressbar){
        String url = BASE_URL+"files/prev_file?parent="+parent;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("file");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Files file = new Files(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("id_kategori"),
                                            jsonObject.getString("nama"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("total_viewed"),
                                            jsonObject.getString("total_downloaded"),
                                            jsonObject.getString("parent"),
                                            jsonObject.getString("status"),
                                            jsonObject.getString("thumbnail")
                                    );

                                    prevFiles.add(file);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listPrevFiles.postValue(prevFiles);
                            utils.setInvisible(progressbar);

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

    public void setListRelatedFiles(Context context, String idKategori, final View progressbar){
        String url = BASE_URL+"files/related_file?id_kategori="+idKategori;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("file");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Files file = new Files(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("id_kategori"),
                                            jsonObject.getString("nama"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("total_viewed"),
                                            jsonObject.getString("total_downloaded"),
                                            jsonObject.getString("parent"),
                                            jsonObject.getString("status"),
                                            jsonObject.getString("thumbnail")
                                    );

                                    relatedFiles.add(file);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listRelatedFiles.postValue(relatedFiles);
                            utils.setInvisible(progressbar);
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

    public void setListSearchFiles(Context context, String name, final View progressbar){

        searchFiles.clear();
        String url = BASE_URL+"files/search?name="+name;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("file");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Files file = new Files(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("id_kategori"),
                                            jsonObject.getString("nama"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("total_viewed"),
                                            jsonObject.getString("total_downloaded"),
                                            jsonObject.getString("parent"),
                                            jsonObject.getString("status"),
                                            jsonObject.getString("thumbnail")
                                    );

                                    searchFiles.add(file);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listSearchFiles.postValue(searchFiles);
                            utils.setInvisible(progressbar);
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
    
    public void setListEmailFound(final Context context, String id, String code, final ProgressDialog progressDialog){
        emailFound.clear();
        listEmailFound.postValue(emailFound);
        String url = BASE_URL+"auth/verif?id="+id+"&code="+code;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("users");

                            if (jsonArray.length() > 0 ){
                                emailFound.add("ada");
                                progressDialog.dismiss();
                                Toast.makeText(context, "Verifikasi berhasil, silahkan login ulang", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Kode yang anda masukkan salah", Toast.LENGTH_SHORT).show();
                            }
                            listEmailFound.postValue(emailFound);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error while verifying, check your connection", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public void setListPaket(Context context, final View progressbar){
        String url = BASE_URL+"files/paket";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("paket");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Paket paket = new Paket();
                                    paket.setId(jsonObject.getString("id"));
                                    paket.setTitle(jsonObject.getString("title"));
                                    paket.setPrice(jsonObject.getString("price"));
                                    paket.setBenefit(jsonObject.getString("benefit"));

                                    pakets.add(paket);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listPaket.postValue(pakets);
                            utils.setGone(progressbar);
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

    public void setListProfesi(Context context){
        String url = BASE_URL+"files/profesi";
        profesies.add(new Profesi("0", "-Pilih profesi-"));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("profesi");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Profesi profesi = new Profesi(jsonObject.getString("id"),
                                            jsonObject.getString("name"));

                                    profesies.add(profesi);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listProfesi.postValue(profesies);
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

    public void setListProvinsi(Context context){
        String url = BASE_URL+"files/provinsi";
        provinsies.add(new Profesi("0", "-Pilih provinsi-"));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("provinsi");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Profesi provinsi = new Profesi(jsonObject.getString("id_provinsi"),
                                            jsonObject.getString("nama_provinsi"));

                                    provinsies.add(provinsi);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listProvinsi.postValue(provinsies);
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

    public void setListKota(Context context, String idProvinsi){
        String url = BASE_URL+"files/kota?id_provinsi="+idProvinsi;
        kotas.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("kota");
                            Log.d("kota_length", ""+jsonArray.length());

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Profesi profesi = new Profesi(jsonObject.getString("id_kota"),
                                            jsonObject.getString("nama_kota"));

                                    kotas.add(profesi);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listKota.postValue(kotas);
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

    public void setListSeminarOn(Context context){
        String url = BASE_URL+"seminar/on";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("seminar");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Seminar seminar = new Seminar(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("nama"),
                                            jsonObject.getString("poster"),
                                            jsonObject.getString("hari"),
                                            jsonObject.getString("tanggal"),
                                            jsonObject.getString("biaya"),
                                            jsonObject.getString("tempat"),
                                            jsonObject.getString("file"));

                                    seminarsOn.add(seminar);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listSeminarOn.postValue(seminarsOn);
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

    public void setListSeminarOff(Context context){
        String url = BASE_URL+"seminar/off";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("seminar");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Seminar seminar = new Seminar(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("nama"),
                                            jsonObject.getString("poster"),
                                            jsonObject.getString("hari"),
                                            jsonObject.getString("tanggal"),
                                            jsonObject.getString("biaya"),
                                            jsonObject.getString("tempat"),
                                            jsonObject.getString("file"));

                                    seminarsOff.add(seminar);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listSeminarOff.postValue(seminarsOff);
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

    public void setListArtikel(Context context, String idKategori){
        String url = BASE_URL+"artikel/by_category?id_kategori="+idKategori;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("artikel");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Artikel artikel = new Artikel(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("id_kategori"),
                                            jsonObject.getString("foto"),
                                            jsonObject.getString("judul"),
                                            jsonObject.getString("isi"),
                                            jsonObject.getString("uploaded_by"),
                                            jsonObject.getString("nama"));

                                    artikels.add(artikel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listArtikel.postValue(artikels);
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

    public void setListVideos(Context context){
        String url = BASE_URL+"artikel/videos";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("videos");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Video video = new Video(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("thumbnail"));

                                    videos.add(video);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listVideos.postValue(videos);
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

    public void setListMessage(final Context context, final String userID, final String adminID, final int limit){
        String url = BASE_URL+"chat";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject usersObj = new JSONObject(response);
                            JSONArray messages = usersObj.getJSONArray("messages");

                            for (int i = 0; i < messages.length(); i++) {
                                try {
                                    JSONObject jsonObject = messages.getJSONObject(i);

                                    Message message = new Message(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("dari"),
                                            jsonObject.getString("kepada"),
                                            jsonObject.getString("pesan"),
                                            jsonObject.getString("sent_at"),
                                            jsonObject.getString("is_read"));

                                    chats.add(message);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listChat.postValue(chats);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(context, "Connection error, check your internet", Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("user_id", userID);
                params.put("admin_id", adminID);
                params.put("limit", String.valueOf(limit));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(postRequest);
    }

    public void setListAdmin(Context context){
        String url = BASE_URL+"chat/list_admin";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("admins");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Admin admin = new Admin(jsonObject.getString("id"),
                                            jsonObject.getString("nama"),
                                            jsonObject.getString("online"));

                                    admins.add(admin);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listAdmin.postValue(admins);
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

    public MutableLiveData<ArrayList<Kategori>> getlistKategori() {
        return listKategori;
    }

    public MutableLiveData<ArrayList<Kategori>> getListSubKategori() {
        return listSubKategori;
    }

    public MutableLiveData<ArrayList<Files>> getListPrevFiles() {
        return listPrevFiles;
    }

    public MutableLiveData<ArrayList<Files>> getListRelatedFiles() {
        return listRelatedFiles;
    }

    public MutableLiveData<ArrayList<Files>> getListFiles() {
        return listFiles;
    }

    public MutableLiveData<ArrayList<Files>> getListSearchFiles() {
        return listSearchFiles;
    }

    public MutableLiveData<ArrayList<String>> getListEmailFound() {
        return listEmailFound;
    }

    public MutableLiveData<ArrayList<Paket>> getListPaket() {
        return listPaket;
    }

    public MutableLiveData<ArrayList<Profesi>> getListProfesi() {
        return listProfesi;
    }

    public MutableLiveData<ArrayList<Profesi>> getListProvinsi() {
        return listProvinsi;
    }

    public MutableLiveData<ArrayList<Profesi>> getListKota() {
        return listKota;
    }

    public MutableLiveData<ArrayList<Seminar>> getListSeminarOn() {
        return listSeminarOn;
    }

    public MutableLiveData<ArrayList<Seminar>> getListSeminarOff() {
        return listSeminarOff;
    }

    public MutableLiveData<ArrayList<Artikel>> getListArtikel() {
        return listArtikel;
    }

    public MutableLiveData<ArrayList<Video>> getListVideos() {
        return listVideos;
    }

    public MutableLiveData<ArrayList<Message>> getListMessages() {
        return listChat;
    }

    public MutableLiveData<ArrayList<Admin>> getListAdmin() {
        return listAdmin;
    }
}

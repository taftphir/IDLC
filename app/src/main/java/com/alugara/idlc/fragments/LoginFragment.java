package com.alugara.idlc.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.ForgetPasswordActivity;
import com.alugara.idlc.activities.MainMenuActivity;
import com.alugara.idlc.activities.VerificationActivity;
import com.alugara.idlc.models.Utils;
import com.alugara.idlc.preferences.Preferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.alugara.idlc.BuildConfig.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    Button loginBtn;
    TextView forgetTv;
    EditText userTv, passwdTv;
    CheckBox rememberMe;
    ProgressDialog progressDialog;
    private Utils utils = new Utils();
    private boolean isEmailFilled, isPassFilled;
    private TextView validEmailTv, validPassTv;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginBtn = view.findViewById(R.id.login_btn);
        userTv = view.findViewById(R.id.login_emailTv);
        passwdTv = view.findViewById(R.id.login_passwdTv);
        validEmailTv = view.findViewById(R.id.tv_val_log_email);
        validPassTv = view.findViewById(R.id.tv_val_log_pass);

        userTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean validEmail = utils.isEmailValid(s.toString());
                if (s.toString().isEmpty()) {
                    validEmailTv.setText(getResources().getString(R.string.must_not_blank));
                    utils.setVisible(validEmailTv);
                    isEmailFilled = false;
                } else if (!validEmail) {
                    validEmailTv.setText(getResources().getString(R.string.not_valid_email));
                    utils.setVisible(validEmailTv);
                    isEmailFilled = false;
                } else if (validEmail) {
                    utils.setGone(validEmailTv);
                    isEmailFilled = true;
                }
            }
        });
        userTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    utils.setGone(validEmailTv);
                }
            }
        });

        passwdTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    validPassTv.setText(getResources().getString(R.string.must_not_blank));
                    utils.setVisible(validPassTv);
                    isPassFilled = false;
                }
                else {
                    utils.setGone(validPassTv);
                    isPassFilled = true;
                }
            }
        });
        passwdTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    utils.setGone(validPassTv);
                }
            }
        });

        forgetTv = view.findViewById(R.id.forget_passwd_tv);
        rememberMe = view.findViewById(R.id.checkbox_remember);
        progressDialog = new ProgressDialog(getContext());

        forgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ForgetPasswordActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailFilled && isPassFilled){
                    String url;
                    url = BASE_URL+"auth";
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    //Without this user can hide loader by tapping outside screen
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();
                    progressDialog.setCancelable(true);

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    try {
                                        JSONObject usersObj = new JSONObject(response);
                                        JSONArray users = usersObj.getJSONArray("users");
                                        if (users.length() > 0){
                                            JSONObject user = users.getJSONObject(0);
                                            if (user.getString("status").equals("off")){
                                                startActivity(new Intent(getActivity(), VerificationActivity.class));
                                            }else{
                                                Intent intent = new Intent(getContext(), MainMenuActivity.class);
                                                if (rememberMe.isChecked()){

                                                    Preferences.setKeyEmail(getContext(), userTv.getText().toString());
                                                    Preferences.setKeyId(getContext(), user.getString("id"));
                                                    Preferences.setKeyLogin(getContext(), true);
                                                }

                                                Preferences.setKeyId(getContext(), user.getString("id"));
                                                Preferences.setKeyPaket(getContext(), user.getString("id_paket"));

                                                startActivity(intent);
                                                getActivity().finish();
                                            }
                                            progressDialog.dismiss();
                                        }else{
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Email & password not valid", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("Response", response);
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Connection error, check your internet", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<>();
                            params.put("email", userTv.getText().toString());
                            params.put("passwd", passwdTv.getText().toString());

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(postRequest);
                }else {
                    if (!isPassFilled){
                        //passwdTv.getBackground().mutate().setColorFilter(getResources().getColor(R.color.not_valid_color), PorterDuff.Mode.SRC_ATOP);
                        validPassTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validPassTv);
                        isPassFilled = false;
                    }

                    if (!isEmailFilled){
                        validEmailTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validEmailTv);
                        isEmailFilled = false;
                    }
                }


            }
        });
        return view;
    }

}

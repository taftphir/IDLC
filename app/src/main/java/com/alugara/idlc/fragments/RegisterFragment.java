package com.alugara.idlc.fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.LaunchActivity;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Profesi;
import com.alugara.idlc.models.Utils;
import com.alugara.idlc.preferences.Preferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.alugara.idlc.BuildConfig.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private String tipeUser;
    private EditText namaTv, emailTv, telpTv, passwdTv, confirmTv;
    private AutoCompleteTextView kotaTv;
    private TextView validNameTv, validEmailTv, validTelpTv, validKotaTv, validPassTv, validConfirmTv, valProfesiTv;
    private boolean isNameFilled, isTelpFilled,isKotaFilled, isPassFilled, isConfirmFilled, isProfesiFilled;
    private Button registerBtn;
    private Spinner spinnerProfesi, spinnerProvinsi;
    private boolean validEmail;
    private Utils utils = new Utils();
    ProgressDialog progressDialog;
    MainViewModel mainViewModel;
    private ArrayList<Profesi> listProfesi = new ArrayList<>();
    private ArrayList<Profesi> listProvinsi = new ArrayList<>();
    private ArrayList<Profesi> listKota = new ArrayList<>();

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tipeUser = bundle.getString("tipe_user");
        }
    }

    private Observer<ArrayList<Profesi>> getProfesi = new Observer<ArrayList<Profesi>>() {
        @Override
        public void onChanged(ArrayList<Profesi> profesis) {
            ArrayAdapter<Profesi> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, profesis);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            spinnerProfesi.setAdapter(adapter);
            listProfesi = profesis;

        }
    };

    private Observer<ArrayList<Profesi>> getProvinsi = new Observer<ArrayList<Profesi>>() {
        @Override
        public void onChanged(ArrayList<Profesi> provinsies) {
            ArrayAdapter<Profesi> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, provinsies);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            spinnerProvinsi.setAdapter(adapter);
            listProvinsi = provinsies;

        }
    };

    private Observer<ArrayList<Profesi>> getKota = new Observer<ArrayList<Profesi>>() {
        @Override
        public void onChanged(ArrayList<Profesi> kotas) {
            ArrayAdapter<Profesi> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, kotas);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            kotaTv.setAdapter(adapter);
            kotaTv.setThreshold(1);
            listKota = kotas;

        }
    };

    private int checkIdKota(String name){
        int indexKota = -1;
        for (Profesi kota : listKota){
            if (kota.getName().equals(name))
                indexKota = Integer.parseInt(kota.getId());
        }

        return indexKota;
    }

    private int checkIdProvinsi(String name){
        int indexProvinsi = -1;
        for (Profesi provinsi : listProvinsi){
            if (provinsi.getName().equals(name))
                indexProvinsi = Integer.parseInt(provinsi.getId());
        }

        return indexProvinsi;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        validNameTv = view.findViewById(R.id.tv_val_name);
        validEmailTv = view.findViewById(R.id.tv_val_email);
        validTelpTv = view.findViewById(R.id.tv_val_phone);
        validKotaTv = view.findViewById(R.id.tv_val_kota);
        validPassTv = view.findViewById(R.id.tv_val_pass);
        validConfirmTv = view.findViewById(R.id.tv_val_confirm);
        valProfesiTv = view.findViewById(R.id.tv_val_profesi);
        spinnerProfesi = view.findViewById(R.id.spinner_profesi);
        spinnerProvinsi = view.findViewById(R.id.spinner_provinsi);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setListProfesi(getContext());
        mainViewModel.getListProfesi().observe(this, getProfesi);
        mainViewModel.setListProvinsi(getContext());
        mainViewModel.getListProvinsi().observe(this, getProvinsi);

        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    String selectedProvinsi = spinnerProvinsi.getItemAtPosition(position).toString();
                    int idProvinsi = checkIdProvinsi(selectedProvinsi);
                    mainViewModel.setListKota(getContext(), String.valueOf(idProvinsi));
                    mainViewModel.getListKota().observe(RegisterFragment.this, getKota);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        namaTv = view.findViewById(R.id.register_namaTv);
        namaTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    validNameTv.setText(getResources().getString(R.string.must_not_blank));
                    isNameFilled = false;
                    utils.setVisible(validNameTv);
                }else{
                    utils.setGone(validConfirmTv);
                    isNameFilled = true;
                }
            }
        });
        namaTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String name = namaTv.getText().toString();
                    if (name.isEmpty()) {
                        validNameTv.setText(getResources().getString(R.string.must_not_blank));
                        isNameFilled = false;
                        utils.setVisible(validNameTv);
                    } else {
                        isNameFilled = true;
                    }

                } else {
                    utils.setGone(validNameTv);
                    isNameFilled = false;
                }
            }
        });

        emailTv = view.findViewById(R.id.register_emailTv);
        emailTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    validEmailTv.setText(getResources().getString(R.string.must_not_blank));
                    utils.setVisible(validEmailTv);
                    validEmail = false;
                }else{
                    validEmail = isEmailValid(s.toString());
                    if (!validEmail) {
                        validEmailTv.setText(getResources().getString(R.string.not_valid_email));
                        utils.setVisible(validEmailTv);
                    }else{
                        utils.setGone(validEmailTv);
                    }
                }
            }
        });
        emailTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email = emailTv.getText().toString();
                    if (email.isEmpty()) {
                        validEmailTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validEmailTv);
                        validEmail = false;
                    } else {
                        validEmail = isEmailValid(email);
                        if (!validEmail) {
                            validEmailTv.setText(getResources().getString(R.string.not_valid_email));
                            utils.setVisible(validEmailTv);
                        }else {
                            utils.setInvisible(validEmailTv);
                        }
                    }


                } else {
                    utils.setGone(validEmailTv);
                    validEmail = false;
                }
            }
        });

        telpTv = view.findViewById(R.id.register_telpTv);
        telpTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    validTelpTv.setText(getResources().getString(R.string.must_not_blank));
                    isTelpFilled = false;
                    utils.setVisible(validTelpTv);
                }else{
                    isTelpFilled = true;
                    utils.setGone(validTelpTv);
                }
            }
        });
        telpTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String name = telpTv.getText().toString();
                    if (name.isEmpty()) {
                        validTelpTv.setText(getResources().getString(R.string.must_not_blank));
                        isTelpFilled = false;
                        utils.setVisible(validTelpTv);
                    } else {
                        isTelpFilled = true;
                    }

                } else {
                    utils.setGone(validTelpTv);
                    isTelpFilled = false;
                }
            }
        });

        kotaTv = view.findViewById(R.id.register_kotaTv);
        kotaTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    validKotaTv.setText(getResources().getString(R.string.must_not_blank));
                    isKotaFilled = false;
                    utils.setVisible(validKotaTv);
                }else{
                    isKotaFilled = true;
                    utils.setGone(validKotaTv);
                }
            }
        });
        kotaTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String name = kotaTv.getText().toString();
                    if (name.isEmpty()) {
                        validKotaTv.setText(getResources().getString(R.string.must_not_blank));
                        isKotaFilled = false;
                        utils.setVisible(validKotaTv);
                    } else {
                        isKotaFilled = true;
                    }

                } else {
                    utils.setGone(validKotaTv);
                }
            }
        });

        passwdTv = view.findViewById(R.id.register_passwdTv);
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
                } else if (s.toString().length() < 8) {
                    validPassTv.setText(getResources().getString(R.string.passwd_criteria));
                    utils.setVisible(validPassTv);
                    isPassFilled = false;
                } else {
                    isPassFilled = true;
                    utils.setGone(validPassTv);
                }
            }
        });
        passwdTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String passwd = passwdTv.getText().toString();
                    if (passwd.isEmpty()) {
                        validPassTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validPassTv);
                        isPassFilled = false;
                    } else if (passwd.length() < 8) {
                        validPassTv.setText(getResources().getString(R.string.passwd_criteria));
                        utils.setVisible(validPassTv);
                        isPassFilled = false;
                    } else {
                        isPassFilled = true;
                    }

                } else {
                    utils.setGone(validPassTv);
                }
            }
        });

        confirmTv = view.findViewById(R.id.register_confirm_passwdTv);
        confirmTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    validConfirmTv.setText(getResources().getString(R.string.must_not_blank));
                    utils.setVisible(validConfirmTv);
                    isConfirmFilled = false;
                } else if (!s.toString().equals(passwdTv.getText().toString())) {
                    validConfirmTv.setText(getResources().getString(R.string.must_same_passwd));
                    utils.setVisible(validConfirmTv);
                    isConfirmFilled = false;
                } else if (s.toString().equals(passwdTv.getText().toString())) {
                    utils.setGone(validConfirmTv);
                    isConfirmFilled = true;
                }
            }
        });
        confirmTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    utils.setGone(validConfirmTv);
                }
            }
        });

        registerBtn = view.findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                //Without this user can hide loader by tapping outside screen
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Registering...");
                progressDialog.show();
                progressDialog.setCancelable(true);

                final int indexKota = checkIdKota(kotaTv.getText().toString());
                final int indexProvinsi = checkIdProvinsi(spinnerProvinsi.getSelectedItem().toString());
                Log.d("kota_index", kotaTv.getText().toString()+" : "+indexKota);
                if (indexKota >= 0)
                    isKotaFilled = true;
                else
                    isKotaFilled = false;

                if (spinnerProfesi.getSelectedItemPosition() > 0)
                    isProfesiFilled = true;
                else
                    isProfesiFilled = false;

                if (isNameFilled &&
                        validEmail &&
                        isTelpFilled &&
                        isKotaFilled &&
                        isPassFilled &&
                        isConfirmFilled &&
                        isProfesiFilled) {
                    String url;
                    url = BASE_URL + "auth/register";
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
                                            Preferences.setKeyRegEmail(getContext(), emailTv.getText().toString());
                                            Toast.makeText(getContext(), "Pendaftaran berhasil, silahkan verifikasi akun anda melalui email", Toast.LENGTH_SHORT).show();
                                            getContext().startActivity(new Intent(getActivity(), LaunchActivity.class));
                                        } else {
                                            Toast.makeText(getContext(), "Pendaftaran gagal, periksa koneksi anda", Toast.LENGTH_SHORT).show();
                                        }
                                        progressDialog.dismiss();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Connection error, check your internet", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("nama", namaTv.getText().toString());
                            params.put("email", emailTv.getText().toString());
                            params.put("telp", telpTv.getText().toString());
                            params.put("id_provinsi", String.valueOf(indexProvinsi));
                            params.put("id_kota", String.valueOf(indexKota));
                            params.put("level", "user");
                            int indexProf = listProfesi.indexOf(spinnerProfesi.getSelectedItem());
                            params.put("id_profesi", listProfesi.get(indexProf).getId());
                            params.put("passwd", passwdTv.getText().toString());
                            params.put("status", "off");
                            params.put("id_paket", "4");

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(postRequest);

                } else {
                    progressDialog.dismiss();

                    if (!isNameFilled) {
                        //namaTv.getBackground().mutate().setColorFilter(getResources().getColor(R.color.not_valid_color), PorterDuff.Mode.SRC_ATOP);
                        validNameTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validNameTv);
                    }
                    if (!validEmail) {
                        //emailTv.getBackground().mutate().setColorFilter(getResources().getColor(R.color.not_valid_color), PorterDuff.Mode.SRC_ATOP);
                        validEmailTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validEmailTv);
                    }
                    if (!isTelpFilled) {
                        validTelpTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validTelpTv);
                    }
                    if (!isPassFilled) {
                        validPassTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validPassTv);
                    }

                    if (!isConfirmFilled) {
                        validConfirmTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(validTelpTv);
                    }

                    if (!isKotaFilled) {
                        validKotaTv.setText(getResources().getString(R.string.must_valid));
                        utils.setVisible(validKotaTv);
                    }

                    if (!isProfesiFilled){
                        valProfesiTv.setText(getResources().getString(R.string.must_not_blank));
                        utils.setVisible(valProfesiTv);
                    }
                }
            }
        });
        return view;
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }


}

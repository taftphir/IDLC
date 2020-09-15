package com.alugara.idlc.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.CategoryActivity;
import com.alugara.idlc.activities.PdfReaderActivity;
import com.alugara.idlc.adapters.RelatedItemsAdapter;
import com.alugara.idlc.models.Files;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Utils;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailDocsFragment extends Fragment {

    private RecyclerView relatedRv, previousRv;
    private ProgressDialog pDialog;
    Button downloadBtn, readBtn;
    MainViewModel mainViewModel;
    private static final int progress_bar_type = 0;
    private Files files;
    private ImageView previewIv;
    private TextView prevNoContent, relatedNoContent;
    private ProgressBar progressBar, progressBarPrev, progressBarRel;
    private Utils util = new Utils();

    PdfStream myTask = null;
    DownloadFileFromURL downloadTask = null;

    public DetailDocsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            files = bundle.getParcelable("selected_files");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_detail_docs, container, false);

        ((CategoryActivity) getActivity())
                .getSupportActionBar().setTitle(files.getName());

        previewIv = view.findViewById(R.id.preview_iv);
        relatedRv = view.findViewById(R.id.rv_related_item);
        previousRv = view.findViewById(R.id.rv_previous_item);
        downloadBtn = view.findViewById(R.id.btn_download);
        readBtn = view.findViewById(R.id.btn_read);
        progressBar = view.findViewById(R.id.progress_bar_detail);
        progressBarPrev = view.findViewById(R.id.progress_bar_prev);
        progressBarRel = view.findViewById(R.id.progress_bar_related);
        prevNoContent = view.findViewById(R.id.prev_no_content_tv);
        relatedNoContent = view.findViewById(R.id.related_no_content_tv);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadTask = new DownloadFileFromURL();
                downloadTask.execute(files.getUrl());
            }
        });
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PdfReaderActivity.class);
                intent.putExtra("url_pdf", files.getUrl());
                intent.putExtra("name_pdf", files.getName());
                startActivity(intent);
            }
        });

        getPrevItem(files.getParent());
        getRelatedItem(files.getIdKategori());
        myTask = new PdfStream();
        myTask.execute(files.getUrl());
        //Glide.with(this).load(files.getThumbnail()).into(previewIv);

        return view;
    }

    public byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    void getThumbnailPdf(byte[] streamPdf) throws IOException {
        int pageNum = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(getContext());
        PdfDocument pdfDocument = pdfiumCore.newDocument(streamPdf);

        pdfiumCore.openPage(pdfDocument, pageNum);

        int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNum);
        int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNum);

        // ARGB_8888 - best quality, high memory usage, higher possibility of OutOfMemoryError
        // RGB_565 - little worse quality, twice less memory usage
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageNum, 0, 0,
                width, height);
        //if you need to render annotations and form fields, you can use
        //the same method above adding 'true' as last param

        previewIv.setImageBitmap(bitmap);

        pdfiumCore.closeDocument(pdfDocument); // important!
    }

    private Observer<ArrayList<Files>> getPrevFiles = new Observer<ArrayList<Files>>() {
        @Override
        public void onChanged(ArrayList<Files> files) {

            if (files.size() > 0){
            RelatedItemsAdapter fileAdapter = new RelatedItemsAdapter(files, getContext());
            previousRv.setAdapter(fileAdapter);
            previousRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            fileAdapter.notifyDataSetChanged();
            }else{
                util.setVisible(prevNoContent);
            }
        }
    };

    private Observer<ArrayList<Files>> getRelatedFiles = new Observer<ArrayList<Files>>() {
        @Override
        public void onChanged(ArrayList<Files> files) {
            if (files.size() > 0) {
                RelatedItemsAdapter fileAdapter = new RelatedItemsAdapter(files, getContext());
                relatedRv.setAdapter(fileAdapter);
                relatedRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                fileAdapter.notifyDataSetChanged();
            }else{
                util.setVisible(relatedNoContent);
            }
        }
    };

    private void getRelatedItem(String idKategori){
        util.setVisible(progressBarRel);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setListRelatedFiles(getContext(), idKategori, progressBarRel);
        mainViewModel.getListRelatedFiles().observe(this, getRelatedFiles);
    }

    private void getPrevItem(String parent){
        util.setVisible(progressBarPrev);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setListPrevFiles(getContext(), parent, progressBarPrev);
        mainViewModel.getListPrevFiles().observe(this, getPrevFiles);
    }

    protected Dialog showDialog(int id){
        switch (id){
            case progress_bar_type:
                pDialog = new ProgressDialog(getContext());
                pDialog.setMessage(getResources().getString(R.string.download_message_text));
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String>{
        protected  void onPreExecute(){
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lenghOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/file.pdf");
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1){
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e){
                Log.e("Error : ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        protected void onPostExecute(String file_url){
            Toast.makeText(getContext(), "File has been saved to "+
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
        }
    }

    class PdfStream extends AsyncTask<String, Void, byte[]> {
        @Override
        protected void onPreExecute() {
            Utils utils = new Utils();
            utils.setVisible(progressBar);
        }

        @Override
        protected byte[] doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                return null;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                int n;

                while ( (n = inputStream.read(byteChunk)) > 0 ) {
                    baos.write(byteChunk, 0, n);
                }
            }
            catch (IOException e) {
                e.printStackTrace ();
                // Perform any other exception handling that's appropriate.
            }
            return baos.toByteArray();
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            try {
                getThumbnailPdf(bytes);
                Utils utils = new Utils();
                utils.setInvisible(progressBar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (downloadTask!=null){
            downloadTask.cancel(true);
        }

        if (myTask != null){
            myTask.cancel(true);
        }
    }
}

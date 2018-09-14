package edu.ptit.vhlee.galleryplus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;

public class MainActivity extends AppCompatActivity {
    public static final String PHOTOS_PATH = "storage/emulated/0/DCIM/Camera";
    public static final String[] EXTENSIONS = {".jpg", ".png", ".gif"};
    public static final int REQUEST_PERMISSION = 13;
    private PhotoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] != PackageManager.PERMISSION_DENIED) loadPhotos();
                else checkPermission();
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, permissions[0])
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, REQUEST_PERMISSION);
        } else loadPhotos();
    }

    private void loadPhotos() {
        PhotoAsyncTask task = new PhotoAsyncTask();
        task.execute(PHOTOS_PATH);
    }

    private void initViews() {
        RecyclerView recycler = findViewById(R.id.recycler_photos);
        mAdapter = new PhotoAdapter(this);
        recycler.setAdapter(mAdapter);
    }

    public class PhotoAsyncTask extends AsyncTask<String, Photo, String> {

        @Override
        protected String doInBackground(String... paths) {
            File directory = new File(paths[0]);
            File[] files = directory.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    for (String extension : EXTENSIONS) {
                        if (file.getName().toLowerCase().endsWith(extension)) return true;
                    }
                    return false;
                }
            });
            for (File file : files) {
                publishProgress(new Photo(file.getPath(), file.getName()));
            }
            return files.length + " " + getString(R.string.notify_loaded);
        }

        @Override
        protected void onProgressUpdate(Photo... photos) {
            mAdapter.addPhoto(photos[0]);
        }

        @Override
        protected void onPostExecute(String notify) {
            Toast.makeText(MainActivity.this, notify, Toast.LENGTH_LONG).show();
        }
    }
}

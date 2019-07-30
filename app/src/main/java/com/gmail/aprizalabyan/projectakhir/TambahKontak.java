package com.gmail.aprizalabyan.projectakhir;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TambahKontak extends AppCompatActivity {
    private EditText txtNama;
    private EditText txtEmail;
    private ImageView imageView;
    private Button btnAmbilfoto;
    private Button btnPilihFoto;
    private Uri file;

    MySQLHelper dbHelper;
    TextView txtDraw;
    Bitmap imageTake;
    Bitmap imageSelect;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_kontak);

        dbHelper = new MySQLHelper(this);

        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmail);
        imageView = findViewById(R.id.imageView);
        btnAmbilfoto = findViewById(R.id.btnAmbilfoto);
        btnPilihFoto = findViewById(R.id.btnPilihfoto);
        txtDraw = findViewById(R.id.txtDraw);

        //TODO 2 : mengecek jika belum memiliki permission untuk mengakses kamera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //TODO 2.1 : button camera didisable
            btnAmbilfoto.setEnabled(false);
            //TODO 2.2 : merequest permission untuk mengakses kamera
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        btnAmbilfoto.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = Uri.fromFile(getOutputMediaFile());
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, file);
                startActivityForResult(intentCamera, 0);
            }
        });

        btnPilihFoto.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentGaleri = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGaleri, 1);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtNama.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Nama harus diisi", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Berhasil Simpan", Toast.LENGTH_SHORT).show();
                    addKontak();
                    Intent intentMenu = new Intent(getApplicationContext(), MainActivity.class);
                    intentMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentMenu);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnAmbilfoto.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String path = file.getPath();
                decodeFileTake(path);
                setProgressBar();
                imageView.setImageBitmap(imageTake);
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 1){
            if (resultCode == RESULT_OK){
                file = data.getData();
                String path = file.getEncodedPath();
                decodeFileSelect(path);
                setProgressBar();
                imageView.setImageBitmap(imageSelect);
                /*
                try{
                    file = data.getData();
                    //imageView.setImageURI(file);
                    InputStream imageStream = getContentResolver().openInputStream(file);
                    Bitmap imageSelect = BitmapFactory.decodeStream(imageStream);
                    setProgressBar();
                    imageView.setImageBitmap(imageSelect);

                    String addImage = imageView.getDrawable().toString();
                    txtDraw.setText(addImage);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }*/
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Camera");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    public void setProgressBar(){
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100){
                    progressBarStatus += 30;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBarbHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }
        }).start();
    }

    private void addKontak(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String addNama = txtNama.getText().toString().trim();
        String addEmail = txtEmail.getText().toString().trim();

        Bitmap imgBit = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imgBit.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] imgByte = baos.toByteArray();

        dbHelper.addToDb(addNama, addEmail, imgByte);
        dbHelper.close();
    }

    private Bitmap decodeFileTake(String imgPath) {
        int max_size = 1000;
        File f = new File(imgPath);
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;
            if (o.outHeight > max_size || o.outWidth > max_size)
            {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(max_size / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            imageTake = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        }
        catch (Exception e) {
        }
        return imageTake;
    }

    private Bitmap decodeFileSelect(String imgPath) {
        int max_size = 1000;
        File f = new File(imgPath);
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;
            if (o.outHeight > max_size || o.outWidth > max_size)
            {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(max_size / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            imageTake = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        }
        catch (Exception e) {
        }
        return imageSelect;
    }
}

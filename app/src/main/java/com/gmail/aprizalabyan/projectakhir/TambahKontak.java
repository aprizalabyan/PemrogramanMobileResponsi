package com.gmail.aprizalabyan.projectakhir;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TambahKontak extends AppCompatActivity {
    ImageView imageView;
    Button btnAmbilfoto;
    Button btnPilihFoto;
    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_kontak);

        imageView = findViewById(R.id.imageView);
        btnAmbilfoto = findViewById(R.id.btnAmbilfoto);
        btnPilihFoto = findViewById(R.id.btnPilihfoto);

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
                Snackbar.make(view, "Simpan", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //TODO 3 : method untuk hasil request permission kamera
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //TODO 3.1 : mengecek jika sudah meiliki permission maka button camera dienable
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnAmbilfoto.setEnabled(true);
            }
        }
    }

    //TODO 5 : method untuk activity hasil take picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //TODO 5.1 : mengecek jika hasilnya benar maka akan menampilkan gambar tersebut pada image view
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(file);
            }
            //TODO 5.2 : jika user membatalkan take picture maka akan memunculkan toast
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 1){
            if (resultCode == RESULT_OK){
                file = data.getData();
                imageView.setImageURI(file);
            }
            //TODO 5.2 : jika user membatalkan take picture maka akan memunculkan toast
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //TODO 6 : method untuk menyimpan hasil foto
    private static File getOutputMediaFile(){
        //TODO 6.1 : mengambil direktori pictures dari penyimpanan perangkat
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Camera");

        //TODO 6.2 : mengecek direktori pictures ada atau tidak
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        //TODO 6.3 : membuat time stamp untuk gambar hasil foto
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //TODO 6.4 : membuat file baru atau menyimpan hasil foto pada direktori yang telah diset tadi
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
}

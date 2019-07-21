package com.gmail.aprizalabyan.projectakhir;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // TODO 1 : Inisialisasi list, maintitle, subtitle, dan imgid
    ListView list;
    String[] maintitle = {"Muhammad Aprizal Abyan",
            "Agus Afriyanto", "Dikdik Maulana", "Jimmy Fernando",
            "Sirojudin Abdul Ghofur", "Pande Kadek Cahya", "Sandy", "Tri Yudo Wibowo", "Addfg" };
    String[] subtitle = {"aprizalabyan@gmail.com",
            "agusafriyan14@gmail.com", "dikdikm@gmail.com", "jimmyfernando99@gmail.com",
            "sirojudinag@gmail.com", "kadekcahya@gmail.com", "sandyao@gmail.com", "triyudo@gmail.com", "asrtyu" };
    Integer[] imgid = { };

    // TODO 2 : Inisialisasi kelas TampilList dengan tampilan layout tampil_list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO 2.1 : Memanggil kelas ListAdapter pada variabel adapter dengan parameter maintitle, subtitle, dan imgid
        ListAdapter adapter = new ListAdapter(this, maintitle, subtitle, imgid);

        // TODO 2.2 : Mengambil layout ListView dan disimpan pada variabel, kemudian memberikan adapter
        list = findViewById(R.id.list);
        list.setAdapter(adapter);

        // TODO 2.3 : Membuat adapter pada saat item list diklik
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 2.3.1 : Menampilkan Toast berisi pesan pada saat item list diklik, sesuai dengan position masing-masing item
                if(position == 0) {
                    Toast.makeText(getApplicationContext(),"Hallo saya Aby",Toast.LENGTH_SHORT).show();
                }
                else if(position == 1) {
                    Toast.makeText(getApplicationContext(),"Hallo saya Agus",Toast.LENGTH_SHORT).show();
                }
                else if(position == 2) {
                    Toast.makeText(getApplicationContext(),"Hallo saya Dikdik",Toast.LENGTH_SHORT).show();
                }
                else if(position == 3) {
                    Toast.makeText(getApplicationContext(),"Hallo saya Jimmy",Toast.LENGTH_SHORT).show();
                }
                else if(position == 4) {
                    Toast.makeText(getApplicationContext(),"Hallo saya Judin",Toast.LENGTH_SHORT).show();
                }
                else if(position == 5) {
                    Toast.makeText(getApplicationContext(),"Hallo saya Kadek",Toast.LENGTH_SHORT).show();
                }
                else if(position == 6) {
                    Toast.makeText(getApplicationContext(),"Hallo saya Sandy",Toast.LENGTH_SHORT).show();
                }
                else if(position == 7) {
                    Toast.makeText(getApplicationContext(),"Hallo saya Yudo",Toast.LENGTH_SHORT).show();
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO x
                startActivity(new Intent(MainActivity.this, TambahKontak.class));
            }
        });
    }
}

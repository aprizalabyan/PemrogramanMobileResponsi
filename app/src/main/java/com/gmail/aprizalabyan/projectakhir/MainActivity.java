package com.gmail.aprizalabyan.projectakhir;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    MySQLHelper dbHelper;
    ListView listView;
    private ArrayList<String> listnama;
    private ArrayList<String> listemail;
    private byte[] listfoto;

    ArrayList<Kontak> kontakList;
    KontakAdapter kontakAdapter;

    // TODO 2 : Inisialisasi kelas TampilList dengan tampilan layout tampil_list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MySQLHelper(this);
        listnama = new ArrayList<>();
        listemail = new ArrayList<>();

        listView = findViewById(R.id.list);
        kontakList = new ArrayList<>();
        getData();
        kontakAdapter = new KontakAdapter(this, R.layout.list_kontak, kontakList);
        listView.setAdapter(kontakAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String subject = "";
                String bodyText = "";
                ArrayList<String> email = new ArrayList<>();
                SQLiteDatabase ReadData = dbHelper.getReadableDatabase();
                Cursor cursor = ReadData.rawQuery("SELECT * FROM "+ dbHelper.TABLE,null);
                cursor.moveToFirst();
                if (cursor.moveToFirst()){
                    do{
                        email.add(cursor.getString(2));
                    } while (cursor.moveToNext());
                }
                String emailAdd = email.get(position);

                String mailto = "mailto:"+emailAdd +
                        "?cc=" +
                        "&subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(bodyText);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(Intent.createChooser(emailIntent, "Kirim Email"));
                } catch (ActivityNotFoundException e) {
                    //TODO: Handle case where no email app is available
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = dbHelper.getData("SELECT _id FROM data");
                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while (cursor.moveToNext()){
                    arrID.add(cursor.getInt(0));
                }
                showDialogDelete(arrID.get(position));
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO x
                Intent intentMenu = new Intent(getApplicationContext(), TambahKontak.class);
                startActivity(intentMenu);
            }
        });
    }

    private void getData(){
        SQLiteDatabase ReadData = dbHelper.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM "+ dbHelper.TABLE,null);
        kontakList.clear();
        cursor.moveToFirst();
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String nama = cursor.getString(1);
                String email = cursor.getString(2);
                byte[] image = cursor.getBlob(3);

                kontakList.add(new Kontak(nama, email, image, id));
            } while (cursor.moveToNext());
        }
    }

    private void showDialogDelete(final int idKontak){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(MainActivity.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Yakin ingin menghapus kontak ini?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    dbHelper.deleteData(idKontak);
                    Toast.makeText(getApplicationContext(), " Berhasil dihapus ",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }

                Intent i = new Intent(MainActivity.this, MainActivity.class);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
}

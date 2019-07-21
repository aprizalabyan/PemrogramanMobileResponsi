package com.gmail.aprizalabyan.projectakhir;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

public class ListAdapter extends ArrayAdapter<String> {
    // TODO 1 : Inisialisasi objek yang akan digunakan
    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;

    // TODO 2 : Membuat konstruktor ListAdapter dan memberikan parameter
    public ListAdapter(Activity context, String[] maintitle, String[] subtitle, Integer[] imgid) {
        super(context, R.layout.list_kontak, maintitle);

        // TODO 2.1 : Inisialisasi variabel-variabel dengan parameter pada ListAdapter
        this.context = context;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.imgid = imgid;
    }

    // TODO 3 : Membuat View pada layout mylist
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_kontak, null, true);

        // TODO 3.1 : Mengambil data title, subtitle dan icon dari layout
        TextView titleText = rowView.findViewById(R.id.title);
        ImageView imageView = rowView.findViewById(R.id.icon);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);

        // TODO 3.2 : Menambahkan data pada title, subtitle dan icon berdasarkan posisi dari masing-masing array
        titleText.setText(maintitle[position]);
        //imageView.setImageResource(imgid[position]);
        subtitleText.setText(subtitle[position]);
        return rowView;
    }
}

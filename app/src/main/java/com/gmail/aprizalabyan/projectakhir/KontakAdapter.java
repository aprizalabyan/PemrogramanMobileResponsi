package com.gmail.aprizalabyan.projectakhir;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class KontakAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Kontak> kontakList;

    public KontakAdapter(Context context, int layout, ArrayList<Kontak> kontakList) {
        this.context = context;
        this.layout = layout;
        this.kontakList = kontakList;
    }

    @Override
    public int getCount() {
        return kontakList.size();
    }

    @Override
    public Object getItem(int position) {
        return kontakList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_kontak, null);

            holder.txtNama = row.findViewById(R.id.tvnama);
            holder.txtEmail = row.findViewById(R.id.tvemail);
            holder.imageView = row.findViewById(R.id.tvfoto);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Kontak kontak = kontakList.get(position);

        holder.txtNama.setText(kontak.getNama());
        holder.txtEmail.setText(kontak.getEmail());

        byte[] kontakImage = kontak.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(kontakImage, 0, kontakImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtNama, txtEmail;
    }
}

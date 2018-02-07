package com.example.svnikitin.vkauthtestapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.svnikitin.vkauthtestapp.utils.CacheImageLoader;

/**
 * Created by svnikitin on 07.02.2018.
 */

public class TodoCursorAdapter extends CursorAdapter {

    private final CacheImageLoader imageLoader;

    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        imageLoader = new CacheImageLoader(context,"list",50);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvFirstName = view.findViewById(R.id.first_name);
        TextView tvLastName = view.findViewById(R.id.last_name);
        ImageView imageView  =  view.findViewById(R.id.imageViewIcon);
        String strFirstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
        String strLastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
        String imgUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));
        tvFirstName.setText(strFirstName);
        tvLastName.setText(strLastName);

        //String imgUrl = "https://pp.userapi.com/c627517/v627517398/c3d1/y5efbMusiPo.jpg";

        //imageLoader.DisplayImage(item.get(Catalogue.KEY_THUMB_URL), thumb_image);
        imageLoader.DisplayImage(imgUrl,imageView);

    }
}



package com.cam.trailrace.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cam.trailrace.R;
import com.cam.trailrace.modelo.PistaItem;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolasfor on 16/09/2016.
 */
public class TrackListAdapter extends ArrayAdapter<PistaItem> {

    private ArrayList<PistaItem> objects;
    private Context context;

    public TrackListAdapter(Context context, int textViewResourceId, ArrayList<PistaItem> objects) {

        super(context, textViewResourceId, objects);
        this.objects = objects;
        this.context=context;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        // assign the view we are converting to a local variable

        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        PistaItem i = objects.get(position);

        if (i.isShowNotify()) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView itemTrack = (TextView) v.findViewById(R.id.itemTrack);


            // check to see if each individual textview is null.
            // if not, assign some text!
                itemTrack.setText(i.getPista().getNombre());


        }

        else{
            TextView itemTrack = (TextView) v.findViewById(R.id.itemTrack);

            itemTrack.setText("No hay pistas");

        }

        // the view must be returned to our activity
        return v;

    }

    }

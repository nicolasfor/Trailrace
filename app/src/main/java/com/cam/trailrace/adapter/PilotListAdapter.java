package com.cam.trailrace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cam.trailrace.R;
import com.cam.trailrace.modelo.PilotoItem;
import com.cam.trailrace.modelo.PistaItem;

import java.util.ArrayList;

/**
 * Created by NicolasForero on 3/10/16.
 */
public class PilotListAdapter extends ArrayAdapter<PilotoItem> {

    private ArrayList<PilotoItem> objects;
    private Context context;

    public PilotListAdapter(Context context, int textViewResourceId, ArrayList<PilotoItem> objects) {

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
        PilotoItem i = objects.get(position);

        if (i.isShowNotify()) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView itemTrack = (TextView) v.findViewById(R.id.itemTrack);


            // check to see if each individual textview is null.
            // if not, assign some text!
            itemTrack.setText(i.getPiloto().getNombre());


        }

        else{
            TextView itemTrack = (TextView) v.findViewById(R.id.itemTrack);

            itemTrack.setText("No hay pilotos");

        }

        // the view must be returned to our activity
        return v;

    }

}


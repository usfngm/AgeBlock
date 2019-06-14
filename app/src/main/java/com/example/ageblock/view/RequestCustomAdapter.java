package com.example.ageblock.view;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ageblock.R;
import com.example.ageblock.model.Request;

import java.util.ArrayList;

public class RequestCustomAdapter extends ArrayAdapter<Request> {

    private ArrayList<Request> requests;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView requestType;
        TextView requestStatus;
        TextView requestElderName;

    }

    public RequestCustomAdapter(ArrayList<Request> data, Context context) {
        super(context, R.layout.request_list_item, data);
        this.requests = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Request Request = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.request_list_item, parent, false);
            viewHolder.requestType = (TextView) convertView.findViewById(R.id.requestListItem_typeTV);
            viewHolder.requestStatus = (TextView) convertView.findViewById(R.id.requestListItem_statusTV);
            viewHolder.requestElderName = (TextView) convertView.findViewById(R.id.requestListItem_elderNameTV);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;


        viewHolder.requestType.setText("Type: " + Request.getType());
        viewHolder.requestStatus.setText("Status: " + Request.getStatusText());
        viewHolder.requestElderName.setText("Made By: " + Request.getElder().getName());

        // Return the completed view to render on screen
        return convertView;
    }
}


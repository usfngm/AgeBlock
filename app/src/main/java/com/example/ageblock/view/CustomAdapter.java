package com.example.ageblock.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ageblock.R;
import com.example.ageblock.model.User;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<User> implements View.OnClickListener{

    private ArrayList<User> elders;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
    }

    public CustomAdapter(ArrayList<User> data, Context context) {
        super(context, R.layout.parent_elder_list_item, data);
        this.elders = data;
        this.mContext=context;
    }

    public void notifyChange()
    {
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        User dataModel=(User)object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.parent_elder_list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.parent_elderListItem_usernameTV);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        
        lastPosition = position;

        viewHolder.txtName.setText(user.getName());

        // Return the completed view to render on screen
        return convertView;
    }
}


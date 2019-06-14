package com.example.ageblock.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.ageblock.R;
import com.example.ageblock.api.API;
import com.example.ageblock.api.callbacks.GenericReturnCallback;
import com.example.ageblock.model.Request;
import com.example.ageblock.model.User;
import com.example.ageblock.view.utils.AD;
import com.example.ageblock.view.utils.PD;

import java.util.ArrayList;

public class VolunteerHistoryRequestsFragment extends Fragment {

    Button newRequestBtn;
    String[] requestTypes = new String[]{"Befriending", "Home Care Nurses", "Call Consultant", "Volunteer", "Maintenance Worker", "Chef"};
    int selectedType = 0;

    ListView requestsLV;
    ArrayList<Request> requests;
    RequestCustomAdapter adapter;
    TextView noTV;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_general_requests, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        registerComponents(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {
        PD.get().init(getActivity(), "Please Wait...").show();

        API.getInstance().getHistoryRequests(User.getLoggedUser(getActivity()), new GenericReturnCallback<ArrayList<Request>>() {
            @Override
            public void success(ArrayList<Request> callback) {
                requests = new ArrayList(callback);
                adapter = new RequestCustomAdapter(requests, getActivity());
                requestsLV.setAdapter(adapter);
                if (requests.size() > 0) {
                    requestsLV.setVisibility(View.VISIBLE);
                    noTV.setVisibility(View.GONE);
                } else {
                    requestsLV.setVisibility(View.GONE);
                    noTV.setVisibility(View.VISIBLE);
                }
                PD.get().hide();
            }

            @Override
            public void error(String msg) {
                String dialog_msg = "";
                switch (msg) {
                    case "wrong_creds":
                        dialog_msg = "Wrong email/password combination, please try again.";
                        break;
                    case "no_server":
                        dialog_msg = "Cannot connect to the server. Please try again.";
                        break;
                    default:
                        dialog_msg = msg;
                }

                AD.get().init(getActivity(), dialog_msg);
                PD.get().hide();
            }
        });
    }


    private void registerComponents(View v) {
        noTV = (TextView) v.findViewById(R.id.general_request_noTV);
        requestsLV = (ListView) v.findViewById(R.id.general_request_listview);
        requestsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                        .setCancelable(false)
                        .setTitle("Request Details")
                        .setMessage(requests.get(position).getTextInfoElder())
                        .addButton("Done", Color.WHITE, Color.parseColor("#e38418"), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });
    }
}
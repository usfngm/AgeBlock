package com.example.ageblock.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.ageblock.R;
import com.example.ageblock.api.API;
import com.example.ageblock.api.callbacks.GenericReturnCallback;
import com.example.ageblock.model.Request;
import com.example.ageblock.model.User;
import com.example.ageblock.view.utils.AD;
import com.example.ageblock.view.utils.PD;

import java.util.ArrayList;

public class ElderMyRequestsFragment extends Fragment {

    Button newRequestBtn;
    String[] requestTypes = new String[]{"Befriending", "Home Care Nurses", "Call Consultant", "Volunteer", "Maintenance Worker", "Chef"};
    int selectedType = 0;

    ListView requestsLV;
    ArrayList<Request> requests;
    RequestCustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_elder_requests, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        registerComponents(view);
        registerBtnListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData()
    {
        PD.get().init(getActivity(), "Please Wait...").show();

        API.getInstance().getCurrentRequests(User.getLoggedUser(getActivity()), new GenericReturnCallback<ArrayList<Request>>() {
            @Override
            public void success(ArrayList<Request> callback) {
                requests = new ArrayList(callback);
                adapter = new RequestCustomAdapter(requests, getActivity());
                requestsLV.setAdapter(adapter);
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

    private void registerBtnListener() {
        int sType;
        newRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity());
                builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                builder.setTitle("Select Request Type");
                builder.setSingleChoiceItems(requestTypes, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        //Toast.makeText(getActivity(), "Selected:"+index, Toast.LENGTH_SHORT).show();
                        selectedType = index;
                    }
                });
                builder.addButton("New Request", Color.WHITE, Color.parseColor("#e38418"), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        PD.get().init(getActivity(), "Please Wait...").show();

                        Request r = new Request();
                        r.setType(requestTypes[selectedType]);
                        r.setElder(User.getLoggedUser(getActivity()));
                        r.setElderID(User.getLoggedUser(getActivity()).getUid());
                        r.setParentID(User.getLoggedUser(getActivity()).getElder_parentID());
                        r.setStatus(0);


                        API.getInstance().newRequest(r, new GenericReturnCallback<Request>() {
                            @Override
                            public void success(Request callback) {
                                Toast.makeText(getActivity(), "Request Added", Toast.LENGTH_SHORT).show();
                                PD.get().hide();
                                dialogInterface.dismiss();
                                fetchData();
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
                                dialogInterface.dismiss();
                            }
                        });


                    }
                });
                builder.show();
            }
        });
    }

    private void registerComponents(View v) {
        newRequestBtn = (Button) v.findViewById(R.id.elder_addTaskBtn);
        requestsLV = (ListView) v.findViewById(R.id.elder_task_listview);
    }
}

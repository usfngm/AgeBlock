package com.example.ageblock.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.ageblock.R;
import com.example.ageblock.api.API;
import com.example.ageblock.api.callbacks.GenericReturnCallback;
import com.example.ageblock.model.User;

import java.util.ArrayList;

public class ParentEldersFragment extends Fragment {

    Button addElderBtn;
    ListView eldersLV;

    ArrayList<User> elders;

    ElderCustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_elders, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (User.getLoggedUser(getActivity()).getParent_elders().size() != elders.size()) {
            showData();
            fetchData();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerComponent(view);
        registerBtnListeners();

        showData();
        fetchData();
    }

    private void showData() {
        elders = new ArrayList();

        User current = User.getLoggedUser(getActivity());
        for (int i = 0; i < current.getParent_elders().size(); i++) {
            User u = new User();
            u.setName("Loading...");
            u.setUid(current.getParent_elders().get(i));
            elders.add(u);
        }

        adapter = new ElderCustomAdapter(elders, getActivity());

        eldersLV.setAdapter(adapter);
    }

    private void fetchData() {
        for (int i = 0; i < elders.size(); i++) {
            API.getInstance().getUser(elders.get(i), new GenericReturnCallback<User>() {
                @Override
                public void success(User callback) {
                    for (int i = 0; i < elders.size(); i++) {
                        if (elders.get(i).getUid().equals(callback.getUid())) {
                            elders.set(i, callback);
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }

                @Override
                public void error(String msg) {
                    Log.d("Fetch Elders Err", msg);
                }
            });
        }
    }

    private void registerBtnListeners() {
        addElderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddElderActivity.class);
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    private void registerComponent(View v) {
        addElderBtn = (Button) v.findViewById(R.id.parent_elders_addElderBtn);
        eldersLV = (ListView) v.findViewById(R.id.parent_elders_listview);
    }
}

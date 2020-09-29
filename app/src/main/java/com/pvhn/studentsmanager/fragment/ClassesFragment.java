package com.pvhn.studentsmanager.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pvhn.studentsmanager.MainActivity;
import com.pvhn.studentsmanager.R;
import com.pvhn.studentsmanager.adapter.ShowClassAdapter;
import com.pvhn.studentsmanager.callbackinterface.CallBack;
import com.pvhn.studentsmanager.model.ClassModel;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


public class ClassesFragment extends Fragment implements View.OnClickListener, CallBack {
    private RecyclerView rcvListClass;
    private FloatingActionButton btnAddClass;
    private ShowClassAdapter classAdapter;
    private RealmResults<ClassModel> dataClass;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        rcvListClass = view.findViewById(R.id.rcv_show_class);
        btnAddClass = view.findViewById(R.id.btn_add_class);
        rcvListClass = view.findViewById(R.id.rcv_show_class);
        btnAddClass.setOnClickListener(this);
        setUpRecycler();
        getAllClass();
        return view;
    }

    private void getAllClass() {
        dataClass = realm.where(ClassModel.class).findAll();
        setAdapter(dataClass);
        classAdapter.notifyDataSetChanged();
    }

    private void setAdapter(RealmResults<ClassModel> results) {
        classAdapter = new ShowClassAdapter(results, this, this);
        rcvListClass.setAdapter(classAdapter);
        classAdapter.notifyDataSetChanged();

    }

    private void setUpRecycler() {
        rcvListClass.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rcvListClass.setLayoutManager(layoutManager);
    }

    private void addClass() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_add_class, null);
        final EditText edtClassName = alertLayout.findViewById(R.id.edt_class_name);
        final Button btnAddClassToRealm = alertLayout.findViewById(R.id.btn_add);
        final Button btnCancel = alertLayout.findViewById(R.id.btn_cancel);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("ADD Class");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();
        btnAddClassToRealm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID uuid = UUID.randomUUID();
                String randomUUID = uuid.toString();
                String getClassName = edtClassName.getText().toString();
                ClassModel checkExitsClass = realm.where(ClassModel.class).equalTo("className", getClassName).findFirst();
                if (TextUtils.isEmpty(getClassName)) {
                    Toast.makeText(getActivity(), "Please Enter Class Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (checkExitsClass == null) {
                    ClassModel classModel = new ClassModel();
                    classModel.setClassID(randomUUID);
                    classModel.setClassName(getClassName);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(classModel);
                    realm.commitTransaction();
                    classAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Add class Successful !!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Add class Fail !!", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_class:
                addClass();
                break;
        }
    }

    @Override
    public void ItemsClick(int position) {
        Transition exitTransition = TransitionInflater.from(getContext())
                .inflateTransition(android.R.transition.explode);
        setExitTransition(exitTransition);
        Transition enterTransition = TransitionInflater.from(getContext())
                .inflateTransition(android.R.transition.fade);
        setEnterTransition(enterTransition);
        ((MainActivity) getActivity())
                .showFragmentStudentsManager(dataClass.get(position));
    }

    @Override
    public void ItemsLongClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to Delete ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        realm.beginTransaction();
                        dataClass.deleteFromRealm(position);
                        realm.commitTransaction();
                        Toast.makeText(getActivity(), "Delete Class Successful", Toast.LENGTH_LONG).show();
                        classAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


}
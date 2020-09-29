package com.pvhn.studentsmanager.fragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pvhn.studentsmanager.R;
import com.pvhn.studentsmanager.adapter.ShowStudentsAdapter;
import com.pvhn.studentsmanager.model.ClassModel;
import com.pvhn.studentsmanager.model.StudentModel;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class StudentsFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rcvListStudent;
    private FloatingActionButton btnAddStudent;
    private ShowStudentsAdapter studentsAdapter;
    private RealmResults<StudentModel> dataStudent;
    private String dataClass;
    private Realm realm = Realm.getDefaultInstance();

    public StudentsFragment(ClassModel classModel) {
        dataClass = classModel.getClassID();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);
        btnAddStudent = view.findViewById(R.id.btn_add_student);
        rcvListStudent = view.findViewById(R.id.rcv_show_students);
        btnAddStudent.setOnClickListener(this);
        setUpRecycler();
        getAllStudent();
        return view;
    }

    private void getAllStudent() {
        dataStudent = realm.where(StudentModel.class).findAll();
        setAdapter(dataStudent);
        studentsAdapter.notifyDataSetChanged();
    }

    private void setAdapter(RealmResults<StudentModel> results) {
        studentsAdapter = new ShowStudentsAdapter(results);
        rcvListStudent.setAdapter(studentsAdapter);
        studentsAdapter.notifyDataSetChanged();

    }

    private void setUpRecycler() {
        rcvListStudent.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListStudent.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_student:
                addClass();
                break;
        }
    }

    private void addClass() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_add_students, null);
        final EditText edtStudentName, edtStudentAge, edtStudentSex, edtStudentAddress;
        final Button btnAddStudent, btnCancel;
        edtStudentName = alertLayout.findViewById(R.id.edt_student_name);
        edtStudentAge = alertLayout.findViewById(R.id.edt_student_age);
        edtStudentSex = alertLayout.findViewById(R.id.edt_student_sex);
        edtStudentAddress = alertLayout.findViewById(R.id.edt_student_address);
        btnAddStudent = alertLayout.findViewById(R.id.btn_add_student);
        btnCancel = alertLayout.findViewById(R.id.btn_cancel_student);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("ADD STUDENT");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID uuid = UUID.randomUUID();
                String randomUUID = uuid.toString();

                StudentModel checkExitsStudent = realm.where(StudentModel.class).equalTo("studentID", randomUUID).findFirst();
                if (TextUtils.isEmpty(edtStudentName.getText().toString())) {
                    Toast.makeText(getActivity(), "Please Enter Student Name", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(edtStudentAge.getText().toString())) {
                    Toast.makeText(getActivity(), "Please Enter Student Age", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(edtStudentSex.getText().toString())) {
                    Toast.makeText(getActivity(), "Please Enter Student Sex", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(edtStudentAddress.getText().toString())) {
                    Toast.makeText(getActivity(), "Please Enter Student Address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (checkExitsStudent == null) {
                    StudentModel studentModel = new StudentModel();
                    studentModel.setStudentID(randomUUID);
                    studentModel.setClassID(dataClass);
                    studentModel.setStudentName(edtStudentName.getText().toString());
                    studentModel.setStudentAge(edtStudentAge.getText().toString());
                    studentModel.setStudentSex(edtStudentSex.getText().toString());
                    studentModel.setStudentAddress(edtStudentAddress.getText().toString());
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(studentModel);
                    realm.commitTransaction();
                    studentsAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Add Student Successful !!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Add Student Fail !!", Toast.LENGTH_LONG).show();
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
}
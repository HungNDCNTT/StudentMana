package com.pvhn.studentsmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pvhn.studentsmanager.R;
import com.pvhn.studentsmanager.model.StudentModel;

import io.realm.RealmResults;

public class ShowStudentsAdapter extends RecyclerView.Adapter<ShowStudentsAdapter.HungHolderStudent> {
    private RealmResults<StudentModel> dataStudent;

    public ShowStudentsAdapter(RealmResults<StudentModel> dataStudent) {
        this.dataStudent = dataStudent;
    }

    public class HungHolderStudent extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvStudentAge, tvStudentSex, tvStudentAddress;

        public HungHolderStudent(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_student_name);
            tvStudentAge = itemView.findViewById(R.id.tv_student_age);
            tvStudentSex = itemView.findViewById(R.id.tv_student_sex);
            tvStudentAddress = itemView.findViewById(R.id.tv_student_address);
        }
    }

    @NonNull
    @Override
    public HungHolderStudent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HungHolderStudent(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_rcv_students, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HungHolderStudent holder, int position) {
        final StudentModel studentPosition = dataStudent.get(position);
        holder.tvStudentName.setText(studentPosition.getStudentName());
        holder.tvStudentAge.setText(studentPosition.getStudentAge());
        holder.tvStudentSex.setText(studentPosition.getStudentSex());
        holder.tvStudentAddress.setText(studentPosition.getStudentAddress());
    }

    @Override
    public int getItemCount() {
        return dataStudent.size();
    }
}

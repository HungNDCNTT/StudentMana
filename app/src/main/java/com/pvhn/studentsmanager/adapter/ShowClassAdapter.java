package com.pvhn.studentsmanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pvhn.studentsmanager.R;
import com.pvhn.studentsmanager.callbackinterface.CallBack;
import com.pvhn.studentsmanager.model.ClassModel;
import com.pvhn.studentsmanager.model.StudentModel;

import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ShowClassAdapter extends RecyclerView.Adapter<ShowClassAdapter.HungHolder> {
    private RealmResults<ClassModel> dataClass;
    private CallBack itemsClick;

    public ShowClassAdapter(RealmResults<ClassModel> dataClass, CallBack itemsClick) {
        this.dataClass = dataClass;
        this.itemsClick = itemsClick;
    }



    public class HungHolder extends RecyclerView.ViewHolder {
        TextView tvShowClassName;

        public HungHolder(@NonNull View itemView) {
            super(itemView);
            tvShowClassName = itemView.findViewById(R.id.tv_show_class_name);
        }

    }

    @NonNull
    @Override
    public HungHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HungHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_rcv_class, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HungHolder holder, final int position) {
        final ClassModel classModelPosition = dataClass.get(position);
        holder.tvShowClassName.setText(classModelPosition.getClassName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsClick.ItemsClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("HungND", "size" + dataClass.size());
        return dataClass.size();
    }
}

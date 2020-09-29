package com.pvhn.studentsmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.pvhn.studentsmanager.fragment.ClassesFragment;
import com.pvhn.studentsmanager.fragment.StudentsFragment;
import com.pvhn.studentsmanager.model.ClassModel;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frmFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frmFrag = findViewById(R.id.frame_fragment);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_fragment, new ClassesFragment()).commit();
        getSupportActionBar().setTitle("Classes Manager");
    }

    public void showFragmentStudentsManager(ClassModel classModel) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_fragment,
                new StudentsFragment(classModel));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
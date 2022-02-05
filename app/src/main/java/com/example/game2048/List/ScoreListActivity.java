package com.example.game2048.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.game2048.List.FragmentList3;
import com.example.game2048.List.FragmentList4;
import com.example.game2048.List.FragmentList5;
import com.example.game2048.List.FragmentViewpage;
import com.example.game2048.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreListActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> list;
    private FragmentViewpage adapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        viewPager=findViewById(R.id.viewpage1);
        list = new ArrayList<>();
        list.add(new FragmentList3());
        list.add(new FragmentList4() );
        list.add(new FragmentList5() );
        adapter=new FragmentViewpage(getSupportFragmentManager(),0,list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }


}
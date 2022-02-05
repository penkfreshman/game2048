package com.example.game2048.List;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game2048.Tool.DataForList;
import com.example.game2048.R;
import com.example.game2048.Tool.Tool;

import java.util.ArrayList;
import java.util.List;

public class FragmentList4 extends Fragment {

    private List<Integer> list_score=new ArrayList<Integer>();
    private List<Integer> list_step=new ArrayList<Integer>();
    private RecyclerView recyclerView;
    private  int return_num;
    private  View view;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_list4,container,false);

        readList();

        recyclerView= view.findViewById(R.id.recycleView1);
        DataListAdapter dataListAdapter=new DataListAdapter(getContext(),list_score,list_step);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(dataListAdapter);
        return view;
    }




    @SuppressLint("Range")
    private void readList(){
        DataForList dataForList=new DataForList(getContext(), Tool.List_name,null,1);
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try{
            db = dataForList.getReadableDatabase();
            cursor=db.rawQuery("select * from ScoreList1 order by score desc",null);


            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                int Step = cursor.getInt(cursor.getColumnIndex(Tool.Step_save));
                int Score = cursor.getInt(cursor.getColumnIndex(Tool.Save_Score));
                Log.d("data_list1", Step + "");
                Log.d("data_list", Score + "");

                list_score.add(Score);
                list_step.add(Step);
            }
            Log.d("data_list1",list_step.size()+"");




        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Log.d("data_list1",list_score.size()+"");
            // Log.d("data_list",list.get(0).getStep()+"");
            cursor.close();
            db.close();

        }
    }

}

package com.example.game2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreListActivity extends AppCompatActivity {

    private List<Integer> list_score=new ArrayList<Integer>();
    private List<Integer> list_step=new ArrayList<Integer>();
    private RecyclerView recyclerView;
    private  int return_num;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);

        readList();

        recyclerView=findViewById(R.id.recycleView);
        DataListAdapter dataListAdapter=new DataListAdapter(this,list_score,list_step);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        ((LinearLayoutManager) manager).setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(dataListAdapter);



    }

    @SuppressLint("Range")
    private void readList(){
        DataForList dataForList=new DataForList(this,Tool.List_name,null,1);
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try{
             db = dataForList.getReadableDatabase();
            cursor=db.rawQuery("select * from ScoreList order by score desc",null);


                for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                    int Step = cursor.getInt(cursor.getColumnIndex(Tool.Step_save));
                    int Score = cursor.getInt(cursor.getColumnIndex(Tool.Save_Score));
                    Log.d("data_list1", Step + "");
                    Log.d("data_list", Score + "");
                    Toast.makeText(this,Score+"",Toast.LENGTH_SHORT).show();
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
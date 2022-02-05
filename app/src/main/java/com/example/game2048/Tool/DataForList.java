package com.example.game2048.Tool;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DataForList extends SQLiteOpenHelper {
    private Context context;
    private  String CreaTable="create table ScoreList (" +
            "id integer primary  key autoincrement," +
            "score integer," +
            "Step integer" +
            ")";
    private  String CreaTable1="create table ScoreList1 (" +
            "id integer primary  key autoincrement," +
            "score integer," +
            "Step integer" +
            ")";
    private  String CreaTable2="create table ScoreList2 (" +
            "id integer primary  key autoincrement," +
            "score integer," +
            "Step integer" +
            ")";
    public DataForList(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    public DataForList(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DataForList(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CreaTable);
        sqLiteDatabase.execSQL(CreaTable1);
        sqLiteDatabase.execSQL(CreaTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

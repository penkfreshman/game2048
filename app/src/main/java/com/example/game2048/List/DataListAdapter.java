package com.example.game2048.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game2048.R;

import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {
    private Context context;
    private List<Integer> lists_step;
    private List<Integer> lists_score;
    private int items[]={R.drawable.ic_baseline_filter_1_24,R.drawable.ic_baseline_filter_2_24,R.drawable.ic_baseline_filter_3_24};

    public DataListAdapter(Context context, List<Integer> lists_score,List<Integer> lists_step){

        this.context=context;
        this.lists_score=lists_score;
        this.lists_step=lists_step;
        //  Log.d("recycle", lists.get(0).getStep()+"1");

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewHolder holder;
        View view= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.item_score.setText(context.getString(R.string.Score_String)+":"+lists_score.get(position)+"");
        holder.item_step.setText(context.getString(R.string.Step)+":"+lists_step.get(position)+"");
        if(position<3){
            holder.item_im.setImageResource(items[position]);
        }else{
            holder.item_im.setBackground(context.getDrawable(R.color.theme2048));
        }
    }

    @Override
    public int getItemCount() {
        return lists_score.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView item_im;
        public TextView item_step;
        public TextView item_score;
        public ViewHolder( View itemView) {
            super(itemView);
            item_im=itemView.findViewById(R.id.list_item_iv);
            item_score=itemView.findViewById(R.id.list_item_score);
            item_step=itemView.findViewById(R.id.list_item_step);
            Log.e("1",""+lists_score.get(0));

        }
    }
}

package com.example.carnote.historylist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carnote.R;
import com.example.carnote.model.TankUpRecord;

import java.text.DateFormat;
import java.util.List;

//Adapter do listy historii zmian

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViweHolder> {

    private Context context;
    private List<TankUpRecord> tankList;
    private TankUpRecord tankUpRecord;
    private Drawable drawable;

    public HistoryAdapter(Context context, List<TankUpRecord> tankList) {
        this.context = context;
        this.tankList = tankList;

    }

    @NonNull
    @Override
    public ViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_history_item,null);
        return new ViweHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViweHolder holder, @SuppressLint("RecyclerView") final int position) {

        tankUpRecord = tankList.get(position);
        drawable = context.getResources().getDrawable(R.drawable.ic_new_tank);
        holder.activityImage_view.setImageDrawable(drawable);
        DateFormat dateFormat = DateFormat.getInstance();
        holder.leftLabelTopTextView.setText(dateFormat.format(tankUpRecord.getTankUpDate()));
        holder.rightLabelTopTextView.setText(tankUpRecord.getMilage().toString()+" KM");
        holder.leftLabelBottomTextView.setText(tankUpRecord.getLiters()+" L");
        holder.rightLabelBottomTextView.setText(tankUpRecord.getCostInPln()+" Zł");
        holder.trashImage_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tankList.remove(position);
                HistoryAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tankList == null) {
            return 0;
        }
        else {
            return tankList.size();
        }
    }

    public class ViweHolder extends RecyclerView.ViewHolder{

        protected TextView leftLabelBottomTextView;
        protected TextView leftLabelTopTextView;
        protected TextView rightLabelBottomTextView;
        protected TextView rightLabelTopTextView;
        protected ImageView trashImage_view;
        protected ImageView activityImage_view;

        public ViweHolder(@NonNull View itemView) {
            super(itemView);
            this.activityImage_view = (ImageView) itemView.findViewById(R.id.activityImageView);
            this.trashImage_view = (ImageView) itemView.findViewById(R.id.trashImageView);

            this.leftLabelBottomTextView = (TextView) itemView.findViewById(R.id.left_label_bottom);
            this.leftLabelTopTextView = (TextView) itemView.findViewById(R.id.left_label_top);
            this.rightLabelBottomTextView = (TextView) itemView.findViewById(R.id.right_label_bottom);
            this.rightLabelTopTextView = (TextView) itemView.findViewById(R.id.right_label_top);
        }
    }
}

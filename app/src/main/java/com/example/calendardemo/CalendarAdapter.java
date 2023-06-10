package com.example.calendardemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder2> {

    private final ArrayList<String> dayOfMonth;
    private final OnItemListener onItemListener;

    private final Context context;

    public CalendarAdapter(ArrayList<String> dayOfMonth, OnItemListener onItemListener, Context context) {
        this.dayOfMonth = dayOfMonth;
        this.onItemListener = onItemListener;
        this.context = context;
    }


    @NonNull
    @Override
    public CalendarViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder2(view, onItemListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder2 holder, int position) {
        holder.dayOfMonth.setText(dayOfMonth.get(position));

    }

    @Override
    public int getItemCount() {
        return dayOfMonth.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }

    static class CalendarViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dayOfMonth;
        private final CalendarAdapter.OnItemListener onItemListener;

        private Context context = null;

        public CalendarViewHolder2(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, Context context) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            this.onItemListener = onItemListener;
            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
        }


    }
}

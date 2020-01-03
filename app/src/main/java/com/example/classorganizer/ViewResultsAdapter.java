package com.example.classorganizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewResultsAdapter extends RecyclerView.Adapter<ViewResultsAdapter.ResultViewHolder>{

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, ArrayList<String>>> data;
    private Context context;

    public ViewResultsAdapter(Context context, ArrayList<HashMap<String, ArrayList<String>>> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.result_item, parent, false);
        ViewResultsAdapter.ResultViewHolder holder = new ViewResultsAdapter.ResultViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        HashMap<String, ArrayList<String>> resultData = data.get(position);

        String result = resultData.get(context.getResources().getString(R.string.result)).get(0);
        holder.textResult.setText(result);

        String date = resultData.get(context.getResources().getString(R.string.added_date)).get(0);
        holder.textDate.setText(date);

        String description = resultData.get(context.getResources().getString(R.string.description)).get(0);
        holder.textDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder{
        private EditText textResult, textDate, textDescription;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textResult = itemView.findViewById(R.id.resultValue);
            textDate = itemView.findViewById(R.id.dateValue);
            textDescription = itemView.findViewById(R.id.descriptionValue);
        }
    }
}

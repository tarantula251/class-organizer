package com.example.classorganizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;

import model.data.Result;

public class ViewResultsAdapter extends RecyclerView.Adapter<ViewResultsAdapter.ResultViewHolder>{

    private LayoutInflater layoutInflater;
    private ArrayList<Result> results;
    private Context context;

    ViewResultsAdapter(ArrayList<Result> results, Context context)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.results = results;
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

        Result result = results.get(position);

        holder.resultId.setText(Integer.toString(result.getId()));

        String score = Double.toString(result.getScore());
        holder.textResult.setText(score);

        String title = result.getTitle();
        holder.textTitle.setText(title);

        String date = result.getLastUpdated().format(new DateTimeFormatterBuilder().appendPattern("dd-MM-YYYY HH:mm").toFormatter());
        holder.textDate.setText(date);

        String description = result.getNote();
        holder.textDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder{
        private EditText textResult, textTitle, textDate, textDescription, resultId;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textResult = itemView.findViewById(R.id.resultValue);
            textTitle = itemView.findViewById(R.id.titleValue);
            textDate = itemView.findViewById(R.id.dateValue);
            textDescription = itemView.findViewById(R.id.descriptionValue);
            resultId = itemView.findViewById(R.id.resultId);
        }
    }
}

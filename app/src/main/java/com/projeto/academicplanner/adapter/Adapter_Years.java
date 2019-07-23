package com.projeto.academicplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.model.Years;

import java.util.List;

public class Adapter_Years extends RecyclerView.Adapter<Adapter_Years.MyViewHolder>  {

    private List<Years> years;
    private static Adapter_Years.ClickListener clickListener;
    private Context context;

    public Adapter_Years(List<Years> years, Context context) {

        this.years = years;
        this.context = context;

    }

    public List<Years> getYears() {
        return years;
    }

    @NonNull
    @Override
    public Adapter_Years.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_years, parent, false);

        Adapter_Years.MyViewHolder showList = new Adapter_Years.MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Years myYears = years.get(position);
        holder.yearName.setText(myYears.getYearName());
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_gray_24dp);

    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView yearName;
        ImageView imageDelete1;
        Adapter_Years adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_Years ref) {
            super(itemView);

            yearName = itemView.findViewById(R.id.yearName);
            imageDelete1 = itemView.findViewById(R.id.imageDelete);
            adapterRef = ref;
            itemView.setOnClickListener(this);

        }

        public void onClick(View v) {

            clickListener.onItemClick(adapterRef, v, getAdapterPosition());

        }

    }

    public interface ClickListener {
        void onItemClick(Adapter_Years adapter_years, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_Years.clickListener = clickListener;
    }

}

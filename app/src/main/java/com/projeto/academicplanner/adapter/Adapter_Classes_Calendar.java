package com.projeto.academicplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.model.Classes;

import java.util.List;

public class Adapter_Classes_Calendar extends RecyclerView.Adapter<Adapter_Classes_Calendar.MyViewHolder>{

    private List<Classes> classes;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_Classes_Calendar(List<Classes> classes, Context context) {

        this.classes = classes;
        this.context = context;

    }

    public List<Classes> getClasses() {
        return classes;
    }

    @NonNull
    @Override
    public Adapter_Classes_Calendar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_events_calendar, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Classes classe = classes.get(position);
        holder.classSubject1.setText(classe.getSubject());
        holder.classNameDiscipline1.setText(classe.getNameDiscipline());
        holder.classNameCourse1.setText(classe.getNameCourse());
        holder.classNameUniversity1.setText(classe.getNameUniversity());
        holder.classDate1.setText(classe.getClassDate());
        holder.classTime1.setText(classe.getClassTime());
        holder.classTimeDuration1.setText(classe.getTimeDuration());
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView classSubject1;
        TextView classNameDiscipline1;
        TextView classNameCourse1;
        TextView classNameUniversity1;
        TextView classDate1;
        TextView classTime1;
        TextView classTimeDuration1;
        Adapter_Classes_Calendar adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_Classes_Calendar ref) {
            super(itemView);

            classSubject1 = itemView.findViewById(R.id.classSubject);
            classNameDiscipline1 = itemView.findViewById(R.id.classNameDiscipline);
            classNameCourse1 = itemView.findViewById(R.id.classNameCourse);
            classNameUniversity1 = itemView.findViewById(R.id.classNameUniversity);
            classDate1 = itemView.findViewById(R.id.classDate);
            classTime1 = itemView.findViewById(R.id.classTime);
            classTimeDuration1 = itemView.findViewById(R.id.classTimeDuration);
            adapterRef = ref;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void onClick(View v) {

            clickListener.onItemClick(adapterRef, v, getAdapterPosition());

        }
        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(adapterRef, v, getAdapterPosition());
            return false;
        }

    }

    public interface ClickListener {
        void onItemClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position);
        void onItemLongClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_Classes_Calendar.clickListener = clickListener;
    }

}

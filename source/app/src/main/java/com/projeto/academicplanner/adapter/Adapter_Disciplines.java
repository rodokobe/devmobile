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
import com.projeto.academicplanner.model.Discipline;

import java.util.List;

public class Adapter_Disciplines extends RecyclerView.Adapter<Adapter_Disciplines.MyViewHolder> {

    private List<Discipline> disciplines;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_Disciplines(List<Discipline> disciplines, Context context) {

        this.disciplines = disciplines;
        this.context = context;

    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    @NonNull
    @Override
    public Adapter_Disciplines.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_disciplines, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Discipline discipline = disciplines.get(position);
        holder.nameDiscipline1.setText(discipline.getDisciplineName());
        holder.acronDiscipline1.setText(discipline.getAcronymDiscipline());
        holder.disciplineYear1.setText(discipline.getDisciplineYearName());
        holder.disciplineSemester1.setText(discipline.getDisciplineSemester());
        holder.nameUniversity1.setText(discipline.getUniversityName());
        holder.nameCourse1.setText(discipline.getCourseName());
        holder.disciplineDuplicate1.setText("duplicate?");
        holder.imageEdit1.setImageResource(R.drawable.ic_edit_gray_24dp);
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_gray_24dp);

    }

    @Override
    public int getItemCount() {
        return disciplines.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameDiscipline1;
        TextView acronDiscipline1;
        TextView disciplineYear1;
        TextView disciplineSemester1;
        TextView nameUniversity1;
        TextView nameCourse1;
        TextView disciplineDuplicate1;
        ImageView imageEdit1;
        ImageView imageDelete1;
        Adapter_Disciplines adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_Disciplines ref) {
            super(itemView);

            nameDiscipline1 = itemView.findViewById(R.id.nameDiscipline);
            acronDiscipline1 = itemView.findViewById(R.id.acronDiscipline);
            disciplineYear1 = itemView.findViewById(R.id.disciplineYear);
            disciplineSemester1 = itemView.findViewById(R.id.disciplineSemester);
            nameUniversity1 = itemView.findViewById(R.id.nameUniversity);
            nameCourse1 = itemView.findViewById(R.id.nameCourse);
            disciplineDuplicate1 = itemView.findViewById(R.id.nameCourse);
            imageEdit1 = itemView.findViewById(R.id.imageEdit);
            imageDelete1 = itemView.findViewById(R.id.imageDelete);
            adapterRef = ref;
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

            clickListener.onItemClick(adapterRef, v, getAdapterPosition());

        }

    }

    public interface ClickListener {
        void onItemClick(Adapter_Disciplines adapter_disciplines, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_Disciplines.clickListener = clickListener;
    }

}

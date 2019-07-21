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
import com.projeto.academicplanner.model.Student;

import java.util.List;

public class Adapter_Students_Disciplines extends RecyclerView.Adapter<Adapter_Students_Disciplines.MyViewHolder> {

    private List<Student> students;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_Students_Disciplines(List<Student> students, Context context) {

        this.students = students;
        this.context = context;

    }

    public List<Student> getStudents() {
        return students;
    }

    @NonNull
    @Override
    public Adapter_Students_Disciplines.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_students_disciplines, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Student student = students.get(position);
        holder.nameDiscipline1.setText(student.getDisciplineName());
        holder.yearDiscipline1.setText(student.getYearName());
        holder.semesterDiscipline1.setText(student.getSemester());
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_white_24dp);

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameDiscipline1;
        TextView yearDiscipline1;
        TextView semesterDiscipline1;
        ImageView imageDelete1;
        Adapter_Students_Disciplines adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_Students_Disciplines ref) {
            super(itemView);

            nameDiscipline1 = itemView.findViewById(R.id.nameDiscipline);
            yearDiscipline1 = itemView.findViewById(R.id.yearDiscipline);
            semesterDiscipline1 = itemView.findViewById(R.id.semesterDiscipline);
            imageDelete1 = itemView.findViewById(R.id.imageDelete);
            adapterRef = ref;
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

            clickListener.onItemClick(adapterRef, v, getAdapterPosition());

        }

    }

    public interface ClickListener {
        void onItemClick(Adapter_Students_Disciplines adapter_students_disciplines, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_Students_Disciplines.clickListener = clickListener;
    }

}

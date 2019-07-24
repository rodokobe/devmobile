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
import com.projeto.academicplanner.model.Classes;

import java.util.List;

public class Adapter_Classes extends RecyclerView.Adapter<Adapter_Classes.MyViewHolder> {

    private List<Classes> classes;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_Classes(List<Classes> classes, Context context) {

        this.classes = classes;
        this.context = context;

    }

    public List<Classes> getClasses() {
        return classes;
    }

    @NonNull
    @Override
    public Adapter_Classes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_classes, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Classes thisClass = classes.get(position);

        holder.nameUniversity.setText(thisClass.getNameUniversity());
        holder.nameCourse.setText(thisClass.getNameCourse());
        holder.nameDiscipline.setText(thisClass.getNameDiscipline());
        holder.subjectClass.setText(thisClass.getSubject());
        holder.timeClass.setText(thisClass.getClassTime().toString());
        holder.classroomClass.setText(thisClass.getClassroom());

        holder.imageEdit.setImageResource(R.drawable.ic_edit_gray_24dp);
        holder.imageDelete.setImageResource(R.drawable.ic_delete_gray_24dp);

    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameUniversity;
        TextView nameCourse;
        TextView nameDiscipline;
        TextView subjectClass;
        TextView timeClass;
        TextView classroomClass;

        Adapter_Classes adapterRef;

        ImageView imageEdit;
        ImageView imageDelete;


        public MyViewHolder(@NonNull View itemView, Adapter_Classes ref) {
            super(itemView);

            nameUniversity = itemView.findViewById(R.id.nameUniversity);
            nameCourse = itemView.findViewById(R.id.nameCourse);
            nameDiscipline = itemView.findViewById(R.id.nameDiscipline);
            subjectClass = itemView.findViewById(R.id.subjectClass);
            timeClass = itemView.findViewById(R.id.timeClass);
            classroomClass = itemView.findViewById(R.id.classroomClass);
            imageEdit = itemView.findViewById(R.id.imageEdit);
            imageDelete = itemView.findViewById(R.id.imageDelete);

            adapterRef = ref;

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

            clickListener.onItemClick(adapterRef, v, getAdapterPosition());

        }

    }

    public interface ClickListener {
        void onItemClick(Adapter_Classes adapter_classes, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_Classes.clickListener = clickListener;
    }

}

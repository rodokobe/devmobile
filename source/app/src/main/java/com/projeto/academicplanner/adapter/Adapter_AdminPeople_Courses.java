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
import com.projeto.academicplanner.model.Course;

import java.util.List;

public class Adapter_AdminPeople_Courses extends RecyclerView.Adapter<Adapter_AdminPeople_Courses.MyViewHolder> {

    private List<Course> courses;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_AdminPeople_Courses(List<Course> courses, Context context) {

        this.courses = courses;
        this.context = context;

    }

    public List<Course> getCourses() {
        return courses;
    }

    @NonNull
    @Override
    public Adapter_AdminPeople_Courses.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_admin_people_courses, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Course course = courses.get(position);

        holder.nameCourse1.setText(course.getCourseName());
        holder.nameUniversity1.setText(course.getUniversityName());
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_gray_24dp);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameCourse1;
        TextView nameUniversity1;
        ImageView imageDelete1;
        Adapter_AdminPeople_Courses adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_AdminPeople_Courses ref) {
            super(itemView);

            nameCourse1 = itemView.findViewById(R.id.nameCourse);
            nameUniversity1 = itemView.findViewById(R.id.nameUniversity);
            imageDelete1 = itemView.findViewById(R.id.imageDelete);
            adapterRef = ref;
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

            clickListener.onItemClick(adapterRef, v, getAdapterPosition());

        }

    }

    public interface ClickListener {
        void onItemClick(Adapter_AdminPeople_Courses adapter_adminPeople_courses, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_AdminPeople_Courses.clickListener = clickListener;
    }

}

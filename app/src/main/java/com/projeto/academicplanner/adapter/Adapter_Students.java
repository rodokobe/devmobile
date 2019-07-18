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

public class Adapter_Students extends RecyclerView.Adapter<Adapter_Students.MyViewHolder> {

    private List<Student> students;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_Students(List<Student> students, Context context) {

        this.students = students;
        this.context = context;

    }

    public List<Student> getStudents() {
        return students;
    }

    @NonNull
    @Override
    public Adapter_Students.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_students, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Student student = students.get(position);

        String firstname = student.getStudentFirstName();
        String lastname = student.getStudentLastName();
        String name = firstname + " " + lastname;

        holder.studentName.setText(name);
        holder.studentEmail1.setText(student.getStudentEmail());
        holder.studentDelegate1.setText(student.getStudentDelegate());
        holder.imageEdit1.setImageResource(R.drawable.ic_edit_white_24dp);
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_white_24dp);

    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView studentName;
        TextView studentEmail1;
        TextView studentDelegate1;
        ImageView imageEdit1;
        ImageView imageDelete1;
        Adapter_Students adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_Students ref) {
            super(itemView);



            studentName = itemView.findViewById(R.id.studentName);
            studentEmail1 = itemView.findViewById(R.id.studentEmail);
            studentDelegate1 = itemView.findViewById(R.id.studentDelegate);

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
        void onItemClick(Adapter_Students adapter_students, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_Students.clickListener = clickListener;
    }

}

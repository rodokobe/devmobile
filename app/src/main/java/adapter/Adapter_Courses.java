package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;

import java.util.List;

import model.Course;

public class Adapter_Courses extends RecyclerView.Adapter<Adapter_Courses.MyViewHolder> {

    private List<Course> courses;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_Courses(List<Course> courses, Context context) {

        this.courses = courses;
        this.context = context;

    }

    public List<Course> getCourses() {
        return courses;
    }

    @NonNull
    @Override
    public Adapter_Courses.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycle_courses, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Course course = courses.get(position);
        holder.nameUniversity1.setText(course.getUniversityName());
        holder.nameCourse1.setText(course.getCourseName());
        holder.acronCourse1.setText(course.getAcronymCourse());
        holder.imageEdit1.setImageResource(R.drawable.ic_edit_white_24dp);
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_white_24dp);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameUniversity1;
        TextView nameCourse1;
        TextView acronCourse1;
        ImageView imageEdit1;
        ImageView imageDelete1;
        Adapter_Courses adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_Courses ref) {
            super(itemView);

            nameUniversity1 = itemView.findViewById(R.id.nameUniversity);
            nameCourse1 = itemView.findViewById(R.id.nameCourse);
            acronCourse1 = itemView.findViewById(R.id.acronCourse);
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
        void onItemClick(Adapter_Courses adapter_courses, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_Courses.clickListener = clickListener;
    }

}

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
import com.projeto.academicplanner.model.University;

import java.util.List;

public class Adapter_Universities extends RecyclerView.Adapter<Adapter_Universities.MyViewHolder> {

    private List<University> universities;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_Universities(List<University> universities, Context context) {

        this.universities = universities;
        this.context = context;

    }

    public List<University> getUniversities() {
        return universities;
    }

    @NonNull
    @Override
    public Adapter_Universities.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_universities, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        University university = universities.get(position);
        holder.nameUni1.setText(university.getUniversityName());
        holder.acronUni1.setText(university.getUniversityAcronym());
        holder.imageEdit1.setImageResource(R.drawable.ic_edit_gray_24dp);
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_gray_24dp);

    }

    @Override
    public int getItemCount() {
        return universities.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameUni1;
        TextView acronUni1;
        ImageView imageEdit1;
        ImageView imageDelete1;
        Adapter_Universities adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_Universities ref) {
            super(itemView);

            nameUni1 = itemView.findViewById(R.id.nameUni);
            acronUni1 = itemView.findViewById(R.id.acronUni);
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
        void onItemClick(Adapter_Universities adapter_universities, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_Universities.clickListener = clickListener;
    }

}

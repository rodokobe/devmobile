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
import com.projeto.academicplanner.model.AdminPeople;

import java.util.List;

public class Adapter_AdminPeople extends RecyclerView.Adapter<Adapter_AdminPeople.MyViewHolder> {

    private List<AdminPeople> adminPeopleS;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_AdminPeople(List<AdminPeople> adminPeopleS, Context context) {

        this.adminPeopleS = adminPeopleS;
        this.context = context;

    }

    public List<AdminPeople> getAdminPeople() {
        return adminPeopleS;
    }

    @NonNull
    @Override
    public Adapter_AdminPeople.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_admin_people, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AdminPeople adminPeople = adminPeopleS.get(position);
        holder.adminPeopleFirstName1.setText(adminPeople.getAdminPeopleFirstName());
        holder.adminPeopleLastName1.setText(adminPeople.getAdminPeopleLastName());
        holder.adminPeopleEmail1.setText(adminPeople.getAdminPeopleEmail());
        holder.imageEdit1.setImageResource(R.drawable.ic_edit_white_24dp);
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_white_24dp);

    }

    @Override
    public int getItemCount() {
        return adminPeopleS.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView adminPeopleFirstName1;
        TextView adminPeopleLastName1;
        TextView adminPeopleEmail1;
        ImageView imageEdit1;
        ImageView imageDelete1;
        Adapter_AdminPeople adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_AdminPeople ref) {
            super(itemView);

            adminPeopleFirstName1 = itemView.findViewById(R.id.adminPeopleFirstName);
            adminPeopleLastName1 = itemView.findViewById(R.id.adminPeopleLastName);
            adminPeopleEmail1 = itemView.findViewById(R.id.adminPeopleEmail);

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
        void onItemClick(Adapter_AdminPeople adapter_adminPeople, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_AdminPeople.clickListener = clickListener;
    }

}
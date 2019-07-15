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
import com.projeto.academicplanner.model.EventType;

import java.util.List;

public class Adapter_EventsType extends RecyclerView.Adapter<Adapter_EventsType.MyViewHolder> {

    private List<EventType> eventsType;
    private static ClickListener clickListener;
    private Context context;

    public Adapter_EventsType(List<EventType> eventsType, Context context) {

        this.eventsType = eventsType;
        this.context = context;

    }

    public List<EventType> getEventsType() {
        return eventsType;
    }

    @NonNull
    @Override
    public Adapter_EventsType.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler_events_type, parent, false);

        MyViewHolder showList = new MyViewHolder(itemList, this);
        return showList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        EventType eventType = eventsType.get(position);
        holder.eventTypeName.setText(eventType.getEventTypeName());
        holder.eventTypeDescription.setText(eventType.getEventTypeDescription());
        holder.imageEdit1.setImageResource(R.drawable.ic_edit_white_24dp);
        holder.imageDelete1.setImageResource(R.drawable.ic_delete_white_24dp);

    }

    @Override
    public int getItemCount() {
        return eventsType.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventTypeName;
        TextView eventTypeDescription;
        ImageView imageEdit1;
        ImageView imageDelete1;
        Adapter_EventsType adapterRef;

        public MyViewHolder(@NonNull View itemView, Adapter_EventsType ref) {
            super(itemView);

            eventTypeName = itemView.findViewById(R.id.nameUni);
            eventTypeDescription = itemView.findViewById(R.id.acronUni);
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
        void onItemClick(Adapter_EventsType adapter_universities, View v, int position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Adapter_EventsType.clickListener = clickListener;
    }

}

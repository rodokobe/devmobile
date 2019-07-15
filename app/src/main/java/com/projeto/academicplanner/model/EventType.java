package com.projeto.academicplanner.model;

import com.google.firebase.database.DatabaseReference;
import com.projeto.academicplanner.helper.ConfigFirebase;

public class EventType {

    private String idUser;
    private String idEventType;
    private String eventTypeName;
    private String eventTypeDescription;

    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();

    public EventType() {

        DatabaseReference eventTypeRef = firebaseRef
                .child("eventType");
        setIdEventType(eventTypeRef.push().getKey());

    }

    public void save() {

        DatabaseReference eventTypeRef = firebaseRef
                .child("eventType")
                .child(getIdUser())
                .child(getIdEventType());
        eventTypeRef.setValue(this);

    }

    public void update(EventType objectToUpdate) {

        DatabaseReference eventTypeRef = firebaseRef
                .child("eventType")
                .child(getIdUser())
                .child(getIdEventType());
        eventTypeRef.setValue(objectToUpdate);

    }

    public void delete() {

        DatabaseReference eventTypeRef = firebaseRef
                .child("eventType")
                .child(getIdUser())
                .child(getIdEventType());
        eventTypeRef.removeValue();
    }

    /*public void recovery(String idUserLoged, final List<EventType> eventsType, final Adapter_EventsType adapter) {

        DatabaseReference eventTypeRef = firebaseRef
                .child("eventsType")
                .child(idUserLoged);

        eventTypeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventsType.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    eventsType.add(ds.getValue(EventType.class));

                }

                //put the item added to the top
                Collections.reverse(eventsType);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }*/

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

    public String getIdEventType() {
        return idEventType;
    }

    public void setIdEventType(String idEventType) {
        this.idEventType = idEventType;
    }

    public String getEventTypeDescription() { return eventTypeDescription; }

    public void setEventTypeDescription(String eventTypeDescription) { this.eventTypeDescription = eventTypeDescription; }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
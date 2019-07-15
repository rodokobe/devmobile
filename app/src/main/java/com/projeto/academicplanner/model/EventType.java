package com.projeto.academicplanner.model;

public class EventType {

    private String idEventType;
    private String typeEventType;
    private String eventTypeName;

    public EventType() {

    }

    public EventType(String idEventTypeP, String typeEventTypeP, String eventTypeNameP) {

        this.idEventType = idEventTypeP;
        this.typeEventType = typeEventTypeP;
        this.eventTypeName = eventTypeNameP;

    }

    public String getIdEventType() {
        return idEventType;
    }

    public void setIdEventType(String idEventType) {
        this.idEventType = idEventType;
    }

    public String getTypeEventType() {
        return typeEventType;
    }

    public void setTypeEventType(String typeEventType) {
        this.typeEventType = typeEventType;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
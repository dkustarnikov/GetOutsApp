package com.mitya.getouts;

import android.util.Log;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

public class Event extends RealmObject {
    @PrimaryKey
//    private ObjectId _id = new ObjectId();
    private ObjectId _id;
    private String name = "Event";

    @Required
    private String status = EventStatus.Created.name();
    private String created_by;
    private Double latitude;
    private Double longitude;
    private Date created_on;
    private String event_date;

    public Event(ObjectId _id, String name, String status, String created_by, Double latitude, Double longitude, Date created_on, String event_date) {
        this._id = _id;
        this.name = name;
        this.status = status;
        this.created_by = created_by;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_on = created_on;
        this.event_date = event_date;
    }

    public Event() {}

    public void setStatus(EventStatus status) {
        this.status = status.name();
    }

    public String getStatus() {
        return this.status;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event(String _name) {
        this.name = _name;
    }



    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", created_by='" + created_by + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", created_on='" + created_on + '\'' +
                ", event_date='" + event_date + '\'' +
                '}';
    }

    public Document eventToDoc(){

        Document doc = new Document();

        doc.put("_id", this.get_id());
        doc.put("name",this.getName());
        doc.put("status", this.getStatus());
        doc.put("created_by", this.getCreated_by());
        doc.put("latitude", this.getLatitude());
        doc.put("longitude", this.getLongitude());
        doc.put("created_on", this.getCreated_on());
        doc.put("event_date", this.getEvent_date());


        Log.e("eventToDOC()Result", doc.toJson());
        return doc;
    }
}


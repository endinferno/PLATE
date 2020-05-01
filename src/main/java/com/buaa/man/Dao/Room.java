package com.buaa.man.Dao;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "room")
public class Room {
    public String id;
    public String rid;
    public String roomName;
    public String photoImage;
    public String describe;
    //public Type type;
    public int type;
    public String owner;
    public long openTime;
    public String openStatus;
    @CreatedDate
    public Date createDate;

}

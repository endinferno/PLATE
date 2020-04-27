package com.buaa.man.Dao;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "room")
public class Room {
    public String id;
    public String roomName;
    public String photoImage;
    public String describe;
    public String type;
    public String owner;
    @CreatedDate
    public Date createDate;
}

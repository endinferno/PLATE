package com.buaa.man.Dao;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Queue;

@Document(collection = "user")
public class User {
    public String id;
    public String uid;
    public String photoImage;
    public String email;
    public String password;
    public String nickName;
    public List<String> favoriteRoom;
    public List<String> historyRoom;
    // 注册验证
    public String validateRegistration;
    // 找回验证
    public String validateRetrieve;
    @CreatedDate
    public Date registerDate;
}

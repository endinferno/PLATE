package com.buaa.man.Service;

import com.buaa.man.Dao.Redis;
import com.buaa.man.Dao.Room;
import com.buaa.man.Dao.UploadFile;
import com.buaa.man.Dao.User;
import com.buaa.man.Util.AesEncryptUtils;
import com.buaa.man.Util.StringUtil;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
	private MongoTemplate mongoTemplate;

	@Autowired
	public UserService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

    public User register(User user) throws Exception {
        try {
            Query email1 = new Query(Criteria.where("email").is(user.email));
            User user1 = mongoTemplate.findOne(email1, User.class);
            if (user1 != null) {
                user1.password = AesEncryptUtils.encrypt(user.password);
                user1.nickName = user.nickName;
                user1.photoImage = user.photoImage;
                user1.favoriteRoom = new ArrayList<>(10);
                user1.historyRoom = new ArrayList<>(10);
                mongoTemplate.save(user1);
            }
        } catch (Exception e) {
            throw new Exception("邮箱已被注册");
        }
        return user;
    }

    public User login(String email, String psw) {
        String encrypt = AesEncryptUtils.encrypt(psw);
        Query q1 = new Query(Criteria.where("email").is(email).and("password").is(encrypt));
        return mongoTemplate.findOne(q1, User.class);
    }

	public void updateRegisterCode(String email, String code) {
		Query email1 = new Query(Criteria.where("email").is(email));
		User user = mongoTemplate.findOne(email1, User.class);
		if (user != null) {
			user.validateRegistration = code;
			mongoTemplate.save(user);
		} else {
            User user1 = new User();
            user1.uid = UUID.randomUUID().toString();
            user1.validateRegistration = code;
            user1.email = email;
            mongoTemplate.insert(user1);
        }
	}

    public String getRegistrationCode(String email) {
        Query email1 = new Query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(email1, User.class);
        if (user != null) {
            return user.validateRegistration;
        }
        return "";
    }

    public boolean getRegistrationStatus(String email) {
        Query email1 = new Query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(email1, User.class);
        return user != null && !StringUtil.isNullOrEmpty(user.password);
    }

    public User getUserByUid(String uid) {
        Query query = new Query(Criteria.where("uid").is(uid));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            User user1 = new User();
            user1.email = user.email;
            user1.photoImage = user.photoImage;
            user1.nickName = user.nickName;
            user1.uid = user.uid;
            user1.favoriteRoom = user.favoriteRoom;
            user1.historyRoom = user.historyRoom;
            user1.birthday = user.birthday;
            user1.gender = user.gender;
            user1.signature = user.signature;
            return user1;
        }
        return null;
    }

    public String getRetrieveCode(String email) {
        Query email1 = new Query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(email1, User.class);
        if (user != null) {
            return user.validateRetrieve;
        }
        return "";
    }

    public void updatePsw(User user) {
        Query email1 = new Query(Criteria.where("email").is(user.email));
        User user1 = mongoTemplate.findOne(email1, User.class);
        if (user1 != null) {
            user1.password = AesEncryptUtils.encrypt(user.password);
            mongoTemplate.save(user1);
        }
    }

    public void updateRetrieveCode(String email, String code) {
        Query email1 = new Query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(email1, User.class);
        if (user != null) {
            user.validateRetrieve = code;
            mongoTemplate.save(user);
        }
    }

    public boolean updateFavoriteRoom(String uid, String rid) {
        Query query1 = new Query(Criteria.where("uid").is(uid));
        User user = mongoTemplate.findOne(query1,User.class);
        Query query2 = new Query(Criteria.where("rid").is(rid));
        Room room = mongoTemplate.findOne(query2,Room.class);
        if (user != null && room != null) {
            Map<String, Long> collect = user.favoriteRoom
                .stream()
                .map(entry -> {return Pair.of(entry.rid, entry.timeStamp);})
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
            collect.put(rid, (new Date()).getTime());

            user.favoriteRoom = collect.entrySet().stream().map(entry ->{
                Redis redis = new Redis();
                redis.rid = entry.getKey();
                redis.timeStamp = entry.getValue();
                return redis;
            }).sorted(Comparator.comparingLong((Redis o) -> o.timeStamp).reversed()).collect(Collectors.toList());
            mongoTemplate.save(user);
            return true;
        }
        return false;
    }

    public boolean updateHistoryRoom(String uid, String rid) {
        Query query1 = new Query(Criteria.where("uid").is(uid));
        User user = mongoTemplate.findOne(query1,User.class);
        Query query2 = new Query(Criteria.where("rid").is(rid));
        Room room = mongoTemplate.findOne(query2,Room.class);
        if (user != null && room != null) {
            Map<String, Long> collect = user.historyRoom
                    .stream()
                    .map(entry -> Pair.of(entry.rid, entry.timeStamp))
                    .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
            collect.put(rid, (new Date()).getTime());

            user.historyRoom = collect.entrySet().stream().map(entry ->{
                Redis redis = new Redis();
                redis.rid = entry.getKey();
                redis.timeStamp = entry.getValue();
                return redis;
            }).sorted(Comparator.comparingLong((Redis o) -> o.timeStamp).reversed()).limit(10)
                    .collect(Collectors.toList());
            mongoTemplate.save(user);
            return true;
        }
        return false;
    }

    public Pair<Boolean, String> uploadUser(String signature, String birthday, String nickName, String gender, String uid, MultipartFile file) {
        Query query = new Query(Criteria.where("uid").is(uid));
        User one = mongoTemplate.findOne(query, User.class);
        if (one == null) {
            return Pair.of(false, "");
        }
	    String image = uploadImage(one, file);
        User updateUser = new User();
        updateUser.signature = signature;
        updateUser.birthday = birthday;
        updateUser.nickName = nickName;
        updateUser.gender = gender;
        String photoImage = uploadUserInfo(one, updateUser, image);
        return Pair.of(true, photoImage);
    }

    private String uploadUserInfo(User user,User updateUser, String image) {
        user.signature = updateUser.signature;
        user.birthday = updateUser.birthday;
        user.nickName = updateUser.nickName;
        user.gender = updateUser.gender;
        if (!StringUtil.isNullOrEmpty(image)) {
            user.photoImage = image;
        }
        mongoTemplate.save(user);
        return user.photoImage;
    }

    private String uploadImage(User user, MultipartFile file) {
	    if (file != null) {
            Query query = new Query(Criteria.where("uid").is(user.uid));
            String fileName = file.getOriginalFilename();
            String url;
            try {
                UploadFile uploadFile1 = mongoTemplate.findOne(query, UploadFile.class);
                if (uploadFile1 != null) {
                    Update update = new Update();
                    update.set("name", fileName);
                    update.set("CreatedTime", new Date());
                    update.set("Content", new Binary(file.getBytes()));
                    update.set("ContentType", file.getContentType());
                    update.set("Size", file.getSize());
                    mongoTemplate.updateFirst(query, update, UploadFile.class);
                    url = "http://39.106.42.184:8080/api/user/image/" + uploadFile1.getId();
                } else {
                    UploadFile uploadFile = new UploadFile();
                    uploadFile.setName(fileName);
                    uploadFile.setUid(user.uid);
                    uploadFile.setCreatedTime(new Date());
                    uploadFile.setContent(new Binary(file.getBytes()));
                    uploadFile.setContentType(file.getContentType());
                    uploadFile.setSize(file.getSize());
                    uploadFile.setId(UUID.randomUUID().toString());
                    UploadFile savedFile = mongoTemplate.save(uploadFile);
                    url = "http://39.106.42.184:8080/api/user/image/" + savedFile.getId();//访问地址
                }
            } catch (IOException e) {
                return "";
            }
            return url;
        }
        return "";
    }

    public byte[] getImage(String id) {
        byte[] data = null;
        UploadFile file = mongoTemplate.findById(id, UploadFile.class);
        if(file != null){
            data = file.getContent().getData();
        }
        return data;
    }
}

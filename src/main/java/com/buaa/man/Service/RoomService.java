package com.buaa.man.Service;

import com.buaa.man.Dao.Room;
import com.buaa.man.Dao.SearchParam;
import com.buaa.man.Dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class RoomService {
    private MongoTemplate mongoTemplate;

    @Autowired
    public RoomService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Room creatRoom(Room room) {
        room.id = null;
        room.rid = UUID.randomUUID().toString();
        room.createDate = new Date();
        mongoTemplate.insert(room);
        return room;
    }

    public Room getRoomById(String rid) {
        Query query = new Query(Criteria.where("rid").is(rid));
        Room room = mongoTemplate.findOne(query, Room.class);
        if (room != null) {
            return convertRoom(room);
        }
        return null;
    }

    public Room updateRoom(Room room) {
        Query query = new Query(Criteria.where("rid").is(room.rid));
        Room room1 = mongoTemplate.findOne(query, Room.class);
        if (room1 != null) {
            mongoTemplate.save(updateConvertRoom(room1, room));
        }
        return room;
    }

    public List<Room> getRoomByRoomName(String roomName) {
        Pattern pattern = Pattern.compile(roomName);
        Query query = new Query(Criteria.where("roomName").regex(pattern));
        List<Room> rooms = mongoTemplate.find(query, Room.class);
        return convertRooms(rooms);
    }

    public void collectRoom(String rid,User user) {
        Query query = new Query(Criteria.where("rid").is(rid));
        Room room1 = mongoTemplate.findOne(query, Room.class);
        if (room1 != null) {
            
        }
    }

    private List<Room> convertRooms(List<Room> rooms) {
        List<Room> roomList = new ArrayList<>();
        for (Room room : rooms) {
            roomList.add(convertRoom(room));
        }
        return roomList;
    }

    private Room convertRoom(Room room) {
        Room room1 = new Room();
        room1.rid = room.rid;
        room1.photoImage = room.photoImage;
        room1.roomName = room.roomName;
        room1.owner = room.owner;
        room1.describe = room.describe;
        room1.openStatus = room.openStatus;
        room1.type = room.type;
        room1.openTime = room.openTime;
        return room1;
    }

    private Room updateConvertRoom(Room upRoom, Room room) {
        upRoom.photoImage = room.photoImage;
        upRoom.roomName = room.roomName;
        upRoom.describe = room.describe;
        upRoom.openStatus = room.openStatus;
        upRoom.type = room.type;
        upRoom.openTime = room.openTime;
        return upRoom;
    }

    public List<Room> queryAll() {
        List<Room> rooms = mongoTemplate.findAll(Room.class);
        return convertRooms(rooms);
    }

    public Page<Room> findAllResolveRules(int page, int size) {
        Query query = new Query();
        Pageable pageable = PageRequest.of(page -1, size);
        List<Room> roomList = mongoTemplate.find(query.with(pageable), Room.class);
        return PageableExecutionUtils.getPage(roomList, pageable, () -> mongoTemplate.count(query, Room.class));
    }

    public Page<Room> findAllResolveRules(SearchParam searchParam) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        String way = searchParam.getWay();
        if (null != way) {
            switch (way) {
                case "按房间名":
                    criteria.and("roomName").regex(Pattern.compile(searchParam.getKeyword()));
                    break;
                case "按房主":
                    criteria.and("owner").regex(Pattern.compile(searchParam.getKeyword()));
                    break;
                case "按房间号":
                    criteria.and("rid").is(searchParam.getKeyword());
                    break;
                default:
                    return null;
            }
        }
        query.addCriteria(criteria);
        Pageable pageable = PageRequest.of(searchParam.getPage() - 1, searchParam.getCnt());
        List<Room> roomList = mongoTemplate.find(query.with(pageable), Room.class);
        return PageableExecutionUtils.getPage(roomList, pageable, () -> mongoTemplate.count(query, Room.class));
    }
}

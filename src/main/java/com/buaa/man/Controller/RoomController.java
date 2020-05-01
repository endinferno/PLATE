package com.buaa.man.Controller;

import com.alibaba.fastjson.JSON;
import com.buaa.man.Dao.Room;
import com.buaa.man.Dao.SearchParam;
import com.buaa.man.Service.RoomService;
import com.buaa.man.Util.ArrayUtil;
import com.buaa.man.Util.CommonRep;
import com.buaa.man.Util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/room")
@Api(tags = "房间相关接口")
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PutMapping(value = "/createRoom")
    @ResponseBody
    @ApiOperation(value = "新增" , notes = "创建房间")
    public CommonRep createRoom(@RequestBody Room room, HttpServletResponse response) {
        if (StringUtil.isNullOrEmpty(room.owner) || StringUtil.isNullOrEmpty(room.roomName)) {
            response.setStatus(250);
            return new CommonRep("创建房间失败");
        }
        CommonRep commonRep = new CommonRep();
        Room creatRoom = roomService.creatRoom(room);
        commonRep.setData(creatRoom.rid);
        return commonRep;
    }

    @PostMapping(value = "/update")
    @ResponseBody
    @ApiOperation(value = "修改" , notes = "修改房间")
    public CommonRep updateRoom(@RequestBody Room room, HttpServletResponse response) {
        if (StringUtil.isNullOrEmpty(room.rid) || StringUtil.isNullOrEmpty(room.roomName)) {
            response.setStatus(250);
            return new CommonRep("房间不存在");
        }
        CommonRep commonRep = new CommonRep();
        Room creatRoom = roomService.updateRoom(room);
        commonRep.setData(JSON.toJSONString(creatRoom));
        return commonRep;
    }

    @GetMapping(value = "/findId")
    @ResponseBody
    @ApiOperation(value = "根据id查找房间" , notes = "根据id查找房间")
    public CommonRep findById(@RequestParam("rid") String rid, HttpServletResponse response) {
        if (StringUtil.isNullOrEmpty(rid)) {
            response.setStatus(250);
            return new CommonRep("房间不存在");
        }
        CommonRep commonRep = new CommonRep();
        Room findRoom = roomService.getRoomById(rid);
        if (findRoom != null) {
            commonRep.setData(JSON.toJSONString(findRoom));
            return commonRep;
        }
        response.setStatus(250);
        return new CommonRep("房间不存在");
    }

    @GetMapping(value = "/findName")
    @ResponseBody
    @ApiOperation(value = "根据名称查找房间" , notes = "根据名称查找房间")
    public CommonRep findByName(@RequestParam("roomName") String roomName, HttpServletResponse response) {
        if (StringUtil.isNullOrEmpty(roomName)) {
            response.setStatus(250);
            return new CommonRep("房间不存在");
        }
        CommonRep commonRep = new CommonRep();
        List<Room> findRoom = roomService.getRoomByRoomName(roomName);
        if (ArrayUtil.isEmptyOrNull(findRoom)) {
            response.setStatus(250);
            return new CommonRep("房间不存在");
        }
        commonRep.setData(JSON.toJSONString(findRoom));
        return commonRep;
    }

    @GetMapping(value = "/getRooms")
    @ResponseBody
    @ApiOperation(value = "分页查询所有房间" , notes = "分页查询所有房间")
    public CommonRep findAllRoom(@RequestParam("page") int page, @RequestParam("cnt") int size,
                                 HttpServletResponse response) {
        if (page <= 0 || size <= 0) {
            response.setStatus(250);
            return new CommonRep("参数错误");
        }
        CommonRep commonRep = new CommonRep();
        Page<Room> findRoom = roomService.findAllResolveRules(page, size);
        commonRep.setData(JSON.toJSONString(findRoom));
        return commonRep;
    }

    @PostMapping(value = "/searchRooms")
    @ResponseBody
    @ApiOperation(value = "分页搜索房间" , notes = "分页搜索房间")
    public CommonRep findAllRoom(@RequestBody SearchParam searchParam,
                                 HttpServletResponse response) {
        if (searchParam.getPage() <= 0 || searchParam.getCnt() <= 0
                || StringUtil.isNullOrEmpty(searchParam.getKeyword())) {
            response.setStatus(250);
            return new CommonRep("参数错误");
        }
        CommonRep commonRep = new CommonRep();
        Page<Room> findRoom = roomService.findAllResolveRules(searchParam);
        if (null == findRoom) {
            response.setStatus(250);
            return new CommonRep("参数错误");
        }
        commonRep.setData(JSON.toJSONString(findRoom));
        return commonRep;
    }
}

package com.buaa.man.Controller;

import com.alibaba.fastjson.JSON;
import com.buaa.man.Dao.Room;
import com.buaa.man.Dao.UploadFile;
import com.buaa.man.Dao.User;
import com.buaa.man.Service.MailService;
import com.buaa.man.Service.UserService;
import com.buaa.man.Util.CommonRep;
import com.buaa.man.Util.RandomUtil;
import com.buaa.man.Util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户相关接口")
public class UserController {
    private UserService userService;
    private MailService mailService;

    @Autowired
    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping(value = "/register/code")
	@ResponseBody
	@ApiOperation(value = "注册验证码" , notes = "发送注册验证码")
    public CommonRep getRegisterCode(@RequestBody User user, HttpServletResponse response) {
		String code = RandomUtil.getCode();
		userService.updateRegisterCode(user.email, code);
		if(userService.getRegistrationStatus(user.email))
        {
            response.setStatus(250);
            return new CommonRep("账号已被注册");
        }
		try {
			mailService.sendMessage("账号注册验证", user.email + "您好：\n"
					+ "请您输入验证码，" + code + "完成您的验证\n", user.email);
		} catch (Exception e) {
			response.setStatus(250);
			return new CommonRep("验证码发送失败");
		}
		return new CommonRep();
    }

    @PostMapping(value = "/register")
	@ResponseBody
	@ApiOperation(value = "注册" , notes = "注册用户")
    public CommonRep register(@RequestBody User user, HttpServletResponse response) {
		User register;
		if(userService.getRegistrationStatus(user.email))
        {
            response.setStatus(250);
            return new CommonRep("账号已被注册");
        }
		try {
			String registerCode = userService.getRegistrationCode(user.email);
			if (!registerCode.equals(user.validateRegistration)) {
				response.setStatus(250);
				return new CommonRep("验证码错误");
			}
			register = userService.register(user);
		} catch (Exception e) {
			response.setStatus(250);
			return new CommonRep(e.getMessage());
		}
		return new CommonRep(JSON.toJSONString(register));
    }

	@PostMapping(value = "/login")
	@ResponseBody
	@ApiOperation(value = "登录" , notes = "登录用户")
    public CommonRep login(@RequestBody User user,
                           HttpServletResponse response) {
        if (StringUtil.isNullOrEmpty(user.email) || StringUtil.isNullOrEmpty(user.password)) {
			response.setStatus(250);
            return new CommonRep("用户名或者密码错误");
        }
		User login = userService.login(user.email, user.password);
		if (login == null || StringUtil.isNullOrEmpty(login.uid)) {
			response.setStatus(250);
			return new CommonRep("用户名或者密码错误");
        }
		User login1 = userService.getUserByUid(login.uid);
		CommonRep commonRep =new CommonRep();
		commonRep.setData(JSON.toJSONString(login1));
		return commonRep;
    }

    @GetMapping(value = "getUserInfo/{uid}")
	@ResponseBody
	@ApiOperation(value = "根据用户id查询" , notes = "根据用户id查询")
    public CommonRep getUserInfo(@PathVariable("uid") String uid, HttpServletResponse response) {
        CommonRep rep = new CommonRep();
        User u = userService.getUserByUid(uid);
        if (u == null) {
			response.setStatus(250);
			return new CommonRep("用户不存在");
		}
        rep.setData(JSON.toJSONString(u));
        return rep;
    }

	@PostMapping(value = "/retrieve/code")
	@ResponseBody
	@ApiOperation(value = "找回验证码" , notes = "发送找回验证码")
	public CommonRep getRetrieveCode(@RequestBody User user, HttpServletResponse response) {
		String code = RandomUtil.getCode();
		userService.updateRetrieveCode(user.email, code);
		if(!userService.getRegistrationStatus(user.email))
        {
            response.setStatus(250);
            return new CommonRep("账号未注册");
        }
		try {
			mailService.sendMessage("账号密码找回验证", user.email + "您好：\n"
					+ "请您输入验证码，" + code + "完成您的验证\n", user.email);
		} catch (Exception e) {
			response.setStatus(250);
			return new CommonRep("验证码发送失败");
		}
		return new CommonRep();
	}

    @PostMapping(value = "/retrieve")
	@ResponseBody
	@ApiOperation(value = "找回" , notes = "重置密码")
	public CommonRep retrieve(@RequestBody User user, HttpServletResponse response) {
		if (StringUtil.isNullOrEmpty(user.email) || StringUtil.isNullOrEmpty(user.password)
				|| StringUtil.isNullOrEmpty(user.validateRetrieve)) {
			response.setStatus(250);
			return new CommonRep("用户名或者密码错误");
		}
		if(!userService.getRegistrationStatus(user.email))
		{
			response.setStatus(250);
			return new CommonRep("账号未注册");
		}
		String retrieveCode = userService.getRetrieveCode(user.email);
		if (!retrieveCode.equals(user.validateRetrieve)) {
			response.setStatus(250);
			return new CommonRep("验证码错误");
		}
		userService.updatePsw(user);
		return new CommonRep();
	}

	@PutMapping(value = "/historyRoom")
	@ResponseBody
	@ApiOperation(value = "添加" , notes = "添加历史房间")
	public CommonRep putHistoryRoom(@RequestBody User user, @RequestBody Room room, HttpServletResponse response) {
		if (StringUtil.isNullOrEmpty(user.uid) || StringUtil.isNullOrEmpty(room.rid)) {
			response.setStatus(250);
			return new CommonRep("用户或房间不存在");
		}
		if (userService.updateHistoryRoom(user.uid, room.rid)) {
			return new CommonRep();
		} else {
			response.setStatus(250);
			return new CommonRep("用户或房间不存在");
		}
	}

	@PutMapping(value = "/favoriteRoom")
	@ResponseBody
	@ApiOperation(value = "添加" , notes = "添加喜爱房间")
	public CommonRep putFavoriteRoom(@RequestBody User user, @RequestBody Room room, HttpServletResponse response) {
		if (StringUtil.isNullOrEmpty(user.uid) || StringUtil.isNullOrEmpty(room.rid)) {
			response.setStatus(250);
			return new CommonRep("用户或房间不存在");
		}
		if (userService.updateFavoriteRoom(user.uid, room.rid)) {
			return new CommonRep();
		} else {
			response.setStatus(250);
			return new CommonRep("用户或房间不存在");
		}
	}

	@PostMapping(value = "/updateUser")
	@ResponseBody
	@ApiOperation(value = "修改用户" , notes = "修改用户信息")
	public CommonRep putFavoriteRoom(@RequestParam(value = "signature") String signature,
									 @RequestParam(value = "birthday") String birthday,
									 @RequestParam(value = "nickName") String nickName,
									 @RequestParam(value = "gender") String gender,
									 @RequestParam(value = "uid") String uid,
									 @RequestParam(value = "image") MultipartFile file,
									 HttpServletResponse response) {
		if (StringUtil.isNullOrEmpty(uid)) {
			response.setStatus(250);
			return new CommonRep("用户不存在");
		}
		Pair<Boolean, String> booleanStringPair =
			userService.uploadUser(signature, birthday, nickName, gender, uid, file);
		if (booleanStringPair.getFirst()) {
			CommonRep commonRep = new CommonRep();
			Map<String, String> image = new HashMap<>();
			image.put("image", booleanStringPair.getSecond());
			commonRep.setData(JSON.toJSONString(image));
			return commonRep;
		} else {
			response.setStatus(250);
			return new CommonRep("用户不存在");
		}
	}

	@CrossOrigin
	@GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public byte[] image(@PathVariable String id) {
    	return userService.getImage(id);
	}
}

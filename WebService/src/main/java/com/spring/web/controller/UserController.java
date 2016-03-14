package com.spring.web.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.dao.AppItemDao;
import com.spring.dao.CallLogDao;
import com.spring.dao.MapDao;
import com.spring.dao.MessageDao;
import com.spring.dao.RolesDao;
import com.spring.dao.UserDao;
import com.spring.entity.AppItem;
import com.spring.entity.CallLog;
import com.spring.entity.Map;
import com.spring.entity.MapJson;
import com.spring.entity.Message;
import com.spring.entity.MessageJson;
import com.spring.entity.Roles;
import com.spring.entity.User;
import com.spring.entity.Views;
import com.spring.service.StatusResponse;
import com.spring.service.Util;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserDao userService;

	@Autowired
	private RolesDao rolesService;

	@Autowired
	private MessageDao messageService;

	@Autowired
	private AppItemDao appService;

	@Autowired
	private CallLogDao callLogService;

	@Autowired
	private MapDao mapService;
	
	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "home";

	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/saveMessage")
	public ResponseEntity<String> getSearchResultViaAjax(
			@RequestParam(value = "userRecieve") String userRecieveEmail,
			@RequestParam(value = "userSend") String userSendEmail,
			@RequestParam(value = "phone", defaultValue = "") String phone,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "date") String date) {
		try {
			User userRecieve = userService.findByUserEmail(userRecieveEmail);
			User userSend = userService.findByUserEmail(userSendEmail);
			Message message = new Message();
			message.setReciever(userRecieve);
			message.setName(name);
			message.setSender(userSend);
			message.setPhone(phone);
			String data = URLDecoder.decode(content, "UTF-8");
			message.setContent(Util.compress(data));
			message.setSent_date(date);
			message.setIsRead(Boolean.FALSE);
			Boolean success = messageService.saveMessage(message);
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_SUCCESS,
					success ? "Success" : "Fail", null);
		} catch (Exception e) {
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
					e.getMessage(), null);
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/check_user")
	public String checUser(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		try {
			User correctUser = userService.findByUserEmail(email);
			if (correctUser != null
					&& correctUser.getPassword().equals(getMd5(password))) {
				return "1";
			} else {
				return "0";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private User checkCorrectUser(User user) {
		User correctUser = userService.findByUserEmail(user.getEmail());
		if (correctUser.getPassword().equals(getMd5(user.getPassword()))) {
			return correctUser;
		} else {
			return null;
		}
	}
	

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/get_message")
	public String getMessage(@RequestParam(value = "user", defaultValue = "") String userJson,
			@RequestParam(value = "type", defaultValue = "sent") String type) {
		try {
			User user = checkCorrectUser(new JSONDeserializer<User>().use(null,
					User.class).deserialize(userJson));
			if (user != null) {
				List<MessageJson> messages = new ArrayList<MessageJson>();
				if (type.equals("sent")) {
					List<User> manageList = userService.findManageByUser(user);
					for (User ur : manageList) {
						List<MessageJson> messList = messageService
								.findMessessSentByUserParent(ur);
						messages.addAll(messList);
					}
				}else {
					List<User> manageList = userService.findManageByUser(user);
					for (User ur : manageList) {
						List<MessageJson> messList = messageService
								.findMessessRecieveByUserParent(ur);
						messages.addAll(messList);
					}
				}
				return new JSONSerializer().exclude("*.class").serialize(
						messages);
			} else {
				return "Password is incorrect or user not exist! Please check it again";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/saveApp")
	public ResponseEntity<String> saveApps(
			@RequestParam(value = "user") String userJson,
			@RequestParam(value = "apps") String apps) {
		try {
			User user = userService.findByUserEmail(userJson);
			String data = URLDecoder.decode(apps, "UTF-8");
			if (user != null) {
				List<AppItem> appItems = new JSONDeserializer<List<AppItem>>()
						.use(null, ArrayList.class)
						.use("values", AppItem.class).deserialize(data);
				for (AppItem appItem : appItems) {
					appItem.setOwner(user);
					appService.saveApp(appItem);
				}
				return Util.getResponseEntity(
						StatusResponse.STATUS_CODE_SUCCESS, "Success", null);
			} else {
				return Util
						.getResponseEntity(
								StatusResponse.STATUS_CODE_ERROR,
								"Password is incorrect or user not exist! Please check it again",
								null);
			}

		} catch (Exception e) {
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
					e.getMessage(), null);
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/saveCallLog")
	public ResponseEntity<String> saveCallLog(
			@RequestParam(value = "user") String userJson,
			@RequestParam(value = "callLog") String callLogs) {
		try {
			User user = userService.findByUserEmail(userJson);
			if (user != null) {
				List<CallLog> callLogItems = new JSONDeserializer<List<CallLog>>()
						.use(null, ArrayList.class)
						.use("values", CallLog.class).deserialize(callLogs);
				for (CallLog callLog : callLogItems) {
					callLog.setOwner(user);
					callLogService.saveCallLog(callLog);
				}
				return Util.getResponseEntity(
						StatusResponse.STATUS_CODE_SUCCESS, "Success", null);
			} else {
				return Util
						.getResponseEntity(
								StatusResponse.STATUS_CODE_ERROR,
								"Password is incorrect or user not exist! Please check it again",
								null);
			}

		} catch (Exception e) {
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
					e.getMessage(), null);
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/saveMap")
	public ResponseEntity<String> saveMap(
			@RequestParam(value = "user") String userJson,
			@RequestParam(value = "map") String mapJson) {
		try {
			User user = userService.findByUserEmail(userJson);
			if (user != null) {
				Map map = new JSONDeserializer<Map>().use(null, Map.class)
						.deserialize(mapJson);
				List<Map> maps = mapService.getMapsByOwner(user);
				if (maps.isEmpty()) {
					map.setOwner(user);
					mapService.saveMap(map);
				} else {
					Map mapUpdate = maps.get(0);
					mapUpdate.setLatitude(map.getLatitude());
					mapUpdate.setLongitude(map.getLongitude());
					mapService.update(mapUpdate);
				}
				return Util.getResponseEntity(
						StatusResponse.STATUS_CODE_SUCCESS, "Success", null);
			} else {
				return Util
						.getResponseEntity(
								StatusResponse.STATUS_CODE_ERROR,
								"Password is incorrect or user not exist! Please check it again",
								null);
			}

		} catch (Exception e) {
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
					e.getMessage(), null);
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/getMap")
	public String getMap(@RequestParam(value = "user") String userJson) {
		try {
			User user = checkCorrectUser(new JSONDeserializer<User>().use(null,
					User.class).deserialize(userJson));
			if (user != null) {
				List<User> users = userService.findManageByUser(user);
				List<MapJson> maps = new ArrayList<MapJson>();
				for (User u : users) {
					maps.addAll(mapService.getMapJsonsByOwner(u));
				}
				return new JSONSerializer().exclude("*.class").serialize(maps);
			} else {
				return "Password is incorrect or user not exist! Please check it again";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/getCallLog")
	public String getCallLog(@RequestParam(value = "user") String userJson) {
		try {
			User user = checkCorrectUser(new JSONDeserializer<User>().use(null,
					User.class).deserialize(userJson));
			if (user != null) {
				List<User> users = userService.findManageByUser(user);
				List<CallLog> callLogItems = new ArrayList<CallLog>();
				for (User u : users) {
					callLogItems.addAll(callLogService.getCallLogByUser(u));
				}
				return new JSONSerializer().exclude("*.class").serialize(
						callLogItems);
			} else {
				return "Password is incorrect or user not exist! Please check it again";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/lockApp")
	public ResponseEntity<String> lockApps(@RequestParam(value = "id") String id) {
		try {
			List<AppItem> appItems = new JSONDeserializer<List<AppItem>>()
					.use(null, ArrayList.class).use("values", AppItem.class)
					.deserialize(id);
			for (AppItem appItem : appItems) {
				AppItem a = appService.findById(appItem.getId());
				a.setIncluded(Boolean.TRUE);
				appService.update(a);
			}
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_SUCCESS,
					"Success", null);

		} catch (Exception e) {
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
					e.getMessage(), null);
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/getApp")
	public String getLockApps(@RequestParam(value = "user") String userJson) {
		try {
			User user = checkCorrectUser(new JSONDeserializer<User>().use(null,
					User.class).deserialize(userJson));
			if (user != null) {
				List<User> users = userService.findManageByUser(user);
				List<AppItem> appItems = new ArrayList<AppItem>();
				for (User u : users) {
					appItems.addAll(appService.getAppsByOwner(u));
				}
				String json = new JSONSerializer().exclude("*.class")
						.serialize(appItems);
				return URLEncoder.encode(json, "UTF-8");
			} else {
				return "Password is incorrect or user not exist! Please check it again";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/getLockApp")
	public String getApps(@RequestParam(value = "user") String userJson) {
		try {
			User user = checkCorrectUser(new JSONDeserializer<User>().use(null,
					User.class).deserialize(userJson));
			if (user != null) {
				List<AppItem> appItems = new ArrayList<AppItem>();
				appItems = appService.getAppsLockByOwner(user);
				String json = new JSONSerializer().exclude("*.class")
						.serialize(appItems);
				return URLEncoder.encode(json, "UTF-8");
				
			} else {
				return "Password is incorrect or user not exist! Please check it again";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/add_user")
	public ResponseEntity<String> addUser(
			@RequestParam(value = "user", defaultValue = "") String userJson,
			@RequestParam(value = "role", defaultValue = "") String role) {
		try {
			if (userJson == "" || role == "") {
				return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
						"Null value from user or role", null);
			}
			User user = new JSONDeserializer<User>().use(null, User.class)
					.deserialize(userJson);
			Set<Roles> userRoles = new HashSet<Roles>();
			userRoles.add(rolesService.findRoleByName(role));
			user.setPassword(getMd5(user.getPassword()));
			user.setRoleses(userRoles);
			userService.addUser(user);
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_SUCCESS,
					"Success", null);
		} catch (Exception ex) {
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
					ex.getMessage(), null);
		}

	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/add_user_manage")
	public ResponseEntity<String> addUserManage(
			@RequestParam(value = "userManage", defaultValue = "") String userManage,
			@RequestParam(value = "userBeManage", defaultValue = "") String userBeManage) {
		try {
			if (userManage == "" || userBeManage == "") {
				return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
						"Null value from user", null);
			}
			User userM = checkCorrectUser(new JSONDeserializer<User>().use(
					null, User.class).deserialize(userManage));
			User userBM = checkCorrectUser(new JSONDeserializer<User>().use(
					null, User.class).deserialize(userBeManage));
			if (userBM == null || userM == null) {
				return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
						"User null", null);
			}
			if (isContain(userM, "Parent")) {
				Set<User> userList = new HashSet<User>(
						userService.findManageByUser(userM));
				userList.add(userBM);
				userM.setManage(userList);
				userService.updateUser(userM);
				return Util.getResponseEntity(
						StatusResponse.STATUS_CODE_SUCCESS, "Success", null);
			} else {
				return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
						"User have no Parent role", null);
			}
		} catch (Exception ex) {
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
					ex.getMessage(), null);
		}

	}

	private Boolean isContain(User user, String field) {
		List<Roles> roleSet = rolesService.findByUser(user);
		for (Roles r : roleSet) {
			if (r.getName().equals(field)) {
				return true;
			}
		}
		return false;
	}

	public String getMd5(String plainText) {
		String result = "";
		MessageDigest mdAlgorithm;
		try {
			mdAlgorithm = MessageDigest.getInstance("MD5");
			mdAlgorithm.update(plainText.getBytes());

			byte[] digest = mdAlgorithm.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < digest.length; i++) {
				plainText = Integer.toHexString(0xFF & digest[i]);

				if (plainText.length() < 2) {
					plainText = "0" + plainText;
				}

				hexString.append(plainText);
			}
			result = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}

package com.spring.web.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.dao.CallLogDao;
import com.spring.dao.MapDao;
import com.spring.dao.MessageDao;
import com.spring.dao.RolesDao;
import com.spring.dao.UserDao;
import com.spring.entity.Map;
import com.spring.entity.MessageJson;
import com.spring.entity.Roles;
import com.spring.entity.User;
import com.spring.service.StatusResponse;
import com.spring.service.Util;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Controller
public class HomeController {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RolesDao roles;
	
	@Autowired
	private CallLogDao callService;
	
	@Autowired
	private MapDao mapService;
	
	@Autowired
	private MessageDao messageService;
	
	private String childEmail = "";
	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView printWelcome() throws IOException {
		ModelAndView model = new ModelAndView();
		User user = getCurrentUser();
		model.addObject("list_user_manage",userDao.findManageByUser(user));
		model.addObject("your_parent",userDao.findParent(user));
		User child = userDao.findByUserEmail(childEmail);
		model.addObject("list_message_recieve",messageService.findMessessRecieveByUserParent(child));
		model.addObject("list_message_send",messageService.findMessessSentByUserParent(child));
		model.addObject("phone",callService.getCallLogByUser(child));
		List<Map> listMap = mapService.getMapsByOwner(child);
		if(!listMap.isEmpty()) {
			model.addObject("location",listMap.get(0));
		}else {
			model.addObject("location",null);
		}
		model.setViewName("home");
		return model;

	}
	
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public @ResponseBody String getListMessage(@RequestParam(value = "child", defaultValue = "") String email) {
		childEmail = email;
		return "OK";
	}
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error",
					getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;

	}
	
	// customize the error message
		private String getErrorMessage(HttpServletRequest request, String key) {

			Exception exception = (Exception) request.getSession()
					.getAttribute(key);

			String error = "";
			if (exception instanceof BadCredentialsException) {
				error = "Invalid username and password!";
			} else if (exception instanceof LockedException) {
				error = exception.getMessage();
			} else {
				error = "Invalid username and password!";
			}

			return error;
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
	
	public User getCurrentUser() {
		try {
			Authentication aut = SecurityContextHolder.getContext()
					.getAuthentication();
			return (User) userDao.findByUserEmail(aut.getName());
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/get_message", method = RequestMethod.GET)
	public ModelAndView getMessage(@RequestParam(value = "type", defaultValue = "sent") String type) {
		ModelAndView model = new ModelAndView();
		try {
			User user = getCurrentUser();

			if (user != null) {
				List<MessageJson> messages = new ArrayList<MessageJson>();
				if (type.equals("sent")) {
					List<User> manageList = userDao.findManageByUser(user);
					for (User ur : manageList) {
						List<MessageJson> messList = messageService
								.findMessessSentByUserParent(ur);
						messages.addAll(messList);
					}
				}else {
					List<User> manageList = userDao.findManageByUser(user);
					for (User ur : manageList) {
						List<MessageJson> messList = messageService
								.findMessessRecieveByUserParent(ur);
						messages.addAll(messList);
					}
				}
				String message =  new JSONSerializer().exclude("*.class").serialize(
						messages);
				model.addObject("messages", message);
				model.addObject("message", "Success");
			} else {
				model.addObject("message", "User is null");
			}

		} catch (Exception e) {
			model.addObject("message", e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String>  signup(HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "roles", defaultValue = "") String roleUser) throws Exception {
		try {
			String userName = request.getParameter("userName");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			List<String> rolesUser = null;
			if (!StringUtils.isEmpty(roleUser)) {
				rolesUser = new JSONDeserializer<List<String>>()
						.use(null, ArrayList.class).use("values", String.class)
						.deserialize(roleUser);
	        }
			if(StringUtils.isEmpty(userName) ||StringUtils.isEmpty(email) ||StringUtils.isEmpty(password)) {
				throw new NullPointerException("Field can't be null");
			}
			Set<Roles> setRole = new HashSet<Roles>();
			for(String r : rolesUser) {
				setRole.add(roles.findRoleByName(r));
			}
			User ur = new User(email, userName, getMd5(password), true);
			ur.setRoleses(setRole);
			userDao.addUser(ur);
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_SUCCESS,
					"Success", userName);
		} catch (Exception ex) {
			return Util.getResponseEntity(StatusResponse.STATUS_CODE_ERROR,
					ex.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String errorrPage() {
		return "403";

	}
}
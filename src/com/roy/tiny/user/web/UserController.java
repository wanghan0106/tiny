package com.roy.tiny.user.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.web.annotation.Auth;
import com.roy.tiny.user.model.User;
import com.roy.tiny.user.service.UserService;

@Controller
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String add(HttpSession session,User user,Model model){
		user.setRegisterTime(new Date());
		userService.save(user);
		session.setAttribute("user",user);
        return "redirect:/home";
    }
	
	@RequestMapping(value = "/user/check/{username}")
	@ResponseBody
    public int check(@PathVariable(value="username") String username){
        return userService.query(Cond.eq("username", username)).size();
    }
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(HttpSession session,User user){
		User pu = userService.get(Cond.eq("username", user.getUsername()));
		if(pu!=null && pu.getPassword().equals(user.getPassword())) {
			pu.setLastLoginTime(new Date());
			userService.save(pu);
			session.setAttribute("user",pu);
		} else {
			session.setAttribute("error", "用户名不存在或密码错误！");
			return "redirect:/login";
		}
        return "redirect:/user/home";
    }
	
	@Auth
	@RequestMapping(value = "/user/home")
    public String home(HttpSession session,Model model) {
		model.addAttribute("user", session.getAttribute("user"));
        return "user/home";
    }
	
	@RequestMapping(value = "/user/{id:\\d+$}")
	public String home(HttpSession session,@PathVariable(value="id") long id,Model model) {
		User user = userService.get(id);
		if(user==null) {
			return "redirect:/404";
		}
		User loginUser = (User) session.getAttribute("user");
		if(loginUser!=null && loginUser.getId()!=user.getId()) {
			User u = userService.get(Cond.eq("focusUsers.id", user.getId()).and(Cond.eq("id", loginUser.getId())));
			if(u==null) {
				model.addAttribute("focus",false);
			} else {
				model.addAttribute("focus",true);
			}
		}
		model.addAttribute("user",user);
        return "user/home";
    }
	
	@RequestMapping(value = "/user/{id:\\d+}/logo")
	public void logo(HttpServletRequest request,HttpServletResponse response,@PathVariable(value="id") long id) {
		String filePath = request.getServletContext().getRealPath("/images/landscape.jpg");
		try {
			File file = new File(filePath);
			FileInputStream input = new FileInputStream(file);
			response.addHeader("Content-Disposition", "attachment;filename="+file.getName());
			response.addHeader("Content-Length", "" + file.length());
	        byte[] buffer = new byte[input.available()]; 
	        input.read(buffer); 
	        input.close();
	        BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream()); 
	        output.write(buffer);
	        output.flush();
	        output.close(); 
		} catch (IOException e) {
			log.equals(e.getMessage());
		}
    }
	
	@Auth
	@RequestMapping(value = "/user/profile")
    public String profile(HttpSession session,Model model) {
		model.addAttribute("user", session.getAttribute("user"));
        return "user/profile";
    }
	
	@Auth
	@RequestMapping(value = "/user/save")
    public String save(HttpServletRequest request,HttpSession session,User user,@RequestParam("logoFile") MultipartFile logoFile,Model model) {
		User pu = userService.get(user.getId());
		if(pu==null) {
			return "redirect:/404";
		}
		if(logoFile!=null && logoFile.getOriginalFilename()!=null && logoFile.getOriginalFilename().length()>0) {
			saveLogo(request, logoFile, pu);
		}
		pu.setSex(user.getSex());
		pu.setLocation(user.getLocation());
		pu.setFavorite(user.getFavorite());
		pu.setBrief(user.getBrief());
		userService.save(pu);
		model.addAttribute("user",pu);
		session.setAttribute("user", pu);
		return "redirect:/user/home";
    }

	private void saveLogo(HttpServletRequest request, MultipartFile logoFile,
			User pu) {
		File dir = new File(request.getServletContext().getRealPath("/") +"images/logo/"+pu.getId());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String[] names = logoFile.getOriginalFilename().split("\\.");
		String ext = names[names.length-1];
		File file = new File(dir.getPath() + "/logo." + ext);
		try {
			FileOutputStream output = new FileOutputStream(file);
			output.write(logoFile.getBytes());
			output.flush();
			output.close();
			pu.setLogo("/images/logo/"+pu.getId()+"/"+file.getName());
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	@Auth
	@RequestMapping(value = "/user/focus/{id:\\d+}")
	@ResponseBody
	public int focus(HttpSession session,@PathVariable(value="id") long id) {
		User u = userService.get(id);
		if(u==null) {
			return 404;
		}
		User loginUser = (User) session.getAttribute("user");
		loginUser = userService.get(loginUser.getId());
		loginUser.getFocusUsers().add(u);
		userService.save(loginUser);
		return 0;
	}
	
	@Auth
	@RequestMapping(value = "/user/unfocus/{id:\\d+}")
	@ResponseBody
	public int unfocus(HttpSession session,@PathVariable(value="id") long id) {
		User u = userService.get(id);
		if(u==null) {
			return 404;
		}
		User loginUser = (User) session.getAttribute("user");
		loginUser = userService.get(loginUser.getId());
		Iterator<User> iterator = loginUser.getFocusUsers().iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == u.getId()) {
				iterator.remove();
			}
		}
		userService.save(loginUser);
		return 0;
	}
	
}

package com.roy.tiny.home.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/home")
    public String home(){
        return "home";
    }
	
	@RequestMapping(value = "/about")
    public String about(){
        return "about";
    }
	
	@RequestMapping(value = "/login")
    public String login(HttpSession session,Model model){
		Object error = session.getAttribute("error");
		if(error!=null) {
			model.addAttribute("error", error);
			session.removeAttribute("error");
		}
        return "login";
    }
	
	@RequestMapping(value = "/logout")
    public String logout(HttpSession session){
		session.removeAttribute("user");
		return "redirect:/home";
    }
	
	@RequestMapping(value = "/register")
    public String register(){
        return "register";
    }
	
	@RequestMapping(value = "/404")
    public String notfound(){
        return "404";
    }

}

package com.roy.tiny.topic.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roy.tiny.base.service.TagService;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;
import com.roy.tiny.base.web.annotation.Auth;
import com.roy.tiny.topic.model.Topic;
import com.roy.tiny.topic.service.TopicService;
import com.roy.tiny.user.model.User;

@Controller
public class TopicController {
	private static final Logger log = LoggerFactory.getLogger(TopicController.class);
	
	@Autowired
	private TopicService topicService;
	@Autowired
	private TagService tagService;
	
	@RequestMapping(value = "/topic")
	public String home(Model model) {
		getTopicList(model,1);
		return  "topic/home";
	}
	
	@RequestMapping(value = "/topic/page/{page:\\d+$}")
	public String page(Model model,@PathVariable(value="page") int page) {
		getTopicList(model,page);
		return  "topic/home";
	}
	
	private void getTopicList(Model model,int page) {
		Pager pager = new Pager(20);
		pager.setPage(page);
		List<Topic> topicList = topicService.query(null, pager , new Sorter("createTime","desc"));
		model.addAttribute("pager", pager);
		model.addAttribute("topicList", topicList);
		model.addAttribute("tags", tagService.getPopularTags());
	}
	
	@RequestMapping(value = "/topic/add")
	@Auth
	public String add(Model model) {
		model.addAttribute("tags", tagService.getPopularTags());
		return  "topic/add";
	}
	
	@RequestMapping(value = "/topic/save")
	@Auth
	public String save(HttpSession session,Topic topic,Model model) {
		User user = (User) session.getAttribute("user");
		topicService.save(topic,user);
		return "redirect:/topic";
	}
	
	@RequestMapping(value = "/topic/{id:\\d+$}")
	public String view(HttpSession session,@PathVariable(value="id") long id,Model model) {
		Topic topic = topicService.get(id);
		if(topic==null) {
			return "redirect:/404";
		}
		model.addAttribute("topic", topic);
		model.addAttribute("tags", tagService.getPopularTags());
		model.addAttribute("comments", topicService.getComments(id));
		return  "topic/view";
	}
}

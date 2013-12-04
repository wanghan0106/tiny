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

import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.model.Tag;
import com.roy.tiny.base.service.TagService;
import com.roy.tiny.base.web.Pager;
import com.roy.tiny.base.web.Sorter;
import com.roy.tiny.base.web.annotation.Auth;
import com.roy.tiny.topic.model.Comment;
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
		getTopicList(null,model,1);
		return  "topic/home";
	}
	
	@RequestMapping(value = "/topic/page/{page:\\d+$}")
	public String page(Model model,@PathVariable(value="page") int page) {
		getTopicList(null,model,page);
		return  "topic/home";
	}
	
	@RequestMapping(value = "/topic/tag/{id:\\d+$}")
	public String showByTag(Model model,@PathVariable(value="id") long id) {
		Tag tag = tagService.get(id);
		if(tag==null) {
			return "redirect:/404";
		}
		getTopicList(Cond.eq("tags.id", id),model,1);
		model.addAttribute("tag", tag);
		return  "topic/showByTag";
	}
	@RequestMapping(value = "/topic/tag/{id:\\d+$}/{page:\\d+$}")
	public String showByTag(Model model,@PathVariable(value="id") long id,@PathVariable(value="page") int page) {
		Tag tag = tagService.get(id);
		if(tag==null) {
			return "redirect:/404";
		}
		getTopicList(Cond.eq("tags.id", id),model,page);
		model.addAttribute("tag", tag);
		return  "topic/showByTag";
	}
	
	private void getTopicList(Cond cond,Model model,int page) {
		Pager pager = new Pager(20);
		pager.setPage(page);
		List<Topic> topicList = topicService.query(cond, pager , new Sorter("createTime","desc"));
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
		viewTopic(id, 1, model, topic);
		return  "topic/view";
	}
	
	@RequestMapping(value = "/topic/{id:\\d+$}/{page:\\d+$}")
	public String view(HttpSession session,@PathVariable(value="id") long id,@PathVariable(value="page") int page,Model model) {
		Topic topic = topicService.get(id);
		if(topic==null) {
			return "redirect:/404";
		}
		viewTopic(id, page, model, topic);
		return  "topic/view";
	}

	private void viewTopic(long id, int page, Model model, Topic topic) {
		model.addAttribute("topic", topic);
		model.addAttribute("tags", tagService.getPopularTags());
		Pager pager = new Pager(20);
		pager.setPage(page);
		model.addAttribute("comments", topicService.getComments(id,pager));
		model.addAttribute("pager", pager);
	}
	
	@RequestMapping(value = "/topic/comment/add")
	@Auth
	public String addComment(HttpSession session,Comment comment) {
		User user = (User) session.getAttribute("user");
		Topic topic = topicService.get(comment.getTopic().getId());
		if(topic==null) {
			return "redirect:/404";
		}
		comment.setTopic(topic);
		topicService.addComment(comment, user);
		return "redirect:/topic/"+topic.getId();
	}
	
	@RequestMapping(value = "/topic/comment/del/{id:\\d+$}")
	@Auth
	public String delComment(HttpSession session,@PathVariable(value="id") long id) {
		User user = (User) session.getAttribute("user");
		Comment comment = topicService.getCommentById(id);
		if(comment==null) {
			return "redirect:/404";
		}
		Topic topic = comment.getTopic();
		topicService.delComment(comment);
		return "redirect:/topic/"+topic.getId();
	}
}

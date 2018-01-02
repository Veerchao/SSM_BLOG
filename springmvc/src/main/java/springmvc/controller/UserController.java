package springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springmvc.model.Article;
import springmvc.model.User;
import springmvc.service.ArticleService;

@Controller
public class UserController {
	@Autowired
	ArticleService service;

	@RequestMapping("/user")
	public ModelAndView user(ModelAndView mav, HttpSession session) {
		List<Article> articles;
		User user = new User();

		String sessionname = "游客";

		if (session.getAttribute("user_session") != null) {
			user = (User) session.getAttribute("user_session");
			sessionname = user.getUsername();
			// System.out.println(user);
		}

		articles = service.listByAuthor(user.getUsername());

		user.setUsername(sessionname);

		mav.addObject("user", user);

		if (articles == null) {
			mav.setViewName("/login.html");
		} else {

			mav.addObject("articles", articles);
			mav.setViewName("/user.html");
		}
		return mav;

	}
}

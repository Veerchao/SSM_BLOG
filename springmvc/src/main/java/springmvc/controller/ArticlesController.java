package springmvc.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import springmvc.model.Article;
import springmvc.model.User;
import springmvc.service.ArticleService;

/*
 * 1. 尝试采用REST风格。
 * */
@Controller
@RequestMapping("/articles")
public class ArticlesController {

	@Autowired
	ArticleService service;

	/*
	 * 列出所有的文章
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listarticles(ModelAndView mav, HttpSession session) {
		System.out.println("listarticles");
		List<Article> articles;
		User user = new User();
		articles = service.list();
		if (articles == null) {

		} else {

			String sessionname = "游客";
			user.setUsername("游客");
			if (session.getAttribute("user_session") != null) {
				user = (User) session.getAttribute("user_session");
				sessionname = user.getUsername();
				// System.out.println(user);
			}

			mav.addObject("user", user);

			for (Article article : articles) {
				System.out.println(article);
			}
			mav.addObject("articles", articles);
			mav.setViewName("/articles.html");
		}
		return mav;
	}

	/*
	 * 获得articleId相应的文章
	 */
	@RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
	public ModelAndView getarticleByarticleId(@PathVariable Integer articleId, ModelAndView mav, HttpSession session) {
		System.out.println("getarticleByarticleId");

		String sessionname = "游客";
		User user = new User();
		user.setUsername("游客");
		if (session.getAttribute("user_session") != null) {
			user = (User) session.getAttribute("user_session");
			sessionname = user.getUsername();
			System.out.println(user);
		}
		Article article = service.getByArticleId(articleId);
		System.out.println(article);
		mav.addObject("article", article);
		mav.addObject("user", user);
		mav.setViewName("article.html");
		return mav;

	}

	/*
	 * 新增文章
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public String addarticle(@RequestBody Article article, HttpServletRequest request) {
		System.out.println("addarticle");

		Date date = new Date();
		Timestamp createtime = new Timestamp(date.getTime());
		article.setCreatetime(createtime);
		User user = (User) request.getSession().getAttribute("user_session");
		article.setAuthor(user.getUsername());
		System.out.println(article);
		service.add(article);

		return "add success";

	}

	/*
	 * 文章修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public String updatearticleByarticleId(@RequestBody Article article) {
		System.out.println("updatearticle:" + article);
		Date date = new Date();
		Timestamp createtime = new Timestamp(date.getTime());
		article.setCreatetime(createtime);
		service.update(article);
		return "update success";
	}

	/*
	 * 删除文章
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE)
	public String deletearticleByarticleId(@RequestBody String articleids) throws IOException {
		//
		// String[] idlist = ids.split(",");
		// for (String id : idlist) {
		// service.delete(Integer.valueOf(id));
		// System.out.println("deletearticleByarticleId:" + id);
		// }
		ObjectMapper mapper = new ObjectMapper(); // 转换器

		Map m = mapper.readValue(articleids, Map.class);
		ArrayList<Object> list = new ArrayList<Object>();
		for (Object obj : m.keySet()) {
			System.out.println("key为：" + obj + "值为：" + m.get(obj).getClass().getName());
			list = (ArrayList<Object>) m.get(obj);

		}
		for (int i = 0; i < list.size(); i++) {

			LinkedHashMap hashmap = (LinkedHashMap) list.get(i);
			for (Object obj : hashmap.keySet()) {
				System.out.println(obj + " = " + hashmap.get(obj));

				try {
					service.delete(Integer.valueOf((String) hashmap.get(obj)));
				} catch (Exception e) {
					return "删除 失败";
				}

			}

			// System.out.println(list.get(i).getClass().getName());
		}

		return "delete success";

	}
}

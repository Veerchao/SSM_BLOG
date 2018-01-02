package springmvc.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import springmvc.model.User;
import springmvc.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	UserService service;

	/*
	 * 新增用户
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public String adduser(@RequestBody User user) {
		System.out.println("adduser");

		Date date = new Date();
		Timestamp createtime = new Timestamp(date.getTime());
		user.setCreatetime(createtime);

		service.add(user);

		return "add success";

	}

	/*
	 * 用户修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public String updateuserByuserId(@RequestBody User user) {
		System.out.println("updateuser:" + user);
		Date date = new Date();
		Timestamp createtime = new Timestamp(date.getTime());
		user.setCreatetime(createtime);
		service.update(user);
		return "update success";
	}

	/*
	 * 删除用户
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE)
	public String deleteuserByuserId(@RequestBody String userids) throws IOException {

		ObjectMapper mapper = new ObjectMapper(); // 转换器

		Map m = mapper.readValue(userids, Map.class);
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

		}

		return "delete success";

	}
}

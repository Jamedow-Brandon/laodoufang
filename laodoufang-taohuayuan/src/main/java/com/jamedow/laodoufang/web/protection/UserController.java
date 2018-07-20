package com.jamedow.laodoufang.web.protection;

import com.jamedow.laodoufang.entity.User;
import com.jamedow.laodoufang.entity.UserExample;
import com.jamedow.laodoufang.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {
    private final String FOLDER_PATH = "protection/";
    private final String DOMAIN_PATH = "user/";
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "list");
        List<User> users = userMapper.selectByExample(new UserExample());

        view.addObject("users", users);

        return view;
    }

    @RequestMapping(value = "form/{id}", method = RequestMethod.GET)
    public ModelAndView form(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "form");
        User user = userMapper.selectByPrimaryKey(id);

        view.addObject("user", user);

        return view;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(User user) {
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/" + DOMAIN_PATH + "list");
        userMapper.updateByPrimaryKeySelective(user);
        return view;
    }
}

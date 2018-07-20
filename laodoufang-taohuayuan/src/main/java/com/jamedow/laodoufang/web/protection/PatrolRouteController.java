package com.jamedow.laodoufang.web.protection;

import com.jamedow.laodoufang.entity.PatrolRoute;
import com.jamedow.laodoufang.entity.PatrolRouteExample;
import com.jamedow.laodoufang.mapper.PatrolRouteMapper;
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
@RequestMapping("/patrolRoute")
public class PatrolRouteController {
    private final String FOLDER_PATH = "protection/";
    private final String DOMAIN_PATH = "patrolRoute/";
    @Autowired
    private PatrolRouteMapper patrolRouteMapper;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "list");
        List<PatrolRoute> patrolRoutes = patrolRouteMapper.selectByExample(new PatrolRouteExample());

        view.addObject("patrolRoutes", patrolRoutes);

        return view;
    }

    @RequestMapping(value = "form/{id}", method = RequestMethod.GET)
    public ModelAndView form(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "form");
        PatrolRoute patrolRoute = patrolRouteMapper.selectByPrimaryKey(id);

        view.addObject("patrolRoute", patrolRoute);

        return view;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(PatrolRoute patrolRoute) {
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/" + DOMAIN_PATH + "list");
        patrolRouteMapper.updateByPrimaryKeySelective(patrolRoute);
        return view;
    }
}

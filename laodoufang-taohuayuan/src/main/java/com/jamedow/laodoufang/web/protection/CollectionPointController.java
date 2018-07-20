package com.jamedow.laodoufang.web.protection;

import com.jamedow.laodoufang.entity.CollectionPoint;
import com.jamedow.laodoufang.entity.CollectionPointExample;
import com.jamedow.laodoufang.mapper.CollectionPointMapper;
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
@RequestMapping("/collectionPoint")
public class CollectionPointController {
    private final String FOLDER_PATH = "protection/";
    private final String DOMAIN_PATH = "collectionPoint/";
    @Autowired
    private CollectionPointMapper collectionPointMapper;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "list");
        List<CollectionPoint> collectionPoints = collectionPointMapper.selectByExample(new CollectionPointExample());

        view.addObject("collectionPoints", collectionPoints);

        return view;
    }

    @RequestMapping(value = "form/{id}", method = RequestMethod.GET)
    public ModelAndView form(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "form");
        CollectionPoint collectionPoint = collectionPointMapper.selectByPrimaryKey(id);

        view.addObject("collectionPoint", collectionPoint);

        return view;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(CollectionPoint collectionPoint) {
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/" + DOMAIN_PATH + "list");
        collectionPointMapper.updateByPrimaryKeySelective(collectionPoint);
        return view;
    }
}

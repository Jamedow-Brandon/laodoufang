package com.jamedow.laodoufang.web.protection;

import com.jamedow.laodoufang.entity.Shelter;
import com.jamedow.laodoufang.entity.ShelterExample;
import com.jamedow.laodoufang.mapper.ShelterMapper;
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
@RequestMapping("/shelter")
public class ShelterController {
    private final String FOLDER_PATH = "protection/";
    private final String DOMAIN_PATH = "shelter/";
    @Autowired
    private ShelterMapper shelterMapper;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "list");
        List<Shelter> shelters = shelterMapper.selectByExample(new ShelterExample());

        view.addObject("shelters", shelters);

        return view;
    }

    @RequestMapping(value = "form/{id}", method = RequestMethod.GET)
    public ModelAndView form(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "form");
        Shelter shelter = shelterMapper.selectByPrimaryKey(id);

        view.addObject("shelter", shelter);

        return view;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(Shelter shelter) {
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/" + DOMAIN_PATH + "list");
        shelterMapper.updateByPrimaryKeySelective(shelter);
        return view;
    }
}

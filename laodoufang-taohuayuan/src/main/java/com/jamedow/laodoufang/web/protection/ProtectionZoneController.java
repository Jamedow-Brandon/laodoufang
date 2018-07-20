package com.jamedow.laodoufang.web.protection;

import com.jamedow.laodoufang.entity.ProtectionZone;
import com.jamedow.laodoufang.entity.ProtectionZoneExample;
import com.jamedow.laodoufang.mapper.ProtectionZoneMapper;
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
@RequestMapping("/protectionzone")
public class ProtectionZoneController {
    private final String FOLDER_PATH = "protection/";
    private final String DOMAIN_PATH = "protectionzone/";
    @Autowired
    private ProtectionZoneMapper protectionZoneMapper;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "list");
        List<ProtectionZone> protectionZones = protectionZoneMapper.selectByExample(new ProtectionZoneExample());

        view.addObject("protectionZones", protectionZones);

        return view;
    }

    @RequestMapping(value = "form/{id}", method = RequestMethod.GET)
    public ModelAndView form(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "form");
        ProtectionZone protectionZone = protectionZoneMapper.selectByPrimaryKey(id);

        view.addObject("protectionZone", protectionZone);

        return view;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(ProtectionZone protectionZone) {
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/" + DOMAIN_PATH + "list");
        protectionZoneMapper.updateByPrimaryKeySelective(protectionZone);
        return view;
    }
}

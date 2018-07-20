package com.jamedow.laodoufang.web.protection;

import com.jamedow.laodoufang.entity.Device;
import com.jamedow.laodoufang.entity.DeviceExample;
import com.jamedow.laodoufang.mapper.DeviceMapper;
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
@RequestMapping("/device")
public class DeviceController {
    private final String FOLDER_PATH = "protection/";
    private final String DOMAIN_PATH = "device/";
    @Autowired
    private DeviceMapper deviceMapper;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "list");
        List<Device> devices = deviceMapper.selectByExample(new DeviceExample());

        view.addObject("devices", devices);

        return view;
    }

    @RequestMapping(value = "form/{id}", method = RequestMethod.GET)
    public ModelAndView form(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "form");
        Device device = deviceMapper.selectByPrimaryKey(id);

        view.addObject("device", device);

        return view;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(Device device) {
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/" + DOMAIN_PATH + "list");
        deviceMapper.updateByPrimaryKeySelective(device);
        return view;
    }
}

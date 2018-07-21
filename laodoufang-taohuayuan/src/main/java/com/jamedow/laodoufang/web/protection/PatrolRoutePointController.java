package com.jamedow.laodoufang.web.protection;

import com.jamedow.laodoufang.entity.PatrolRoutePoint;
import com.jamedow.laodoufang.entity.PatrolRoutePointExample;
import com.jamedow.laodoufang.mapper.PatrolRoutePointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/patrolRoutePoint")
public class PatrolRoutePointController {
    private final String FOLDER_PATH = "protection/";
    private final String DOMAIN_PATH = "patrolRoutePoint/";
    @Autowired
    private PatrolRoutePointMapper patrolRoutePointMapper;

    @RequestMapping(value = "getPointsByPatrolRouteId", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public Object list(Integer patrolRouteId) {
        PatrolRoutePointExample example = new PatrolRoutePointExample();
        example.createCriteria().andPatrolRouteIdEqualTo(patrolRouteId);
        List<PatrolRoutePoint> patrolRoutePoints = patrolRoutePointMapper.selectByExample(example);
        return patrolRoutePoints;
    }

    @RequestMapping(value = "form/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ModelAndView form(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        view.setViewName(FOLDER_PATH + DOMAIN_PATH + "form");
        PatrolRoutePoint patrolRoutePoint = patrolRoutePointMapper.selectByPrimaryKey(id);

        view.addObject("patrolRoutePoint", patrolRoutePoint);

        return view;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ModelAndView edit(PatrolRoutePoint patrolRoutePoint) {
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/" + DOMAIN_PATH + "list");
        patrolRoutePointMapper.updateByPrimaryKeySelective(patrolRoutePoint);
        return view;
    }
}

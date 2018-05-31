package com.jamedow.laodoufang.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jamedow.laodoufang.entity.Recipe;
import com.jamedow.laodoufang.service.CategoryService;
import com.jamedow.laodoufang.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 365 on 2016/12/9 0009.
 */
@Controller
@RequestMapping("snack")
public class SnackController {
    private static final Logger logger = LoggerFactory.getLogger(SnackController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RecipeService recipeService;

    @RequestMapping(value = "")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("snack/snack");

//        Page categories = categoryService.queryPageSumCategories();
//        view.addObject("categories", categories);

        Page page = recipeService.queryPageSumRecipes();
        return view;
    }

    @RequestMapping(value = "snacks", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public PageInfo<Recipe> snacks() {
        return new PageInfo<Recipe>(recipeService.queryPageSumRecipes());
    }
}

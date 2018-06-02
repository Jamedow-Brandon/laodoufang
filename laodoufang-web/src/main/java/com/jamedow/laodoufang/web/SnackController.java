package com.jamedow.laodoufang.web;

import com.github.pagehelper.PageInfo;
import com.jamedow.laodoufang.entity.Recipe;
import com.jamedow.laodoufang.service.CategoryService;
import com.jamedow.laodoufang.service.RecipeService;
import com.jamedow.laodoufang.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private TestService testService;

    @RequestMapping(value = "")
    @ResponseBody
    public void index() {
//        Page categories = categoryService.queryPageSumCategories();
//        view.addObject("categories", categories);

//        Page page = recipeService.queryPageSumRecipes();
        testService.solicitation(Recipe.class);
    }

    @RequestMapping(value = "snacks", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public PageInfo<Recipe> snacks() {
        return recipeService.queryPageSumRecipes();
    }
}

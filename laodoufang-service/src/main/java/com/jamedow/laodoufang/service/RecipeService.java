package com.jamedow.laodoufang.service;

import com.github.pagehelper.PageInfo;
import com.jamedow.laodoufang.entity.Recipe;
import com.jamedow.laodoufang.entity.RecipeExample;
import com.jamedow.laodoufang.mapper.RecipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ydy on 2017/2/15.
 */
@Service
public class RecipeService {
    private Logger logger = LoggerFactory.getLogger(RecipeService.class);

    //    @Autowired
//    private ElasticSearchService esService;
    @Autowired
    private RecipeMapper recipeMapper;


    public List<Recipe> getRecipesByCategoryId(Integer categoryId) {
        RecipeExample example = new RecipeExample();
        example.createCriteria().andCategoryEqualTo(categoryId);
        return recipeMapper.selectByExample(example);
    }

    public Recipe getRecipeById(Integer recipeId) {
        return recipeMapper.selectByPrimaryKey(recipeId);
    }

    public int saveRecipe(Recipe recipe) {
        if (null != recipe.getId()) {
            return recipeMapper.updateByPrimaryKeySelective(recipe);
        }
        recipe.setIsOfficial("0");
        recipe.setCreateTime(new Date());
        int result = recipeMapper.insert(recipe);
//        esService.insertRecipeToEs(recipe);
        return result;
    }

    public PageInfo<Recipe> queryPageSumRecipes() {
        return new PageInfo<>(recipeMapper.queryPageSumRecipes(new RecipeExample()));
    }
}

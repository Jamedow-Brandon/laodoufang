package com.jamedow.laodoufang.pipeline;

import com.jamedow.laodoufang.entity.Recipe;
import com.jamedow.laodoufang.mapper.RecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.Map;

/**
 * Description
 * <p>
 * Created by Administrator on 2017/10/24.
 */
@Component
public class TestPipeline implements Pipeline {
    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> fields = resultItems.getAll();
        Recipe recipe = new Recipe();
        recipe.setName(fields.get("name").toString());
        recipe.setIntro(fields.get("intro") == null ? "" : fields.get("intro").toString());
        recipe.setDetail(fields.get("detail") == null ? "" : fields.get("detail").toString());
        recipe.setCreateTime(new Date());
        recipe.setImgUrl(fields.get("centerImg") == null ? "" : fields.get("centerImg").toString());
        recipe.setIsOfficial("0");
//        recipe.setTags(fields.get("ingredients").toString());
        recipe.setIngredient(fields.get("ingredient").toString());
        recipe.setBurdening(fields.get("burdening").toString());
        recipe.setUserId(1);
        recipeMapper.insert(recipe);
    }
}

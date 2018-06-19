package com.jamedow;

import com.jamedow.laodoufang.LaodoufangHuaYaoApplication;
import com.jamedow.laodoufang.entity.Product;
import com.jamedow.laodoufang.entity.ProductExample;
import com.jamedow.laodoufang.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaodoufangHuaYaoApplication.class)
public class LaodoufangWebApplicationTests {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductMapper productMapper;


    @Test
    public void getProductTest() {
        long startTime = new Date().getTime();

        List<Product> products = productMapper.selectByExample(new ProductExample());
        logger.info("===products:[{}]==", products);
        long endTime = new Date().getTime();

        String load = "C:/";
        String fileName = "text.txt";


        File file = new File(load + fileName);

        if (file == null) {
            logger.error("失败");
        }
    }

}

package com.jamedow.laodoufang.mongo.service;

import com.jamedow.laodoufang.mongo.bean.FormData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.mongodb.repository.MongoRepository;

@Mapper
public interface TestMongoService extends MongoRepository<FormData, String> {
}

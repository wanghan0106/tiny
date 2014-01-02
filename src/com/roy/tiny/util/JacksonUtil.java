package com.roy.tiny.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JacksonUtil {
	
	private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);
	
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper;
	}
	
	public static BasicDBObject toDBObject(Object object) throws IOException {
		ObjectMapper objectMapper = JacksonUtil.getObjectMapper();
		String json = objectMapper.writeValueAsString(object);
		return objectMapper.readValue(json, BasicDBObject.class);
	}
	
	public static <T> T toPojo(Class<T> entityClass, DBObject object) throws IOException {
		ObjectMapper objectMapper = JacksonUtil.getObjectMapper();
		object.removeField("_id");
		String json = objectMapper.writeValueAsString(object);
		return (T) objectMapper.readValue(json, entityClass);
	}

}

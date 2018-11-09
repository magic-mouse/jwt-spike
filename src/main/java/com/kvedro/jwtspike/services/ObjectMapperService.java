package com.kvedro.jwtspike.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ObjectMapperService {

    Logger log = LoggerFactory.getLogger(ObjectMapperService.class);

    public String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String mappedJson;
        mappedJson = mapper.writeValueAsString(object);
        return mappedJson;
    }

}

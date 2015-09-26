package com.wrox.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

/**
 * Created by dengb on 2015/9/11.
 */
@Component
public class DefaultObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = -3531750769248070874L;

    public DefaultObjectMapper() {
        this.findAndRegisterModules();
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
    }
}

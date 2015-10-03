package com.wrox.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrox.entities.ChatMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.*;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by Dengbin on 2015/9/29.
 */
public class ChatMessageCodec implements Encoder.TextStream<ChatMessage>, Decoder.TextStream<ChatMessage> {
    private static final Logger log = LogManager.getLogger();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.findAndRegisterModules();
        MAPPER.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    }

    @Override
    public void encode(ChatMessage chatMessage, Writer writer) throws EncodeException, IOException {
        log.entry();
        try {
            MAPPER.writeValue(writer, chatMessage);
        } catch (JsonGenerationException | JsonMappingException e) {
            throw new EncodeException(chatMessage, e.getMessage(), e);
        } finally {
            log.exit();
        }
    }

    @Override
    public ChatMessage decode(Reader reader) throws DecodeException, IOException {
        log.entry();
        try {
            return MAPPER.readValue(reader, ChatMessage.class);
        } catch (JsonGenerationException | JsonMappingException e) {
            throw new DecodeException((String) null, e.getMessage(), e);
        } finally {
            log.exit();
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}

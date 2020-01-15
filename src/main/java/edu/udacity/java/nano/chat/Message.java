package edu.udacity.java.nano.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * WebSocket message model
 */
public class Message {
    @JsonProperty
    private String msg;
    @JsonProperty
    private String username;
    @JsonProperty
    private String type;
    @JsonProperty
    private String onlineCount;

    public Message() {
        this.msg = "";
        this.type = "";
        this.username = "";
        this.onlineCount = "";
    }

    public Message(String message, String type, String username, String onlineCount) {
        this.msg = message;
        this.type = type;
        this.username = username;
        this.onlineCount = onlineCount;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(String onlineCount) {
        this.onlineCount = onlineCount;
    }

    public static String toJson(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonStr = "{}";
        try {
            jsonStr = objectMapper.writeValueAsString(message);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static Message fromJson(String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();

        Message message = null;
        try {
            message = objectMapper.readValue(jsonStr, Message.class);
        } catch (IOException e) {
            e.printStackTrace();

            message = new Message();
        }

        return message;
    }
}

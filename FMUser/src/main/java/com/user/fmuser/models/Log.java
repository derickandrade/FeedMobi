package com.user.fmuser.models;

import java.time.LocalDateTime;

public class Log {
    public String item;
    public String content;
    public LocalDateTime time;

    public Log(String item, String content, LocalDateTime time) {
        this.item = item;
        this.content = content;
        this.time = time;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

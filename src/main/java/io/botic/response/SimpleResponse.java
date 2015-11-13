package io.botic.response;

import java.util.Date;

public class SimpleResponse {
    private String text;
    private Date created;

    public SimpleResponse(String text) {
        this.text = text;
        this.created = new Date();
    }

    public SimpleResponse() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

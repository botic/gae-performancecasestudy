package io.botic.response;

import java.util.ArrayList;

public class EntityGroupResponse {
    private String text;
    private ArrayList<String> entities = new ArrayList<>();

    public EntityGroupResponse() {
    }

    public EntityGroupResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<String> entities) {
        this.entities = entities;
    }
}

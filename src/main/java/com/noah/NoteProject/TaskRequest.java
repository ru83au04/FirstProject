package com.noah.NoteProject;

public class TaskRequest {
    private final String title;
    private final String description;


    public TaskRequest(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

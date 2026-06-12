package org.example.model;

public class TodoItem {
    private int id;
    private String title;
    private String description;
    private boolean completed;
    private String createdAt;

    public TodoItem(int id, String title, String description, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public String getCreatedAt() { return createdAt; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        String status = completed ? "[✓]" : "[ ]";
        return String.format("%s #%d | %s\n    %s\n    생성: %s", status, id, title, description, createdAt);
    }
}

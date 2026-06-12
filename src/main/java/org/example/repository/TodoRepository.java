package org.example.repository;

import org.example.model.TodoItem;
import org.example.storage.JsonStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class TodoRepository {
    private final JsonStorage storage;
    private List<TodoItem> items;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TodoRepository(JsonStorage storage) {
        this.storage = storage;
        this.items = storage.load();
    }

    // CREATE
    public TodoItem create(String title, String description) {
        int nextId = items.stream().mapToInt(TodoItem::getId).max().orElse(0) + 1;
        String now = LocalDateTime.now().format(FORMATTER);
        TodoItem item = new TodoItem(nextId, title, description, now);
        items.add(item);
        storage.save(items);
        return item;
    }

    // READ ALL
    public List<TodoItem> findAll() {
        return List.copyOf(items);
    }

    // READ ONE
    public Optional<TodoItem> findById(int id) {
        return items.stream().filter(i -> i.getId() == id).findFirst();
    }

    // UPDATE
    public boolean update(int id, String title, String description, Boolean completed) {
        Optional<TodoItem> opt = findById(id);
        if (opt.isEmpty()) return false;
        TodoItem item = opt.get();
        if (title != null && !title.isBlank()) item.setTitle(title);
        if (description != null) item.setDescription(description);
        if (completed != null) item.setCompleted(completed);
        storage.save(items);
        return true;
    }

    // DELETE
    public boolean delete(int id) {
        boolean removed = items.removeIf(i -> i.getId() == id);
        if (removed) storage.save(items);
        return removed;
    }
}

package org.example.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.TodoItem;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage {
    private final String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type listType = new TypeToken<List<TodoItem>>() {}.getType();

    public JsonStorage(String filePath) {
        this.filePath = filePath;
    }

    public List<TodoItem> load() {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            List<TodoItem> items = gson.fromJson(reader, listType);
            return items != null ? items : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("데이터 로드 실패: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void save(List<TodoItem> items) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            gson.toJson(items, writer);
        } catch (IOException e) {
            System.err.println("데이터 저장 실패: " + e.getMessage());
        }
    }
}

package org.example;

import org.example.model.TodoItem;
import org.example.repository.TodoRepository;
import org.example.storage.JsonStorage;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "todos.json";
    private static TodoRepository repo;
    private static Scanner scanner;

    public static void main(String[] args) {
        repo = new TodoRepository(new JsonStorage(DATA_FILE));
        scanner = new Scanner(System.in);

        System.out.println("=== To Do List (JSON 저장) ===");
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> createTodo();
                case "2" -> listTodos();
                case "3" -> updateTodo();
                case "4" -> deleteTodo();
                case "5" -> toggleComplete();
                case "0" -> { System.out.println("종료합니다."); return; }
                default -> System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. 할 일 추가 (Create)");
        System.out.println("2. 전체 목록 보기 (Read)");
        System.out.println("3. 할 일 수정 (Update)");
        System.out.println("4. 할 일 삭제 (Delete)");
        System.out.println("5. 완료 토글 (Toggle)");
        System.out.println("0. 종료");
        System.out.print("선택 > ");
    }

    private static void createTodo() {
        System.out.print("제목: ");
        String title = scanner.nextLine().trim();
        if (title.isBlank()) { System.out.println("제목은 필수입니다."); return; }
        System.out.print("설명: ");
        String desc = scanner.nextLine().trim();
        TodoItem item = repo.create(title, desc);
        System.out.println("추가됨: " + item.getTitle() + " (ID: " + item.getId() + ")");
    }

    private static void listTodos() {
        List<TodoItem> items = repo.findAll();
        if (items.isEmpty()) { System.out.println("등록된 할 일이 없습니다."); return; }
        System.out.println("\n--- 할 일 목록 ---");
        items.forEach(item -> System.out.println(item + "\n"));
    }

    private static void updateTodo() {
        System.out.print("수정할 ID: ");
        int id = readInt(); if (id < 0) return;
        Optional<TodoItem> opt = repo.findById(id);
        if (opt.isEmpty()) { System.out.println("해당 ID를 찾을 수 없습니다."); return; }
        TodoItem current = opt.get();
        System.out.println("현재 제목: " + current.getTitle() + " (빈칸 입력 시 유지)");
        System.out.print("새 제목: ");
        String title = scanner.nextLine().trim();
        System.out.println("현재 설명: " + current.getDescription());
        System.out.print("새 설명: ");
        String desc = scanner.nextLine().trim();
        repo.update(id, title.isEmpty() ? null : title, desc.isEmpty() ? null : desc, null);
        System.out.println("수정 완료.");
    }

    private static void deleteTodo() {
        System.out.print("삭제할 ID: ");
        int id = readInt(); if (id < 0) return;
        if (repo.delete(id)) System.out.println("삭제 완료.");
        else System.out.println("해당 ID를 찾을 수 없습니다.");
    }

    private static void toggleComplete() {
        System.out.print("토글할 ID: ");
        int id = readInt(); if (id < 0) return;
        Optional<TodoItem> opt = repo.findById(id);
        if (opt.isEmpty()) { System.out.println("해당 ID를 찾을 수 없습니다."); return; }
        boolean next = !opt.get().isCompleted();
        repo.update(id, null, null, next);
        System.out.println(next ? "완료 처리됨." : "미완료로 변경됨.");
    }

    private static int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.");
            return -1;
        }
    }
}

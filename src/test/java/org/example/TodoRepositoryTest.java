package org.example;

import org.example.model.TodoItem;
import org.example.repository.TodoRepository;
import org.example.storage.JsonStorage;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TodoRepositoryTest {
    private static final String TEST_FILE = "test_todos.json";
    private TodoRepository repo;

    @BeforeEach
    void setUp() {
        new File(TEST_FILE).delete();
        repo = new TodoRepository(new JsonStorage(TEST_FILE));
    }

    @AfterEach
    void tearDown() {
        new File(TEST_FILE).delete();
    }

    @Test
    void create_저장후_조회() {
        TodoItem item = repo.create("공부하기", "자료구조 복습");
        assertEquals(1, item.getId());
        assertEquals("공부하기", item.getTitle());
        assertFalse(item.isCompleted());

        List<TodoItem> all = repo.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void create_여러개_ID_자동증가() {
        repo.create("첫 번째", "");
        repo.create("두 번째", "");
        TodoItem third = repo.create("세 번째", "");
        assertEquals(3, third.getId());
        assertEquals(3, repo.findAll().size());
    }

    @Test
    void findById_존재() {
        repo.create("운동하기", "30분 달리기");
        Optional<TodoItem> found = repo.findById(1);
        assertTrue(found.isPresent());
        assertEquals("운동하기", found.get().getTitle());
    }

    @Test
    void findById_없음() {
        assertTrue(repo.findById(999).isEmpty());
    }

    @Test
    void update_제목_수정() {
        repo.create("옛날 제목", "설명");
        boolean ok = repo.update(1, "새 제목", null, null);
        assertTrue(ok);
        assertEquals("새 제목", repo.findById(1).get().getTitle());
    }

    @Test
    void update_완료_토글() {
        repo.create("할 일", "");
        repo.update(1, null, null, true);
        assertTrue(repo.findById(1).get().isCompleted());
        repo.update(1, null, null, false);
        assertFalse(repo.findById(1).get().isCompleted());
    }

    @Test
    void delete_정상삭제() {
        repo.create("삭제 대상", "");
        assertTrue(repo.delete(1));
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void delete_없는ID() {
        assertFalse(repo.delete(999));
    }

    @Test
    void json_영속성_재로드() {
        repo.create("영속성 테스트", "JSON 저장 확인");
        TodoRepository reloaded = new TodoRepository(new JsonStorage(TEST_FILE));
        assertEquals(1, reloaded.findAll().size());
        assertEquals("영속성 테스트", reloaded.findById(1).get().getTitle());
    }
}

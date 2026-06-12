# DataPersistence-SunbinKwon-22020106

JSON 파일을 이용한 데이터 영속성 구현 실습 — To Do List CRUD 예제

## 프로젝트 개요

- **과목**: 데이터 영속성 (Data Persistence)
- **학번**: 22020106
- **이름**: Sunbin Kwon
- **언어**: Java 21
- **빌드**: Gradle (Kotlin DSL)
- **JSON 라이브러리**: Gson 2.11.0

## 기능

| 기능 | 설명 |
|------|------|
| **Create** | 제목·설명을 입력해 할 일 추가, ID 자동 증가 및 생성 시각 기록 |
| **Read** | 전체 목록 조회 / ID로 단건 조회 |
| **Update** | 제목·설명 수정, 완료 상태 변경 |
| **Delete** | ID로 할 일 삭제 |
| **Toggle** | 완료 ↔ 미완료 토글 |
| **영속성** | 모든 변경 사항을 `todos.json`에 즉시 저장, 재실행 후에도 데이터 유지 |

## 프로젝트 구조

```
src/
├── main/java/org/example/
│   ├── model/
│   │   └── TodoItem.java          # 데이터 모델 (id, title, description, completed, createdAt)
│   ├── storage/
│   │   └── JsonStorage.java       # Gson 기반 JSON 파일 읽기/쓰기
│   ├── repository/
│   │   └── TodoRepository.java    # CRUD 비즈니스 로직
│   └── Main.java                  # CLI 진입점
└── test/java/org/example/
    └── TodoRepositoryTest.java    # 단위 테스트 (9개)
```

## 데이터 저장 형식

실행 디렉터리에 `todos.json` 파일로 저장됩니다.

```json
[
  {
    "id": 1,
    "title": "공부하기",
    "description": "자료구조 복습",
    "completed": false,
    "createdAt": "2026-06-12 15:30:00"
  }
]
```

## 실행 방법

```bash
# 빌드
./gradlew build

# 테스트
./gradlew test

# 실행 (IntelliJ에서 Main.java 직접 실행 권장)
./gradlew run
```

## 실행 화면

```
=== To Do List (JSON 저장) ===

1. 할 일 추가 (Create)
2. 전체 목록 보기 (Read)
3. 할 일 수정 (Update)
4. 할 일 삭제 (Delete)
5. 완료 토글 (Toggle)
0. 종료
선택 >
```

## 의존성

```kotlin
implementation("com.google.code.gson:gson:2.11.0")
```

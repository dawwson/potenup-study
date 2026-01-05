package com.ohgiraffers.chap01.section02;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

public class TestLifeCycleTest {

    // 사용자 DTO
    static class User {
        private final String id;
        private final String name;
        User(String id, String name) { this.id = id; this.name = name; }
        String getId() { return id; }
        String getName() { return name; }
    }


    // 테스트 대상 클래스 (SUT)
    static class UserRepository {
        private final DatabaseConnectionMocker connection;
        // '트랜잭션' 시뮬레이션: 이 Map은 @AfterEach에서 리셋된다.
        private final Map<String, User> transactionContext;

        UserRepository(DatabaseConnectionMocker connection) {
            this.connection = connection;
            this.transactionContext = new HashMap<>(); // 트랜잭션 범위의 저장소
        }

        void beginTransaction() {
            System.out.println("      [SUT] 트랜잭션 시작.");
            this.transactionContext.clear(); // 새 트랜잭션을 위해 컨텍스트 초기화
        }

        void save(User user) {
            System.out.println("      [SUT] " + user.getId() + " 저장 시도 (트랜잭션 컨텍스트).");
            this.transactionContext.put(user.getId(), user);
        }

        User find(String id) {
            System.out.println("      [SUT] " + id + " 조회 시도 (트랜잭션 컨텍스트).");
            return this.transactionContext.get(id);
        }

        void rollbackTransaction() {
            System.out.println("      [SUT] 트랜잭션 롤백. (저장된 " + transactionContext.size() + "개 내용 초기화)");
            this.transactionContext.clear();
        }
    }

    // DB 커넥션 시뮬레이션 (비싼 자원)
    static class DatabaseConnectionMocker {
        void connect() { System.out.println("  [DB Mocker] ...연결 중... 연결 성공."); }
        void disconnect() { System.out.println("  [DB Mocker] ...연결 해제 중... 해제 성공."); }
    }

    // ------------------------------------------

    /*
     * [자원 1] 공유 자원(비싼 자원)
     * - static으로 선언
     * - 이유 : @BeforeAll(static) 메서드에서 초기화하고 @AfterAll(static)에서 해제할 자원.
     * - "TestLifeCycleTest" 클래스 전체에서 하나만 존재해야 한다.
     */
    private static DatabaseConnectionMocker databaseConnectionMocker;

    /*
     * [자원 2] 격리 대상 (sut)
     * - static이 아니다.
     * - 이유 : @BeforeEach 메서드에서 매번 새로 생성하여 독립적인 텟트 환경을 구축하기 위한 대상
     * 1. 일관된 테스트 패턴 유지 : 모든 테스트가 독립적으로 실행 가능한 단위
     * 2. 확장성 : 레포가 캐싱을 갖게 되는 경우 다른 테스트에 영향
     */
    private UserRepository userRepository;

    /*
     * @BeforeAll: 클래스 단위 (최초 1회)
     * [문법]
     * - "BeforeAll" 어노테이션을 메서드 위에 붙인다.
     * - 메서드는 "반드시" static이어야 한다.
     *
     * [개념 : 실행 시점]
     * - TestLifeCycleTest : 클래스의 모든 @Test가 실행되기 전, 딱 한 번 실행된다.
     *
     * [필요성]
     * - junit은 기본적으로 @Test마다 새로운 인스턴스를 만드는데,
     *   이 메서드는 인스턴스가 존재하기도 전에, 클래스 로딩 시점에 실행되어야 하기 때문이다.
     * - 용도 : DB 연결, 테스트 서버 구동 중 "비용이 비싸서" 한 번만 수행해야 하는 공유 자원을 초기화 하는 데 사용한다.
     */
    @BeforeAll
    static void setupSharedDatabaseConnection() {
        System.out.println("== @BeforeAll: 비용이 큰 작업 DB 커넥션 성공 (클래스 당 1회)");
        databaseConnectionMocker = new DatabaseConnectionMocker();
        databaseConnectionMocker.connect();
    }

    /*
     * @BeforeEach : 테스트 단위 준비 (매번)
     * [문법]
     * - @BeforeEach 어노테이션ㅇㄹ 메서드 위헤 붙인다.
     * - 메서드는 static이 아닌 인스턴스 메서드여야 한다.
     *
     * [개념 : 실행 시점]
     * - 각각의 @Test 메서드(saveUserTest, findUserTest)가 실행되기 직전에 항상 실행된다.
     *
     * [필요성]
     * - @Test가 실행될 인스턴스가 생성된 직후, 해당 인스턴스의 환경을 초기화 하는 역할이다.
     * - 용도 : 테스트 격리가 핵심 목적
     */
    @BeforeEach
    void setupForEachTest() {
        System.out.println("Each: 테스트 격리를 위한 sut 생성 및 트랜잭션 시작");
        this.userRepository = new UserRepository(databaseConnectionMocker);

        this.userRepository.beginTransaction();
        System.out.println("[BeforeEach] 트랜잭션 준비");
    }

    @Test
    @DisplayName("사용자 저장 테스트 : 사용자를 저장하면 해당 트랜잭션 내에서 조회가 가능하다.")
    void saveUserTest_Should_Be_Found_In_SameTransaction() {
        System.out.println("사용자 저장 테스트 실행 중");

        User user = new User("userA", "홍길동");

        userRepository.save(user);

        User foundUser = userRepository.find("userA");
        Assertions.assertNotNull(foundUser, "userA가 null이면 안된다.");

        Assertions.assertEquals("홍길동", foundUser.getName());
        System.out.println("테스트 1 저장 및 조회 완료");
    }

    @Test
    @DisplayName("사용자 조회 테스트 : 저장하지 않은 사용자는 null을 반환해야 한다.")
    void findUserTest_Should_Be_Return_Null_Not_Saved() {
        System.out.println("테스트 2번 미저장 사용자 조회 테스트");

        User foundUser = userRepository.find("userA");
        Assertions.assertNull(foundUser, "userA는 이 트랜잭션에 저장된 적 없으므로 null이어야 한다.");
        System.out.println("테스트 2 종료");

    }
    /*
     * @AfterEach
     * [문법]
     * - @AfterEach : 어노테이션을 메서드 위에 붙인다.
     * - @BeforeEach와 마찬가지로 static이 아닌 인스턴스 메서드여야 한다.
     *
     * [개념 : 실행 시점]
     * - 각각의 @Test 메서드가 (성공핟ㄴ, 실패하든) 실행된 직후에 항상 실행된다.
     */
    @AfterEach
    void tearDownForEachTest() {
        System.out.println("");

    }

    /*
     * AfterAll : 클래스 단위
     * [문법]
     * - @AfterAll : 어노테이션을 메서드 위에 붙인다.
     * - @BeforeAll과 마찬가지로 반드시 static이여야 한다.
     *
     * [개념 : 실행 시점]
     * - 클래스의 모든 @Test가 완료된 후, "딱 한 번" 실행된다.
     *
     * [필요성]
     * - @BeforeAll과 대칭을 이룬다.
     * - 용도 : @BeforeAll에서 설정했던 공유 자원(DB 커넥션, 테스트 버더 등)을 최종 해제한다.
     */
    @AfterAll
    static void tearDownAfterTest() {}
}

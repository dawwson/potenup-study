# JDBC란 무엇인가?
## ✅ JDBC(Java Database Connectivity)란?
- JDBC는 Java에서 데이터베이스(DB)와 연결하기 위한 API(응용 프로그램 인터페이스) <br>
  Java 프로그램에서 SQL을 실행하고 DB의 데이터를 읽고, 쓰고, 수정하고 삭제할 수 있도록 지원 <br>
  Java와 데이터베이스 간의 브리지 역할을 수행하는 표준 인터페이스


### ✅ JDBC의 주요 기능
| 기능          | 설명                                               |
|---------------|----------------------------------------------------|
| DB 연결       | Java 프로그램에서 DB 서버와 연결                 |
| SQL 실행      | SELECT, INSERT, UPDATE, DELETE 등 SQL 문 실행     |
| 결과 처리     | SQL 실행 결과를 Java에서 처리                    |
| 트랜잭션 관리 | 여러 SQL 문을 하나의 트랜잭션으로 관리 (commit, rollback) |

## 2. JDBC의 역할 및 필요성
### ✅ 왜 JDBC가 필요한가?
#### 1️⃣ 데이터베이스와 Java 프로그램을 연결
- JDBC를 사용하면 MySQL, Oracle, PostgreSQL, SQLite 등 다양한 DBMS와 연결 가능
- DBMS마다 드라이버만 변경하면 Java 코드 변경 없이 다양한 DB를 사용할 수 있음

#### 2️⃣ 표준화된 API 제공
- SQL 실행 및 결과 처리를 위한 일관된 인터페이스 제공
- 개발자가 특정 DBMS에 종속되지 않고, 표준 방식으로 코드를 작성 가능

#### 3️⃣ SQL Injection 방지
- PreparedStatement를 활용하여 보안 강화
- 사용자 입력값을 안전하게 처리하여 SQL 공격을 방어

#### 4️⃣ 트랜잭션 관리
- commit()과 rollback()을 활용하여 데이터 정합성 유지
- 예: 결제 시스템에서 데이터가 중간에 손실되지 않도록 보장


## 3. JDBC 아키텍처 및 동작 방식
### ✅ JDBC 아키텍처 구성
- JDBC는 Java 애플리케이션과 데이터베이스(DBMS) 사이에서 동작하며, 다음과 같은 구조를 가짐.

#### 📌 JDBC 아키텍처 구성 요소
#### JDBC API
- Java 프로그램에서 DB와 상호 작용할 수 있도록 제공하는 API
- java.sql 패키지에 포함 (Connection, Statement, ResultSet 등)

#### JDBC Driver
- Java에서 DBMS에 연결할 수 있도록 도와주는 드라이버 프로그램
- DBMS마다 제공하는 전용 JDBC 드라이버를 사용해야 함 (MySQL, Oracle, PostgreSQL 등)

#### DBMS (Database Management System)
- 실제 데이터를 저장하고 SQL 문을 처리하는 데이터베이스

```css
[Java Application] → [JDBC API] → [JDBC Driver] → [DBMS]
```


## 📌 JDBC 드라이버 종류 및 데이터베이스별 설정

### **1. JDBC 드라이버 종류**
- JDBC 드라이버는 Java 애플리케이션과 데이터베이스(DBMS)를 연결하는 역할을 한다.
- JDBC 드라이버는 크게 **4가지 유형**으로 분류된다.

#### **✅ 1️⃣ Type 1: JDBC-ODBC 브리지 드라이버**
- ODBC(Open Database Connectivity)를 사용하여 DBMS와 연결하는 방식
- **Java → JDBC API → ODBC Driver → DBMS**
- **운영체제(OS)의 ODBC 드라이버에 의존**하므로 플랫폼 종속적임
- **단점**: 속도가 느리고, 운영체제마다 ODBC 드라이버 설치가 필요
- **현재는 사용되지 않음 (Java 8 이후로 삭제됨)**

#### ✅ 2️⃣ Type 2: 네이티브 API 드라이버
- DBMS의 네이티브 C/C++ 라이브러리를 사용하여 DB와 연결
- Java → JDBC API → 네이티브 드라이버 → DBMS
- Oracle, MySQL 등에서 제공하는 네이티브 클라이언트 라이브러리가 필요함
- 장점: 성능이 빠름
- 단점: 특정 DBMS에 종속되며, 클라이언트 환경에 따라 설정이 필요

```java
Connection conn = DriverManager.getConnection("jdbc:oracle:oci:@MyOracleDB", "user", "password");
```

#### **✅ 4️⃣ Type 3: 순수 Java 드라이버 (Thin Driver) → ⭐ 추천 ⭐
- Java만으로 구현된 가장 빠르고 효율적인 드라이버
- Java → JDBC API → DBMS
- DBMS별로 제공되는 드라이버 사용
- 장점: 플랫폼 독립적이며, 추가 설치 없이 사용 가능
- 단점: DBMS마다 전용 드라이버가 필요함



--------------------------

## **📌 SQL 실행: Statement, PreparedStatement, CallableStatement**

- JDBC에서 SQL을 실행하는 방법에는 **Statement, PreparedStatement, CallableStatement** 3가지가 있습니다.
- 각 방법은 **보안성, 성능, 사용 용도**가 다르므로 적절한 상황에 맞게 사용해야 합니다.


### **1. Statement (일반 SQL 실행)**
#### **✅ Statement란?**
- 가장 기본적인 **SQL 실행 객체**
- `Statement` 객체를 사용하여 **정적 SQL(고정된 SQL)**을 실행
- 보안 취약(SQL Injection 공격 가능)
- **성능이 낮음** (매번 SQL을 파싱해야 함)

#### **✅ Statement 사용법**
##### **📌 1️⃣ Statement 객체 생성**
```java
Statement stmt = connection.createStatement();
```

### 2. PreparedStatement (보안 강화 및 성능 향상)
#### ✅ PreparedStatement란?
- SQL 실행 시 미리 컴파일하여 성능 최적화
- SQL Injection 공격 방지 (사용자 입력을 자동으로 이스케이프)
- **변수 바인딩 (? 사용)**으로 가독성 향상

#### ✅ PreparedStatement 사용법
##### 📌 1️⃣ PreparedStatement 객체 생성
```java
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement pstmt = connection.prepareStatement(sql);
```

##### 📌 2️⃣ 값 설정 (변수 바인딩)
```java
pstmt.setString(1, "john_doe");  // 첫 번째 `?`에 값 설정
ResultSet rs = pstmt.executeQuery();
```

##### 📌 3️⃣ SQL 실행 (INSERT, UPDATE, DELETE)
```java
String insertSql = "INSERT INTO users (username, email, password_hash, role_id) VALUES (?, ?, ?, ?)";
PreparedStatement pstmt = connection.prepareStatement(insertSql);
pstmt.setString(1, "John");
pstmt.setString(2, "");
pstmt.setString(3, "hashed_password");
pstmt.setInt(4, 2);

int result = pstmt.executeUpdate();
System.out.println(result + "개의 행이 추가됨");
```
> ✅ PreparedStatement의 장점 <br>
✅ SQL Injection 방지 <br>
✅ SQL 재사용 가능 (미리 컴파일되어 빠름) <br>
✅ 가독성 증가 (변수 바인딩 사용)


## 3. CallableStatement (스토어드 프로시저 호출)
### ✅ CallableStatement란?
- DBMS의 스토어드 프로시저(Stored Procedure)를 실행하는 객체
- 복잡한 트랜잭션 처리 시 유용
- 입력 매개변수 (IN), 출력 매개변수 (OUT) 지원
### ✅ CallableStatement 사용법
#### 📌 1️⃣ 스토어드 프로시저 생성 (MySQL 예제)
```java
DELIMITER //
CREATE PROCEDURE getUserById(IN userId INT)
BEGIN
    SELECT * FROM users WHERE user_id = userId;
END //
DELIMITER ;
```
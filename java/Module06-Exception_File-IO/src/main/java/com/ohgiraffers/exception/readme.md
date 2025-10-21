# 예외(Exception)와 오류(Error)에 대한 이해와 활용

프로그래밍에서 예외(Exception)와 오류(Error)는 코드 실행 중 발생할 수 있는 비정상적인 상황을 다루는 핵심 개념이다.

## 1. 예외(Exception)와 오류(Error)의 기본 개념

### 1.1 예외란?
예외는 프로그램 실행 중 발생하는 예상치 못한 상황으로, 이를 적절히 처리하지 않으면 프로그램이 종료되거나 비정상적인 동작을 일으킬 수 있다. <br>
- 예를 들어 파일을 열 때 파일이 존재하지 않거나, 숫자를 0으로 나누는 경우 등이 이에 해당한다.

### 1.2 오류(Error)란?
오류는 예외보다 더 심각한 문제로, 주로 복구가 불가능한 시스템 수준의 문제를 의미한다.
- 예를 들어 메모리 부족(`OutOfMemoryError`)이나 스택 오버플로우(`StackOverflowError`) 같은 경우이다.

### 차이점
- **예외(Exception)**: 코드에서 처리 가능하며, 프로그램의 흐름을 제어할 수 있다.
- **오류(Error)**: 일반적으로 코드로 해결하기 어렵고, JVM이나 하드웨어 문제와 관련된다.

---

## 2. 예외와 오류의 상속 계층도
Java를 기준으로 예외와 오류는 `Throwable` 클래스에서 파생된다.
```aiignore
Throwable
├── Error                           // 심각한 문제로, 일반적으로 프로그램에서 처리하지 않음
│   ├── VirtualMachineError         // JVM에서 발생하는 오류
│   │   ├── OutOfMemoryError       // 메모리가 부족할 때 발생
│   │   └── StackOverflowError      // 스택 메모리가 초과할 때 발생
│   ├── LinkageError                // 클래스와 관련된 문제
│   │   ├── NoClassDefFoundError    // 클래스 정의를 찾을 수 없을 때 발생
│   │   └── ClassCastException      // 잘못된 타입 변환 시 발생 (예외로 오해되기 쉬움, 실제로는 Error 아님)
│   └── AssertionError              // Assertion 문이 실패했을 때 발생
└── Exception                        // 일반적인 예외 처리
    ├── RuntimeException (Unchecked Exception) // 런타임 중 발생할 수 있는 예외
    │   ├── NullPointerException     // null 참조에 접근할 때 발생
    │   ├── IndexOutOfBoundsException // 배열 인덱스가 범위를 초과할 때 발생
    │   ├── IllegalArgumentException  // 잘못된 인자가 제공되었을 때 발생
    │   └── ArithmeticException      // 수학적 연산 오류 (예: 0으로 나누기)
    ├── IOException                  // 입출력 작업 중 발생하는 예외
    ├── SQLException                 // 데이터베이스 접근 중 발생하는 예외
    └── ClassNotFoundException       // 지정한 클래스를 찾지 못했을 때 발생 (Checked Exception 예시)

```


### 2.1 상속 계층도 설명
- **`Throwable`**: 모든 예외와 오류의 최상위 클래스이다.
- **`Error`**: 시스템 수준의 심각한 문제로, 개발자가 직접 처리하기 어렵다.
- **`Exception`**: 개발자가 처리해야 하는 예외로, 크게 두 가지로 나뉘게 된다.
    - **Checked Exception**: 컴파일 시점에 반드시 처리해야 하는 예외 (e.g., `IOException`).
    - **Unchecked Exception**: 런타임에 발생하며, 명시적으로 처리하지 않아도 컴파일이 가능하다 (e.g., `NullPointerException`).

> 📌 Unchecked Exception  <br>
> 초보 개발자의 입장에서 보면 Unchecked Exception가 더 좋아보일 수 있다. <br>
> 그러나 Unchecked Exception는 프로그램이 실행하면서 발생하는 오류로 실제 서비스가 운영중에 발생하게되어 치명적이며 <br>
> 테스트를 하지 않고 오류를 알 수 없기 때문에 주의 해야한다.
---

## 3. 예외의 종류

### 3.1 자주 만나는 예외
- **`NullPointerException`**: 객체 참조가 `null`일 때 발생한다.
  ```java
      String str = null;
      System.out.println(str.length()); // NullPointerException 발생
  ```
- ArithmeticException: 0으로 나누기와 같은 산술 오류이다.
  ```java
    int result = 10 / 0; // ArithmeticException 발생
  ```
    - ArrayIndexOutOfBoundsException: 배열의 잘못된 인덱스 접근이다.
  ```java
    int[] arr = {1, 2, 3};
    System.out.println(arr[5]); // ArrayIndexOutOfBoundsException 발생
  ```

---
### 3.2 Checked vs Unchecked
- Checked Exception: 메서드 선언부에 throws로 명시하거나 try-catch로 처리해야 한다.
    ```java
        import java.io.File;
        import java.io.FileReader;
        
        public void readFile() throws IOException {
            FileReader fr = new FileReader(new File("file.txt"));
        }
    ```

- Unchecked Exception: 명시적 처리가 필수가 아니다.
    ```java
        public void divide(int a, int b) {
          System.out.println(a / b); // b가 0이면 ArithmeticException 발생
        }
    ```

### 3.3 커스텀 예외와 예외 체인
- 커스텀 예외: 특정 상황에 맞는 예외를 직접 정의한다.
    ```java
        public class InsufficientBalanceException extends Exception {
            public InsufficientBalanceException(String message) {
                super(message);
            }
        }
        
        public void withdraw(int amount) throws InsufficientBalanceException {
            if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance");
            }
        }
    ```

- 예외 체인: 원인 예외를 연결해 상세 정보를 제공한다.
    ```java
        try {
            methodThatThrows();
        } catch (IOException e) {
            throw new CustomException("Operation failed", e);
        }
    ```

----

## 4. 예외를 사용하지 않으면 발생하는 문제
### 4.1 프로그램 종료
- 예외를 처리하지 않으면 프로그램이 비정상 종료된다.
    ```java
        public class Main {
            public static void main(String[] args) {
                int[] numbers = {1, 2, 3};
                System.out.println(numbers[10]); // 예외 발생 후 종료
                System.out.println("This line will not execute");
            }
        }
    ```

### 4.2 디버깅 어려움
- 예외를 무시하면 어디서 문제가 발생했는지 추적하기 어렵다.
    ```java
        public void processData(String input) {
            int value = Integer.parseInt(input); // NumberFormatException 발생 가능
            System.out.println(value * 2);
        }
    ```
    - 위 코드는 input이 숫자가 아니면 실패하지만, 원인을 파악하기 어렵다.

### 4.3 시스템 안정성 저하
- 복잡한 애플리케이션에서 예외를 무시하면 연쇄적인 실패가 발생할 수 있다.
  > 예: 데이터베이스 연결 실패 → 트랜잭션 롤백 실패 → 데이터 무결성 손상.


------

## 5. 예외 사용하기
### 5.1 기본적인 try-catch 사용
```java
    try {
        int result = 10 / 0;
    } catch (ArithmeticException e) {
        System.out.println("Cannot divide by zero!");
    }
```

### 5.2 구체적 예외 처리와 리소스 관리

- 구체적 예외 처리 :
    ```java
        try {
            int[] arr = {1, 2};
            System.out.println(arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of bounds: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Other exception: " + e);
        }
    ```

- try-with-resources로 리소스 관리
    ```java
    try (FileReader fr = new FileReader("file.txt")) {
        int data = fr.read();
    } catch (IOException e) {
        System.out.println("File read failed: " + e.getMessage());
    }
    ```

----

### 5.3 예외 전략 설계
- 의미 있는 메시지 제공 :
    ```java
    throw new IllegalArgumentException("Input must be greater than 0: " + input);
    ```

- 예외 로깅:
    ```java
    import java.util.logging.Logger;
    
    Logger logger = Logger.getLogger("MyApp");
    try {
        riskyOperation();
    } catch (Exception e) {
        logger.severe("Error occurred: " + e.getMessage());
        throw e; // 필요 시 상위로 전달
    }
    ```

- 예외 복구 로직 :
    ```java
    int retries = 3;
    while (retries > 0) {
        try {
            connectToServer();
            break;
        } catch (IOException e) {
            retries--;
            if (retries == 0) throw e;
            Thread.sleep(1000); // 재시도 전 대기
        }
    }
    ```

---

# 결론
> 예외 처리는 단순한 에러 방지를 넘어, 견고하고 유지보수 가능한 코드를 작성하는 데 필수적입니다.
> 예외를 무시하지 않고, 적절한 try-catch 사용, 리소스 관리,
> 그리고 시스템 안정성을 위한 전략적인 설계를 통해 코드의 품질을 높일 수 있다. 

# 1. 딕셔너리 생성 (Type Hint: dict[Key타입, Value타입])
# 즉, 키는 문자열, 값은 문자열 또는 정수인 딕셔너리
user: dict[str, str | int] = {"user_id": "neuron_01", "name": "홍길동", "level": 10}

print("--- [1] 초기 사용자 정보 ---")
print(f"전체 데이터: {user}")
print(f"사용자 이름: {user['name']}")  # 키로 직접 접근

# 2. 데이터 추가 및 수정
user["email"] = ""  # 새로운 키 추가
user["level"] = 11  # 기존 키의 값 수정

print("\n--- [2] 데이터 업데이트 후 ---")
print(f"수정된 레벨: {user['level']}")
print(f"추가된 이메일: {user['email']}")

# 3. 안전한 데이터 조회 (get)
# 존재하지 않는 키를 요청할 때의 차이점 확인
print("\n--- [3] 안전한 조회 테스트 ---")
# point = user["point"] # 이 코드는 에러를 발생시켜 프로그램을 멈춥니다.
point = user.get("point", 0)  # 키가 없으면 기본값 0을 반환
print(f"보유 포인트: {point}")

# 4. 데이터 삭제
del user["user_id"]
print("\n--- [4] 데이터 삭제 후 ---")
print(f"남은 키들: {user.keys()}")  # keys()는 딕셔너리의 키 목록을 보여줍니다.

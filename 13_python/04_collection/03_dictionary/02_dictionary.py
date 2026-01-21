# [1] 중첩 딕셔너리 (Nested Dictionary)
# 회원 ID를 알면 해당 회원의 상세 정보 딕셔너리에 즉시 접근 가능
# dict의 타입을 생략하면 dict[any, any]가 되어 버리며 이는 어떤 타입을 써도 된다는 뜻이므로 지양해야 합니다.
sessions: dict[str, dict] = {
    "user_101": {"name": "김철수", "status": "Online", "ip": ""},
    "user_102": {"name": "이영희", "status": "Away", "ip": ""},
}

print("--- [1] 세션 관리 시스템 가동 ---")
target_user = "user_101"
print(f"조회 대상: {target_user}")
print(f"사용자 이름: {sessions[target_user]['name']}")
print(f"접속 IP: {sessions[target_user].get('ip')}")

# [2] 튜플을 키로 활용하기
# (서버구역, 층수)를 튜플로 묶어 고유한 위치 식별자로 사용
server_status: dict[tuple[str, int], str] = {
    ("A구역", 1): "Normal",
    ("A구역", 2): "Warning",
    ("B구역", 1): "Critical",
}

print("\n--- [2] 서버 위치별 상태 조회 ---")
location = ("A구역", 2)
status = server_status.get(location)
print(f"위치 {location}의 상태: {status}")

# [3] 데이터 유무 확인 (in 연산자)
# 불필요한 에러를 막기 위해 키가 있는지 먼저 확인합니다.
check_id = "user_103"
if check_id in sessions:  # 3.2에서 배운 논리 연산의 응용
    print(f"{check_id}가 시스템에 존재합니다.")
else:
    print(f"[경고] {check_id}는 미등록 사용자입니다.")

"""
[심화 분석: 왜 이 방식이 효율적인가?]
1. 계층적 구조: 세션 딕셔너리 안에 또 다른 딕셔너리를 넣음으로써
   데이터를 논리적으로 구조화할 수 있습니다.
2. 튜플 키의 활용: 리스트는 불가능하지만 튜플은 해시가 가능하므로,
   좌표나 다중 속성을 하나의 키로 묶을 때 매우 유용합니다.
3. 존재 확인 최적화: 'in' 연산자 역시 해시 테이블을 이용하므로
   리스트에서 값을 찾는 것보다 수만 배 빠릅니다.
"""

"""
실행 결과:
--- [1] 세션 관리 시스템 가동 ---
조회 대상: user_101
사용자 이름: 김철수
접속 IP: 1.2.3.4

--- [2] 서버 위치별 상태 조회 ---
위치 ('A구역', 2)의 상태: Warning

--- [3] 데이터 유무 확인 ---
[경고] user_103는 미등록 사용자입니다.
"""

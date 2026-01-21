# 1. 센서 이름 리스트 생성 (Type Hints 사용)
# 리스트는 List[타입] 또는 list[타입]으로 힌트를 줍니다.
sensors: list[str] = ["Temp_A", "Temp_B", "Humidity_A", "Pressure_V"]

print(f"--- [1] 전체 센서 목록 ---")
print(f"데이터: {sensors}")
print(f"총 센서 수: {len(sensors)}") # len()은 리스트의 길이를 반환

# 2. 인덱싱 (Indexing)
# 파이썬은 숫자를 0부터 셉니다 (0, 1, 2, 3...)
print(f"\n--- [2] 개별 센서 접근 ---")
print(f"첫 번째 센서: {sensors[0]}")
print(f"마지막 센서(역인덱스): {sensors[-1]}") # -1은 뒤에서 첫 번째

# 3. 데이터 수정
# 리스트는 가변(Mutable)하므로 특정 위치의 값을 바꿀 수 있습니다.
sensors[2] = "Humidity_B"
print(f"\n--- [3] 데이터 수정 후 ---")
print(f"수정된 목록: {sensors}")

# 4. 슬라이싱 (Slicing) - [시작:끝] (끝은 포함 안 됨)
print(f"\n--- [4] 범위 추출 ---")
# 0번부터 2번 앞(즉 0, 1)까지 가져오기
temp_sensors = sensors[0:2]
print(f"온도 관련 센서만: {temp_sensors}")
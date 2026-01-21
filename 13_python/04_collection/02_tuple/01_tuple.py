# 1. tuple[타입, ...])
# 드론의 위도와 경도
home_location: tuple[float, float] = (37.5665, 126.9780)
print("--- [1] 드론 홈 좌표 (튜플) ---")
print(f"좌표 데이터: {home_location}")
print(f"위도: {home_location[0]}")
print(f"경도: {home_location[1]}")

# 2. 튜플 언패킹 (Unpacking)
# 튜플의 값을 여러 변수에 한 번에 담습니다. 매우 유용한 기능입니다.
lat, lon = home_location
print("\n--- [2] 언패킹 결과 ---")
print(f"추출된 위도: {lat}, 추출된 경도: {lon}")

# 3. 불변성 테스트 (오류 발생 유도)
print("\n--- [3] 불변성 테스트 (수정 시도) ---")
# 주석을 해제하면 TypeError가 발생합니다.
# home_location[0] = 38.0 # 에러 발생.
print("주의: home_location[0] = 38.0 과 같은 수정은 파이썬에서 금지됩니다.")

# 4. 메모리 크기 비교 (고급 시니어 팁)
import sys  # 시스템 정보를 위해 잠시 호출

list_version = [37.5665, 126.9780]
print("\n--- [4] 메모리 효율성 ---")
print(f"리스트의 메모리 크기: {sys.getsizeof(list_version)} 바이트")
print(f"튜플의 메모리 크기: {sys.getsizeof(home_location)} 바이트")

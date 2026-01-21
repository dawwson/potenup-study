# 1. 세트 생성 (Type Hint: set[타입])
# 중복된 데이터를 일부러 넣어봅니다.
fruits: set[str] = {"apple", "banana", "orange", "apple", "mango"}

print("--- [1] 세트의 자동 중복 제거 ---")
# "apple"이 두 번 들어갔지만 출력은 한 번만 됩니다.
print(f"세트 내용: {fruits}")
# 5개가 아닌 4개가 출력됨
print(f"요소 개수: {len(fruits)}")  # 애플이 중복되어도 하나로 처리됨

# 2. 데이터 추가 및 삭제
fruits.add("pineapple")  # 데이터 추가
fruits.remove("banana")  # 데이터 삭제
# fruits.remove("banana") # 키가 없으므로 KeyError 발생 (주석 처리 필요)
fruits.discard("grape")  # 키가 없어도 에러를 내지 않는 안전한 삭제

print("\n--- [2] 수정 후 세트 ---")
print(f"최종 과일 바구니: {fruits}")

# 3. 실무 꿀팁: 리스트의 중복 제거
raw_data: list[int] = [1, 2, 2, 3, 4, 4, 4, 5]
# 리스트 -> 세트 (중복 제거) -> 리스트 (다시 순서 있는 구조로)
# set() : 중복된 값을 제거하여 세트로 반환
unique_data: list[int] = list(set(raw_data))  # list() : 세트를 다시 리스트로 변환

print("\n--- [3] 리스트 중복 제거 기법 ---")
print(f"원본 리스트: {raw_data}")
print(f"중복 제거 리스트: {unique_data}")

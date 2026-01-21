# [1] 초기 재고 리스트
inventory: list[str] = ["CPU", "RAM", "Mainboard"]
print(f"초기 재고: {inventory}")

# [2] 부품 입고 (맨 뒤에 추가)
print("\n[이벤트] 그래픽카드(GPU) 1개가 입고되었습니다.")
inventory.append("GPU") # append는 리스트 끝에 항목 추가

# [3] 부품 입고 (특정 위치에 삽입)
print("[이벤트] 파워 서플라이(PSU)가 우선 순위로 입고되었습니다.")
inventory.insert(1, "PSU") # 1번 인덱스 위치에 "PSU" 삽입, 나머지는 뒤로 밀림

print(f"현재 재고 상태: {inventory}")

# [4] 부품 출고 (삭제)
print("\n[이벤트] RAM이 조립에 사용되어 출고되었습니다.")
inventory.remove("RAM") # 값으로 찾아서 첫 번째 항목 삭제
print(f"제거 후 현재 재고 상태: {inventory}")

# [5] 마지막 입고 부품 반품 (맨 뒤 삭제 및 가져오기)
returned_item = inventory.pop() # 맨 뒤 요소를 제거하고 그 값을 반환
print(f"[이벤트] {returned_item}에 결함이 발견되어 반품 처리되었습니다.")

# [6] 최종 보고서
print("\n========== INVENTORY REPORT ==========")
print(f"남은 부품 종류: {len(inventory)}종")
print(f"상세 목록: {inventory}")
print(f"첫 번째 중요 부품: {inventory[0]}")
print("======================================")
# [1] 서버 설정 정보 (IP 주소, 포트 번호, 프로토콜)
# 이 정보는 프로그램 실행 중 절대 변하면 안 됩니다.
server_config: tuple[str, int, str] = ("", 8080, "TCP")

# [2] 다중 드론 좌표 (튜플을 담은 리스트)
# 드론들의 '목적지'는 추가/삭제될 수 있으므로 리스트에 담되,
# 각 좌표(X, Y)는 변하지 않아야 하므로 튜플로 담습니다. (실무에서 매우 흔한 패턴)
destinations: list[tuple[float, float]] = [(37.5, 127.1), (37.6, 127.2), (37.7, 127.3)]

print("--- 서버 접속 정보 ---")
print(f"IP: {server_config[0]}, Port: {server_config[1]}")

print("\n--- 드론 목적지 리스트 ---")
print(f"첫 번째 목적지 좌표: {destinations[0]}")
# 목적지 리스트 자체는 수정 가능 (리스트니까!)
destinations.append((37.8, 127.4))
print(f"새 목적지 추가 후 총 개수: {len(destinations)}")

# [3] 튜플 결합 (수정은 안 되지만, 더해서 '새로운' 튜플을 만드는 건 가능)
# 기존 설정에 보안 옵션을 더한 새로운 설정 튜플 생성
security_option: tuple[str] = ("SSL_ON",)
full_config = server_config + security_option

print("\n--- 최종 보안 설정 ---")
print(f"데이터: {full_config}")
print(f"메모리 주소 변경 확인: {id(server_config)} -> {id(full_config)}")

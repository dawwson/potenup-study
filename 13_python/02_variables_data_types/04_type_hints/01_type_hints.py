server_name: str = "localhost"
port: int = 8080
use_ssl: bool = True
timeout: float = 30.0
server_year: int
server_year = "2024"  # 문법적으로 막아주지는 않는다.

print("--- [1] 타입 힌트 적용 변수 ---")
print("server_name: ", server_name, "type: ", type(server_name))
print("port: ", port, "type: ", type(port))
print("use_ssl: ", use_ssl, "type: ", type(use_ssl))
print("timeout: ", timeout, "type: ", type(timeout))
print("server_year: ", server_year, "type: ", type(server_year))

wrong_usage: int = "이것은 숫자입니다."
print("wrong_usage: ", wrong_usage, "type: ", type(wrong_usage))

import inspect
import sys

current_module = sys.modules[__name__]

print("--- [2] 현재 모듈의 타입 힌트들 ---")
print(current_module)
print(inspect.get_annotations(current_module))

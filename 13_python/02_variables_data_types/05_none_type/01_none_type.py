empty_val: None = None
zero_val: int = 0
empty_str: str = ""
false_val: bool = False

print("--- 각 값들의 타입 확인 ---")
print("empty_val: ", empty_val, "type: ", type(empty_val))
print("zero_val: ", zero_val, "type: ", type(zero_val))
print("empty_str: ", empty_str, "type: ", type(empty_str))
print("false_val: ", false_val, "type: ", type(false_val))
# None은 아무 값도 없음을 나타내는 특별한 값이다.

another_none: None = None
print("another_none: ", another_none, "type: ", type(another_none))
print("empty_val: ", empty_val, "type: ", type(empty_val))
print("두 주소가 같은가? : ", empty_val is another_none)

first_name: str = "길동"
middle_name: str | None = None
last_name: str | None = "홍"

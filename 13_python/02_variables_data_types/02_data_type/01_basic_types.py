user_age = 25
very_big_number = 123145879435463213548489746543135
print("----- 정수형 테스트 -----")
print("very_big_number: ", very_big_number, "type:", type(very_big_number))

pi = 3.141592
float_error = 0.1 + 0.2
print("\n--- 실수형 테스트 ---")
print("pi: ", pi, "type: ", type(pi))
print("0.1 + 0.2: ", float_error, "type: ", type(float_error))

user_name = "Alice"
number_as_string = "2026"
print("\n--- 묹자열형 테스트 ---")
print("user_name: ", user_name, "type: ", type(user_name))
print("number_as_string: ", number_as_string, "type: ", type(number_as_string))
print(
    "숫자 모양의 문자열: ",
    number_as_string,
    "-> 정수형 변환: ",
    type(int(number_as_string)),
)

# 1. 초기화 (변수가 존재해야 복합 할당이 가능합니다)
data_value: int = 100
print(f"초기 값: {data_value}, 메모리 주소: {id(data_value)}")

# 2. 덧셈 복합 할당
data_value += 50  # data_value = data_value + 50
print(f"50 더한 후: {data_value}, 메모리 주소: {id(data_value)}")

# 3. 곱셈 복합 할당
data_value *= 2  # data_value = data_value * 2
print(f"2 곱한 후: {data_value}, 메모리 주소: {id(data_value)}")

# 4. 나눗셈 복합 할당 (이때 타입이 float으로 변합니다)
data_value /= 3  # data_value = data_value / 3
print(
    f"3 나눈 후: {data_value}, 메모리 주소: {id(data_value)}, 타입: {type(data_value)}"
)

# 5. 거듭제곱 복합 할당
data_value **= 2
print(f"제곱 후: {data_value}")

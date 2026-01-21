# 1. 기초 데이터 선언 (이진수임을 명시하기 위해 0b 접두어 사용)
a: int = 0b1010  # 십진수 10
b: int = 0b1100  # 십진수 12

# 0b는 'binary'의 약자로 2진수 리터럴을 나타낸다.
print(f"a: {bin(a)} ({a})")  # 0b1010 (10)
print(f"b: {bin(b)} ({b})")  # 0b1100 (12)

print("\n--- [1] 기본 비트 연산 ---")
# AND: 둘 다 1인 자리만 1 (1010 & 1100 = 1000)
print(f"a & b : {bin(a & b)} ({a & b})")

# OR: 하나라도 1이면 1 (1010 | 1100 = 1110)
print(f"a | b : {bin(a | b)} ({a | b})")

# XOR: 다르면 1 (1010 ^ 1100 = 0110)
print(f"a ^ b : {bin(a ^ b)} ({a ^ b})")

print("\n--- [2] 시프트 연산 (2의 배수 연산) ---")
base_val: int = 5  # 0b101
print(f"기본값: {bin(base_val)} ({base_val})")

# 왼쪽 시프트 2칸: 5 * (2의 2승) = 20
shift_left = base_val << 2
print(f"5 << 2 : {bin(shift_left)} ({shift_left})")

# 오른쪽 시프트 1칸: 20 // 2 = 10
shift_right = shift_left >> 1
print(f"20 >> 1: {bin(shift_right)} ({shift_right})")


# 1. 산술 연산자 순서 (기본)
result_1: int = 10 + 2 * 5
print(f"10 + 2 * 5 = {result_1}")  # 2 * 5가 먼저 계산되어 20

result_2: int = (10 + 2) * 5
print(f"(10 + 2) * 5 = {result_2}")  # 괄호가 먼저 계산되어 60

# 2. 산술 vs 비트 시프트
# << 는 + 보다 우선순위가 낮습니다.
# 즉, 5 << 1 + 1 은 5 << (1 + 1) 로 계산됩니다.
shift_val: int = 5 << 1 + 1 
print(f"\n5 << 1 + 1 의 결과: {shift_val} (2진수: {bin(shift_val)})")
print(f"해석: 5 << (1 + 1) = 5 << 2 = 5 * 4 = 20")

# 의도적으로 시프트를 먼저 하고 싶다면?
shift_fixed: int = (5 << 1) + 1
print(f"(5 << 1) + 1 의 결과: {shift_fixed}")
print(f"해석: (5 * 2) + 1 = 11")

# 3. 거듭제곱의 우결합성
power_val: int = 2 ** 3 ** 2
print(f"\n2 ** 3 ** 2 의 결과: {power_val}")
print(f"해석: 2 ** (3 ** 2) = 2 ** 9 = 512")
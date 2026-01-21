input_price = "15000"
input_count = "3"

print("--- 문자열형 테스트 ---")
print("input_price: ", input_price, "type: ", type(input_price))
print("input_count: ", input_count, "type: ", type(input_count))

real_price = int(input_price)
real_count = int(input_count)

print("--- 정수형 변환 후 테스트 ---")
print("real_price: ", real_price, "type: ", type(real_price))
print("real_amount: ", real_count, "type: ", type(real_count))

tax_rate = "0.1"
tax = (real_price * real_count) * float(tax_rate)
print("[결과] 부가세 (10%) ", tax, "type: ", type(tax))

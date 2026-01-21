my_age = 25
required_age = 18
user_name = "Alice"

print("--- 비교 연산자 ---")
is_adult = my_age >= required_age
print("성인인가요? ", is_adult)  # True
print("이름이 'Bob'인가요? ", user_name == "Bob")  # False

print("\n--- 논리 연산자 ---")
can_enter: bool = (my_age >= required_age) and (user_name != "Bob")
print("입장할 수 있나요? ", can_enter)  # True

print("--- 불리언 객체의 메모리 주소 ---")
val_true1: bool = 10 < 20
val_true2: bool = 100 == 100
print("val_true1의 메모리 주소: ", id(val_true1))
print("val_true2의 메모리 주소: ", id(val_true2))
print("val_true1과 val_true2는 같은 객체인가요?: ", val_true1 is val_true2)  # True

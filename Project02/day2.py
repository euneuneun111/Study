'''
A = int (input("숫자 입력"))
B = int (input("숫자 입력"))
C = int (input("선택 1: 덧셈 2: 뺄셈 3: 곱셈 4: 나눗셈"))


if C == 1  :
    print(A+B)
elif C == 2 :
    print(A-B)
elif C == 3 :
    print(A*B)
elif C == 4 :
    print(A/B)
else :
    print("잘못된 수식입니다.")
'''

'''
sum = 0

for i in range(1,50) :

    if i % 5 == 0 :
        sum += i
    print(sum)
   '''


"""
for A in range(1,10):
    print(A, "단")
    for B in range(1,10):
        print (A, "*", B ," = ", A*B)
        
   """

'''
i = 0
sum = 0

while i <10 :
    i += 1
    sum += i
    print(sum)
'''

x = 'python is widely used by a number of big computers'
count = 0
i = 0

while i < len(x) :

    if (x[i] == 'a' or x[i] == 'u' or x[i] == 'e' or x[i] == 'y' or x[i] == 'o' or
        x[i] == 'A' or x[i] == 'U' or x[i] == 'E' or x[i] == 'Y' or x[i] == 'O') :

        count += 1
    i += 1

print(count)
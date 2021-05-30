import random
import re
import pickle

def process(line):
    print(line)
    num = random.randint(1,100000)*random.random()
    print(num)
    

array = [];
with open('data1.txt') as f:
    
    for line in f:
        #print(line.split("=")[0])
        #print(line.split("=")[1])
        #print(line.split("=")[2])
        new_line =  line.split("=")[0] + line.split("=")[1] + line.split("=")[2]
        #print(new_line)
        #print(line)
        

        str1 = '='
        str2 = ';'

        newstring = str(random.randint(1,100000)*random.random())

        reg = "(?<=%s).*?(?=%s)" % (str1,str2)
        r = re.compile(reg,re.DOTALL)
        result = r.sub(newstring, line)
        array.append(result)
        #print(result)
with open("newdata1.txt", 'w') as f:
    for s in array:
        f.write(s)
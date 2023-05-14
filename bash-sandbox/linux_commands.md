##_Операции с файлами/директориями_

### Список файлов/директорий: ls ###

Пример расширенного вывода (размер файла, timestamp с секундами)

$ ls -lh --block-size K --time-style=full-iso
**ls --color -lh --block-size K --time-style=full-iso**
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ ls --color -lh --block-size K --time-style=full-iso
total 3130K
-rw-r--r-- 1 nixx7 197609 3125K 2022-11-21 09:43:03.527068700 +0200 2.txt
-rw-r--r-- 1 nixx7 197609    1K 2022-11-21 10:11:14.187940700 +0200 777.txt
-rw-r--r-- 1 nixx7 197609    1K 2022-11-21 09:40:07.192959600 +0200 new.1.txt
drwxr-xr-x 1 nixx7 197609    0K 2022-11-21 10:15:48.574774700 +0200 sample.dir
```
Вывод файлов с сортировкой по времени в обратном порядке (-r): 
**ls -lh --time-style=full-iso --sort=time -r**
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ ls -lh --time-style=full-iso --sort=time -r
total 3.1M
-rw-r--r-- 1 nixx7 197609   40 2022-11-21 09:40:07.192959600 +0200 new.1.txt
-rw-r--r-- 1 nixx7 197609 3.1M 2022-11-21 09:43:03.527068700 +0200 2.txt
-rw-r--r-- 1 nixx7 197609   18 2022-11-21 10:11:14.187940700 +0200 777.txt
drwxr-xr-x 1 nixx7 197609    0 2022-11-21 10:15:48.574774700 +0200 sample.dir
-rw-r--r-- 1 nixx7 197609   44 2022-11-21 10:18:18.131927900 +0200 app.log
```

### Переход в директорию: cd ### 
Переход в корневую директорию пользователя: $ cd~  
Переход в корневую директорию диска: cd /c


### Показ текущей директории: pwd ### 

### Создание директории: mkdir ###
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ mkdir -v new_dir1 new_dir2
mkdir: created directory 'new_dir1'
mkdir: created directory 'new_dir2'
```
### Перемещение или переименование файла: mv ### 
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ mv -v 2.txt 2_new.txt
renamed '2.txt' -> '2_new.txt'
````
### Копирование файла: cp ### 

### Удаление файла или директории: rm  ###
Удаление директории: rm -r <DIR_NAME>

### Создание ссылки на файл: ln  ###

## _Работа с архивами_ ##
### Создание архива ###
tar -czvf txt.gz *.txt
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ tar -czvf txt.gz *.txt
2.txt
777.txt
new.1.txt
````
### Получение содержимого архива ###
_tar -ztvf txt.gz_
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ tar -ztvf txt.gz
-rw-r--r-- nixx7/197609 3199983 2022-11-21 09:43 2.txt
-rw-r--r-- nixx7/197609      18 2022-11-21 10:11 777.txt
-rw-r--r-- nixx7/197609      40 2022-11-21 09:40 new.1.txt
````
### Распаковка архива ###
Распаковка архива в директорию logs (директория уже должны существовать):
_tar -xvf txt.gz -C ./logs_ 
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ tar -xvf txt.gz -C ./logs
2.txt
777.txt
new.1.txt
````
##Операции с содержимым файлов ###
### Создание файла touch ####
_touch new_file.txt_
#### Получение содержимого файла: cat ####
Получить все строки с ошибками из файла, записать в отдельный файл и показать его содержимое
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ cat -n app.log | grep "ERROR" > app.error | cat app.error
16  ERROR Line 1
18  ERROR Line 2
````

### Потоковый редактор текста: sed ###
Sed (StreamEDitor)  
sed -n 5p app.log - получение строки по номеру  


### Редактирование файла: vi ###
_vi sample.log_ - открытие файла для редактирования  
Поиск в файле: /<STRING_TO_SEARH> ENTER  
n - следующее вхождение  
N - предыдущее вхождение  

? - поиск с конца файла

### Получение содержимого файла: tail ###
_tail -n 3 app.log_ - получение последних 3 строк из файла  
_tail -n +5 app.log_ - получение последних строчек из файла, начиная с 5 строки  
_tail -n 5 app.log_ | grep -n "ERROR" - получение строк с ошибкой из последних 5 строк, в результат включается номер строки  
_tail -f sample.log_ - чтение файла с обновлением данных на экране (ctrl + C выход)
_tail -f sample.log  | grep "#ERROR"_ - получение данных из файла, применяя фильтр, при этом, данные обновляются (-f following)  
_tail -f --pid=2223 sample.log  | grep "#ERROR"_ - получение данных из файла. При этом, если процесс с PID=2223 заканчивается, то tail заканчивает свою работу

### Поиск в файле: grep ###
_grep -B 3 line10 app.log_ - получить строку 'line10' из файла: app.log и 3 строки перед ней    
_grep -A 5 line10 app.log_ - получить строку 'line10' из файла: app.log и 5 строк после нее  
_grep -A 5 -B 3 --color "line10" app.log_ - получить строки вокруг "line10", строку "line10" подсветить

### Сравнение файлов: diff
Файл f1.txt
``` text

line2 more
line3
line4
```
Файл f2.txt
``` text
line1
line2 more 1
line3
line4
```  

_diff -s -q f1.txt f1.txt_ - сравнение только с кратким ответом
````text
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground/diff_sample
$ diff -s -q f1.txt f1.txt
Files f1.txt and f1.txt are identical

nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground/diff_sample
$ diff -s -q f1.txt f2.txt
Files f1.txt and f2.txt differ
 ````

_diff f1.txt f2.txt --color_ - показать разницу с подсветкой
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground/diff_sample
$ diff f1.txt f2.txt --color
1,2c1,2
<
< line2 more
---
> line1
> line2 more 123
```
_diff -y  f1.txt f2.txt --color | cat -n_ - показать разницу в колонку, c номерами строк 
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground/diff_sample
$ diff -y  f1.txt f2.txt --color | cat -n
     1                                                                | line1
     2  line2 more                                                    | line2 more 123
     3  line3                                                           line3
     4  line4                                                           line4
     5                                                                > line5
```

### Сравнение файлов и вывод общих строк: comm
_comm f1.txt f2.txt_ - запуск команды без параметров, результат,3 колонки:
строки уникальные для первого файла, строки уникальные для второго файла, строки общие для обоих файлов
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground/diff_sample
$ comm f1.txt f2.txt

        line1
line2 more
        line2 more 123
                line3
                line4
        line5
```
_comm -1 -2 f1.txt f2.txt_ - исключение из вывода первых двух колонок, в результате только общие колонки
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground/diff_sample
$ comm -1 -2 f1.txt f2.txt
line3
line4
```
### Сортировка содержимого файла: sort
#### Простые операции
_sort -o sorted.txt unsorted.txt_ - сортировка файла с записью результата в другой файл  
_sort -nro sorted.txt unsorted.txt_ - сортировка файла в обратной порядка (-r), строки сортируются как числа (-n), результат записывается в другой файл
_sort -nrc sorted.txt_ - проверка (-c), отсортированы ли строки в файле в обратном порядке (-r) как цифры (-n).
_sort -un unsorted.txt_ - отсортировать строки файла как числа (-n) и убрать дубликаты (-u)

#### Сортировка строк по значению в колонке
_sort -k 2n unsorted_with_columns.txt_ - сортировка файла по значению во второй колонке (разделитель колонок TAB)
```text
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground/sort_sample
$ cat unsorted_with_columns.txt
name1   19
name2   34
name3   67
name4   0
name5   10

nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground/sort_sample
$ sort -k 2n unsorted_with_columns.txt
name4   0
name5   10
name1   19
name2   34
name3   67
```


##_Вызовы REST_

### Запрос GET ###
Получить данные при помощи GET с параметром message и данными в header (custom.header)  
_curl -X 'GET' 'http://localhost:8080/rest-spring/log/message?message=message.value' -H 'accept: */*' -H 'custom.header: headerValue'_

Получение ответа от сервера с подробной информацией (-v Verbose):
```
$ curl -v 'http://localhost:8080/rest-spring/log/message?message=message.value' -H 'accept: */*' -H 'custom.header: headerValue'
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /rest-spring/log/message?message=message.value HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.85.0
> accept: */*
> custom.header: headerValue
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 200
< Set-Cookie: JSESSIONID=57AC07B7D64B9AF6D7315BDD8E68C78E; Path=/rest-spring; HttpOnly
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Fri, 25 Nov 2022 07:36:22 GMT
<
["MessageResponse:message.value","HeaderResponse:headerValue"]* Connection #0 to host localhost left intact
```

Получение расширенного ответа от сервера (-i):
```
$ curl -i 'http://localhost:8080/rest-spring/log/message?message=message.value' -H 'accept: */*' -H 'custom.header: headerValue'
HTTP/1.1 200
Set-Cookie: JSESSIONID=F390E40D87609842B279686D7A55E91A; Path=/rest-spring; HttpOnly
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 25 Nov 2022 07:38:13 GMT

["MessageResponse:message.value","HeaderResponse:headerValue"]
```


### Отправка данных на сервер POST
**Отправка POST запроса и получение подробного ответа**  
_curl -v -X 'POST' 'http://localhost:8080/rest-spring/rest/person' -H 'accept: */*' -H 'Content-Type: application/json' -d
'{"name": "Name.Value", "surname": "Surname.Value", "dateOfBirth": "1978-11-25T07:49:47.149Z"}'_
```
$ curl -v -X 'POST' 'http://localhost:8080/rest-spring/rest/person' -H 'accept: */*' -H 'Content-Type: application/json' -d
 '{"name": "Name.Value", "surname": "Surname.Value", "dateOfBirth": "1978-11-25T07:49:47.149Z"}'
Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /rest-spring/rest/person HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.85.0
> accept: */*
> Content-Type: application/json
> Content-Length: 93
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 201
< Set-Cookie: JSESSIONID=0347E036462CFEC23795DFC4B4C029F6; Path=/rest-spring; HttpOnly
< Location: http://localhost:8080/rest-spring/person/8
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Fri, 25 Nov 2022 07:58:27 GMT
<
{"id":8,"name":"Name.Value","surname":"Surname.Value","dateOfBirth":"1978-11-25T07:49:47.149+00:00"}* Connection #0 to host localhost left intact
```

**Отправка POST запроса и получение только тела ответа**  
_curl -X 'POST' 'http://localhost:8080/rest-spring/rest/person' -H 'accept: */*' -H 'Content-Type: application/json' -d '{"name": "Name.Value", "surname": "Surname.Value", "dateOfBirth": "2022-11-25T07:49:47.149Z"}'_
```
curl -X 'POST' 'http://localhost:8080/rest-spring/rest/person' -H 'accept: */*' -H 'Content-Type: application/json' -d '{"name": "Name.Value", "surname": "Surname.Value", "dateOfBirth": "2022-11-25T07:49:47.149Z"}'
{"dateOfBirth":["Minimal age must be: 18 current age is: 0 years, current value is: Fri Nov 25 09:49:47 EET 2022"]}
```

**Отправка POST запроса и получение ответа и извлечение из него несколько строк (HTTP.Status & Body**  
_curl -i -X 'POST' 'http://localhost:8080/rest-spring/rest/person' -H 'accept: */*' -H 'Content-Type: application/json' -d '{"name": "Name.Value", "surname": "Surname.Value", "dateOfBirth": "2022-11-25T07:49:47.149Z"}' | sed -n '1p;8p'_
```
$ curl -i -X 'POST' 'http://localhost:8080/rest-spring/rest/person' -H 'accept: */*' -H 'Content-Type: application/json' -d '{"name": "Name.Value", "surname": "Surname.Value", "dateOfBirth": "2022-11-25T07:49:47.149Z"}' | sed -n '1p;8p'
% Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
Dload  Upload   Total   Spent    Left  Speed
100   208    0   115  100    93   9001   7279 --:--:-- --:--:-- --:--:-- 17333
HTTP/1.1 400
{"dateOfBirth":["Minimal age must be: 18 current age is: 0 years, current value is: Fri Nov 25 09:49:47 EET 2022"]}
```

##_Прочие операции_

### Получение информации о системе и пользователе: uname & whoami
``` text
nixx7@LAPTOP-M44LHQT2 MINGW64 /bin
$ uname -a
MINGW64_NT-10.0-19044 LAPTOP-M44LHQT2 3.3.6-341.x86_64 2022-09-05 20:28 UTC x86_64 Msys

nixx7@LAPTOP-M44LHQT2 MINGW64 /bin
$ whoami
nixx7
```
### Получение информации о процессах: ps
ps - список процессов
```text
nixx7@LAPTOP-M44LHQT2 MINGW64 /bin
$ ps
      PID    PPID    PGID     WINPID   TTY         UID    STIME COMMAND
      128       1     128       6472  cons0     197609 16:31:58 /usr/bin/bash
      137     128     137      31036  cons0     197609 16:32:01 /usr/bin/ps
```

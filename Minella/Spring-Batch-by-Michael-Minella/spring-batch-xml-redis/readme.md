redis 127.0.0.1:6379> KEYS *
 1) "customer:2"
 2) "customer:6"
 3) "customer:9"
 4) "customer:3"
 5) "customer:8"
 6) "customer:11"
 7) "customer:4"
 8) "customer:1"
 9) "customer:5"
10) "customer:12"
11) "customer"
12) "customer:7"
13) "customer:10"


redis 127.0.0.1:6379> HSCAN customer:1 0 COUNT 10000
1) "0"
2)  1) "_class"
    2) "com.example.domain.Customer"
    3) "id"
    4) "1"
    5) "firstName"
    6) "John"
    7) "lastName"
    8) "Doe"
    9) "birthdate"
   10) "1988-10-10"
   
redis 127.0.0.1:6379> SSCAN customer 0 COUNT 10000
1) "0"
2)  1) "1"
    2) "2"
    3) "3"
    4) "4"
    5) "5"
    6) "6"
    7) "7"
    8) "8"
    9) "9"
   10) "10"
   11) "11"
   12) "12"


docker-compose.yml

```
version: '3'
services:
  master:
    container_name: master
    image: redis
    ports:
      - 6379:6379
  slave-1:
    container_name: slave-1
    image: redis
    ports:
      - 16379:6379
    volumes:
      - ./conf:/usr/local/etc/redis/
    command: redis-server /usr/local/etc/redis/redis.conf
  slave-2:
    container_name: slave-2
    image: redis
    ports:
      - 26379:6379
    volumes:
      - ./conf:/usr/local/etc/redis/
    command: redis-server /usr/local/etc/redis/redis.conf    
  redis-commander:
    container_name: redis-commander
    hostname: redis-commander
    image: rediscommander/redis-commander:latest
    restart: always
    environment:
    - REDIS_HOSTS=master:master,slave-1:slave-1,slave-2:slave-2
    ports:
    - "8081:8081"
```


<img width="861" alt="Screenshot 2022-05-31 at 9 56 43 AM" src="https://user-images.githubusercontent.com/54174687/171092723-890c0d70-afb9-416d-ace6-3e418b5b88aa.png">


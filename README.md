# 출처 

<https://www.baeldung.com/nats-java-client>  
<https://docs.nats.io/>  

# Nats Streaming Server start

아래 nats 디렉토이레서 아래 명령어 수행

```shell
$ cd nats
 
$ docker pull nats

$ docker run --name nats \
 -v $(pwd)/etc/nats:/etc/nats \
 -p 4222:4222 \
 -p 8222:8222 \
 nats --http_port 8222 -c /etc/nats/default.conf
```

`default.conf` 에서 각종 `nats` 관련 설정 가능
> <https://docs.nats.io/running-a-nats-service/configuration>

# nats java client

> <https://github.com/nats-io/nats.java>

# sync, async

`spring.profiles.active` 를 변경하여 사용


# Nats Streaing Server start

```shell
$ docker pull nats
$ docker run --name nats \
 -p 4222:4222 \
 -p 8222:8222 \
 nats --http_port 8222
```

# sync, async

`spring.profiles.active` 를 변경하여 사용
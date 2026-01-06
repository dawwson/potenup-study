```bash
docker network create consul-network

### 1
docker run -d --name consul-server-1 
--network consul-network consul:1.15 agent 
--server -bootstrap-expect=3 -node=server-1

### 2
docker run -d --name consul-server-2 
--network consul-network consul:1.15 agent 
--server -node=server-2 -join=consul-server-1

### 3
docker run -d --name consul-server-3 
--network consul-network consul:1.15 agent 
--server -node=server-3 -join=consul-server-1

### 서버 모드
```bash
docker run -d 
--name consul-server-1 -p 8500:8500 
--network consul-network consul:1.15 agent 
--server -ui -bootstrap-expect=3 -node=server-1 -client=0.0.0.0
```

### 클라이언트
```bash
docker run -d --name consul-client
--network consul-network
-p 8500:8500
consul:1.15 agent -node=client-1 -join=consul-server-1 --client=0.0.0.0
-ui
```

```bash
# 서버1 종료
docker stop consul-server-1

# 
docker exec consul-server-2 consul members

> Node      Address          Status  Type    Build   Protocol  DC   Partition  Segment
> server-1  172.19.0.2:8301  left    server  1.15.4  2         dc1  default    <all>
> server-2  172.19.0.3:8301  alive   server  1.15.4  2         dc1  default    <all>
> server-3  172.19.0.4:8301  alive   server  1.15.4  2         dc1  default    <all>
> client-1  172.19.0.5:8301  alive   client  1.15.4  2         dc1  default    <default>

# leader, follower 확인
docker exec consul-server-2 consul operator raft list-peers

> Node      ID                                    Address          State     Voter  RaftProtocol  Commit Index  Trails Leader By
> server-2  848a85a9-2f68-b7be-46a4-482e512e9a15  172.19.0.3:8300  leader    true   3             380           -
> server-3  cb3feb4c-4dc6-9f3f-1fb5-f3b5f17adc5d  172.19.0.4:8300  follower  true   3             380           0 commits

# 2번 서버가 리더의 원본을 카피해서 리더가 됨
```

### 서버 1 종료
```bash

```
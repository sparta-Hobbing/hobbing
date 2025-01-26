FROM redis:latest

# 필요한 설정이나 파일을 추가할 수 있다면 여기서 추가
# 예시: 사용자 정의 redis.conf 파일을 추가할 경우
# COPY ./redis.conf /usr/local/etc/redis/redis.conf

# 컨테이너 시작 시 Redis 서버를 실행하도록 지정
CMD ["redis-server"]
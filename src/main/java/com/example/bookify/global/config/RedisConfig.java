package com.example.bookify.global.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    //Redis 서버와 통신하는 클라이언트 프로그램. 서버 주소를 지정해 만든다.
    @Bean(destroyMethod = "shutdown")
    public RedisClient redisClient() {
        return RedisClient.create("redis://localhost:6379");
    }

    //Redis와 실제 연결된 네트워크 통로(소켓 연결). 데이터 주고받는 창구 역할.
    @Bean
    public StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
        return redisClient.connect();
    }

    //Redis에 비동기 명령을 보내고 결과를 받을 수 있게 해주는 도구(인터페이스).
    @Bean
    public RedisAsyncCommands<String, String> redisAsyncCommands(StatefulRedisConnection<String, String> connection) {
        return connection.async();
    }
}

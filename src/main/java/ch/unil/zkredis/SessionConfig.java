package ch.unil.zkredis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

// based on https://potix.freshdesk.com/support/tickets/6742


@Configuration
@EnableSpringHttpSession
public class SessionConfig {


    @Bean
    public SessionRepository<MapSession> mapSessionSessionRepository(StringRedisTemplate redisTemplate) {
        return new CustomMapSessionRepository(redisTemplate);
    }

}

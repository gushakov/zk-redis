package ch.unil.zkredis;

import org.slf4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PreDestroy;
import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.slf4j.LoggerFactory.getLogger;

public class CustomMapSessionRepository extends MapSessionRepository {

    private static final Logger logger = getLogger(CustomMapSessionRepository.class);

    private JdkSerializationRedisSerializer jdkSerializer;

    private Set<String> sessionIds;

    private StringRedisTemplate redisTemplate;

    public CustomMapSessionRepository(StringRedisTemplate redisTemplate) {
        super(new ConcurrentHashMap<>());
        this.redisTemplate = redisTemplate;
        this.sessionIds = new ConcurrentSkipListSet<>();
        this.jdkSerializer = new JdkSerializationRedisSerializer();
    }

    @Override
    public MapSession createSession() {
        MapSession session = super.createSession();
        logger.debug("[Session repository][Create] Created session: {}, id: {}, session cookie: {}",
                session.getOriginalId(), session.getId(), getSessionCookie().orElse(null));
        return session;
    }

    @Override
    public void save(MapSession session) {
        super.save(session);
        sessionIds.add(session.getOriginalId());
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
        sessionIds.remove(id);
    }

    @PreDestroy
    public void destroy() {
        sessionIds.forEach(id -> {
            MapSession session = findById(id);
            logger.debug("[Session repository][Destroy] Persisting session: {}", session.getOriginalId());
            // from https://stackoverflow.com/a/9951607/8516495
            redisTemplate.opsForValue().set(id, (String) redisTemplate.getValueSerializer().deserialize(jdkSerializer.serialize(session)));

        });
    }

    private Optional<String> getSessionCookie() {
        return Arrays.stream(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getCookies())
                .filter(cookie -> cookie.getName().equals("SESSION"))
                .map(Cookie::getValue)
                .map(value -> new String(Base64.getDecoder().decode(value)))
                .findAny();
    }
}

package ku.cs.entity;

import java.sql.Timestamp;
import java.time.Duration;

public class AccessToken {
    private String token;
    private Timestamp timestamp;
    private Duration lifetime;

    public AccessToken(String token) {
        this(token, Duration.ofHours(1));
    }

    public AccessToken(String token, Duration lifetime) {
        this.lifetime = lifetime;
        this.token = token;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getToken() {
        return this.token;
    }

    public boolean isExpired() {
        return this.timestamp.getTime() > this.timestamp.getTime() + this.lifetime.toMillis();
    }

    public Duration getLifetime() {
        return this.lifetime;
    }
}

package top.hisoft.swagger.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * Configuration properties for swagger2 security.
 *
 * @author shijin.liu
 * @since 1.0.0
 */
@ConfigurationProperties("swagger2.basic")
public class Swagger2SecurityProperties {
    /* Log */
    private static final Logger LOGGER = LoggerFactory.getLogger(Swagger2SecurityProperties.class);
    /* Whether the default enable type is false. */
    private boolean enable = false;
    /* The default username is sky. */
    private String username = "sky";
    /* The default password is six zero. */
    private String password = "000000";

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Swagger2SecurityProperties{" +
                "enable=" + enable +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Print config log.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("Swagger2SecurityProperties config:[{}]", this);
    }
}

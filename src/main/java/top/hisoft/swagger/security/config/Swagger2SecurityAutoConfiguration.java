package top.hisoft.swagger.security.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.hisoft.swagger.security.filter.SecurityBasicAuthFilter;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for swagger2 security provided from basic auth.
 *
 * @author shijin.liu
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({Swagger2SecurityProperties.class})
@ConditionalOnProperty(prefix = "swagger2.basic", name = "enable", havingValue = "true")
public class Swagger2SecurityAutoConfiguration {

    /**
     * Define security basic auth filter bean.
     *
     * @param properties The {@link Swagger2SecurityProperties} property.
     * @return The IOC bean of {@link SecurityBasicAuthFilter}.
     */
    @Bean
    public SecurityBasicAuthFilter securityBasicAuthFilter(Swagger2SecurityProperties properties) {
        return new SecurityBasicAuthFilter(properties.getEnable(), properties.getUsername(), properties.getPassword());
    }
}

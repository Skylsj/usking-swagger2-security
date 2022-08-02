package top.hisoft.swagger.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Abstract implementation of the {@link Filter} interface.
 * Do mandate the type of storage used for configuration; simply implements common match functionality.
 * Uses the Template Method design pattern,requiring concrete subclasses to implement abstract methods.
 *
 * @author shijin.liu
 * @since 1.0.0
 */
public abstract class AbstractSecurityFilter implements Filter {
    /* Log */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSecurityFilter.class);
    /* Session filed */
    protected String SWAGGER_BASIC_AUTH_SESSION = "SwaggerBasicAuthSession";
    /* List of url filters */
    protected List<Pattern> urlFilters;

    protected AbstractSecurityFilter() {
        urlFilters = new ArrayList<>(1<<2);
        urlFilters.add(Pattern.compile(".*?/v2/api-docs.*", Pattern.CASE_INSENSITIVE));
        urlFilters.add(Pattern.compile(".*?/swagger-ui\\.html.*", Pattern.CASE_INSENSITIVE));
        // Add custom filters
        addFilters(urlFilters);
    }

    /**
     * Match path.
     *
     * @param uri The path.
     * @return True if matched ,others false.
     */
    protected boolean match(String uri) {
        boolean match = false;
        if (uri != null) {
            for (Pattern pattern : getUrlFilters()) {
                if (pattern.matcher(uri).matches()) {
                    match = true;
                    break;
                }
            }
        }
        return match;
    }

    /**
     * Decodes a Base64 encoded String into a newly-allocated String tye
     * using the {@link Base64} encoding scheme.
     *
     * @param message The string to decode.
     * @return A newly-allocated String tye.
     */
    protected String decodeBase64(String message) {
        Assert.notNull(message, "The decode message must not be null");
        try {
            byte[] bytes = Base64.getDecoder().decode(message);
            return new String(bytes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Get the filtered URL.
     *
     * @return The filtered URL.
     */
    private List<Pattern> getUrlFilters() {
        return urlFilters;
    }

    /**
     * Add custom filters of {@link Pattern} type.
     *
     * @param urlFilters
     */
    abstract void addFilters(List<Pattern> urlFilters);

    @Override
    public void destroy() {
        LOGGER.info("Security filter destroy.");
    }
}

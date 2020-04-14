package tech.sollabs.urlshortener.resource.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.validation.constraints.NotNull;

/**
 * @author Cyan
 * @since 0.9
 * @see <a href="https://owasp.org/www-community/OWASP_Validation_Regex_Repository">OWASP Regex</a>
 */
public class RegisterUrlRequest {

  @NotNull
  private URI originalUrl;

  public URI getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(URL originalUrl) throws URISyntaxException {
    this.originalUrl = originalUrl.toURI();
  }
}

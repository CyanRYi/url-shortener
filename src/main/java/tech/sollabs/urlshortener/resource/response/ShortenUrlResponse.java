package tech.sollabs.urlshortener.resource.response;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;
import tech.sollabs.urlshortener.domain.ShortenUrl;

/**
 * @author Cyan
 * @since 0.9
 */
public class ShortenUrlResponse {

  private String shortenKey;
  private URI baseDomain;
  private int redirectCount;

  private ShortenUrlResponse(URI baseDomain, ShortenUrl shortenUrl) {
    this.baseDomain = baseDomain;
    this.shortenKey = shortenUrl.getShortenKey();
    this.redirectCount = shortenUrl.getRedirectCount();
  }

  public static ShortenUrlResponse of(URI baseDomain, ShortenUrl shortenUrl) {
    return new ShortenUrlResponse(baseDomain, shortenUrl);
  }

  public String getShortenUrl() {
    return UriComponentsBuilder
        .fromUri(baseDomain)
        .path(shortenKey)
        .toUriString();
  }

  public int getRedirectCount() {
    return redirectCount;
  }
}

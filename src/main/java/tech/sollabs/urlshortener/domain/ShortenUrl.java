package tech.sollabs.urlshortener.domain;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.Type;
import tech.sollabs.urlshortener.util.UriStringConverter;

@Entity
public class ShortenUrl {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  private UUID id;

  @Column(length = 500, nullable = false, unique = true, updatable = false)
  @Convert(converter = UriStringConverter.class)
  private URI originalUrl;

  @Column(length = 8, nullable = false, unique = true, updatable = false)
  private String shortenKey;

  @Column(nullable = false)
  private int redirectCount;

  protected ShortenUrl() {
  }

  private ShortenUrl(URI originalUrl, String shortenKey) {
    this.originalUrl = originalUrl;
    this.shortenKey = shortenKey;
  }

  public static ShortenUrl of(URI originalUrl, String shortenKey) {
    return new ShortenUrl(originalUrl, shortenKey);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public URI getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(URI originalUrl) {
    this.originalUrl = originalUrl;
  }

  public String getShortenKey() {
    return shortenKey;
  }

  public void setShortenKey(String shortenKey) {
    this.shortenKey = shortenKey;
  }

  public int getRedirectCount() {
    return redirectCount;
  }

  protected void setRedirectCount(int redirectCount) {
    this.redirectCount = redirectCount;
  }

  public void increaseCount() {
    setRedirectCount(getRedirectCount() + 1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShortenUrl that = (ShortenUrl) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

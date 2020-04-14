package tech.sollabs.urlshortener.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import tech.sollabs.urlshortener.domain.ShortenUrl;
import tech.sollabs.urlshortener.repository.UrlRepository;
import tech.sollabs.urlshortener.util.Base62;

/**
 * @author Cyan
 * @since 0.9
 */
@Service
public class UrlService {

  private final UrlRepository urlRepository;

  public UrlService(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  public ShortenUrl createShortUrl(URI originalUrl) {
    Optional<ShortenUrl> existUrl = urlRepository.findByOriginalUrl(originalUrl);

    return existUrl.orElseGet(() -> {
      String shortenUrl = Base62.encode(System.currentTimeMillis());
      return urlRepository.saveAndFlush(ShortenUrl.of(originalUrl, shortenUrl));
    });
  }

  public List<ShortenUrl> getRegisteredUrls() {
    return urlRepository.findAll(Sort.by(Direction.DESC, "redirectCount"));
  }

  public Optional<ShortenUrl> getRedirectionUrl(String shortenKey) {
    return urlRepository.findByShortenKey(shortenKey)
        .map(shortenUrl -> {
          shortenUrl.increaseCount();
          return urlRepository.saveAndFlush(shortenUrl);
        });
  }
}

package tech.sollabs.urlshortener.repository;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sollabs.urlshortener.domain.ShortenUrl;

/**
 * @author Cyan
 * @since 0.9
 */
@Repository
public interface UrlRepository extends JpaRepository<ShortenUrl, UUID> {

  Optional<ShortenUrl> findByOriginalUrl(URI originalUrl);
  Optional<ShortenUrl> findByShortenKey(String shortenKey);
}

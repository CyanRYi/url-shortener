package tech.sollabs.urlshortener.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.sollabs.urlshortener.domain.ShortenUrl;
import tech.sollabs.urlshortener.exception.NotFoundException;
import tech.sollabs.urlshortener.resource.request.RegisterUrlRequest;
import tech.sollabs.urlshortener.resource.response.ShortenUrlResponse;
import tech.sollabs.urlshortener.service.UrlService;

/**
 * @author Cyan
 * @since 0.9
 */
@RestController
public class UrlResource {

  @Value("${app.base-domain}")
  private URI baseDomain;

  private final UrlService urlService;

  public UrlResource(UrlService urlService) {
    this.urlService = urlService;
  }

  @PostMapping(path = "/shorten-url")
  public ShortenUrlResponse createShortUrl(@Validated @RequestBody RegisterUrlRequest request) {
    ShortenUrl result = urlService.createShortUrl(request.getOriginalUrl());
    return ShortenUrlResponse.of(baseDomain, result);
  }

  @GetMapping(path = "/shorten-url")
  public List<ShortenUrlResponse> getShortenUrlList() {
    return urlService.getRegisteredUrls()
        .stream()
        .map(shortenUrl -> ShortenUrlResponse.of(baseDomain, shortenUrl))
        .collect(Collectors.toList());
  }

  @GetMapping(path = "/{shortenKey}")
  public ResponseEntity<Void> handleRedirection(@PathVariable String shortenKey) {
    ShortenUrl result = urlService.getRedirectionUrl(shortenKey)
        .orElseThrow(NotFoundException::new);

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(result.getOriginalUrl());
    return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
  }
}

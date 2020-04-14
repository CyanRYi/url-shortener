package tech.sollabs.urlshortener.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cyan
 * @since 0.9
 */
@Controller
public class IndexResource {

  @GetMapping
  public String index() {
    return "index";
  }
}

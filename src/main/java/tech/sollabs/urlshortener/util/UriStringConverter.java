package tech.sollabs.urlshortener.util;

import java.net.URI;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Cyan
 * @since 0.9
 */
@Converter
public class UriStringConverter implements AttributeConverter<URI, String> {

  @Override
  public String convertToDatabaseColumn(URI attribute) {
    if (attribute == null) {
      return null;
    }

    return attribute.toString();
  }

  @Override
  public URI convertToEntityAttribute(String dbData) {
    if (StringUtils.isEmpty(dbData)) {
      return null;
    }

    return URI.create(dbData);
  }
}

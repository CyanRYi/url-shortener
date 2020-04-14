package tech.sollabs.urlshortener.config;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cyan
 * @since
 */
@Configuration
public class H2Configuration {

  @Bean
  @ConfigurationProperties("spring.datasource.hikari")
  public DataSource datasource() throws SQLException {
    Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
    return new HikariDataSource();
  }
}

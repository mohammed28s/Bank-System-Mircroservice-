package guru.springframework.userservice.Configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.util.Objects;


@Configuration
public class SqliteConf {  // This is a Sqlite database configuration

    final Environment env;

    public SqliteConf(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driverClassName")));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("username","")); // Default empty if the username not set
        dataSource.setPassword(env.getProperty("password", ""));  // Default empty if the password not set
        return  dataSource;
    }
}

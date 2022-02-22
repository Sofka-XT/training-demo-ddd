package co.sideralis.core.application.config;

import co.com.sofka.application.ApplicationQueryExecutor;
import co.com.sofka.business.repository.QueryMapperRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QueryExecutorConfiguration {

    private static final String PACKAGE_QUERIES = "co.sideralis.core.queries";

    @Bean
    public ApplicationQueryExecutor applicationQueryExecutor(QueryMapperRepository queryMapperRepository) {
        return new ApplicationQueryExecutor(PACKAGE_QUERIES, queryMapperRepository);
    }

}

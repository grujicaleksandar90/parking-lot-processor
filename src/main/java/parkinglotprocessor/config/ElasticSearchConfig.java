package parkinglotprocessor.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ElasticSearchConfig {

    private final ParkingLotProcessorConfigurationProperties properties;

    @Bean
    public RestClient getRestClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(properties.getElasticSearch().getUsername(), properties.getElasticSearch().getPassword()));

        return RestClient.builder(new HttpHost("localhost", properties.getElasticSearch().getPort()))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }).build();
    }

    @Bean
    public ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(getRestClient(), new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        return new ElasticsearchClient(getElasticsearchTransport());
    }
}

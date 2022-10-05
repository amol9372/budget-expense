package org.budget.tracker.expenseapp.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig  {

    @Value("${elastic.url}")
    private String url;

    @Value("${elastic.user}")
    private String user;

    @Value("${elastic.password}")
    private String password;

    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(user, password));

        RestClientBuilder builder = RestClient.builder(
                        new HttpHost(url, 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setSSLHostnameVerifier((hostname, session) -> true)
                );

//        ClientConfiguration clientConfiguration = ClientConfiguration
//                .builder()
//                .connectedTo("budget-tracker.es.us-east-1.aws.found.io:443")
//                .withBasicAuth("elastic", "s6eeYl1RWewaRhiwldOIHpSp")
//                .build();

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                builder
        );

        return restHighLevelClient;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

//    @Override
//    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
//        return new ElasticsearchCustomConversions(
//                List.of(new MapToAddress()));
//    }
//
//    @ReadingConverter
//    static class MapToAddress implements Converter<List<String>, LocalDateTime> {
//
//        @Override
//        public LocalDateTime convert(List<String> source) {
//
//            return null;
//        }
//    }

    //    @Bean
//    public ElasticsearchClient esClient() {
////        RestClient httpClient = RestClient.builder(
////                new HttpHost("budget-tracker.es.us-east-1.aws.found.io", 443, "https")
////
////
////        )
////                .build();
//        final CredentialsProvider credentialsProvider =
//                new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("elastic", "s6eeYl1RWewaRhiwldOIHpSp"));
//
//        RestClientBuilder builder = RestClient.builder(
//                        new HttpHost("https://budget-tracker.es.us-east-1.aws.found.io", 443, "https"))
//                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
//                        .setDefaultCredentialsProvider(credentialsProvider)
//                        .setSSLHostnameVerifier((hostname, session) -> true)
//                );
//
//        // Create the HLRC
////        RestHighLevelClient hlrc = new RestHighLevelClientBuilder(httpClient)
////                .setApiCompatibilityMode(true)
////                .build();
//
//        // Create the Java API Client with the same low level client
//        ElasticsearchTransport transport = new RestClientTransport(
//                builder.build(),
//                new JacksonJsonpMapper()
//        );
//
//        return new ElasticsearchClient(transport);
//    }
}

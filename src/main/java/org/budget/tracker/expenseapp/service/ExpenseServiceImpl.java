package org.budget.tracker.expenseapp.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.budget.tracker.expenseapp.app.Expense;
import org.budget.tracker.expenseapp.builder.ExpenseBuilder;
import org.budget.tracker.expenseapp.config.KafkaJsonSerializer;
import org.budget.tracker.expenseapp.db.JExpense;
import org.budget.tracker.expenseapp.elasticsearch.ExpenseESRepository;
import org.budget.tracker.expenseapp.repository.ExpenseJpaRepository;
import org.budget.tracker.expenseapp.repository.GroupJpaRepository;
import org.budget.tracker.expenseapp.rest.request.CreateExpenseRequest;
import org.budget.tracker.expenseapp.rest.request.GetExpensesRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final Logger logger = LogManager.getLogger(this.getClass());

  @Autowired GroupJpaRepository groupJpaRepository;

  @Autowired ExpenseJpaRepository expenseJpaRepository;

  @Autowired KafkaProducer<String, Object> producer;

  @Autowired ExpenseESRepository expenseESRepository;

  @Autowired RestHighLevelClient elasticsearchClient;

  @Override
  @Transactional
  public void createExpense(CreateExpenseRequest request) {
    // Fetch group
    //    JGroup jGroup = null;
    //    if (StringUtils.hasLength(request.getGroup())) {
    //      jGroup = groupJpaRepository.findByName(request.getGroup());
    //    }

    JExpense expense = ExpenseBuilder.with(request);
    JExpense savedExpense = expenseJpaRepository.save(expense);

    // push data in kafka -> elasticsearch
    logger.info("Publishing data on Kafka topic");
    try {
      ProducerRecord<String, Object> record =
          new ProducerRecord<>("my-topic", String.valueOf(savedExpense.getId()), savedExpense);

      producer.send(
          record,
          (metadata, exception) -> {
            if (exception == null) {

              logger.info("Message published to topic with id :: {}", savedExpense.getId());
            } else {
              logger.error("Error in publishing data to kafka {}", exception.getMessage());
            }
          });
    } catch (Exception e) {
      logger.error("Error in publishing data to kafka {}", e.getMessage());
    }
  }

  @Override
  public List<Expense> getExpenses() {

    // fetch expenses from Index
    final var expenses = new ArrayList<Expense>();
    SearchRequest request = new SearchRequest("my_index");

    // MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "long");
    QueryBuilder queryBuilder =
        QueryBuilders.queryStringQuery("{\"match\": {\"budgetId\": {\"query\": 1 }}}");
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(queryBuilder);
    request.source(searchSourceBuilder);

    try {
      SearchResponse searchResponse = elasticsearchClient.search(request, RequestOptions.DEFAULT);
      SearchHit[] searchHits = searchResponse.getHits().getHits();
      for (SearchHit hit : searchHits) {
        expenses.add(
            KafkaJsonSerializer.getCustomConfigMapper()
                .readValue(hit.getSourceAsString(), Expense.class));
      }

    } catch (Exception e) {
      logger.error(e);
    }

    return expenses;
  }

  @Override
  public List<Expense> getExpensesWithFilter(GetExpensesRequest request) {

    // fetch expenses from Index
    var expenses = new ArrayList<Expense>();
    SearchRequest searchRequest = new SearchRequest("my_index");

    MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("budgetId", request.getBudgetId());

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(queryBuilder);
    searchRequest.source(searchSourceBuilder);

    try {
      SearchResponse searchResponse =
          elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
      SearchHit[] searchHits = searchResponse.getHits().getHits();
      for (SearchHit hit : searchHits) {
        expenses.add(
            KafkaJsonSerializer.getCustomConfigMapper()
                .readValue(hit.getSourceAsString(), Expense.class));
      }

    } catch (Exception e) {
      logger.error("Error in fetching records from ElasticSearch ::: ", e);
    }

    return expenses;
  }
}

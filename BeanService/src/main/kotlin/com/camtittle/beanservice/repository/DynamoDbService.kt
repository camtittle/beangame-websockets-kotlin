package com.camtittle.beanservice.repository

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression

object DynamoDbService {

     val dynamoDb = AmazonDynamoDBClientBuilder.standard()
             .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:8000/shell", "localhost"))
             .build()

     val mapper = DynamoDBMapper(dynamoDb)

     fun put(item: Any) {
          mapper.save(item)
     }

     fun delete(item: Any) {
          mapper.delete(item)
     }

     fun <T> scan(clazz: Class<T>): List<T> {
          return mapper.scan(clazz, DynamoDBScanExpression())
     }



}
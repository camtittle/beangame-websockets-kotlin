package com.camtittle.beanservice.repository

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper

object DynamoDbService {

     val dynamoDb = AmazonDynamoDBClientBuilder.standard()
             .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:8000/shell", "localhost"))
             .build()

     val mapper = DynamoDBMapper(dynamoDb)

     fun put(item: Any) {
          mapper.save(item)
     }


}
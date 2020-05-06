package com.camtittle.beanservice.repository.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "beanClients")
class WebsocketClient {
    @DynamoDBHashKey(attributeName = "connectionId")
    lateinit var connectionId: String

    @DynamoDBAttribute(attributeName = "username")
    var username: String? = null
}
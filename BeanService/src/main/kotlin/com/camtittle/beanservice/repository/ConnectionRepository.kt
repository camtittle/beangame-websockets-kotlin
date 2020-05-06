package com.camtittle.beanservice.repository

import com.camtittle.beanservice.repository.model.WebsocketClient
import org.apache.logging.log4j.LogManager

object ConnectionRepository {

    private const val tableName = "beanClients"

    private val LOG = LogManager.getLogger(javaClass.simpleName)

    fun addConnection(id: String) {
        val connection = WebsocketClient()
        connection.connectionId = id
        connection.username = "Ongo"
        LOG.info("Putting connection to DynamoDB table with ID ${connection.connectionId}")
        DynamoDbService.put(connection)
    }

    fun removeConnection(id: String) {
        val connection = WebsocketClient()
        connection.connectionId = id
        LOG.info("Deleting connection from DynamoDB table with ID ${connection.connectionId}")
        DynamoDbService.delete(connection)
    }

    fun getAll(): List<WebsocketClient> {
        return DynamoDbService.scan(WebsocketClient::class.java)
    }
}
package com.camtittle.beanservice.repository

import com.camtittle.beanservice.repository.model.WebsocketClient
import org.apache.logging.log4j.LogManager

object ConnectionRepository {

    private const val tableName = "beanClients"

    private val LOG = LogManager.getLogger(javaClass.simpleName)

    fun addConnection(id: String) {
        val connection = WebsocketClient(id, "Ongo")

        LOG.info("Putting connection to DynamoDB table with ID ${connection.connectionId}")
        DynamoDbService.put(connection)
    }
}
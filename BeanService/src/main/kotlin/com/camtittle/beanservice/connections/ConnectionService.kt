package com.camtittle.beanservice.connections

import com.amazonaws.AmazonServiceException
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiAsyncClientBuilder
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionRequest
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionResult
import com.camtittle.beanservice.repository.ConnectionRepository
import com.google.gson.Gson
import org.apache.logging.log4j.LogManager
import java.lang.Exception
import java.nio.ByteBuffer
import java.util.concurrent.CompletableFuture

object ConnectionService {

    val client = AmazonApiGatewayManagementApiAsyncClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:3001", "eu-west-1"))
            .build()

    val gson = Gson()
    private val LOG = LogManager.getLogger(javaClass.simpleName)

    fun <T> sendToAll(payload: T, exceptConnectionId: String): CompletableFuture<Void> {
        LOG.info("Send to all")
        val tasks = ConnectionRepository.getAll()
                .filter { it.connectionId != exceptConnectionId }
                .map { send(it.connectionId, payload) }

        return CompletableFuture.allOf(*tasks.toTypedArray())
    }

    fun <T> send(connectionId: String, payload: T): CompletableFuture<Void> {
        val jsonString = gson.toJson(payload)
        val request = PostToConnectionRequest()
                .withConnectionId(connectionId)
                .withData(ByteBuffer.wrap(jsonString.toByteArray()))

        val completableFuture = CompletableFuture<Void>()
        client.postToConnectionAsync(request, object : AsyncHandler<PostToConnectionRequest, PostToConnectionResult> {
            override fun onSuccess(request: PostToConnectionRequest?, result: PostToConnectionResult?) {
                completableFuture.complete(null)
            }

            override fun onError(exception: Exception?) {
                // Remove from DB if connection no longer exists
                if (exception is AmazonServiceException && exception.statusCode == 410) {
                    ConnectionRepository.removeConnection(connectionId)
                    completableFuture.complete(null)
                    return
                }
                LOG.error(exception)
                completableFuture.completeExceptionally(exception)
            }
        })

        return completableFuture
    }

}
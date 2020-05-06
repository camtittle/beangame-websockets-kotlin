package com.camtittle.beanservice.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.camtittle.beanservice.handler.model.WebsocketInput
import com.camtittle.beanservice.repository.ConnectionRepository
import org.apache.logging.log4j.LogManager

class ConnectHandler : RequestHandler<WebsocketInput, String> {
    override fun handleRequest(input: WebsocketInput, context: Context?): String {
        LOG.info("** \$Connect Request ***")
        input.requestContext?.connectionId?.let {
            ConnectionRepository.addConnection(it)
        }

        return "Welcome to our legitimate show of hamsters!"
    }

    companion object {
        private val LOG = LogManager.getLogger(ConnectHandler::class.java)
    }
}
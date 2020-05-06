package com.camtittle.beanservice.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.camtittle.beanservice.handler.model.WebsocketInput
import com.camtittle.beanservice.repository.ConnectionRepository
import org.apache.logging.log4j.LogManager

class DisconnectHandler : RequestHandler<WebsocketInput, Unit> {

    private val LOG = LogManager.getLogger(javaClass.simpleName)

    override fun handleRequest(input: WebsocketInput?, context: Context?) {
        LOG.info("** \$Disconnect Request ***")
        input?.requestContext?.connectionId?.let {
            ConnectionRepository.removeConnection(it)
        }
    }



}
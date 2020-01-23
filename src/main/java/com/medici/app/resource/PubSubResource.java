package com.medici.app.resource;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.medici.app.integration.PubsubOutboundGateway;

/**
 * 
 * @author a73s
 *
 */
@RestController
@RequestMapping("/pubsub")
public class PubSubResource {

	@Autowired
	private PubsubOutboundGateway pubsubOutboundGateway;

	@RequestMapping(value = "/{message}", method = RequestMethod.GET)
	public Message<?> save(@PathParam("message") String message) {
		pubsubOutboundGateway.send2Pubsub(message);
		return MessageBuilder.withPayload(null).build();
	}

}

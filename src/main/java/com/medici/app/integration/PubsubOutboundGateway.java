package com.medici.app.integration;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "outputChannel", defaultReplyTimeout = "8000")
public interface PubsubOutboundGateway {

	void send2Pubsub(String request);

}

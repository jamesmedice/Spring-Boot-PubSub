package com.medici.app.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@MessageEndpoint
public class PubSubOutboundActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(PubSubOutboundActivator.class);

	public static final String PAYLOAD2 = "Payload: ";

	@Bean
	@ServiceActivator(inputChannel = "outputChannel")
	public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, "jpmTopic");
	}

	@Bean
	@ServiceActivator(inputChannel = "inputChannel")
	public MessageHandler messageReceiver() {
		return message -> {
			LOGGER.info(PAYLOAD2 + new String((byte[]) message.getPayload()));
			BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
			originalMessage.ack();
		};
	}

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(@Qualifier("inputChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "jpmSubscription");
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		return adapter;
	}

	/*
	 * ONLY FOR SPRING INTEGRATION
	 * 
	 * @ServiceActivator(inputChannel = "inputChannel", outputChannel =
	 * "outputChannel") public void messageReceiver(String payload) {
	 * LOGGER.info(PAYLOAD2 + payload); }
	 */

	@Bean(name = "outputChannel")
	public MessageChannel outputChannel() {
		return new DirectChannel();
	}

	@Bean(name = "inputChannel")
	public MessageChannel inputChannel() {
		return new DirectChannel();
	}

}

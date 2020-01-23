package com.medici.app.resource;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.medici.app.integration.PubsubOutboundGateway;

@Component
public class PubSubScheduler {

	@Autowired
	private PubsubOutboundGateway pubsubOutboundGateway;

	@Scheduled(fixedRate = 2000)
	public void execute() {
		pubsubOutboundGateway.send2Pubsub(Calendar.getInstance().getTime().toString());
	}
}

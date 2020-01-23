#!/bin/bash

gcloud pubsub topics        create jpmTopic
gcloud pubsub subscriptions create jpmSubscription --topic jpmTopic --ack-deadline=60
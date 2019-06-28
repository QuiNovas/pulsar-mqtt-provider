package com.echostreams.pulsar.mqtt.broker;

import com.echostreams.pulsar.mqtt.BrokerConstants;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PulsarConnect {
    private static final Logger LOG = LoggerFactory.getLogger(PulsarConnect.class);

    public static PulsarConnect pulsarConnect;
    public PulsarClient client;

    public static synchronized PulsarConnect getPulsarCon() {
        if (pulsarConnect == null) {
            //synchronized block to remove overhead
            synchronized (PulsarConnect.class) {
                if (pulsarConnect == null) {
                    // if instance is null, initialize
                    pulsarConnect = new PulsarConnect();
                }
            }
        }
        return pulsarConnect;
    }

    private PulsarConnect() {
        try {
            client = PulsarClient.builder()
                    .serviceUrl(BrokerConstants.PULSAR_SERVICE_URL)
                    .build();
        } catch (PulsarClientException e) {
            LOG.error("Error occurred during pulsar client : {}", e);
        }
    }

}

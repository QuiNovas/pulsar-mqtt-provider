package com.echostreams.pulsar.mqtt.broker.utils;

import org.h2.util.StringUtils;

public class CommonUtils {

    /**
     * Create topic prefix like this persistent://my-tenant/my-namespace/my-topic
     * @param persistentName
     * @param tenantName
     * @param namespaceName
     * @param topicName
     * @return
     */
    public static String createTopicNameWithPrefix(String persistentName, String tenantName, String namespaceName,
                                                   String topicName) {
        String topicNameWithPrefix = persistentName+"://"+tenantName+"/"+namespaceName;

        if(StringUtils.isNullOrEmpty(topicName))
            return topicNameWithPrefix;
        else if("/".contains(topicName))
            return topicNameWithPrefix + topicName;
        else
            return topicNameWithPrefix + "/"+topicName;
    }
}

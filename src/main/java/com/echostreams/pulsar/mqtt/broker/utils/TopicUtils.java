package com.echostreams.pulsar.mqtt.broker.utils;

import com.echostreams.pulsar.mqtt.BrokerConstants;
import org.h2.util.StringUtils;

import java.util.regex.Pattern;

public class TopicUtils {

    private TopicUtils() {
    }

    /**
     * Create topic prefix like this persistent://my-tenant/my-namespace/my-topic
     *
     * @param topicPrefix
     * @param topicName
     * @return
     */
    public static String createTopicWithPrefix(String topicPrefix,
                                               String topicName) {

        if (StringUtils.isNullOrEmpty(topicName))
            return topicPrefix;
        else if ("/".contains(topicName))
            return topicPrefix + topicName;
        else
            return topicPrefix + "/" + topicName;
    }

    /**
     * Create a new topic prefix for the given persistentName, tenantName, namespaceName
     *
     * @param persistentName
     * @param tenantName
     * @param namespaceName
     * @return
     */
    public static String createOnlyPrefixWithoutTopic(String persistentName, String tenantName, String namespaceName) {
        return persistentName + BrokerConstants.DOUBLE_FORWARD_SLASH + tenantName + BrokerConstants.SINGLE_FORWARD_SLASH + namespaceName;

    }

    /**
     * Updating BrokerConstants.PULSAR_TOPIC_NAME_PREFIX value if defined in config file
     *
     * @param persistentName
     * @param tenantName
     * @param namespaceName
     */
    public static void createOnlyPrefixWithConfigValues(String persistentName, String tenantName, String namespaceName) {
        if (!CommonUtils.isAnyObjectNull(persistentName, tenantName, namespaceName))
            BrokerConstants.PULSAR_TOPIC_NAME_PREFIX = persistentName + BrokerConstants.DOUBLE_FORWARD_SLASH + tenantName + BrokerConstants.SINGLE_FORWARD_SLASH + namespaceName;
    }

    // Create topic prefix with value given in config file
    public static String getDefaultPrefixWithoutTopic() {
        return BrokerConstants.PERSISTENT_NAME + BrokerConstants.DOUBLE_FORWARD_SLASH + BrokerConstants.TENANT_NAME +
                BrokerConstants.SINGLE_FORWARD_SLASH + BrokerConstants.NAMESPACE_NAME;

    }

    /**
     * Validate topic string against regular expression i.e. pattern
     * defined in config file and return true/false
     *
     * @param topic
     * @return
     */
    public static boolean matchTopicWithPattern(String topic) {
        return Pattern.matches(BrokerConstants.TOPIC_PROCESSING_IDENTIFIER, topic);
    }

    /**
     * Return true if message storage type  is persistent or non-persistent in config file,
     * else returns false
     *
     * @param messageStorageType
     * @return
     */
    public static boolean isValidMessageStorageTypeName(String messageStorageType) {
        return BrokerConstants.PERSISTENT_NAME.equals(messageStorageType)
                || BrokerConstants.NON_PERSISTENT_NAME.equals(messageStorageType);
    }

}

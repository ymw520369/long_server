package org.alan.logging;

import org.longjiang.core.log.LogMessage;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * Created on 2017/6/12.
 *
 * @author Alan
 * @since 1.0
 */
public class LogMessageJsonDeserializer extends JsonDeserializer<LogMessage> {
}

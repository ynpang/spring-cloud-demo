package cc.mrbird.streamkafkademo.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class MessageProducerBean {

    @Autowired
    @Qualifier(Source.OUTPUT)
    private MessageChannel messageChannel;

    @Autowired
    private Source source;

    public void send(String message){
        //messageChannel.send(MessageBuilder.withPayload(message).build());
        source.output().send(MessageBuilder.withPayload(message).build());
    }
}

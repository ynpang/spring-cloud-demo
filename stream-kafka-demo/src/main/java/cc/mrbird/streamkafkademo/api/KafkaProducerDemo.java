package cc.mrbird.streamkafkademo.api;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.connect.json.JsonSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaProducerDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.serializer", JsonSerializer.class.getName());
        properties.setProperty("value.serializer", JsonSerializer.class.getName());
        KafkaProducer<String,String> kafkaProducer = new KafkaProducer(properties);
        String topic = "guopao";
        Integer partition = 0;
        Long timestamp = System.currentTimeMillis();
        String key = "message-key";
        String value = "a";
        ProducerRecord<String,String> record =
                new ProducerRecord<String,String>( topic,  partition,  timestamp,  key,  value);
        Future<RecordMetadata> metadataFuture = kafkaProducer.send(record);
        metadataFuture.get();
    }
}

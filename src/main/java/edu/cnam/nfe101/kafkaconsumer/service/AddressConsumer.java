package edu.cnam.nfe101.kafkaconsumer.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cnam.nfe101.kafkaconsumer.dto.AddressJson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AddressConsumer {
    private static final Logger log = LoggerFactory.getLogger(AddressConsumer.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String addressesTopicName;
    private final KafkaConsumer<String, String> consumer;
    private final AddressService addressService;

    public AddressConsumer(@Value("${addresses.kafka-server}") String kafkaServers,
                               @Value("${addresses.raw-kafka-topic}") String addressTopicName,
                               AddressService addressService) {
        this.addressesTopicName = addressTopicName;
        this.addressService = addressService;

        // Configuration utilisée par Kafka
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers); // Adresse serveur
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // La clé est une string
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // La valeur est une string
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "addresses-db-consumer"); // Consumer group
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.toString()); // A partir de quel moment où lit les messages (existants ou nouveaux), ici dès le début
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true); // Le client kafka commit la consommation (état d'avancement du client)
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100); // Commit toutes les 100ms avec le cluster

        this.consumer = new KafkaConsumer<>(properties);
    }

    public void consume() {
        consumer.subscribe(List.of(addressesTopicName)); // Souscription à la topic

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000)); // Chercher de nouveaux records/messages sur le serveur Kafka toutes les 1s

            for (ConsumerRecord<String, String> record : records){
                // Offset = index sur la partition
                log.debug("Received record with key: {} from partition: {} at offset: {}", record.key(), record.partition(), record.offset());
                try {
                    AddressJson jsonAddress = objectMapper.readValue(record.value(), AddressJson.class);
                    addressService.save(jsonAddress);
                } catch (Exception e) {
                    log.error("Error while processing record with key: {} at partition/offset : {}/{}. Stopping consumption", record.key(), record.partition(), record.offset(), e);
                    throw new RuntimeException(e);
                }
            }
            log.info("Processed {} records", records.count());
        }
    }
}

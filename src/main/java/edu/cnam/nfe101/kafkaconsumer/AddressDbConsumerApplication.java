package edu.cnam.nfe101.kafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.cnam.nfe101.kafkaconsumer.service.AddressConsumer;

@SpringBootApplication
public class AddressDbConsumerApplication implements CommandLineRunner{

    private static Logger log = LoggerFactory.getLogger(AddressDbConsumerApplication.class);

    @Autowired
    private AddressConsumer addressConsumer;

    public static void main(String[] args) {
        log.info("App started");
        SpringApplication.run(AddressDbConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting consumer");
        addressConsumer.consume();
    }
}

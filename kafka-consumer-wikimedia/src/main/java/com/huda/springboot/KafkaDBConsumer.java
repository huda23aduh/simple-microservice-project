package com.huda.springboot;

import com.huda.springboot.entity.WikimediaData;
import com.huda.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDBConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDBConsumer.class);

    private WikimediaDataRepository dataRepository;

    public KafkaDBConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(
            topics = "wikimedia_recentchange",
            groupId = "myGroup"
    )
    public void consume(String eventMsg) {
        LOGGER.info(String.format("Message received --> %s", eventMsg));

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikimediaData(eventMsg);

        dataRepository.save(wikimediaData);
    }

}

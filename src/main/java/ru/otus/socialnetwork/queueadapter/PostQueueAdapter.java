package ru.otus.socialnetwork.queueadapter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.otus.socialnetwork.model.PostDto;

@Component
@RequiredArgsConstructor
public class PostQueueAdapter {

    @Value("${application.post-feed-topic}")
    public String topicName;

    private final KafkaTemplate<String, PostDto> kafkaTemplate;

    public void sendPostToFriends(String userId, String postText) {
        kafkaTemplate.send(topicName, PostDto.builder().id("1").userId(userId).text(postText).build());
    }
}

package com.hackathon.bothelper.route.handlers;

import com.hackathon.bothelper.domain.ReservationRequest;
import com.hackathon.bothelper.domain.ResponseMessage;
import com.hackathon.bothelper.props.BotProperties;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ReservationHandler implements Handler {
    @Value("${reservation}")
    private int handlerPropertyIndex;

    @Value("${url_reservation}")
    private String url;

    private final BotProperties botProperties;

    private BotProperties.HandlerProperties properties;

    @PostConstruct
    public void init() {
        properties = botProperties.getHandlerProperties().get(handlerPropertyIndex);
    }

    public boolean isSuitableFor(final Message message) {
        return properties.getKey().equals(message.getText());
    }

    @Override
    public boolean isSuitableFor(final Message message, final Session session1) {
        return isSuitableFor(message);
    }

    @Override
    public ResponseMessage getMessageForReply(final Message message) {
        final RestTemplate restTemplate = new RestTemplate();

        final ReservationRequest request = new ReservationRequest();
        final User from = message.getFrom();
        final String userName = from.getUserName();
        request.setId(userName == null ? from.getFirstName() + " " + from.getLastName() : userName );

        restTemplate.postForObject(url, request, Void.class);
        return new ResponseMessage(message.getChatId().toString(), properties.getValue());
    }

}

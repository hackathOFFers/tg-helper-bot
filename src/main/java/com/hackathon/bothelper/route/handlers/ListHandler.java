package com.hackathon.bothelper.route.handlers;

import com.hackathon.bothelper.domain.BookInfo;
import com.hackathon.bothelper.domain.BookRequest;
import com.hackathon.bothelper.domain.ResponseMessage;
import com.hackathon.bothelper.props.BotProperties;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListHandler implements Handler {
    @Value("${list}")
    private int handlerPropertyIndex;

    @Value("${url_list}")
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

        final BookInfo[] reservations = restTemplate.getForObject(url, BookInfo[].class);
        final String collect = Arrays.stream(reservations).map(BookInfo::toString).collect(Collectors.joining("\n"));
        return new ResponseMessage(message.getChatId().toString(), MessageFormat.format(properties.getValue(), collect));
    }

}

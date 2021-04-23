package com.hackathon.bothelper.route.handlers;

import com.hackathon.bothelper.domain.ResponseMessage;
import com.hackathon.bothelper.props.BotProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class HelpHandler implements Handler {

    @Value("${help}")
    private int handlerPropertyIndex;

    private final BotProperties botProperties;

    private BotProperties.HandlerProperties properties;

    @PostConstruct
    public void init() {
        properties = botProperties.getHandlerProperties().get(handlerPropertyIndex);
    }

    @Override
    public boolean isSuitableFor(final Message message) {
        return properties.getKey().equals(message.getText());
    }

    @Override
    public ResponseMessage getMessageForReply(final Message message, final TelegramLongPollingBot hackathonBot) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < botProperties.getHandlerProperties().size(); i++) {
            final BotProperties.HandlerProperties handlerProperties = botProperties.getHandlerProperties().get(i);
            if (handlerProperties.getKey().equals("/start")) {
                continue;
            }
            if (i > 0) {
                sb.append("\n");
            }
            sb.append("*" + handlerProperties.getKey() + "*");
            sb.append(" ");
            sb.append(handlerProperties.getDescription());
        }
        return new ResponseMessage(message.getChatId().toString(), sb.toString());
    }
}

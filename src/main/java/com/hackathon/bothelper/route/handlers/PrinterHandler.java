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
public class PrinterHandler implements Handler {
    @Value("${printer}")
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
    public ResponseMessage getMessageForReply(Message message, TelegramLongPollingBot hackathonBot) {
        return new ResponseMessage(message.getChatId().toString(), properties.getValue());
    }

}

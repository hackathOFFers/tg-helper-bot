package com.hackathon.bothelper.route.handlers;

import com.hackathon.bothelper.domain.ResponseMessage;
import com.hackathon.bothelper.graph.CommandGraph;
import com.hackathon.bothelper.props.BotProperties;
import com.hackathon.bothelper.utils.BotUtils;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

@Component
@RequiredArgsConstructor
public class PrintHandler implements Handler {
    @Value("${print}")
    private int handlerPropertyIndex;

    private final BotProperties botProperties;

    private BotProperties.HandlerProperties properties;

    @PostConstruct
    public void init() {
        properties = botProperties.getHandlerProperties().get(handlerPropertyIndex);
    }

    @Override
    public boolean isSuitableFor(final Message message, final Session session1) {
        if (session1.getAttribute("CurrentState") != null && !session1.getAttribute("CurrentState").equals(CommandGraph.PRINT)) {
            return false;
        }
        return isSuitableFor(message);
    }

    public boolean isSuitableFor(final Message message) {
        return message.getDocument() != null;
    }

    @Override
    public ResponseMessage getMessageForReply(final Message message) {
        if ("image/jpeg".equals(message.getDocument().getMimeType())) {
            return new ResponseMessage(message.getChatId().toString(), MessageFormat.format(properties.getValue(), 1));
        }
        return new ResponseMessage(message.getChatId().toString(), MessageFormat.format(properties.getValue(), BotUtils.getRandomNumber(3, 10)));

    }

}

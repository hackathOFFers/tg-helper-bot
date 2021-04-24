package com.hackathon.bothelper.route;


import com.hackathon.bothelper.domain.ResponseMessage;
import com.hackathon.bothelper.route.handlers.Handler;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.session.Session;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Router {

    private final List<Handler> handlers;

    public ResponseMessage route(final Update update, final Session session) {
        final Message message = update.getMessage();

        final List<Handler> applicableHandlers = handlers.stream().filter(x -> x.isSuitableFor(message, session))
                .collect(Collectors.toList());
        if (applicableHandlers.size() != 1) {
            throw new IllegalStateException("There is more than one handler for such command");
        }
        return applicableHandlers.get(0).getMessageForReply(message);
    }
}

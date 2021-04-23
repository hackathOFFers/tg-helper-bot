package com.hackathon.bothelper.config.bots;

import com.hackathon.bothelper.config.TextToButtonTransformer;
import com.hackathon.bothelper.domain.ResponseMessage;
import com.hackathon.bothelper.route.Router;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.session.TelegramLongPollingSessionBot;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HackathonBot extends TelegramLongPollingSessionBot {


    @Value("${username}")
    private String username;

    @Value("${token}")
    private String token;

    private final Router router;

    private final TextToButtonTransformer transformer;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(final Update update, final Optional<Session> optional) {
        final ResponseMessage msg = router.route(update, this);
        final SendMessage reply = new SendMessage();
        reply.setChatId(msg.getId());
        reply.setText(msg.getBody());
        reply.setParseMode("Markdown");

        reply.setReplyMarkup(transformer.transform(update));
        try {
            execute(reply);
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Can't send message");
        }
    }
}

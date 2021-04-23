package com.hackathon.bothelper.route.handlers;

import com.hackathon.bothelper.domain.ResponseMessage;
import com.hackathon.bothelper.props.BotProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ParkingHandler implements Handler {

    private final List<Integer> nums = IntStream.range(1, 27).boxed().collect(Collectors.toList());

    private final List<Character> letters = IntStream.range('A', 'Z' + 1).boxed().map(x -> (char) x.intValue())
            .collect(Collectors.toList());

    {
        Collections.shuffle(nums);
        Collections.shuffle(letters);
    }

    @Value("${parking}")
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

    public int getRandomNumber(final int min, final int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public ResponseMessage getMessageForReply(Message message, TelegramLongPollingBot hackathonBot) {

        final int amountOfAvailablePlaces = getRandomNumber(1, 13);

        return new ResponseMessage(message.getChatId().toString(),
                MessageFormat.format(properties.getValue(), amountOfAvailablePlaces, 26-amountOfAvailablePlaces, 26));
    }
}

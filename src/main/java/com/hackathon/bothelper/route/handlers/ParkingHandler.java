package com.hackathon.bothelper.route.handlers;

import com.hackathon.bothelper.domain.ParkingServiceGetResponse;
import com.hackathon.bothelper.domain.ResponseMessage;
import com.hackathon.bothelper.props.BotProperties;
import com.hackathon.bothelper.utils.BotUtils;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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

    @Value("${url_get_place}")
    private String url;

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
    public boolean isSuitableFor(final Message message, final Session session1) {
        return isSuitableFor(message);
    }
    public boolean isSuitableFor(final Message message) {
        return properties.getKey().equals(message.getText());
    }

    @Override
    public ResponseMessage getMessageForReply(final Message message) {

        final int amountOfAvailablePlaces = BotUtils.getRandomNumber(1, 13);
        final RestTemplate restTemplate = new RestTemplate();

        final ParkingServiceGetResponse body = restTemplate.getForEntity(url, ParkingServiceGetResponse.class).getBody();

        return new ResponseMessage(message.getChatId().toString(),
                MessageFormat.format(properties.getValue(), body.getFree(), body.getTaken(), body.getTotal()));
    }
}

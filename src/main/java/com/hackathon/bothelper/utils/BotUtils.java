package com.hackathon.bothelper.utils;

import com.hackathon.bothelper.graph.CommandGraph;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class BotUtils {
    public static int getRandomNumber(final int min, final int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static CommandGraph getCurrentState(final Update update) {
        final String text = update.getMessage().getText();
        return Arrays.stream(CommandGraph.values()).filter(x -> x.getKey().equals(text)).findFirst().orElse(null);
    }
}

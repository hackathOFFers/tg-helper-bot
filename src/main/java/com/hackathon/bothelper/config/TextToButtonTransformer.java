package com.hackathon.bothelper.config;


import com.hackathon.bothelper.graph.CommandGraph;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TextToButtonTransformer {
    public ReplyKeyboard transform(final Update update) {
        final String text = update.getMessage().getText();
        final CommandGraph commandGraph = Arrays.stream(CommandGraph.values()).filter(x -> x.getKey().equals(text)).findFirst().get();
        final List<KeyboardRow> rows = commandGraph.getNextCommands().stream().map(x -> {
            final KeyboardRow keyboardButtons = new KeyboardRow();
            keyboardButtons.add(x.getKey());
            return keyboardButtons;
        }).collect(Collectors.toList());
        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setKeyboard(rows);
        return replyKeyboard;
    }
}

package com.hackathon.bothelper.graph;

import java.util.List;

public enum CommandGraph {
    START("В начало"), START_DIALOG("/start"), PARK("Парковка"),
    PRINT("Принтер"), BOOK("Забронировать"), HELP("Помощь"),
    LIST("Кто забронировал?");

    static {
        START.setNextCommands(List.of(PARK, PRINT, HELP));
        PRINT.setNextCommands(List.of(START));
        PARK.setNextCommands(List.of(BOOK, LIST, START));
        START_DIALOG.setNextCommands(START.getNextCommands());
        HELP.setNextCommands(List.of(PARK, PRINT, START));
        BOOK.setNextCommands(List.of(LIST, START));
        LIST.setNextCommands(START.getNextCommands());

    }

    private final String key;

    CommandGraph(final String key) {
        this.key = key;
    }

    private List<CommandGraph> nextCommands;

    public String getKey() {
        return key;
    }

    public List<CommandGraph> getNextCommands() {
        return nextCommands;
    }

    private void setNextCommands(final List<CommandGraph> nextCommands) {
        this.nextCommands = nextCommands;
    }
}

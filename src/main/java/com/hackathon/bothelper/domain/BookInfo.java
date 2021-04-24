package com.hackathon.bothelper.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookInfo {
    private String id;
    private String timeStamp;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        if(id.contains(" ")){
            return stringBuilder.append(id).append(" ").append(timeStamp).toString();
        }
        return stringBuilder.append("@").append(id).append(" ").append(timeStamp).toString();
    }
}

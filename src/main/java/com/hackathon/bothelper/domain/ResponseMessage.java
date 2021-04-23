package com.hackathon.bothelper.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseMessage {
    private final String id;
    private final String body;
}

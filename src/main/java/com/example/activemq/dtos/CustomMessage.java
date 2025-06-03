package com.example.activemq.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage implements Serializable {

    private UUID id;
    private String content;
    private String queue;

}

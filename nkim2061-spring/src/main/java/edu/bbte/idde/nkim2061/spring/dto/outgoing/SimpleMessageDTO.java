package edu.bbte.idde.nkim2061.spring.dto.outgoing;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleMessageDTO implements Serializable {
    private final String message;
}

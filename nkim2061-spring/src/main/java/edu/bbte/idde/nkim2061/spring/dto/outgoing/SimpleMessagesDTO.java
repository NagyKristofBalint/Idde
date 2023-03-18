package edu.bbte.idde.nkim2061.spring.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
public class SimpleMessagesDTO implements Serializable {
    private final Collection<String> messages;
}

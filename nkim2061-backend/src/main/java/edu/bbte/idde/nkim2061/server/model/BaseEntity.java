package edu.bbte.idde.nkim2061.server.model;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseEntity implements Serializable {
    protected Long id;
}

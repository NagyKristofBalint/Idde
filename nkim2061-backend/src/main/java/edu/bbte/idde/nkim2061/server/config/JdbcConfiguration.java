package edu.bbte.idde.nkim2061.server.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JdbcConfiguration {
    private boolean createTables;
    private String driverClass;
    private String url;
    private String user;
    private String password;
    private int poolSize;
}

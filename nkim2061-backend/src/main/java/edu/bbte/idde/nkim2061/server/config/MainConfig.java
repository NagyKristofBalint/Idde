package edu.bbte.idde.nkim2061.server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainConfig {
    private String profile;

    @JsonProperty("jdbc")
    private JdbcConfiguration jdbcConfiguration;
}

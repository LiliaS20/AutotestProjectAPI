package tripDemo.dictionaries;

import lombok.Data;

@Data
public class ConnectionProperties {
    private String url;
    private String user;
    private String password;
    private String driver;
    private String dialect;
}

package tripDemo.model;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;
import tripDemo.dictionaries.ConnectionProperties;
import tripDemo.dictionaries.IPathEnum;
import tripDemo.dictionaries.ServiceEnum;

import java.util.HashMap;
import java.util.Map;

public class ConfigQA {

    @Getter
    private Map<IPathEnum, String> serviceDataMap = new HashMap<>();
    @Getter
    private final Map<ServiceEnum, ConnectionProperties> baseConnectionDataMap = new HashMap<>();

    private static ConfigQA instance;

    public static ConfigQA getInstance() {
        if (instance == null) {
            instance = new ConfigQA();
        }
        return instance;
    }

    private ConfigQA() {
        Config config = ConfigFactory.parseResources("config.conf");
        for (ServiceEnum value : ServiceEnum.values()) {
            Config conf = config.getConfig("service")
                    .getConfig(value.name().toLowerCase());
            readPaths(value, conf);
            readBaseProperties(value, conf.getConfig("data_base"));
        }
    }

    private void readBaseProperties(ServiceEnum value, Config config) {
        ConnectionProperties connectionProperties = new ConnectionProperties();
        connectionProperties.setUrl(config.getString("url_base"));
        connectionProperties.setUser(config.getString("user_base"));
        connectionProperties.setPassword(config.getString("password_base"));
        connectionProperties.setDriver(config.getString("driver"));
        connectionProperties.setDialect(config.getString("dialect"));
        baseConnectionDataMap.put(value, connectionProperties);
    }

    private void readPaths(ServiceEnum value, Config config) {
        String port, host;
        host = config.getString("host");
        port = config.getString("port");
        Config pathsConf = config.getConfig("path");
        for (IPathEnum iPathEnum : value.getPathEnumList()) {
            String path = pathsConf.getString(iPathEnum.name().toLowerCase());
            serviceDataMap.put(iPathEnum, generateFullPath(host, port, path));
        }
    }

    public String generateFullPath(String host, String port, String path) {
        return host + ":" + port + "/" + path;
    }
}

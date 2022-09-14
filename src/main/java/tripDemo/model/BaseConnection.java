package tripDemo.model;

import tripDemo.dictionaries.ConnectionProperties;
import tripDemo.dictionaries.ServiceEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class BaseConnection {
    private final Map<ServiceEnum, Connection> connectionMap;
    private final ConfigQA configQA;

    private static BaseConnection instance;

    private BaseConnection() {
        connectionMap = new ConcurrentHashMap<>();
        configQA = ConfigQA.getInstance();
    }

    public static BaseConnection getInstance() {
        if (instance == null) {
            instance = new BaseConnection();
        }
        return instance;
    }

    public Connection getConnection(ServiceEnum serviceEnum) {
        if (Objects.nonNull(serviceEnum)) {
            return connectionMap.computeIfAbsent(serviceEnum,
                    a -> {
                        ConnectionProperties properties = configQA.getBaseConnectionDataMap().get(a);
                        Connection connection = null;
                        try {
                            connection = DriverManager.getConnection(properties.getUrl(),
                                    properties.getUser(),
                                    properties.getPassword());
                        } catch (SQLException throwable) {
                            throwable.printStackTrace();
                        }
                        return connection;
                    });
        }
        throw new IllegalArgumentException();
    }

    public void closeConnection(ServiceEnum serviceEnum) {
        connectionMap.computeIfPresent(serviceEnum,
                (a, b) -> {
                    try {
                        connectionMap.remove(a).close();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    return null;
                });
    }













}

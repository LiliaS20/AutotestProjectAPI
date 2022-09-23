package tripDemo.model;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import tripDemo.dictionaries.ConnectionProperties;
import tripDemo.dictionaries.ServiceEnum;
import tripDemo.hibernate.CompanyEntity;
import tripDemo.hibernate.PassengerEntity;
import tripDemo.hibernate.TripEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BaseConnection {
    private final Map<ServiceEnum, Session> connectionMap;
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

 /*   public Connection getConnection(ServiceEnum serviceEnum) {
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
    }*/

    public void closeConnection(ServiceEnum serviceEnum) {
        connectionMap.computeIfPresent(serviceEnum,
                (a, b) -> {
                    /*try {
                        connectionMap.remove(a).close();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }*/
                    connectionMap.remove(a).close();
                    return null;
                });
    }

    private List<Class<?>> getClasses(ServiceEnum serviceEnum) {
        List<Class<?>> classes = new ArrayList<>();
        switch (serviceEnum) {
            case TRIP:
                classes.add(CompanyEntity.class);
                classes.add(PassengerEntity.class);
                classes.add(TripEntity.class);
        }
        return classes;
    }

    private Properties getSettings(ServiceEnum serviceEnum) {
        Properties properties = new Properties();
        ConnectionProperties connectionProperties = configQA.getBaseConnectionDataMap().get(serviceEnum);
        properties.put(Environment.DRIVER, connectionProperties.getDriver());
        properties.put(Environment.DIALECT, connectionProperties.getDialect());
        properties.put(Environment.URL, connectionProperties.getUrl());
        properties.put(Environment.USER, connectionProperties.getUser());
        properties.put(Environment.PASS, connectionProperties.getPassword());
        return properties;
    }

    public Session getSession(ServiceEnum serviceEnum) {
        if (Objects.nonNull(serviceEnum)) {
            return connectionMap.computeIfAbsent(serviceEnum,
                    a -> {
                        Configuration configuration = new Configuration();
                        configuration.setProperties(getSettings(a));
                        getClasses(a).forEach(configuration::addAnnotatedClass);
                        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties()).build();
                        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                        return sessionFactory.openSession();
                    });
        }
        throw new IllegalArgumentException();
    }
}

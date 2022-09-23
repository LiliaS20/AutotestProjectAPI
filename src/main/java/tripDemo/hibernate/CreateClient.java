package tripDemo.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.time.LocalDate;

public class CreateClient {
/*    Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(ClientEntity.class);
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();
    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    Session session = sessionFactory.openSession();

    //Генерация тестовой модели
    ClientEntity client = new ClientEntity ();
        client.setFirstName("Петр");
        client.setMeddleName("Петрович");
        client.setLastName("Петров");
        client.setBirthDay(LocalDate.now().minusYears(25));

        session.beginTransaction();
    //Сохранение клиента
        session.save(client);
        session.getTransaction().commit();
    //Считывание объекта из БД
    ClientEntity newClient = session.get(ClientEntity.class, client.getId());*/
}

import com.example.model.entity.Message;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldHibernateToJPATest {

    private static EntityManagerFactory entityManagerFactory(){


        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(Message.class);

        Map<String,String> properties = new HashMap<>();

        Enumeration<?> enumeration =  configuration.getProperties().elements();
        while (enumeration.hasMoreElements()) {
            String element = properties.put((String) enumeration.nextElement(), configuration.getProperties().getProperty((String) enumeration.nextElement()));
        }

        return Persistence.createEntityManagerFactory("CH02",properties);
    }
}

import com.example.model.entity.Message;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldHibernateTest {

    private static SessionFactory createSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(Message.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void storeLoadMessage(){

        try(SessionFactory sessionFactory=createSessionFactory();
            Session session = sessionFactory.openSession()){

            session.beginTransaction();

            Message message = new Message();
            message.setText("Hello World from Hibernate!");

            session.persist(message);

            session.getTransaction().commit();

            session.beginTransaction();

            CriteriaQuery<Message>  criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class);
            criteriaQuery.from(Message.class);

            List<Message> messages = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();

            assertAll(
                    ()->assertEquals(2,messages.size()),
                    ()->assertEquals("Hello World from Hibernate!",messages.get(1).getText())
            );

        }catch (Exception e){

        }finally {

        }

    }
}

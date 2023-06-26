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

        /*To create a SessionFactory, we first need to create a configuration.*/
        Configuration configuration = new Configuration();

        /*We need to call the configure method on it and to add Message to it as an annotated
        class. The execution of the configure method will load the content of the
        default hibernate.cfg.xml file.*/
        configuration.configure().addAnnotatedClass(Message.class);

        /*The builder pattern helps us create the immutable service registry and configure it
        by applying settings with chained method calls. A ServiceRegistry hosts and manages
        services that need access to the SessionFactory. Services are classes that provide
        pluggable implementations of different types of functionality to Hibernate*/
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

            /*Create an instance of CriteriaQuery by calling the CriteriaBuilder create-
            Query() method. A CriteriaBuilder is used to construct criteria queries, compound
            selections, expressions, predicates, and orderings. A CriteriaQuery defines
            functionality that is specific to top-level queries. CriteriaBuilder and Criteria-
             Query belong to the Criteria API, which allows us to build a query programmaticallyCreate an instance of CriteriaQuery by calling the CriteriaBuilder create-
            Query() method. A CriteriaBuilder is used to construct criteria queries, compound
            selections, expressions, predicates, and orderings. A CriteriaQuery defines
            functionality that is specific to top-level queries. CriteriaBuilder and Criteria-
             Query belong to the Criteria API, which allows us to build a query programmatically*/
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

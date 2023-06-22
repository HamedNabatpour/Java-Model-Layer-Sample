import com.example.model.entity.Message;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HelloWorldJpaTest {

    @Test
    public void storeLoadMessage(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql");

        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Message message = new Message();
            message.setText("Hello World");

            entityManager.persist(message);
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            List<Message> loadMessages = entityManager.createQuery("select m from Message m",Message.class).getResultList();

            loadMessages.get(loadMessages.size() - 1).setText("New Hello World From Jpa");

            entityManager.getTransaction().commit();

            assertAll(
                    ()->assertEquals(1,loadMessages.size()),
                    ()->assertEquals("New Hello World From Jpa",loadMessages.get(0).getText())
            );

            entityManager.close();
        }catch (Exception e){

        }finally {
            entityManagerFactory.close();
        }
    }
}

import com.example.model.configuration.SpringDataConfiguration;
import com.example.model.entity.Message;
import com.example.model.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
public class HelloWorldSpringDataJPATest {

//    @Autowired
//    MessageRepository messageRepository;

    @Test
    public void storeLoadMessage(){

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringDataConfiguration.class);
        MessageRepository messageRepository = applicationContext.getBean(MessageRepository.class);
        Message message = new Message();
        message.setText("Hello World from Spring Data JPA!");

        messageRepository.save(message);

        List<Message> messages = (List<Message>) messageRepository.findAll();

        assertAll(
                () -> assertEquals(7, messages.size()),
                () -> assertEquals("Hello World from Spring Data JPA!",
                        messages.get(0).getText())
        );
    }
}

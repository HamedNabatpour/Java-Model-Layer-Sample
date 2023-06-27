package com.example.model.repository;

import com.example.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/*Spring Data JPA will
create a proxy class implementing the MessageRepository
interface and implement its methods*/
public interface MessageRepository extends CrudRepository<Message,Integer> {
}

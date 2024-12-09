package com.example.tutorial_client.client;

import com.example.tutorial_client.client.model.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

}
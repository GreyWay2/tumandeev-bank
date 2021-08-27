package org.haulmont.tumandeev.Services;

import org.haulmont.tumandeev.Models.Client;
import org.haulmont.tumandeev.Repos.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void delete(Client client) {

        clientRepository.delete(client);
        System.out.println("Delete client " + client.toString());
    }

    public List<Client> findAllSort() {
        List<Client> clients = clientRepository.findAll();
        Collections.sort(clients);
        return clients;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public void save(Client client) { clientRepository.save(client);
        System.out.println("Save a new client " + client.toString());}

}
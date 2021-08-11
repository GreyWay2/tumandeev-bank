package org.haulmont.tumandeev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ClientService() { }

    public void deleteById(Long id) {

        clientRepository.delete(id);
        System.out.println("Delete client with ID " + id);
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

    public Client findClient(long passport) {
        Client client = new Client ();
        client.setPassport(passport);
        return clientRepository.findOne(Example.of(client));
    }
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public void save(Client client) { clientRepository.save(client);
     System.out.println("Save a new client " + client.toString());}

}
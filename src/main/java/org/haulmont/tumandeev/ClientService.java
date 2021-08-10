package org.haulmont.tumandeev;

import org.haulmont.tumandeev.Bank;
import org.haulmont.tumandeev.Client;
import org.haulmont.tumandeev.Credit;
import org.haulmont.tumandeev.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
        return clientRepository.findAll();
    }

    public Client findClient(String passport) {
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
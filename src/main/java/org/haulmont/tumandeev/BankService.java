package org.haulmont.tumandeev;

import org.haulmont.tumandeev.Bank;
import org.haulmont.tumandeev.Client;
import org.haulmont.tumandeev.Credit;
import org.haulmont.tumandeev.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BankService {

    final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void deleteById(Long id) {
        bankRepository.delete(id);
        System.out.println("Delete client from bank (by BANK_ID)");
    }

    public void delete(Bank bank) {

        bankRepository.delete(bank);
        System.out.println("Delete client from bank");
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public Bank findBank(Client client, Credit credit) {
        Bank bank = new Bank(client, credit);
        return bankRepository.findOne(Example.of(bank));
    }

    public void save(Bank bank) { bankRepository.save(bank);
        System.out.println("Saving a new client in the bank ");}

}
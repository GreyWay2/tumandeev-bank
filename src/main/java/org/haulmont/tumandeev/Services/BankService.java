package org.haulmont.tumandeev.Services;

import org.haulmont.tumandeev.Models.Bank;
import org.haulmont.tumandeev.Repos.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void delete(Bank bank) {
        bankRepository.delete(bank);
        System.out.println("Delete client from bank");
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public void save(Bank bank) { bankRepository.save(bank);
        System.out.println("Saving a new client in the bank ");}

}
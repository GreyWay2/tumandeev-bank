package org.haulmont.tumandeev.Services;

import org.haulmont.tumandeev.Models.Credit;
import org.haulmont.tumandeev.Repos.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditService {

    @Autowired
    final CreditRepository creditRepository;

    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public void delete(Credit credit) {
        System.out.println("Delete Credit: "+ credit.getCreditLimit() + ", " + credit.getCreditPercent());
        creditRepository.delete(credit);

    }

    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    public List<Credit> findCreditsByAmount(Long creditAmount) {
        return creditRepository.findCreditsByAmount(creditAmount);
    }

    public void save(Credit credit) { creditRepository.save(credit);
        System.out.println("Save Credit: "+ credit.getCreditLimit() + ", " + credit.getCreditPercent());}
}
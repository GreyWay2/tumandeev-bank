package org.haulmont.tumandeev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CreditService {

    @Autowired
    CreditRepository creditRepository;

    public CreditService() { }

    public void deleteById(Long id) {

        creditRepository.delete(id);
        System.out.println("Delete Credit with ID " + id);
    }

    public void delete(Credit credit) {
        System.out.println("Delete Credit: "+ credit.getCreditLimit() + ", " + credit.getCreditProcent());
        creditRepository.delete(credit);

    }

    public List<Credit> findAllSort() {
        List<Credit> credits = creditRepository.findAll();
        Collections.sort(credits);
        return credits;
    }

    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    public Credit findCredit(Long creditAmount, Double creditProcent) {
        Credit credit = new Credit(creditAmount, creditProcent);
        return creditRepository.findOne(Example.of(credit));
    }

    public List<Credit> findCreditsByAmount(Long creditAmount) {
        return creditRepository.findCreditsByAmount(creditAmount);
    }

    public void save(Credit credit) { creditRepository.save(credit);
        System.out.println("Save Credit: "+ credit.getCreditLimit() + ", " + credit.getCreditProcent());}
}
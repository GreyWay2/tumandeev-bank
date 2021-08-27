package org.haulmont.tumandeev.Services;

import org.haulmont.tumandeev.Models.CreditOffer;
import org.haulmont.tumandeev.Repos.CreditOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditOfferService {
    @Autowired
    final CreditOfferRepository creditOfferRepository;

    public CreditOfferService(CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }

    public List<CreditOffer> findAllOffersForClient(long bankID) {
        return creditOfferRepository.findAllOffersForClient(bankID);
    }
    public void deleteAllOffersForClient(long bankID) {
        creditOfferRepository.deleteAllOffersForClient(bankID);
    }

    public void save(CreditOffer creditOffer) {
        creditOfferRepository.save(creditOffer);
    }

}
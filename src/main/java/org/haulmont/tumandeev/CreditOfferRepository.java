package org.haulmont.tumandeev;

import org.haulmont.tumandeev.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
}

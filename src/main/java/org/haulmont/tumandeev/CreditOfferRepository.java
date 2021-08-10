package org.haulmont.tumandeev;

import org.haulmont.tumandeev.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
    @Query("select p from CreditOffer p where p.bank_id =:bankID")
    List<CreditOffer> findAllOffersForClient(@Param("bankID") long bankID);
}
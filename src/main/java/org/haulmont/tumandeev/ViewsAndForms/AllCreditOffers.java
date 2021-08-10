package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.haulmont.tumandeev.Credit;
import org.haulmont.tumandeev.CreditOffer;
import org.haulmont.tumandeev.CreditOfferService;
import org.haulmont.tumandeev.ViewsAndForms.BankView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = "allOffers")
public class AllCreditOffers extends VerticalLayout implements View {
    @Autowired
    CreditOfferService creditOfferService;

    @PostConstruct
    protected void init() {
        Grid<CreditOffer> grid = new Grid<>(CreditOffer.class);
        grid.setColumns("client", "credit", "creditAmount", "paymentSchedule");
        grid.setItems(creditOfferService.findAllOffersForClient(BankView.bank_id));
        grid.setSizeFull();
        addComponent(grid);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
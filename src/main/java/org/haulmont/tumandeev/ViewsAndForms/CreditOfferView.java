package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;

import org.haulmont.tumandeev.Models.Client;
import org.haulmont.tumandeev.Models.Credit;
import org.haulmont.tumandeev.Services.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Arrays;


@SpringView(name = "CreditOffer")
public class CreditOfferView extends VerticalLayout implements View, Serializable {
    VerticalLayout mainLayout = new VerticalLayout();

    static NativeSelect<Client> clientNativeSelect;
    private NativeSelect<Credit> creditNativeSelect;
    private final NativeSelect<Integer> creditPeriod = new NativeSelect<>
            ("Срок", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    @Autowired
    private ClientService clientService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private PaymentScheduleService scheduleService;
    @Autowired
    CreditOfferService creditOfferService;
    @Autowired
    BankService bankService;

    private final TextField creditAmount = new TextField("Сумма кредита");

    @PostConstruct
    void init() {
        Navigator.setStyleForButton(3);
        Page.getCurrent().setTitle("CreditOffer");
        Label header = new Label("Оформление кредита");
        header.addStyleName(ValoTheme.LABEL_H2);
        clientNativeSelect = new NativeSelect<>("Клиент", clientService.findAllSort());
        clientNativeSelect.setRequiredIndicatorVisible(true);
        clientNativeSelect.setEmptySelectionAllowed(false);
        creditPeriod.setEmptySelectionAllowed(false);
        HorizontalLayout amountAndPeriodLayout = new HorizontalLayout(creditAmount, creditPeriod);
        amountAndPeriodLayout.setHeight("100px");
        creditAmount.setRequiredIndicatorVisible(true);
        creditAmount.setPlaceholder("Сумма");
        creditPeriod.setSelectedItem(1);
        creditPeriod.setRequiredIndicatorVisible(true);

        Button getCreditOffer = new Button("Подобрать кредит");
        getCreditOffer.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        getCreditOffer.addClickListener(clickEvent -> {
            if (!clientNativeSelect.isEmpty()) {
                try {
                    if (Long.parseLong(creditAmount.getValue()) > 0) {
                        CreditOfferForm creditOfferForm = new CreditOfferForm(creditService,
                                Long.parseLong(creditAmount.getValue()), creditPeriod.getValue(),
                                clientNativeSelect.getValue(), creditOfferService, scheduleService, bankService);
                        getUI().addWindow(creditOfferForm);
                    } else {
                        Notification error = new Notification("Сумма должна быть положительным числом!");
                        error.setDelayMsec(1500);
                        error.show(getUI().getPage());
                    }
                    } catch(Exception e){
                        Notification error = new Notification("Сумма должна быть числом!");
                        error.setDelayMsec(1500);
                        error.show(getUI().getPage());
                    }
            } else {
                Notification error = new Notification("Выберите клиента!");
                error.setDelayMsec(1500);
                error.show(getUI().getPage());
            }
        });

        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        mainLayout.addComponents(header, clientNativeSelect, amountAndPeriodLayout, getCreditOffer);
        addComponents(mainLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
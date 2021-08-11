package org.haulmont.tumandeev.ViewsAndForms;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.Serializable;
import java.io.File;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View, Serializable {

    @PostConstruct
    void init() {
        Navigator.buttonsLayout.setVisible(false);
        FileResource bankImageResource = createFileResource("bank.png");
        FileResource creditImageResource = createFileResource("credit.png");
        FileResource clientImageResource = createFileResource("clients.png");
        FileResource offerImageResource = createFileResource("offer.png");

        HorizontalLayout firstRowLayout = new HorizontalLayout();
        HorizontalLayout secRowLayout = new HorizontalLayout();
        VerticalLayout bankLayout = new VerticalLayout();
        VerticalLayout creditLayout = new VerticalLayout();
        VerticalLayout clientsLayout = new VerticalLayout();
        VerticalLayout offerLayout = new VerticalLayout();

        Image bank = new Image("", bankImageResource);
        Image credit = new Image("", creditImageResource);
        Image clients = new Image("", clientImageResource);
        Image offer = new Image("", offerImageResource);

        bank.setWidth("30%");
        bank.setHeight("30%");
        credit.setWidth("30%");
        credit.setHeight("30%");
        clients.setWidth("30%");
        clients.setHeight("30%");
        offer.setWidth("30%");
        offer.setHeight("30%");

        Label bankLabel = new Label("Банк");
        Label creditLabel = new Label("Кредиты");
        Label clientsLabel = new Label("Клиенты");
        Label offerLabel = new Label("Оформить кредит");

        bankLabel.addStyleName(ValoTheme.LABEL_H3);
        creditLabel.addStyleName(ValoTheme.LABEL_H3);
        clientsLabel.addStyleName(ValoTheme.LABEL_H3);
        offerLabel.addStyleName(ValoTheme.LABEL_H3);

        bankLayout.addComponents(bank, bankLabel);
        bankLayout.setComponentAlignment(bankLabel, Alignment.TOP_CENTER);
        bankLayout.setComponentAlignment(bank, Alignment.TOP_CENTER);
        bankLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("Bank");
            Navigator.buttonsLayout.setVisible(true);
        });
        bankLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        creditLayout.addComponents(credit, creditLabel);
        creditLayout.setComponentAlignment(creditLabel, Alignment.TOP_CENTER);
        creditLayout.setComponentAlignment(credit, Alignment.TOP_CENTER);
        creditLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("Credits");
            Navigator.buttonsLayout.setVisible(true);
        });
        creditLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        clientsLayout.addComponents(clients, clientsLabel);
        clientsLayout.setComponentAlignment(clientsLabel, Alignment.TOP_CENTER);
        clientsLayout.setComponentAlignment(clients, Alignment.TOP_CENTER);
        clientsLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("Clients");
            Navigator.buttonsLayout.setVisible(true);
        });
        clientsLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        offerLayout.addComponents(offer, offerLabel);
        offerLayout.setComponentAlignment(offerLabel, Alignment.TOP_CENTER);
        offerLayout.setComponentAlignment(offer, Alignment.TOP_CENTER);
        offerLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("CreditOffer");
            Navigator.buttonsLayout.setVisible(true);
        });
        offerLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        firstRowLayout.setWidth("100%");
        firstRowLayout.setHeight("100%");
        secRowLayout.setWidth("100%");
        secRowLayout.setHeight("100%");
        firstRowLayout.addComponents(bankLayout, creditLayout);
        secRowLayout.addComponents(clientsLayout, offerLayout);
        addComponents(firstRowLayout, secRowLayout);
    }

    private FileResource createFileResource (String fileName) {
        return new FileResource(new File("src/main/resources/images/"+fileName));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
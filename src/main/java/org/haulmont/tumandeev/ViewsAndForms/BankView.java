package org.haulmont.tumandeev.ViewsAndForms;

import javax.annotation.PostConstruct;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Bank;
import org.haulmont.tumandeev.BankService;
import org.haulmont.tumandeev.ClientService;
import org.haulmont.tumandeev.CreditService;
import org.haulmont.tumandeev.ViewsAndForms.MyUI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "Bank")
public class BankView extends VerticalLayout implements View {

    @Autowired
    private BankService bankService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CreditService creditService;

    public static Grid<Bank> bankGrid = new Grid<>(Bank.class);
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");
    private final Button viewCurrentCreditOffer = new Button("Детали кредита");
    public static long bank_id;

    @PostConstruct
    void init() {
        MyUI.setStyleForButton(1);
        Page.getCurrent().setTitle("Bank");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        viewCurrentCreditOffer.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        editButton.setIcon(VaadinIcons.PENCIL);
        deleteButton.setIcon(VaadinIcons.MINUS);
        addButton.setIcon(VaadinIcons.PLUS);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(addButton, editButton, viewCurrentCreditOffer, deleteButton);
        horizontalLayout.setSizeFull();
        horizontalLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        horizontalLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        addComponent(horizontalLayout);

        bankGrid.setSizeFull();
        bankGrid.setColumns("client", "creditInBank");
        bankGrid.setItems(bankService.findAll());

        addComponent(bankGrid);

        createButtonsListeners();
    }

    private void createButtonsListeners() {
        bankGrid.addSelectionListener(valueChangeEvent -> {
            if (!bankGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                viewCurrentCreditOffer.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                viewCurrentCreditOffer.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Bank bank = new Bank();
            BankForm bankForm = new BankForm(bankService, bank, clientService, creditService);
            getUI().addWindow(bankForm);
        });

        editButton.addClickListener(e -> {
            Bank bank = bankGrid.asSingleSelect().getValue();
            BankForm bankForm = new BankForm(bankService, bank, clientService, creditService);
            getUI().addWindow(bankForm);
        });

        viewCurrentCreditOffer.addClickListener(e -> {
            bank_id = bankGrid.asSingleSelect().getValue().getId();
            getUI().getNavigator().navigateTo("allOffers");
        });

        deleteButton.addClickListener(e -> {
            Bank bank = bankGrid.asSingleSelect().getValue();
            try {
                bankService.delete(bank);
                updateBankGrid(bankService);
                Notification notification = new Notification( bank.toString() + " был успешно удален",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification notification = new Notification("Ошибка! Попробуйте еще раз позже",
                        Notification.Type.WARNING_MESSAGE);
                notification.show(getUI().getPage());
            }
        });
        updateBankGrid(bankService);
    }

    static void updateBankGrid(BankService bankService) {
        bankGrid.setItems(bankService.findAll());
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
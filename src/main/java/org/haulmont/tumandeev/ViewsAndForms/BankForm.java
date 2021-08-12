package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Models.Bank;
import org.haulmont.tumandeev.Models.Client;
import org.haulmont.tumandeev.Models.Credit;
import org.haulmont.tumandeev.Services.BankService;
import org.haulmont.tumandeev.Services.ClientService;
import org.haulmont.tumandeev.Services.CreditService;

import java.util.List;


public class BankForm extends Window implements View {

    private final Button save = new Button("Сохранить", VaadinIcons.CHECK);
    private final Button cancel = new Button("Отмена");

    private final ClientService clientService;
    private final CreditService creditService;
    private final BankService bankService;

    private final Bank bank;

    private NativeSelect<Client> clientNativeSelect;
    private NativeSelect<Credit> creditNativeSelect;


    public BankForm(BankService bankService, Bank bank, ClientService clientService, CreditService creditService) {
        this.bankService = bankService;
        this.bank = bank;
        this.clientService = clientService;
        this.creditService = creditService;
        setCaption(" Добавление клиента в банк");
        setIcon(VaadinIcons.PIGGY_BANK);
        setModal(true);
        center();
        setContent(loadFormData());
    }

    private Component loadFormData() {
        List<Client> clients = clientService.findAll();
        List<Credit> credits = creditService.findAll();

        VerticalLayout form = new VerticalLayout();

        clientNativeSelect = new NativeSelect<>(" Клиент", clients);
        clientNativeSelect.setEmptySelectionAllowed(false);
        clientNativeSelect.setIcon(VaadinIcons.USER);

        creditNativeSelect = new NativeSelect<>(" Кредит", credits);
        creditNativeSelect.setEmptySelectionAllowed(false);
        creditNativeSelect.setIcon(VaadinIcons.USER);

        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);

        form.addComponents(clientNativeSelect, creditNativeSelect, formButtons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(event -> this.save());

        cancel.addClickListener(event -> getUI().removeWindow(BankForm.this));

        return form;
    }

    private void save() {
        try {
            bank.setClient(clientNativeSelect.getValue());
            bank.setCreditInBank(creditNativeSelect.getValue());
            bankService.save(bank);

            Notification notification = new Notification("Успешно! Клиент добавлен в банк",
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.setDelayMsec(1500);
            notification.show(getUI().getPage());
            getUI().removeWindow(BankForm.this);
        } catch (Exception e) {
            new Notification("Ошибка! Проверьте корректность вводимых данных!",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
        BankView.updateBankGrid(bankService);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
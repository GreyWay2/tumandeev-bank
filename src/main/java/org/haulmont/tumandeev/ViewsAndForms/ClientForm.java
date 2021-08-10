package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Client;
import org.haulmont.tumandeev.ClientService;

public class ClientForm extends Window implements View {
    private final TextField firstName = new TextField("Имя");
    private final TextField lastName = new TextField("Фамилия");
    private final TextField middleName = new TextField("Отчество");
    private final TextField passport = new TextField("Паспорт");

    private final Button save = new Button("Сохранить", VaadinIcons.CHECK);
    private final Button cancel = new Button("Отмена");

    private final ClientService clientService;
    private final Client client;

    public ClientForm(ClientService clientService, Client client) {
        this.clientService = clientService;
        this.client = client;
        setCaption(" Заполните данные");
        setIcon(VaadinIcons.USER);
        setModal(true);
        center();
        setContent(loadFormData());
    }

    public Component loadFormData() {
        VerticalLayout form = new VerticalLayout();
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        HorizontalLayout formInputs = new HorizontalLayout(firstName, lastName);
        HorizontalLayout formUnputsDown = new HorizontalLayout(middleName, passport);
        form.addComponents(formInputs, formUnputsDown, formButtons);

        try {
            firstName.setValue(client.getFirstName());
            lastName.setValue(client.getLastName());
            middleName.setValue(client.getMiddleName());
            passport.setValue(client.getPassport());

        } catch (Exception ignored) {}

        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);
        passport.setRequiredIndicatorVisible(true);

        save.addClickListener(event -> this.save());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        cancel.addClickListener(event -> getUI().removeWindow(ClientForm.this));
        return form;
    }

    private void save() {
        try {
            if(clientService.findClient(passport.getValue())==null) {
                client.setFirstName(firstName.getValue().trim());
                client.setLastName(lastName.getValue().trim());
                client.setMiddleName(middleName.getValue().trim());
                client.setPassport(passport.getValue().trim());
                clientService.save(client);
                Notification notification = new Notification("Успешно! Клиент добавлен",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
                getUI().removeWindow(ClientForm.this);
            } else {
                Notification notification = new Notification("Ошибка! Клиент с таким паспортом уже сущестует",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
            }
        } catch (Exception e) {
            new Notification("Ошибка! Проверьте корректность вводимых данных!",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
        ClientsView.updateClientGrid(clientService);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) { }
}
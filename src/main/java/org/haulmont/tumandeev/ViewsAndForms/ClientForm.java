package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.annotations.PropertyId;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Models.Client;
import org.haulmont.tumandeev.Services.ClientService;

public class ClientForm extends Window implements View {
    private final TextField firstName = new TextField("Имя");
    private final TextField lastName = new TextField("Фамилия");
    private final TextField middleName = new TextField("Отчество");
    private final TextField passport = new TextField("Паспорт");
    private final TextField phoneNumber = new TextField("Телефон");
    private final TextField email = new TextField("Емайл");

    private final Button save = new Button("Сохранить", VaadinIcons.CHECK);
    private final Button cancel = new Button("Отмена");

    public ClientForm(ClientService clientService, Client client, boolean flag) {
        setCaption(" Заполните данные");
        setIcon(VaadinIcons.USER);
        setModal(true);
        center();

        Binder<Client> binder = new Binder<>(Client.class);
        binder.forField(firstName)
                .withValidator (string -> string != null && !string.isEmpty (), "Имя не должно быть пустым!")
                .withValidator (new RegexpValidator("В имени не должно быть пробелов, знаков и цифр!", "^[a-zA-Zа-яА-Я]{1,25}$", true))
                .bind("firstName");
        binder.forField(lastName)
                .withValidator (string -> string != null && !string.isEmpty (), "Фамилия не должна быть пустой!")
                .withValidator (new RegexpValidator("В фамилии не должно быть пробелов, знаков и цифр!", "^[a-zA-Zа-яА-Я]{1,25}$", true))
                .bind("lastName");
        binder.forField(middleName)
                .withValidator (new RegexpValidator("В отчестве не должно быть пробелов, знаков и цифр!", "^[a-zA-Zа-яА-Я]{0,25}$", true))
                .bind("middleName");
        binder.forField(passport)
                .withValidator(string -> string != null && !string.isEmpty (), "Паспорт не может быть пустым!")
                .withValidator (new RegexpValidator("Паспорт должен состоит из 10 цифр!", "^[0-9]{10}$", true))
                .bind("passport");
        binder.forField(phoneNumber)
                .withValidator (new RegexpValidator("Телефон состит из 11 цифр и знака +!", "^[0-9+]{11,12}$", true))
                .bind("phoneNumber");
        binder.forField(email)
                .withValidator(string -> string != null && !string.isEmpty (), "Емайл не должен быть пустым!")
                .withValidator (new EmailValidator("Не выглядит как емайл!"))
                .bind("email");


        if(flag) {

            binder.readBean(client);
        }

        setContent(loadFormData());

        binder.addStatusChangeListener(event -> {
            boolean isValid = event.getBinder().isValid();
            boolean hasChanges = event.getBinder().hasChanges();

            save.setEnabled(hasChanges && isValid);
        });

        save.addClickListener(event -> {
            try{
                binder.writeBean(client);
                clientService.save(client);
                Notification notification = new Notification("Успешно!", Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
                getUI().removeWindow(ClientForm.this);
            } catch (ValidationException e){
                Notification notification = new Notification("Ошибка, не сохранено, проверьте ошибки для каждого поля");
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
            }
            ClientsView.updateClientGrid(clientService);
        });
    }

    public Component loadFormData() {
        VerticalLayout form = new VerticalLayout();
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        HorizontalLayout formInputs = new HorizontalLayout(firstName, lastName, middleName);
        HorizontalLayout formInputsDown = new HorizontalLayout(passport, phoneNumber, email);
        form.addComponents(formInputs, formInputsDown, formButtons);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel.addClickListener(event -> getUI().removeWindow(ClientForm.this));
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);
        passport.setRequiredIndicatorVisible(true);
        email.setRequiredIndicatorVisible(true);
        return form;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
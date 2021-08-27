package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Models.Credit;
import org.haulmont.tumandeev.Services.CreditService;

public class CreditForm extends Window implements View {

    private final TextField creditLimit = new TextField("Лимит по кредиту");
    private final TextField creditPercent = new TextField("Процент по кредиту");
    private final Button save = new Button("Сохранить", VaadinIcons.CHECK);
    private final Button cancel = new Button("Отмена");

    public CreditForm(CreditService creditService, Credit credit, boolean flag) {
        setCaption(" Заполните данные по кредиту");
        setIcon(VaadinIcons.PIGGY_BANK_COIN);
        setModal(true);
        center();

        Binder<Credit> binder = new Binder<>(Credit.class);

        binder.forField(creditLimit)
                .withValidator (string -> string != null && !string.isEmpty (), "Лимит не должен быть пустым!")
                .withValidator (new RegexpValidator("Лимит должен быть положительным числом без пробелов!", "^[0-9]{5,25}$", true))
                .withConverter(new StringToLongConverter("Конвертация не удалась"))
                .bind("creditLimit");
        binder.forField(creditPercent)
                .withValidator (string -> string != null && !string.isEmpty (), "Процент не должен быть пустым!")
                .withValidator (new RegexpValidator("Процент должен быть положительным числом", "^[0-9]{1,3}[.,]{0,1}[0-9]*$", true))
                .withConverter(new StringToDoubleConverter("Конвертация не удалась"))
                .bind("creditPercent");
        if (flag) {
            binder.readBean(credit);
        }

        setContent(loadFormData());

        binder.addStatusChangeListener(event -> {
        boolean isValid = event.getBinder().isValid();
        boolean hasChanges = event.getBinder().hasChanges();

        save.setEnabled(hasChanges && isValid);
        });

        save.addClickListener(event -> {
            try{
                binder.writeBean(credit);
                creditService.save(credit);
                Notification notification = new Notification("Успешно!", Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
                getUI().removeWindow(CreditForm.this);
            } catch (ValidationException e){
                Notification notification = new Notification("Ошибка, не сохранено, проверьте ошибки для каждого поля");
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
            }
            CreditsView.updateCreditGrid(creditService);
        });
    }

    public Component loadFormData() {
        VerticalLayout form = new VerticalLayout();
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        HorizontalLayout formInputs = new HorizontalLayout(creditLimit, creditPercent);
        form.addComponents(formInputs, formButtons);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel.addClickListener(event -> getUI().removeWindow(CreditForm.this));
        creditLimit.setRequiredIndicatorVisible(true);
        creditPercent.setRequiredIndicatorVisible(true);
        return form;
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) { }
}
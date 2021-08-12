package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Models.Credit;
import org.haulmont.tumandeev.Services.CreditService;

public class CreditForm extends Window implements View {

    private final TextField creditLimitInput = new TextField("Лимит по кредиту");
    private final TextField creditProcentInput = new TextField("Процент по кредиту");
    private final Button save = new Button("Сохранить", VaadinIcons.CHECK);
    private final Button cancel = new Button("Отмена");

    private final CreditService creditService;
    private final Credit credit;

    public CreditForm(CreditService creditService, Credit credit) {
        this.creditService = creditService;
        this.credit = credit;
        setCaption(" Заполните данные по кредиту");
        setIcon(VaadinIcons.PIGGY_BANK_COIN);
        setModal(true);
        center();
        setContent(loadFormData());
    }

    public Component loadFormData() {
        VerticalLayout form = new VerticalLayout();
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        HorizontalLayout formInputs = new HorizontalLayout(creditLimitInput, creditProcentInput);
        form.addComponents(formInputs, formButtons);

        try {
            creditLimitInput.setValue(credit.getCreditLimit().toString());
            creditProcentInput.setValue(credit.getCreditProcent().toString());
        } catch (Exception ignored) {}

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        creditLimitInput.setRequiredIndicatorVisible(true);
        creditProcentInput.setRequiredIndicatorVisible(true);

        save.addClickListener(event -> this.save());
        cancel.addClickListener(event -> getUI().removeWindow(CreditForm.this));
        return form;
    }

    private void save() {
        try {
            if(
                    creditService.findCredit(Long.parseLong(creditLimitInput.getValue()),
                            Double.parseDouble(creditProcentInput.getValue()))==null) {
                credit.setCreditLimit(Long.parseLong(creditLimitInput.getValue().trim()));
                credit.setCreditProcent(Double.parseDouble(creditProcentInput.getValue().trim()));
                creditService.save(credit);
                Notification notification = new Notification("Успешно! Кредит добавлен",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
                getUI().removeWindow(CreditForm.this);
            } else {
                Notification notification = new Notification("Ошибка! Такой кредит уже существует",
                        Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
            }
        } catch (Exception e) {
            new Notification("Ошибка! Проверьте корректность вводимых данных!",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
        CreditsView.updateCreditGrid(creditService);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) { }
}
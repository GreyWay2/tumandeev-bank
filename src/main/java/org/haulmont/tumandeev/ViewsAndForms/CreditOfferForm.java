package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Client;
import org.haulmont.tumandeev.Credit;
import org.haulmont.tumandeev.CreditService;
import org.haulmont.tumandeev.ViewsAndForms.ClientForm;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class CreditOfferForm extends Window implements View {

    private final VerticalLayout form = new VerticalLayout();
    private final Button ok = new Button("Выбрать", VaadinIcons.CHECK);
    private final Button back = new Button("Назад");
    private final CreditService creditService;
    private final long creditAmount;
    private final int creditPeriod;
    private final Client client;
    private NativeSelect<Credit> creditNativeSelect;

    public CreditOfferForm(CreditService creditService, long creditAmount, int creditPeriod, Client client) {
        this.creditService = creditService;
        this.creditAmount = creditAmount;
        this.creditPeriod = creditPeriod;
        this.client = client;
        setCaption(" Подходящие кредиты");
        setIcon(VaadinIcons.PIGGY_BANK_COIN);
        setModal(true);
        center();
        setContent(loadDataForm());
    }

    private Component loadDataForm() {
        HorizontalLayout buttons = new HorizontalLayout(ok, back);
        Label creditOffer = new Label("Выберете подходящий кредит:");
        if (creditService.findCreditsByAmount(creditAmount).size()==0) {
            form.addComponents(new Label("К сожалению подходящих кредитов не найдено"));
        } else {
            List<Credit> credits = creditService.findCreditsByAmount(creditAmount);
            creditNativeSelect = new NativeSelect<>("Кредиты", credits);
            form.addComponents(creditOffer, creditNativeSelect, buttons);
            ok.addClickListener(event -> this.ok(creditNativeSelect.getValue(), client, creditAmount, creditPeriod));
            ok.setStyleName(ValoTheme.BUTTON_PRIMARY);
            ok.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            back.addClickListener(event -> getUI().removeWindow(CreditOfferForm.this));
        }
        return form;
    }

    private void ok(Credit credit, Client client, Long creditAmount, int creditPeriod) {
        setCaption("Подтверждение данных");
        form.removeAllComponents();
        center();
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        Label checkInfo = new Label("Проверьте данные:");
        checkInfo.addStyleName(ValoTheme.LABEL_SUCCESS);
        header.addComponent(checkInfo);
        header.setComponentAlignment(checkInfo, Alignment.MIDDLE_CENTER);
        Button accept = new Button("ОК");
        Button cancel = new Button("Отмена");
        accept.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        cancel.addStyleName(ValoTheme.BUTTON_DANGER);
        HorizontalLayout buttons = new HorizontalLayout(accept, cancel);
        double paymentPerMounth = (creditAmount + (creditAmount *
                (credit.getCreditProcent()/100) * creditPeriod)) / (creditPeriod*12);
        DecimalFormat df = new DecimalFormat("#.##");
        form.addComponents(
                header,
                new Label(client.toString()),
                new Label("\nСумма кредита: " + creditAmount + " рублей"),
                new Label("\nСрок кредита: " + creditPeriod + " лет"),
                new Label("\nПроцентная ставка: " + credit.getCreditProcent() + "%"),
                new Label("\nПервоначальный взнос: " +
                        df.format(creditAmount - creditAmount*0.8) + "рублей"),
                new Label("\nЕжемесячный платеж: "+ df.format(paymentPerMounth) +" рублей"),
                buttons
        );
        accept.addClickListener(clickEvent -> this.saveCredit());
        cancel.addClickListener(clickEvent -> getUI().removeWindow(CreditOfferForm.this));
        form.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
    }

    private void saveCredit() {

        Notification success = new Notification("Операция завершена успешно!",
                Notification.Type.HUMANIZED_MESSAGE);
        success.setDelayMsec(1500);
        success.show(getUI().getPage());
        getUI().removeWindow(CreditOfferForm.this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
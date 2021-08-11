package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.sql.Date;

public class CreditOfferForm extends Window implements View {

    private final VerticalLayout form = new VerticalLayout();
    private final Button ok = new Button("Выбрать", VaadinIcons.CHECK);
    private final Button back = new Button("Назад");
    private final CreditService creditService;
    private final CreditOfferService creditOfferService;
    private final PaymentScheduleService scheduleService;
    private final BankService bankService;
    private final long creditAmount;
    private final int creditPeriod;
    private final Client client;
    private Credit credit;
    private NativeSelect<Credit> creditNativeSelect;

    public CreditOfferForm(CreditService creditService, long creditAmount, int creditPeriod,
                           Client client, CreditOfferService creditOfferService,
                           PaymentScheduleService scheduleService, BankService bankService) {
        this.creditService = creditService;
        this.creditAmount = creditAmount;
        this.creditPeriod = creditPeriod;
        this.client = client;
        this.creditOfferService = creditOfferService;
        this.scheduleService = scheduleService;
        this.bankService = bankService;

        setCaption(" Подходящие кредиты");
        setIcon(VaadinIcons.PIGGY_BANK_COIN);
        setModal(true);
        center();
        setContent(loadDataForm());
    }

    private Component loadDataForm() {
        HorizontalLayout buttons = new HorizontalLayout(ok, back);
        Label creditOffer = new Label("Выберете подходящий кредит:");
        if (creditService.findCreditsByAmount(creditAmount).size() == 0) {
            form.addComponents(new Label("К сожалению подходящих кредитов не найдено"));
        } else {
            List<Credit> credits = creditService.findCreditsByAmount(creditAmount);
            Collections.sort(credits);
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
        this.credit = credit;
        setCaption("Подтверждение данных");
        form.removeAllComponents();
        center();
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        Label checkInfo = new Label("Проверьте данные");
        checkInfo.addStyleName(ValoTheme.LABEL_SUCCESS);
        header.addComponent(checkInfo);
        header.setComponentAlignment(checkInfo, Alignment.MIDDLE_CENTER);
        Button accept = new Button("Добавить");
        Button cancel = new Button("Отмена");
        accept.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        cancel.addStyleName(ValoTheme.BUTTON_DANGER);
        HorizontalLayout buttons = new HorizontalLayout(accept, cancel);
        double firstPayment = (creditAmount * 0.8 / (creditPeriod * 12)) +
                ((creditAmount * 0.8 * (credit.getCreditProcent() / 100)) / (creditPeriod * 12));
        DecimalFormat df = new DecimalFormat("#.##");
        form.addComponents(
                header,
                new Label(client.toString()),
                new Label("\nСумма кредита: " + creditAmount + " рублей"),
                new Label("\nСрок кредита: " + creditPeriod + " лет"),
                new Label("\nПроцентная ставка: " + credit.getCreditProcent() + "%"),
                new Label("\nПервоначальный взнос: " +
                        df.format(creditAmount - creditAmount * 0.8) + " рублей"),
                new Label("\nПлатеж за первый месяц (фиксированнный + проценты): " +
                        df.format(firstPayment) + " рублей"),
                new Label("\nФиксированный ежемесячный платеж (без учёта процентов): " +
                        df.format(creditAmount * 0.8 / (creditPeriod * 12))),
                buttons
        );
        accept.addClickListener(clickEvent -> this.saveCredit());
        cancel.addClickListener(clickEvent -> getUI().removeWindow(CreditOfferForm.this));
        form.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
    }

    private void saveCredit() {
        try {
            Bank bank = new Bank(client, credit);
            bankService.save(bank);

            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.valueOf(localDateTime.toLocalDate());
            double scale = Math.pow(10, 2);

            double ostatok = creditAmount * 0.8;
            double procent = credit.getCreditProcent();
            int period = creditPeriod * 12;
            double paymentBody = Math.ceil(ostatok / period * scale) / scale;

            for (int i = 0; i < creditPeriod * 12; i++) {
                double paymentProcent = Math.ceil(((ostatok * (procent / 100)) / period) * scale) / scale;
                double paymentPerMonth = Math.ceil((paymentBody + paymentProcent) * scale) / scale;
                if(paymentProcent<0) paymentProcent=0;
                PaymentSchedule schedule = new PaymentSchedule(date, paymentPerMonth, paymentBody, paymentProcent);
                ostatok -= paymentPerMonth;
                localDateTime = localDateTime.plusMonths(1);
                date = Date.valueOf(localDateTime.toLocalDate());
                scheduleService.save(schedule);
                CreditOffer creditOffer = new CreditOffer(client, credit, creditAmount, schedule, bank.getId());
                creditOfferService.save(creditOffer);
            }
            Notification success = new Notification("Операция завершена успешно!",
                    Notification.Type.HUMANIZED_MESSAGE);
            success.setDelayMsec(1500);
            success.show(getUI().getPage());
            getUI().removeWindow(CreditOfferForm.this);
        } catch (Exception e) {
            Notification success = new Notification("Не удалось завершить операцию, попробуйте снова!",
                    Notification.Type.ERROR_MESSAGE);
            success.setDelayMsec(1500);
            success.show(getUI().getPage());
            e.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

}
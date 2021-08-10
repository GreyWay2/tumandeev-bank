package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Credit;
import org.haulmont.tumandeev.CreditService;
import org.haulmont.tumandeev.ViewsAndForms.ClientForm;

public class CreditOfferForm extends Window implements View {

    private CreditService creditService;
    private long creditAmount;
    private int creditPeriod;
    private NativeSelect<Credit> creditNativeSelect;
    private final VerticalLayout form = new VerticalLayout();
    private final Button ok = new Button("Выбрать", VaadinIcons.CHECK);
    private final Button back = new Button("Назад");

    public CreditOfferForm(CreditService creditService, long creditAmount, int creditPeriod) {
        this.creditService = creditService;
        this.creditAmount = creditAmount;
        this.creditPeriod = creditPeriod;
        setCaption(" Подходящие кредиты");
        setIcon(VaadinIcons.PIGGY_BANK_COIN);
        setModal(true);
        center();
        setContent(loadDataForm());
    }

    private Component loadDataForm() {
        HorizontalLayout buttons = new HorizontalLayout(ok, back);
        Label creditOffer = new Label("Выберете подходящий кредит:");
        creditNativeSelect = new NativeSelect<>("Кредиты",
                creditService.findCreditsByAmount(creditAmount));
        form.addComponents(creditOffer, creditNativeSelect, buttons);
        ok.addClickListener(event -> this.save());
        ok.setStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        back.addClickListener(event -> getUI().removeWindow(CreditOfferForm.this));
        return form;
    }

    private void save() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
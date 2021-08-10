package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import java.util.Arrays;


@SpringView(name = "CreditOffer")
public class CreditOfferView extends VerticalLayout implements View {
    HorizontalLayout mainLayout = new HorizontalLayout();
    private final TextField creditAmount = new TextField("Сумма кредита");
    private final NativeSelect<Integer> creditPeriod = new NativeSelect<>
            ("Срок кредита (лет)", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    @PostConstruct
    void init() {
        creditAmount.setRequiredIndicatorVisible(true);
        creditAmount.setPlaceholder("1 000 000");
        creditPeriod.setSelectedItem(1);
        addComponents(creditAmount, creditPeriod);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
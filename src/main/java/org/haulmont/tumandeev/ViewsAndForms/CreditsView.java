package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Credit;
import org.haulmont.tumandeev.CreditService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Credits")
public class CreditsView extends VerticalLayout implements View {

    @Autowired
    CreditService creditService;

    public static Grid<Credit> creditGrid = new Grid<>(Credit.class);
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");

    @PostConstruct
    void init() {
        Navigator.setStyleForButton(2);
        Page.getCurrent().setTitle("Credits");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        editButton.setIcon(VaadinIcons.PENCIL);
        deleteButton.setIcon(VaadinIcons.MINUS);
        addButton.setIcon(VaadinIcons.PLUS);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(addButton, editButton, deleteButton);
        horizontalLayout.setSizeFull();
        horizontalLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        horizontalLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        CheckBox sortCredits = new CheckBox("Сортировать по лимиту");
        sortCredits.addValueChangeListener(valueChangeEvent -> {
            if(sortCredits.getValue())
                creditGrid.setItems(creditService.findAllSort());
            else creditGrid.setItems(creditService.findAll());
        });
        addComponents(sortCredits, horizontalLayout);
        setComponentAlignment(sortCredits, Alignment.TOP_RIGHT);

        creditGrid.setSizeFull();
        creditGrid.setColumns("creditLimit", "creditProcent");
        creditGrid.setItems(creditService.findAll());

        addComponent(creditGrid);

        createEventHandlers();
    }

    private void createEventHandlers() {
        creditGrid.addSelectionListener(valueChangeEvent -> {
            if (!creditGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Credit credit = new Credit();
            CreditForm creditForm = new CreditForm(creditService, credit);
            getUI().addWindow(creditForm);
        });

        editButton.addClickListener(e -> {
            Credit credit = creditGrid.asSingleSelect().getValue();
            CreditForm creditForm = new CreditForm(creditService, credit);
            getUI().addWindow(creditForm);
        });

        deleteButton.addClickListener(e -> {
            Credit credit = creditGrid.asSingleSelect().getValue();
            try {
                creditService.delete(credit);
                updateCreditGrid(creditService);
                Notification notification = new Notification( credit.toString() + " был успешно удален",
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
    }

    static void updateCreditGrid(CreditService creditService) {
        creditGrid.setItems(creditService.findAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}
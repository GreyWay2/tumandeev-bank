package org.haulmont.tumandeev.Views;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.haulmont.tumandeev.Bank;
import org.haulmont.tumandeev.BankService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "Bank")
public class BankView extends VerticalLayout implements View {

    @Autowired
    BankService bankService;

    public static Grid<Bank> bankGrid = new Grid(Bank.class);
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("BankInfo");
        addComponent(new Label("Bank"));
        bankGrid.setSizeFull();
        addComponent(bankGrid);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
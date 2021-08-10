package org.haulmont.tumandeev.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.haulmont.tumandeev.Client;
import org.haulmont.tumandeev.Credit;
import org.haulmont.tumandeev.CreditService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Credits")
public class CreditsView extends VerticalLayout implements View {
    @Autowired
    CreditService creditService;

    public static Grid<Client> creditGrid = new Grid(Credit.class);
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("Credits");
        addComponent(new Label("Credits"));
        creditGrid.setSizeFull();
        addComponent(creditGrid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}
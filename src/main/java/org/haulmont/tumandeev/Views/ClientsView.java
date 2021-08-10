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
import org.haulmont.tumandeev.Client;
import org.haulmont.tumandeev.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "Clients")
public class ClientsView extends VerticalLayout implements View {

    @Autowired
    ClientService clientService;

    public static Grid<Client> clientGrid = new Grid(Client.class);
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("ClientsInfo");
        addComponent(new Label("Clients"));
        clientGrid.setSizeFull();
        addComponent(clientGrid);
    }

    @Override
    public void enter(ViewChangeEvent event) {}
}
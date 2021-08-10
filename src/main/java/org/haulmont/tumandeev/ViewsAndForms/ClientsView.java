package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.haulmont.tumandeev.Client;
import org.haulmont.tumandeev.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "Clients")
public class ClientsView extends VerticalLayout implements View {

    @Autowired
    ClientService clientService;

    public static Grid<Client> clientGrid = new Grid<>(Client.class);
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("Clients");
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
        addComponent(horizontalLayout);

        clientGrid.setSizeFull();
        clientGrid.setColumns("lastName", "firstName", "middleName", "passport");
        clientGrid.setItems(clientService.findAll());

        addComponent(clientGrid);

        createButtonsListeners();
    }

    private void createButtonsListeners() {
        clientGrid.addSelectionListener(valueChangeEvent -> {
            if (!clientGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Client client = new Client();
            ClientForm clientForm = new ClientForm(clientService, client);
            getUI().addWindow(clientForm);
        });

        editButton.addClickListener(e -> {
            Client client = clientGrid.asSingleSelect().getValue();
            ClientForm clientForm = new ClientForm(clientService, client);
            getUI().addWindow(clientForm);
        });

        deleteButton.addClickListener(e -> {
            Client client = clientGrid.asSingleSelect().getValue();
            try {
                clientService.delete(client);
                updateClientGrid(clientService);
                Notification notification = new Notification(client.toString() + " был успешно удален",
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

    static void updateClientGrid(ClientService clientService) {
        clientGrid.setItems(clientService.findAll());
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
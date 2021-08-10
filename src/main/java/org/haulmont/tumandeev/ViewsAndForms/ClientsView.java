package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
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
        MyUI.setStyleForButton(3);
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
        addComponent(createFilter());

        clientGrid.setSizeFull();
        clientGrid.setColumns("lastName", "firstName", "middleName", "passport", "phoneNumber", "email");
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

    private HorizontalLayout createFilter() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        Label label = new Label("Поиск: ");
        label.setSizeFull();
        HorizontalLayout labelLayout = new HorizontalLayout();
        labelLayout.setWidth("100%");
        labelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        labelLayout.addComponents(label);
        TextField nameField = new TextField();
        nameField.setPlaceholder("Фамилия");
        TextField passportField = new TextField();
        passportField.setPlaceholder("Паспорт");
        CheckBox sortClients = new CheckBox("Сортировать по фамилии");
        sortClients.addValueChangeListener(valueChangeEvent -> {
            if(sortClients.getValue()) {
                clientGrid.setItems(clientService.findAllSort());
            } else {
                clientGrid.setItems(clientService.findAll());
            }
        });
        HorizontalLayout sortClientLayout = new HorizontalLayout(sortClients);
        HorizontalLayout filters = new HorizontalLayout(labelLayout, nameField, passportField);
        nameField.addValueChangeListener(this::nameFilter);
        passportField.addValueChangeListener(this::passportFilter);
        mainLayout.addComponents(filters, sortClientLayout);
        mainLayout.setComponentAlignment(sortClientLayout, Alignment.MIDDLE_RIGHT);
        mainLayout.setWidth("100%");
        return mainLayout;
    }

    private void passportFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent) {
        ListDataProvider<Client> dataProvider = (ListDataProvider<Client>) clientGrid.getDataProvider();
        dataProvider.setFilter(Client::getPassport, passport ->
                (passport.toLowerCase()).contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    private void nameFilter(HasValue.ValueChangeEvent<String> stringValueChangeEvent) {
        ListDataProvider<Client> dataProvider = (ListDataProvider<Client>) clientGrid.getDataProvider();
        dataProvider.setFilter(Client::getLastName, lastName ->
                lastName.toLowerCase().contains(stringValueChangeEvent.getValue().toLowerCase()));
    }

    static void updateClientGrid(ClientService clientService) {
        clientGrid.setItems(clientService.findAll());
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
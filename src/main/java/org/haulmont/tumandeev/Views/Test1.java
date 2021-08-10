package org.haulmont.tumandeev.Views;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.haulmont.tumandeev.Client;
import org.haulmont.tumandeev.ClientService;
import org.springframework.beans.factory.annotation.Autowired;


@UIScope
@SpringView(name = "Test1")
public class Test1 extends VerticalLayout implements View {

    @Autowired
    ClientService doctorsService;

    public static Grid<Client> grid = new Grid<>(Client.class);
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");
    private final Button infoButton = new Button("Информация о рецепте");

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("Test1");
        addComponent(new Label("Test1"));
        grid.setSizeFull();
        addComponent(grid);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }
}

package org.haulmont.tumandeev.Views;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "")
public class DefaultView extends VerticalLayout implements View {

    @PostConstruct
    void init() {
        Label label = new Label("Deafult View");
        label.setWidth("50%");
        addComponent(label);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
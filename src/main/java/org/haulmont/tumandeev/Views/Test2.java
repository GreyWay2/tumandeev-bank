package org.haulmont.tumandeev.Views;

import javax.annotation.PostConstruct;

import com.vaadin.spring.annotation.UIScope;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = "Test2")
public class Test2 extends VerticalLayout implements View {


    @PostConstruct
    void init() {
        addComponent(new Label("Test2"));
        System.out.println("Test2");
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
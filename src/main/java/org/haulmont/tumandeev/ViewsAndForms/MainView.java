package org.haulmont.tumandeev.ViewsAndForms;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.Serializable;
import java.io.File;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View, Serializable {

    @PostConstruct
    void init() {

    }

    private FileResource createFileResource (String fileName) {
        return new FileResource(new File("src/main/resources/images/"+fileName));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
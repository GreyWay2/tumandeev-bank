package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.io.Serializable;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class Navigator extends UI implements ViewDisplay, Serializable {

    final VerticalLayout root = new VerticalLayout();
    public static HorizontalLayout buttonsLayout;
    private Panel springViewDisplay;

    @Override
    protected void init(VaadinRequest request) {

        root.setSizeFull();
        setContent(root);

        final HorizontalLayout navigationBar = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();

        navigationBar.setWidth("100%");

        FileResource bankImageResource = createFileResource("bank-icon.png");
        FileResource creditImageResource = createFileResource("credit-icon.png");
        FileResource clientImageResource = createFileResource("clients-icon.png");
        FileResource offerImageResource = createFileResource("offer-icon.png");

        buttonsLayout.addComponents(
                createNavigationButton("Банк", "Bank", bankImageResource),
                createNavigationButton("Кредитные предложения", "Credits", creditImageResource),
                createNavigationButton("Клиенты", "Clients", clientImageResource),
                createNavigationButton("Оформить кредит", "CreditOffer", offerImageResource)
        );

        navigationBar.addComponents(buttonsLayout);
        navigationBar.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

        root.addComponents(navigationBar);
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);
    }

    private Button createNavigationButton(String caption, final String viewName, final FileResource resName) {
        Button button = new Button(caption);
        button.setIcon(resName);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    public static void setStyleForButton(int indexOfCurrentPage) {
        int countOfButtons = buttonsLayout.getComponentCount();
        for(int i=0; i<countOfButtons; i++)
            buttonsLayout.getComponent(i).removeStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        buttonsLayout.getComponent(indexOfCurrentPage).addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
    }

    private FileResource createFileResource (String fileName) {
        return new FileResource(new File("src/main/resources/images/"+fileName));
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }
}
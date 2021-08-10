package org.haulmont.tumandeev.ViewsAndForms;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MyUI extends UI implements ViewDisplay {

    final VerticalLayout root = new VerticalLayout();
    public static HorizontalLayout buttonsLayout;
    private Panel springViewDisplay;

    @Override
    protected void init(VaadinRequest request) {

        root.setSizeFull();
        setContent(root);

        final HorizontalLayout navigationBar = new HorizontalLayout();
        final HorizontalLayout loginLayout = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();

        navigationBar.setWidth("100%");

        buttonsLayout.addComponents(
                createNavigationButton("Меню", ""),
                createNavigationButton("Банк", "Bank"),
                createNavigationButton("Кредиты", "Credits"),
                createNavigationButton("Клиенты", "Clients"),
                createNavigationButton("Оформить кредит", "CreditOffer")
        );

        navigationBar.addComponents(buttonsLayout, loginLayout);
        navigationBar.setComponentAlignment(loginLayout, Alignment.MIDDLE_RIGHT);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

        root.addComponents(navigationBar);
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }
}
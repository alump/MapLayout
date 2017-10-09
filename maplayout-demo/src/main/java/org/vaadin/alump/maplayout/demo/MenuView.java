package org.vaadin.alump.maplayout.demo;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class MenuView extends VerticalLayout implements View {

    public final static String VIEW_NAME = "";

    public MenuView() {
        setMargin(true);
        setSpacing(true);
        setWidth(100, Unit.PERCENTAGE);

        Label header = new Label("MapLayouts Vaadin Add-on");
        header.addStyleName(ValoTheme.LABEL_H1);
        addComponent(header);

        Label description = new Label("Vaadin 8 add-on that allows to present information on and top of SVG maps.");
        addComponent(description);

        addComponent(createNavigationButton(USView.VIEW_NAME, "US States Map"));
        addComponent(createNavigationButton(EuropeView.VIEW_NAME, "Europe Map"));
        addComponent(createNavigationButton(WorldView.VIEW_NAME, "World Countries Map"));

        Link gitHub = new Link("Project at GitHub",
                new ExternalResource("https://github.com/alump/MapLayout"));
        addComponent(gitHub);
    }

    private Button createNavigationButton(String viewName, String buttonCaption) {
        Button button = new  Button(buttonCaption);
        button.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}

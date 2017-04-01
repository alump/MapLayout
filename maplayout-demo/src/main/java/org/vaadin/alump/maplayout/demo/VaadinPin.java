package org.vaadin.alump.maplayout.demo;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * Created by alump on 31/03/2017.
 */
public class VaadinPin extends CssLayout {

    public final static String DEFAULT_DESCRIPTION =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et "
            + "dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut "
            + "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse "
            + "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in "
            + "culpa qui officia deserunt mollit anim id est laborum.";

    public VaadinPin(String location) {
        this(location, DEFAULT_DESCRIPTION);
    }

    public VaadinPin(String location, String description) {
        addStyleName("vaadin-pin");

        Label logo = new Label(VaadinIcons.VAADIN_H.getHtml(), ContentMode.HTML);
        logo.addStyleName("vaadin-pin-logo");
        addComponent(logo);

        Label title = new Label(location);
        title.addStyleName("vaadin-pin-header");
        addComponent(title);

        Label body = new Label(description);
        body.addStyleName("vaadin-pin-body");
        addComponent(body);
    }
}

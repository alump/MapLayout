package org.vaadin.alump.maplayout.demo;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * Created by alump on 31/03/2017.
 */
public class VaadinPin extends CssLayout {

    public VaadinPin(String location) {
        addStyleName("vaadin-pin");

        Label label = new Label(VaadinIcons.VAADIN_H.getHtml(), ContentMode.HTML);
        label.addStyleName("vaadin-pin-content");
        addComponent(label);
    }
}

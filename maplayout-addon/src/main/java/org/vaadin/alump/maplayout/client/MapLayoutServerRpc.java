package org.vaadin.alump.maplayout.client;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.ui.LayoutClickRpc;

// ServerRpc is used to pass events from client to server
public interface MapLayoutServerRpc extends ServerRpc {

    void onClientSideError(String message);

    void onItemClicked(MouseEventDetails details, String itemId);
}

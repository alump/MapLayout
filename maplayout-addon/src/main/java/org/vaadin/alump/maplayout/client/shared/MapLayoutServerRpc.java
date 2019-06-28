package org.vaadin.alump.maplayout.client.shared;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.ui.LayoutClickRpc;

import java.util.List;

// ServerRpc is used to pass events from client to server
public interface MapLayoutServerRpc extends ServerRpc {

    void currentViewport(double fromX, double fromY, double toX, double toY);

    void onClientSideError(String message);

    void onItemClicked(MapLayoutMouseEventDetails details);
}

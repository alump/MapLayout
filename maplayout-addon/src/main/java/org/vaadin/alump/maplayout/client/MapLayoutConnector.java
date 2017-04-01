package org.vaadin.alump.maplayout.client;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractClickEventHandler;
import com.vaadin.client.ui.AbstractLayoutConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;
import org.vaadin.alump.maplayout.MapLayout;
import org.vaadin.alump.maplayout.client.shared.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Client side connector for MapLayout
 */
@SuppressWarnings("GwtInconsistentSerializableClass")
@Connect(MapLayout.class)
public class MapLayoutConnector extends AbstractLayoutConnector {

    private final static Logger LOGGER = Logger.getLogger(MapLayoutConnector.class.getName());

    MapLayoutServerRpc rpc = RpcProxy.create(MapLayoutServerRpc.class, this);
    MapLayoutClickHandler clickEventHandler;

    protected final MapLayoutClientRpc clientRpc = new MapLayoutClientRpc() {

    };

    protected final MapLayoutWidgetListener widgetListener = new MapLayoutWidgetListener() {
        @Override
        public void onInitialRenderDone(MapLayoutWidget widget) {
            getWidget().setItemStyleNames(getState().extraStyleNames);

            getState().childCoordinates.forEach((child, coord) -> {
               getWidget().move(((ComponentConnector)child).getWidget(), coord.x, coord.y);
            });
        }
    };

    public MapLayoutConnector() {
        registerRpc(MapLayoutClientRpc.class, clientRpc);
        clickEventHandler = new MapLayoutClickHandler(this);
    }

    @Override
    protected void init() {
        super.init();
        getWidget().setListener(widgetListener);
    }

    @Override
    public void onUnregister() {
        super.onUnregister();
    }

    @Override
    public MapLayoutWidget getWidget() {
        return (MapLayoutWidget) super.getWidget();
    }

    @Override
    public MapLayoutState getState() {
        return (MapLayoutState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent event) {
        super.onStateChanged(event);

        getWidget().setSkipTitles(!getState().addTitles);

        if(event.isInitialStateChange() || event.hasPropertyChanged("mapResource")) {
            if(getState().mapResource != null) {
                String mapUrl = getResourceUrl(getState().mapResource);
                getWidget().setMap(mapUrl);
            } else {
                LOGGER.severe("Resource null?");
            }
        } else if(getState().extraStyleNames != null) {
            getWidget().setItemStyleNames(getState().extraStyleNames);
        }

        clickEventHandler.handleEventHandlerRegistration();
    }

    @Override
    public void onConnectorHierarchyChange(ConnectorHierarchyChangeEvent event) {

        for (ComponentConnector child : event.getOldChildren()) {
            if (child.getParent() != this) {
                Widget widget = child.getWidget();
                if (widget.isAttached()) {
                    getWidget().remove(widget);
                }
            }
        }

        for (ComponentConnector child : getChildComponents()) {
            if (child.getWidget().getParent() != getWidget()) {
                MapLayoutChildCoords coords = getState().childCoordinates.get(child);

                if(coords != null) {
                    getWidget().add(child.getWidget(), coords.x, coords.y);
                } else {
                    getWidget().add(child.getWidget(), 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public void updateCaption(ComponentConnector connector) {
        //ignored for now
    }

    public class MapLayoutClickHandler extends AbstractClickEventHandler {

        public MapLayoutClickHandler(MapLayoutConnector connector) {
            super(connector, EventId.MAPLAYOUT_CLICK_EVENT_IDENTIFIER);
        }

        protected MapLayoutConnector getConnector() {
            return (MapLayoutConnector)super.connector;
        }

        @Override
        protected void fireClick(NativeEvent event) {
            MouseEventDetails mouseDetails = MouseEventDetailsBuilder
                    .buildMouseEventDetails(event, getRelativeToElement());
            fireClick(event, mouseDetails);
        }

        protected void fireClick(NativeEvent event, MouseEventDetails details) {
            MapLayoutMouseEventDetails fullDetails = new MapLayoutMouseEventDetails();
            fullDetails.setMouseEventDetails(details);
            fullDetails.setElementIds(getWidget().resolveElementIds(event));
            fullDetails.setViewBoxX(getWidget().resolveViewBoxX(details.getRelativeX()));
            fullDetails.setViewBoxY(getWidget().resolveViewBoxY(details.getRelativeY()));

            getConnector().getRpcProxy(MapLayoutServerRpc.class).onItemClicked(fullDetails);
            event.stopPropagation();
        }
    }
}

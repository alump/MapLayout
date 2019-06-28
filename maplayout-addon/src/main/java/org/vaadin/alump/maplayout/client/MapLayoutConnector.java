package org.vaadin.alump.maplayout.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.TooltipInfo;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractClickEventHandler;
import com.vaadin.client.ui.AbstractLayoutConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.ErrorLevel;
import org.vaadin.alump.maplayout.MapLayout;
import org.vaadin.alump.maplayout.client.shared.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Client side connector for MapLayout
 */
@SuppressWarnings("GwtInconsistentSerializableClass")
@Connect(MapLayout.class)
public class MapLayoutConnector extends AbstractLayoutConnector {

    private final static Logger LOGGER = Logger.getLogger(MapLayoutConnector.class.getName());

    MapLayoutClickHandler clickEventHandler;

    protected final MapLayoutClientRpc clientRpc = new MapLayoutClientRpc() {

    };

    protected final MapLayoutWidgetListener widgetListener = new MapLayoutWidgetListener() {
        @Override
        public void onInitialRenderDone(MapLayoutWidget widget) {
            getWidget().setItemStyleNames(getState().extraStyleNames);
            getWidget().setItemStyles(getState().extraStyles);

            getState().childCoordinates.forEach((child, coord) -> {
               getWidget().move(((ComponentConnector)child).getWidget(), coord.x, coord.y);
            });

            Double[] viewport = getWidget().getViewport();
            if(viewport != null) {
                MapLayoutConnector.this.getRpcProxy(MapLayoutServerRpc.class).currentViewport(
                        viewport[0], viewport[1], viewport[2], viewport[3]);
            }
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
    public boolean hasTooltip() {
        return true;
    }

    @Override
    public TooltipInfo getTooltipInfo(Element element) {
        consoleLog("getToolInfo called");
        if(!getState().tooltips.isEmpty()) {
            consoleLog("getToolInfo called 1");
            List<String> elementIds = getWidget().resolveElementIds(element);
            if(elementIds != null && !elementIds.isEmpty()) {

                consoleLog("getToolInfo called 2");

                Optional<String> message = elementIds.stream().filter(id -> getState().tooltips.containsKey(id))
                        .map(id -> getState().tooltips.get(id)).filter(value -> value != null && !value.isEmpty())
                        .findFirst();

                if(message.isPresent()) {
                    consoleLog("getToolInfo called 3");
                    return createTooltipInfo(message.get());
                }
            }
        }

        if(super.hasTooltip()) {
            return super.getTooltipInfo(element);
        } else {
            return null;
        }
    }

    private TooltipInfo createTooltipInfo(String content) {
        TooltipInfo info = new TooltipInfo();
        info.setContentMode(getState().tooltipContentMode);
        info.setTitle(content);
        info.setIdentifier(this);
        info.setErrorLevel(ErrorLevel.INFO);
        return info;
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
        } else {
            if(getState().extraStyleNames != null && event.hasPropertyChanged("extraStyleNames")) {
                getWidget().setItemStyleNames(getState().extraStyleNames);
            }
            if(getState().extraStyles != null && event.hasPropertyChanged("extraStyles")) {
                getWidget().setItemStyles(getState().extraStyles);
            }
        }

        if(event.hasPropertyChanged("viewBox") && getState().viewBox != null) {
            getWidget().setViewBox(getState().viewBox.minX, getState().viewBox.minY, getState().viewBox.width,
                    getState().viewBox.height);
            getState().childCoordinates.forEach((child, coord) -> {
                getWidget().move(((ComponentConnector)child).getWidget(), coord.x, coord.y);
            });
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

    public static native void consoleLog(String msg) /*-{
        console.log(msg);
    }-*/;
}

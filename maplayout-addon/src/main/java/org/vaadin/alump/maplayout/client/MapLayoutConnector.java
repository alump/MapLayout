package org.vaadin.alump.maplayout.client;

import com.google.gwt.user.client.Window;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;
import org.vaadin.alump.maplayout.MapLayout;

import java.util.logging.Logger;

/**
 * Client side connector for MapLayout
 */
@Connect(MapLayout.class)
public class MapLayoutConnector extends AbstractComponentConnector {

    private final static Logger LOGGER = Logger.getLogger(MapLayoutConnector.class.getName());

    MapLayoutServerRpc rpc = RpcProxy.create(MapLayoutServerRpc.class, this);

    private MapLayoutClientRpc clientRpc = new MapLayoutClientRpc() {

    };

    private MapLayoutWidgetListener widgetListener = new MapLayoutWidgetListener() {
        @Override
        public void onInitialRenderDone(MapLayoutWidget widget) {
            getWidget().setItemStyleNames(getState().extraStyleNames);
        }
    };

    public MapLayoutConnector() {
        registerRpc(MapLayoutClientRpc.class, clientRpc);
    }

    @Override
    protected void init() {
        super.init();
        getWidget().setListener(widgetListener);
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
    }
}

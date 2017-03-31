package org.vaadin.alump.maplayout;

import com.vaadin.server.Resource;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.Registration;
import org.vaadin.alump.maplayout.client.shared.MapLayoutServerRpc;
import org.vaadin.alump.maplayout.client.shared.MapLayoutState;
import org.vaadin.alump.maplayout.client.shared.EventId;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Layout where you can overlay components to map
 */
public class MapLayout extends com.vaadin.ui.AbstractComponent {

    private AtomicInteger resourceCounter = new AtomicInteger(0);
    private List<MapLayoutClickListener> clickListeners = new ArrayList<>();

    private MapLayoutServerRpc serverRpc = new MapLayoutServerRpc() {
        @Override
        public void onClientSideError(String message) {
            System.err.println("Client side error: " + message);
        }

        @Override
        public void onItemClicked(MouseEventDetails details, List<String> itemIds) {
            fireClick(details, itemIds);
        }
    };

    public MapLayout() {
        registerRpc(serverRpc, MapLayoutServerRpc.class);
    }

    public MapLayout(Resource background) {
        this();
        setMapBackground(background);
    }

    public void beforeClientResponse(boolean initial) {
        if(getState(false).mapResource == null) {
            throw new IllegalStateException("Background resource not defined");
        }

        super.beforeClientResponse(initial);
    }

    @Override
    protected MapLayoutState getState() {
        return (MapLayoutState) super.getState();
    }

    @Override
    protected MapLayoutState getState(boolean markDirty) {
        return (MapLayoutState) super.getState(markDirty);
    }

    /**
     * Define resource from where the SVG background map will be loaded
     * @param background Background SVG map
     */
    public void setMapBackground(Resource background) {

        if(background == null) {
            throw new IllegalArgumentException("Resource can not be null");
        }

        if(getState(false).mapResource != null) {
            String oldResourceKey = getState(false).mapResource;
            setResource(oldResourceKey, null);
        }

        String newResourceKey = Integer.toHexString(resourceCounter.incrementAndGet());
        getState().mapResource = newResourceKey;
        setResource(newResourceKey, background);
    }

    protected Collection<String> getIdsForExtraStyleName(String styleName) {
        Set<String> ids = getState(false).extraStyleNames.get(Objects.requireNonNull(styleName));
        if(ids == null) {
            ids = new HashSet<>();
            getState().extraStyleNames.put(styleName, ids);
            markAsDirty();
        }
        return ids;
    }

    public void setStyleNameOfItem(String styleName, MapIdProvider item, boolean enabled) {
        setStyleNameOfId(styleName, Objects.requireNonNull(item).getMapId(), enabled);
    }

    public void setStyleNameOfId(String styleName, String id, boolean enabled) {
        if(enabled) {
            addStyleNameToId(styleName, id);
        } else {
            removeStyleNameFromId(styleName, id);
        }
    }

    public void addStyleNameToItem(String styleName, MapIdProvider item) {
        addStyleNameToId(styleName, Objects.requireNonNull(item).getMapId());
    }

    public void addStyleNameToId(String styleName, String id) {
        if(getIdsForExtraStyleName(styleName).add(Objects.requireNonNull(id))) {
            markAsDirty();
        }
    }

    public void removeStyleNameFromItem(String stylename, MapIdProvider item) {
        removeStyleNameFromId(stylename, Objects.requireNonNull(item).getMapId());
    }

    public void removeStyleNameFromId(String stylename, String id) {
        if(getIdsForExtraStyleName(stylename).remove(id)) {
            markAsDirty();
        }
    }

    public void setStyleNamesToItems(String styleName, MapIdProvider ... items) {
        setStyleNamesToItems(styleName, Arrays.asList(items));
    }

    public void setStyleNamesToIds(String styleName, String ... ids) {
        setStyleNamesToIds(styleName, Arrays.asList(ids));
    }

    public void setStyleNamesToItems(String styleName, Collection<MapIdProvider> items) {
        setStyleNamesToIds(styleName, Objects.requireNonNull(items).stream().map(i -> i.getMapId()).collect(Collectors.toSet()));
    }

    public void setStyleNamesToIds(String styleName, Collection<String> ids) {
        Collection<String> styled = getIdsForExtraStyleName(styleName);
        ids.forEach(id -> styled.add(Objects.requireNonNull(id)));
    }


    public void setAddTitles(boolean addTitles) {
        getState().addTitles = addTitles;
    }

    public Registration addMapLayoutClickListener(MapLayoutClickListener listener) {
        return addListener(EventId.MAPLAYOUT_CLICK_EVENT_IDENTIFIER,
                MapLayoutClickEvent.class, listener,
                MapLayoutClickListener.MAP_LAYOUT_CLICK_METHOD);
    }

    public void removeMapLayoutClickListener(MapLayoutClickListener listener) {
        removeListener(EventId.MAPLAYOUT_CLICK_EVENT_IDENTIFIER,
                MapLayoutClickEvent.class, listener);
    }

    protected void fireClick() {
        this.fireEvent(new MapLayoutClickEvent(this, null, null));
    }

    protected void fireClick(MouseEventDetails details, List<String> itemIds) {
        this.fireEvent(new MapLayoutClickEvent(this, details, itemIds));
    }
}

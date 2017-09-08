package org.vaadin.alump.maplayout;

import com.vaadin.server.Resource;

import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import org.vaadin.alump.maplayout.client.shared.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Layout where you can overlay components to map
 */
public class MapLayout<T> extends AbstractLayout {

    public final static String TRANSPARENT_BG_STYLENAME = "transparent-bg";
    public final static String CLICKABLE_STYLENAME = "clickable";

    private AtomicInteger resourceCounter = new AtomicInteger(0);
    private List<MapLayoutClickListener> clickListeners = new ArrayList<>();
    private Set<Component> childComponents = new LinkedHashSet();
    private MapIdProvider<T> mapIdProvider = null;

    private MapLayoutServerRpc serverRpc = new MapLayoutServerRpc() {
        @Override
        public void onClientSideError(String message) {
            System.err.println("Client side error: " + message);
        }

        @Override
        public void onItemClicked(MapLayoutMouseEventDetails details) {
            fireClick(details);
        }
    };

    public MapLayout() {
        registerRpc(serverRpc, MapLayoutServerRpc.class);
    }

    public MapLayout(MapIdProvider<T> mapIdProvider) {
        registerRpc(serverRpc, MapLayoutServerRpc.class);
        this.mapIdProvider = Objects.requireNonNull(mapIdProvider);
    }

    public MapLayout(Resource background, String styleName) {
        this();
        addStyleName(styleName);
        setMapBackground(background);
    }

    public MapLayout(Resource background, String styleName, MapIdProvider<T> mapIdProvider) {
        this(background, styleName);
        this.mapIdProvider = Objects.requireNonNull(mapIdProvider);
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

    public void setStyleNameOfItem(String styleName, T item, boolean enabled) {
        setStyleNameOfId(styleName, mapIdProvider.getMapIdForItem(item), enabled);
    }

    public void setStyleNameOfId(String styleName, String id, boolean enabled) {
        if(enabled) {
            addStyleNameToId(styleName, id);
        } else {
            removeStyleNameFromId(styleName, id);
        }
    }

    public void addStyleNameToItem(String styleName, T item) {
        addStyleNameToId(styleName, mapIdProvider.getMapIdForItem(item));
    }

    public void addStyleNameToId(String styleName, String id) {
        if(getIdsForExtraStyleName(styleName).add(Objects.requireNonNull(id))) {
            markAsDirty();
        }
    }

    public void removeStyleNameFromItem(String stylename, T item) {
        removeStyleNameFromId(stylename, mapIdProvider.getMapIdForItem(item));
    }

    public void removeStyleNameFromId(String stylename, String id) {
        if(getIdsForExtraStyleName(stylename).remove(id)) {
            markAsDirty();
        }
    }

    public void setStyleNamesToItems(String styleName, T ... items) {
        setStyleNamesToItems(styleName, Arrays.asList(items));
    }

    public void setStyleNamesToIds(String styleName, String ... ids) {
        setStyleNamesToIds(styleName, Arrays.asList(ids));
    }

    public void setStyleNamesToItems(String styleName, Collection<T> items) {
        setStyleNamesToIds(styleName, Objects.requireNonNull(items).stream()
                .map(i -> mapIdProvider.getMapIdForItem(i)).collect(Collectors.toSet()));
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

    protected void fireClick(MapLayoutMouseEventDetails details) {
        this.fireEvent(new MapLayoutClickEvent(this, details));
    }

    @Override
    public void replaceComponent(Component oldComponent, Component newComponent) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public int getComponentCount() {
        return childComponents.size();
    }

    @Override
    public Iterator<Component> iterator() {
        return Collections.unmodifiableCollection(childComponents).iterator();
    }

    public Optional<T> getItemForMapId(String mapId) {
        return Optional.ofNullable(mapIdProvider).flatMap(p -> p.getItemFromMapId(mapId));
    }

    @Override
    public void addComponent(Component component) {
        addComponentToViewBox(component, 0.0, 0.0);
    }

    public void addComponentToViewBox(Component component, Double x, Double y) {
        super.addComponent(component);

        getState().childCoordinates.put(component, new MapLayoutChildCoords(x, y));
        childComponents.add(component);
    }

    @Override
    public void removeComponent(Component component) {
        super.removeComponent(component);
        getState().childCoordinates.remove(component);
        childComponents.remove(component);
    }

    public void removeAllComponents() {
        super.removeAllComponents();
        getState().childCoordinates.clear();
        childComponents.clear();
    }

    public void setViewBox(double minX, double minY, double width, double height) {
        getState().viewBox = new MapLayoutViewBox(minX, minY, width, height);
    }
}

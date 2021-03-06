package org.vaadin.alump.maplayout.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.*;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.vaadin.client.VTooltip;

import java.util.*;
import java.util.logging.Logger;

/**
 * GWT widget part of MapLayout
 */
@SuppressWarnings("GWTStyleCheck")
public class MapLayoutWidget extends ComplexPanel {

    private final static Logger LOGGER = Logger.getLogger(MapLayoutWidget.class.getName());

    private Element currentMapElement;
    private boolean skipTitles = false;

    private Map<String,Set<String>> currentStyleNames = new HashMap<>();
    private Map<String,Set<String>> currentStyles = new HashMap<>();

    protected static int widgetCounter = 0;

    protected MapLayoutWidgetListener listener;

    protected Double viewBoxMinX = null;
    protected Double viewBoxMinY = null;
    protected Double viewBoxWidth = null;
    protected Double viewBoxHeight = null;

    public MapLayoutWidget() {

        setElement(Document.get().createDivElement());
        setStyleName("maplayout-addon");

    }

    public void setSkipTitles(boolean skipTitles) {
        this.skipTitles = skipTitles;
    }

    public void setListener(MapLayoutWidgetListener listener) {
        this.listener = listener;
    }

    public void setMap(String url) {
        addStyleName("maplayout-loading");
        RequestBuilder httpClient = new RequestBuilder(RequestBuilder.GET, url);

        try {
            httpClient.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    String responseContent = response.getText();
                    com.google.gwt.xml.client.Document svg = XMLParser.parse(responseContent);
                    injectMap(svg);
                    currentStyleNames = null;

                    Scheduler.get().scheduleDeferred(() -> {
                        if(listener != null) {
                            listener.onInitialRenderDone(MapLayoutWidget.this);
                        }
                    });
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    exception.printStackTrace();
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private void parseViewBox(String viewBox) {
        String[] parts = viewBox.split("\\s", 4);
        viewBoxMinX = Double.parseDouble(parts[0]);
        viewBoxMinY = Double.parseDouble(parts[1]);
        viewBoxWidth = Double.parseDouble(parts[2]);
        viewBoxHeight = Double.parseDouble(parts[3]);
    }

    private void injectMap(com.google.gwt.xml.client.Document source) {
        if(currentMapElement != null) {
            currentMapElement.removeFromParent();
            currentMapElement = null;
        }

        com.google.gwt.xml.client.Element root = source.getDocumentElement();

        if(!root.getTagName().toLowerCase().equals("svg")) {
            LOGGER.severe("Missing SVG tag");
            return;
        } else if(!root.hasAttribute("viewBox")) {
            LOGGER.severe("Missing viewBox attribute");
            return;
        }

        try {
            parseViewBox(root.getAttribute("viewBox"));
        } catch(Exception e) {
            LOGGER.severe("Failed to parse viewBox");
            return;
        }

        currentMapElement = injectInto(getElement(), root);

        //injectTooltipElement(currentMapElement, root);

        currentMapElement.setId("maplayout-addon-" + (++widgetCounter));

        removeStyleName("maplayout-loading");
    }

    /*
    private Element injectTooltipElement(Element mapElement, com.google.gwt.xml.client.Node source) {
        if(this.tooltipWrapper != null) {
            tooltipWrapper.removeFromParent();
        }

        DivElement tooltipElement = Document.get().createDivElement();
        tooltipElement.addClassName("maplayout-tooltip");

        Element foreignObjectElement = createElementNS(source.getNamespaceURI(), "foreignObject");
        foreignObjectElement.appendChild(tooltipElement);

        addStyle(foreignObjectElement, "pointerEvents", "none");

        mapElement.appendChild(foreignObjectElement);

        tooltipElement.getStyle().setDisplay(Style.Display.NONE);

        this.tooltipWrapper = foreignObjectElement;
        this.tooltipElement = tooltipElement;
        return tooltipElement;
    }
    */

    private Element injectInto(Element to, com.google.gwt.xml.client.Node source) {
        if(source.getNodeType() == Node.TEXT_NODE) {
            Text text = Document.get().createTextNode(source.getNodeValue());
            to.appendChild(text);
            return null;
        } else if(source.getNodeType() == Node.ELEMENT_NODE) {
            String nodeName = source.getNodeName();
            if(skipTitles && nodeName.equals("title")) {
                return null;
            }

            Element injected = createElementNS(source.getNamespaceURI(), nodeName);

            for (int i = 0; i < source.getAttributes().getLength(); i++) {
                Node attribute = source.getAttributes().item(i);
                setAttributeNS(injected, attribute.getNamespaceURI(), attribute.getNodeName(), attribute.getNodeValue());
            }

            for (int i = 0; i < source.getChildNodes().getLength(); ++i) {
                com.google.gwt.xml.client.Node injectedChild = source.getChildNodes().item(i);
                injectInto(injected, injectedChild);
            }

            to.appendChild(injected);
            return injected;
        } else {
            return null;
        }
    }

    private static native Element createElementNS(final String ns, final String name)
	/*-{
        return document.createElementNS(ns, name);
    }-*/;

    private static native void setAttributeNS(JavaScriptObject element, final String ns, final String attribute, final String value)
    /*-{
        element.setAttributeNS(ns, attribute, value);
    }-*/;

    private static native Element findChildElement(JavaScriptObject element, final String id)
    /*-{
        if(!id) {
            console.error("Can not find with undefined id");
            return null;
        }
        if(!element) {
            console.error("Can not find from undefined element");
            return null;
        }
        var child = element.querySelector('#' + id);
        if(!child) {
            console.warn('Failed to find map child: ' + id);
        }
        return child;
    }-*/;

    private static native void addClassName(JavaScriptObject element, String className)
    /*-{
        element.classList.add(className);
    }-*/;

    private static native void addStyle(JavaScriptObject element, String styleType, String value)
    /*-{
        element.style[styleType] = value;
    }-*/;

    private static native void removeClassName(JavaScriptObject element, String className)
    /*-{
        element.classList.remove(className);
    }-*/;

    private static native void removeStyle(JavaScriptObject element, String styleName)
    /*-{
        element.style[styleName] = null;
    }-*/;

    public void setItemStyleNames(Map<String,Set<String>> styleNames) {
        if(styleNames == null || currentMapElement == null) {
            return;
        }

        if(currentStyleNames != null) {
            currentStyleNames.entrySet().forEach(entry -> {
                final String removedStyleName = entry.getKey();
                Collection<String> ids = entry.getValue();
                ids.forEach(id -> {
                    Element child = findChildElement(currentMapElement, id);
                    if(child != null) {
                        removeClassName(child, removedStyleName);
                    } else {
                        LOGGER.warning("Failed to find id '" + id + "' from map");
                    }
                });
            });
        }

        currentStyleNames = new HashMap<>();
        styleNames.entrySet().forEach(entry -> {
            final String styleName = entry.getKey();
            Collection<String> ids = entry.getValue();
            if(ids.isEmpty()) {
                return;
            }

            ids.forEach(id -> {
                Element child = findChildElement(currentMapElement, id);
                if(child != null) {
                    addClassName(child, styleName);
                } else {
                    LOGGER.warning("Failed to find id '" + id + "' from map");
                }
            });
            currentStyleNames.put(styleName, new HashSet<>(ids));
        });

    }

    public void setItemStyles(Map<String,Map<String,String>> styles) {
        if(styles == null || currentMapElement == null) {
            return;
        }

        if(currentStyles != null) {
            currentStyles.entrySet().forEach(entry -> {
                final String elementID = entry.getKey();
                final Set<String> styleTypes = entry.getValue();

                Element child = findChildElement(currentMapElement, elementID);
                if(child != null) {
                    styleTypes.forEach(type -> {
                        removeStyle(child, type);
                    });
                } else {
                    LOGGER.warning("Failed to find id '" + elementID + "' from map");
                }
            });
        }

        currentStyles = new HashMap<>();
        styles.forEach((id, elementStyles) -> {
            if(elementStyles.isEmpty()) {
                return;
            }
            Element element = findChildElement(currentMapElement, id);
            if(element != null) {
                elementStyles.forEach((styleType, value) -> {
                    addStyle(element, styleType, value);

                    Set<String> styleTypes = currentStyles.get(id);
                    if (styleTypes == null) {
                        styleTypes = new HashSet<>();
                        currentStyles.put(id, styleTypes);
                    }
                    styleTypes.add(styleType);
                });
            } else {
                LOGGER.warning("Failed find element with ID " + id);
            }
        });
    }

    public List<String> resolveElementIds(NativeEvent event) {
        Element element = Element.as(event.getEventTarget());
        return resolveElementIds(element);
    }

    public List<String> resolveElementIds(Element element) {
        List<String> itemIds = new ArrayList<>();
        try {
            while (element != null && element != currentMapElement) {
                if(element.hasAttribute("id")) {
                    itemIds.add(element.getAttribute("id"));
                }

                element = element.getParentElement();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return itemIds;
    }

    public Double resolveViewBoxX(int relativeX) {
        int clientWidth = getElement().getClientWidth();
        if(clientWidth <= 0) {
            return null;
        }
        //Fix this
        Double viewBoxX = viewBoxMinX + ((double)relativeX) / ((double)clientWidth) * viewBoxWidth;
        return viewBoxX;
    }

    public Double resolveViewBoxY(int relativeY) {
        int clientHeight = getElement().getClientHeight();
        if(clientHeight <= 0) {
            return null;
        }
        //Fix this
        Double viewBoxY = viewBoxMinY + ((double)relativeY) / ((double)clientHeight) * viewBoxHeight;
        return viewBoxY;
    }

    public void add(Widget widget, Double viewBoxX, Double viewBoxY) {

        DivElement wrapper = Document.get().createDivElement();
        wrapper.addClassName("maplayout-child-wrapper");
        getElement().appendChild(wrapper);

        viewBoxToRelative(wrapper, viewBoxX, viewBoxY);

        super.add(widget, wrapper);
    }

    public void move(Widget widget, Double viewBoxX, Double viewBoxY) {
        if(widget.isAttached() && widget.getParent() == this) {
            viewBoxToRelative(widget.getElement().getParentElement(), viewBoxX, viewBoxY);
        }
    }

    @Override
    public boolean remove(Widget widget) {
        if(widget.isAttached() && widget.getParent() == this) {
            widget.getElement().getParentElement().removeFromParent();
        }

        return super.remove(widget);
    }

    public void viewBoxToRelative(Element element, Double x, Double y) {
        if(viewBoxWidth != null && viewBoxWidth > 0) {
            if(x == null) {
                x = viewBoxMinX;
            }
            //x = ((x - viewBoxMinX) / viewBoxWidth)* (double)getElement().getClientWidth();
            x = (x - viewBoxMinX) / viewBoxWidth * 100.0;
            element.getStyle().setLeft(x, Style.Unit.PCT);
        }

        if(viewBoxHeight != null && viewBoxHeight > 0) {
            if(y == null) {
                y = viewBoxMinY;
            }
            //y = ((y - viewBoxMinY) / viewBoxHeight)* (double)getElement().getClientHeight();
            y = (y - viewBoxMinY) / viewBoxHeight * 100.0;
            element.getStyle().setTop(y, Style.Unit.PCT);
        }
    }

    public void setViewBox(double minX, double minY, double width, double height) {
        if(currentMapElement != null) {
            this.viewBoxMinX = minX;
            this.viewBoxMinY = minY;
            this.viewBoxWidth = width;
            this.viewBoxHeight = height;

            String attrValue = minX + " " + minY + " " + width + " " + height;
            currentMapElement.setAttribute("viewBox", attrValue);

            /*
            if(tooltipWrapper != null) {
                tooltipWrapper.setAttribute("width", Double.toString(width));
                tooltipWrapper.setAttribute("height", Double.toString(height));
            }
            */
        }
    }

    public Double[] getViewport() {
        if(viewBoxMinX != null && viewBoxMinY != null && viewBoxWidth != null && viewBoxHeight != null) {
            return new Double[] { viewBoxMinX, viewBoxMinY, viewBoxMinX + viewBoxWidth, viewBoxMinY + viewBoxHeight};
        } else {
            return null;
        }
    }
}
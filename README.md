# MapLayout Add-on for Vaadin 8

MapLayout will offer easy way to layout Vaadin components on top of maps. Eg. allowing to show information of factories around the world in dashboard applications and act as navigation map at the same time. Component will also allow to get click events to map items (eg. countries) and will allow to style map items (eg. highlight some countries).

Add-on will use Apache 2 compatible maps, allowing free usage also in commercial apps.

**NOTICE**: Still under development, no versions released yet. If you want to try add-on, please git clone and build it.

## Source maps used

[World Map with Countries](https://commons.wikimedia.org/wiki/File:BlankMap-World6.svg) (wikipedia.org) - Adapted from [Brianski](https://en.wikipedia.org/wiki/User:Brianski)'s File:[BlankMap-World3.svg](https://en.wikipedia.org/wiki/File:BlankMap-World3.svg) by [Canuckguy](https://en.wikipedia.org/wiki/User:Canuckguy) and originally based on [CIA's political world map](https://www.cia.gov/library/publications/the-world-factbook/docs/refmaps.html). Public Domain, 2017-03-31.

## Online demo

Try the add-on demo at http://app.siika.fi/MapLayoutDemo

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to http://vaadin.com/addon/maplayout

## Building and running demo

git clone <url of the MyComponent repository>
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for maplayout-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your maplayout-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the maplayout-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/maplayout-demo/ to see the application.

### Debugging client-side

Debugging client side code in the maplayout-demo project:
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application or by adding ?superdevmode to the URL
  - You can access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings.
 
## Release notes

### Version 0.1.0 (TBD)
- Still under development
- ...

## Roadmap

This component is developed as a hobby with no public roadmap or any guarantees of upcoming releases. That said, the following features are planned for upcoming releases:
- Adding components to layout (make it actually a layout)
- Layout click events (to notify when child component is clicked)
- Hover effects
- Optimize SVG map (too large now)
- Zooming (allows to adjust viewpoint via server API)
- Latitude and Longitude in click events
- More maps

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

MyComponent is written by <...>

# Developer Guide

## Getting started

Here is a simple example on how to try out the add-on component:

<...>

## API

MapLayouts JavaDoc is available online at <...>

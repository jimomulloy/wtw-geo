wtw-geo
=======

Whatever The Weather (WTW) - Geographic Location service sub system components.

##What is it
A multi-module maven project that provides a Geographic Location service within the WTW SOA application system. 

##Component modules
1. wtw-geo-service: Geolocation service API. 
2. wtw-geo-client: JAXRS service proxy for client side access to Geolocation service API. 
3. wtw-geo-rs: JAXRS Geolocation service access.
4. wtw-geo-manager: Geolocation service API manager implementation.
5. wtw-geo-source: Geolocation data source API. 
6. wtw-geo-source-google: Google implementation of Geolocation data source API. 
7. wtw-geo-source-met: MET Office implementation of Geolocation data source API. 
8. wtw-geo-source-yahoo: Yahoo implementation of Geolocation data source API. 

##Architectural principles
1. Modularity.
2. Encapsulation, separation of concerns, Loose coupling.
3. Flexible, Extensible.
4. Distributed processing
5. Asynchronous processing
6. Variety of front ends, web, mobile, desktop
7. SOA, Web Services
8. Cloud deployment.
9. Continuous Integration, build, test, (unit, integration, UAT, performance, smoke test,) deploy.
10. Source code management with GIT, branches and master development.
11. Architect for OSGi.

## What does it look like?
wtw will be deployed on linode with UI currently prototyping on www.jimomulloy.co.uk:4000
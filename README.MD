# Setup
1. clone the repository
2. run ```./gradlew cleanEclipse eclipse``` (this generates the eclipse project files)
3. open eclipse, and import the (now generated) project into your workspace (any workspace will do)  ENSURE YOU HAVE THE ECLIPSE WTP BEFORE IMPORTING

# Obtaining the eclipse wtp
The eclipse WTP stuff comes with the Eclipse for JEE developers. If you are installing eclipse for the first time, get that version.
Otherwise you will need to install it like so...
1. help -> install new software
2. for the update sites, choose the one that says Luna on it and check the following packages
- - Eclipse Java EE Deveoper Tools
- - Eclipse Web Developer Tools
- - Eclipse Java Web Developer Tools
- - Eclipse Faceted Project Framework
- - Eclipse faceted Project Framework JDT Enabblement
- - Web Development Tools
- - WST Server Adapaters
And anything else you might want or think is relevant.
3. restart eclipse

# Testing
The easy way to test is to run ```./gradlew run```.
The hard way is to install the Liberty profile eclipse plugin and run it through there. If anyone wants to do that they should let me (AbrarSyed) know.

#Deploying to bluemix
```gradle deploy```
You will need the CloudFOundry program installed, and logged in. Dont bother with this for now.. Eventually il add the CloudFoundry eclipse plugin and we can use that.

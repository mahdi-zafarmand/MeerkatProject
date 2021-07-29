# MeerkatProject
 A data analytic tool for analyzing network of entities (static &amp; dynamic networks). 

Predominantly, the development of the application Meerkat is done using Netbeans IDE on Linux. The application is run on Java version 1.8.0 build 45 and above. 

The project from the developer’s perspective is divided into 3 projects:
* MeerkatUI
* MeerkatAPI
* MeerkatLogic


**Recommendation**: It would be best to install Netbeans IDE 8.2 as it is the most compatible tool to run and develop Meerkat (developers' choice).

You need to compile (build) the MeerkatLogic, MeerkatAPI, and MeerkatUI in this exact order and introduce each jar file to the next project while making it. This way, you would have a functioning version of Meerkat that is ready to use.

**Some other tips:**

1. For all 3 projects, you need to add the Junit library. You can right click on project -> Properties -> libraries -> Compile Test tab -> add Library -> Add Junit 4.xx (Do the same thing for <Run test> tab too).
 
2. For the MeerkatUI project, you need to add the Hamcrest library. You can right click on project -> Properties -> libraries -> Compile Test tab -> add Library -> Add Hamcrest.
 
3. Instead of adding projects as dependencies for MeerkatUI (MeerkatLogic & MeerkatAPI) and MeerkatLogic (MeerkatAPI), you may remove the projects as dependencies and instead add the jar files of projects (you can find jar files in <dist> folder under each project). Make sure you compile the MeerkatLogic first, then MeerkatAPI before adding the corresponding jar files to the dependent projects.
 

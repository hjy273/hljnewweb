weblogic.xml
---------------------------------
<?xml version="1.0" encoding="UTF-8"?>
<weblogic-web-app xmlns="http://www.bea.com/ns/weblogic/90" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.bea.com/ns/weblogic/90 http://www.bea.com/ns/weblogic/90/weblogic-web-app.xsd">
    <context-root>WebApp</context-root>

</weblogic-web-app>
-------------------------------------
<?xml version='1.0' encoding='UTF-8'?> 
 <weblogic-web-app xmlns="http://www.bea.com/ns/weblogic/90" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> 
   <container-descriptor> 
     <filter-dispatched-requests-enabled>false</filter-dispatched-requests-enabled> 
   </container-descriptor> 
  
   <charset-params> 
     <input-charset> 
       <resource-path>/*</resource-path> 
       <java-charset-name>GBK</java-charset-name> 
     </input-charset> 
   </charset-params> 
 </weblogic-web-app> 
 
weblogic10.xml
<?xml version='1.0' encoding='UTF-8'?> 
 <weblogic-web-app xmlns="http://www.bea.com/ns/weblogic/weblogic-web-app" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> 
   <container-descriptor> 
     <filter-dispatched-requests-enabled>false</filter-dispatched-requests-enabled> 
         <prefer-web-inf-classes>true</prefer-web-inf-classes> 
   </container-descriptor> 
  
   <charset-params> 
     <input-charset> 
       <resource-path>/*</resource-path> 
       <java-charset-name>GBK</java-charset-name> 
     </input-charset> 
   </charset-params> 
 </weblogic-web-app>  
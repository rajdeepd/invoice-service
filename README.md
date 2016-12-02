#SOAP Based Invoice Service

Input SOAP Xmls can be found in `request` folder

##UnAuthorized Request

**Request**


    $ curl --header "content-type: text/xml" -d @request-unauthorized.xml http://invoice-server.herokuapp.com/ws

**Response**

    <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
    <ns2:billProjectResponse xmlns:ns2="http://salesforce.com/th/invoice-web-service">
    <ns2:status>unauthorized</ns2:status>
    </ns2:billProjectResponse></SOAP-ENV:Body>
    </SOAP-ENV>
  
##Valid New Request##

**RequestCommand**

    $ curl --header "content-type: text/xml" -d @request-valid.xml http://invoice-server.herokuapp.com/ws

**Response**

     <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
        <SOAP-ENV:Header/>
        <SOAP-ENV:Body>
        <ns2:billProjectResponse xmlns:ns2="http://salesforce.com/th/invoice-web-service">
        <ns2:status>ok</ns2:status>
        </ns2:billProjectResponse></SOAP-ENV:Body>
     </SOAP-ENV>
     
##Valid Update Request##

**RequestCommand**

    $ curl --header "content-type: text/xml" -d @request-valid.-updated.xml http://invoice-server.herokuapp.com/ws

**Response**

    <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
        <SOAP-ENV:Header/>
        <SOAP-ENV:Body>
        <ns2:billProjectResponse xmlns:ns2="http://salesforce.com/th/invoice-web-service">
        <ns2:status>ok</ns2:status>
        </ns2:billProjectResponse></SOAP-ENV:Body>
    </SOAP-ENV>
    
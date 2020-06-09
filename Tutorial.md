# Tutorial Spider & Active Scan
## Prerequisites
To follow this tutorial it is beneficial to have a Java IDE with Maven support. I recommend [IntelliJ](https://www.jetbrains.com/idea/). In addition you need the [Zaproxy](https://www.zaproxy.org/).  
The starting point of this tutorial is the [demo branch](https://github.com/hottek/e-portfolio/tree/demo) of this repository. This provides you with a basic setup of the application which we are going to build. During this tutorial we are going to target the [Google Firing Range](https://github.com/google/firing-range).
## Spider
Lets spider the application. In the ```start()``` function located in the ```Spider``` class we will add our code. Spidering the application will give us more information about the target by discovering new resources (URLs) on the site. 
### Writing the Spider
First up you can see that we have created a `ClientApi` object called `api`. This object allows us to call api functions provided by ZAP. A documention of the API can be found [here](https://www.zaproxy.org/docs/api/).  
Next we are going to call the 
```
// Start spidering the target
System.out.println("Spidering target : " + TARGET);
ApiResponse resp = api.spider.scan(TARGET, null, null, null, null);
String scanID;
int progress;
```
``api.spider.scan``. This gets us an `ApiResponse` object. Additionally we declare the variables `scanID` and `progress`.  
The `scanID` is retrived from the `resp`.
```
// The scan returns a scan id to support concurrent scanning
scanID = ((ApiResponseElement) resp).getValue();
```
Next we have to poll the status until the scan completes.
```
// Poll the status until it completes
while (true) {
    Thread.sleep(1000);
    progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanID)).getValue());
    System.out.println("Spider progress : " + progress + "%");
    if (progress >= 100) {
        break;
    }
}
```
We do that, by checking if the progress has reached 100, as returned from the `api.spider.status` call.  
Once the scan has finished we can retrieve the results from the api and print them.
```
System.out.println("Spider completed");
// If required post process the spider results
List<ApiResponse> spiderResults = ((ApiResponseList) api.spider.results(scanID)).getItems();
for (ApiResponse response: spiderResults) {
    System.out.println(response.toString());
}
```
To get this piece to run we have to surround the code from above with a `try catch`.
```
try {
    // Start spidering the target

    ...    

    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        e.printStackTrace();
    }
```  
### Starting the Spider
Back in the `Main` class, make sure that a new `Spider` object is created in the `main` function and that the `start()` function of `spider` is called.
```
Spider spider = new Spider(ZAP_ADDRESS, ZAP_PORT, TARGET, ZAP_API_KEY);
spider.start();
``` 
## Active Scan
After retrieving information about the target, we are ready to actively scan it. With this process, we are able to find potential vulnerabilites by using known attacks against the target. 
### Writing the Active Scan
On the code site of things, much of the code from the `Spider` class can be copied.  
The call for the `ApiResponse` object has to be changed to:
```
 ApiResponse resp = api.ascan.scan(TARGET, "True", "False", null, null, null);
```
Additionally when checking on the progress, we set a higher timeout and also have to adjust how we retrieve the progress.
```
Thread.sleep(5000);
progress = Integer.parseInt(((ApiResponseElement) api.ascan.status(scanid)).getValue());
```
To print the found vulnerabilities we simply create a xml-report which is also provided by the api.
```
// Print vulnerabilities found by the scanning
System.out.println("Alerts:");
System.out.println(new String(api.core.xmlreport(), StandardCharsets.UTF_8));
```
This report can also be saved to a file.
### Starting the Active Scan
Like the `Spider` class, we create a new `ActiveScan` object in the `Main` class and call its `start()` function.
```
ActiveScan activeScan = new ActiveScan(ZAP_ADDRESS, ZAP_PORT, TARGET, ZAP_API_KEY);
activeScan.start();
```
## Closing Words
A working example of the code above is located in the [master branch](https://github.com/hottek/e-portfolio/tree/master) of this repository. For questions, please open an issue.
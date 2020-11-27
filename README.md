# Applitools Holiday Shopping Festival Hackathon

#### Automated visual validation testing using Applitools across several browsers and devices using Ultrafast Cloud solution from Applitools.

## Table of Contents
1. [About the repository](#about)  
2. [Demonstration](#demo)
3. [Installation](#installation)
4. [Execution using the terminal](#terminalExecution)
5. [Execution using Eclipse IDE](#IDEExecution)
6. [Test Execution Results](#executionResults)
7. [Pixel based comparison demo](#pixelDemo)


<a name="about"></a>
## About the repository
* This repository was created as part of [Applitools Holiday Shopping Hackathon entry](https://applitools.com/hackathon-v20-3-instructions/).
* This assignment is created using [Selenium webdriver]() available in Java binding to automate the browser actions.  
* [TestNG]() is used as the testing framework.  
* It also uses Maven to manage dependencies effortlessly.  
* **Structure:**
  - Two packages:
    - applitools.HolidayShoppingHackathon
    - base
  - Two classes:
    - BaseTests.java
    - AllTests.java
  - resources directory
    - chromedriver
  - pom.xml
  - testng.xml
    

<a name="demo"></a>
## Demonstration
![Ultrafast Cloud](resources/media/final_tests.gif)


<a name="installation"></a>
## Prerequisites & Installation
* An account is created with Applitools. If you don't have one, you can create a free account from [here](https://auth.applitools.com/users/register).  
* **_Dependencies:_**  
  - Java 11  
  - Maven 3.6.3  
  - Chrome browser. I currently have v87 on my machine.
  - Chromedriver executable
* The environment variable for `APPLITOOLS_API_KEY` is correctly set in your system path.  
* The chromedriver should be compatible with your browser version. Click here to understand what version is applicable to your machine and replace it with the one placed in the _resources_ directory.


<a name="terminalExecution"></a>
## Execution (using the terminal)
1. Clone the repository using the command `git clone https://github.com/shwetaneelsharma/applitools-holiday-shopping-festival-hackathon.git`.  
2. Open the terminal and navigate to the project directory.  
3. To run all the tests, execute the command `mvn clean test -DsuiteXmlFile=testng.xml` in your terminal.  
4. **NOTE:** Since chrome is configured to run in headless mode, you will not see a physical browser opening up.

<a name="IDEExecution"></a>
## Execution (using the IDE)
1. Import the maven repository in the Eclipse IDE.  
2. Right click on `testng.xml` -> `Run As -> `TestNG Suite`. 


<a name="executionResults"></a>
## Test Execution Results  
### PART 1
|Main Page|Filtered Product Grid|Product details|
|---------|---------------------|---------------|
|![Test 1](resources/media/part1_T1.png)|![Test 2](resources/media/part1_T2.png)|![Test 3](resources/media/part1_T3.png)|


### PART 2
|Main Page|Filtered Product Grid|Product details|
|---------|---------------------|---------------|
|![Test 1](resources/media/part2_T1_bug.png)|![Test 2](resources/media/part2_T2_bug.png)|![Test 3](resources/media/part2_T3_bug.png)|

### PART 3
|Main Page|Filtered Product Grid|Product details|
|---------|---------------------|---------------|
|![Test 1](resources/media/part3_T1.png)|![Test 2](resources/media/part3_2.png)|![Test 3](resources/media/part3_T3.png)|


<a name="pixelDemo"></a>
## Pixel based comparison demo using the _Exact_ match level
![Demonstration](resources/media/part3_exact.gif)



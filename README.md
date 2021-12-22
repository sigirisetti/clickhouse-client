# Simple Java Swing Client to connect to Clickhouse DB

clone the repo and import projects into IDE as maven projects

## Application class to run

clickhouse-client-gui module contains com.ssk.clickhouse.client.gui.Application, which is main class to launch application
s
You may perform local maven build to generate executable jar and run it directly from jar

![Application Main Window](/images/App.png)


## Connecting to Clickhouse

Click connection icon to add connections

![Application Main Window](/images/ConnDialog.png)

Once added connections appear on the left navigation menu. Right click on target connection node and click connect

Expand database node and expand tables under database.

Right click on any table node to view data. 

![Table Data](/images/TableData.png)

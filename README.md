# Task: Queue between a file reader and writer

Application entry point:
```
com.company.queuelines.App
```

# Implementation details
Server/client was implemented using HTTP protocol. App.java contains full worklfow to test the solution: 
1) start server in a separate thread
2) start reader in a separate thread
3) start writer in a separate thread

# Requirements
Java Runtime (version 8 or later) for executing application
Maven (version 3 or later) for running tests

# Usage
Application takes input file as #1 argument, output file as #2 argument and (optional, default: 8888) port as #3 parameter.
It can be run via executable jar inside 'bin' directory. For example, to copy contents from file '300.txt' to file '300.txt.new' while using the queue service binded at port 8000, execute:
```
java -jar queue-lines-1.0-SNAPSHOT.jar 300.txt 300.txt.new 8000
```

# Tests
Tests can be run via maven:
```
mvn test
```

# External Integration with Team 1

## Contributing Guidelines

* It is good programming practice that commit messages that follow a standard. For our project, kindly use the standard given [here](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)

* the ```src``` folder contains your source code in a hierarchical package structure.

* It is also good practice to never write code to the ```master``` branch directly.

* Code should be written in a per-feature basis. Every new feature should branch off of ```dev``` and when ready, make a Pull/Merge request to ```dev``` . The ```dev``` branch should then be merged with ```master``` when the code is stable enough

* Comments should be JavaDoc documentation comments
```java
 /**
   * This method is used to add two integers. This is
   * a the simplest form of a class method, just to
   * show the usage of various javadoc Tags.
   * @param numA This is the first paramter to addNum method
   * @param numB  This is the second parameter to addNum method
   * @return int This returns sum of numA and numB.
   */
   public int addNum(int numA, int numB) {
      return numA + numB;
   }
```

### Dependencies 
JARs should be placed in ``${PROJECT_ROOT}/ext/`` added to ``CLASSPATH`` while executing.

- Java version 11 
- [Java MySQL Driver](https://static.javatpoint.com/src/jdbc/mysql-connector.jar)
- Mysql and related binaries in ``PATH`` env variable
* JUnit4 (Maven: junit:junit:4.12)
* JUnit5

## Work Model 
The client ```sdd.team1.david.client.Client``` queries the servers ```sdd.team1.david.server.Server``` once per day at a pre-determined time of the day.

The server contains a script file ```dbdump.sh``` . It must be configured with the name of the database to be synced.

The server then replies back with the requested data.

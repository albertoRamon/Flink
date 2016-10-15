# How to Read Apache Kylin from Apache Flink using Scala 
&nbsp;

**Date:** October 2016

**Author:** Ramón Portolés, Alberto  ![alt text](./Images/00.png)  [Linkedin](https://www.linkedin.com/in/alberto-ramon-portoles-a02b523b "My Linkedin") 

&nbsp;
&nbsp;
### Intro
There are several Attempts to use this in Scala  and JDBC [Attempt1](http://apache-flink-user-mailing-list-archive.2336050.n4.nabble.com/JDBCInputFormat-preparation-with-Flink-1-1-SNAPSHOT-and-Scala-2-11-td5371.html)  [Attempt2](http://apache-flink-user-mailing-list-archive.2336050.n4.nabble.com/Type-of-TypeVariable-OT-in-class-org-apache-flink-api-common-io-RichInputFormat-could-not-be-determi-td7287.html)  [Attempt3](http://stackoverflow.com/questions/36067881/create-dataset-from-jdbc-source-in-flink-using-scala)  [Attempt4](https://codegists.com/snippet/scala/jdbcissuescala_zeitgeist_scala)  but none works  ...

**Problem 1:** There aren’t any doc about connect  Kylin with  Flink

**Problem 2:**  We will try use CreateInput and [JDBCInputFormat](https://ci.apache.org/projects/flink/flink-docs-release-1.2/dev/batch/index.html) in batch mode and access via JDBC to Kylin. But isn’t implemented in Scala is only in Java [MailList](http://apache-flink-user-mailing-list-archive.2336050.n4.nabble.com/jdbc-JDBCInputFormat-td9393.html)
	

Then , We will go step by step solving problems

&nbsp;
### Pre-requisites
* We need an instance of Kylin, with a cube: [Quick Start with Sample Cube](http://kylin.apache.org/docs15/tutorial/kylin_sample.html), will be enough

   You can check: 
<p align="center">
  <img src=./Images/01.png />
</p>

* [Scala](http://www.scala-lang.org/) and [Apache Flink](http://flink.apache.org/) Installed

* [IntelliJ](https://www.jetbrains.com/idea/) Installed and configured for Scala / Flink (See [Flink IDE setup guide](https://ci.apache.org/projects/flink/flink-docs-release-1.1/internals/ide_setup.html) )

&nbsp;
### Used Software:
* [Apache Flink](http://flink.apache.org/downloads.html) v1.2-SNAPSHOT
* [Apache Kylin](http://kylin.apache.org/download/) v1.5.2
* [IntelliJ](https://www.jetbrains.com/idea/download/#section=linux)  v2016.2
* [Scala](downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz)  v2.11

&nbsp;
### Starting point:
This can be out initial skeleton: 

```
import org.apache.flink.api.scala._
val env = ExecutionEnvironment.getExecutionEnvironment
val inputFormat = JDBCInputFormat.buildJDBCInputFormat()
  .setDrivername("org.apache.kylin.jdbc.Driver")
  .setDBUrl("jdbc:kylin://172.17.0.2:7070/learn_kylin")
  .setUsername("ADMIN")
  .setPassword("KYLIN")
  .setQuery("select count(distinct seller_id) as sellers from kylin_sales group by part_dt order by part_dt")
  .finish()
  val dataset =env.createInput(inputFormat)
```

The first error is: ![alt text](./Images/02.png)

We add to Scala: 
```
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat
```

&nbsp;

Next error is  ![alt text](./Images/03.png)

We can solve dependencies [(mvn repository: jdbc)](https://mvnrepository.com/artifact/org.apache.flink/flink-jdbc/1.1.2)

Add this to your pom.xml:
```
<dependency>
   <groupId>org.apache.flink</groupId>
   <artifactId>flink-jdbc</artifactId>
   <version>${flink.version}</version>
</dependency>
```

&nbsp;
## Solve dependencies of row 
Similar to previous point we need solve dependencies of Row Class [(mvn repository: Table) ](https://mvnrepository.com/artifact/org.apache.flink/flink-table_2.10/1.1.2):
<p align="center">
  <img src=./Images/03b.png />
</p>

* In POM.XML
```
<dependency>
   <groupId>org.apache.flink</groupId>
   <artifactId>flink-table_2.10</artifactId>
   <version>${flink.version}</version>
</dependency>
```

* In Scala: 
```
import org.apache.flink.api.table.Row
```

&nbsp;
## Solve RowTypeInfo property (and their new dependencies)
This is the new error to solve
<p align="center">
  <img src=./Images/04.png />
</p>

* If we check the code of [JDBCInputFormat.java](https://github.com/apache/flink/blob/master/flink-batch-connectors/flink-jdbc/src/main/java/org/apache/flink/api/java/io/jdbc/JDBCInputFormat.java#L69), we can see [this new property](https://github.com/apache/flink/commit/09b428bd65819b946cf82ab1fdee305eb5a941f5#diff-9b49a5041d50d9f9fad3f8060b3d1310R69) (and mandatory) added on Apr 2016 by [FLINK-3750](https://issues.apache.org/jira/browse/FLINK-3750)  Manual [JDBCInputFormat](https://ci.apache.org/projects/flink/flink-docs-master/api/java/org/apache/flink/api/java/io/jdbc/JDBCInputFormat.html) v1.2 in Java

   Add the new Property: **setRowTypeInfo**
   
```
val inputFormat = JDBCInputFormat.buildJDBCInputFormat()
  .setDrivername("org.apache.kylin.jdbc.Driver")
  .setDBUrl("jdbc:kylin://172.17.0.2:7070/learn_kylin")
  .setUsername("ADMIN")
  .setPassword("KYLIN")
  .setQuery("select count(distinct seller_id) as sellers from kylin_sales group by part_dt order by part_dt")
  .setRowTypeInfo(DB_ROWTYPE)
  .finish()
```

* ¿How can configure this  property in Scala? In [Attempt4](https://codegists.com/snippet/scala/jdbcissuescala_zeitgeist_scala), there is an incorrect solution
   
   We can check the types using the intellisense: ![alt text](./Images/05.png)
   
   Then we will need add more dependences :(
   
   Add to scala:
```
		import org.apache.flink.api.table.typeutils.RowTypeInfo
		import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeInformation}
```

   We need create a Array or Seq of TypeInformation[ ]
<p align="center">
  <img src=./Images/06.png />
</p>

   My solution:
   
```
   var stringColum: TypeInformation[String] = createTypeInformation[String]
   val DB_ROWTYPE = new RowTypeInfo(Seq(stringColum))
```

&nbsp;
## Solve Class Not Found
We need find the kylin-jdbc-x.x.x.jar and expose to flink
<p align="center">
  <img src=./Images/07.png />
</p>

1. We need to find the JAR Class for the JDBC Connector 

   From Kylin [Download](http://kylin.apache.org/download/) Choose **Binary** and the **correct version of Kylin and HBase**
   
   Download & Unpack: in ./lib: 
   
<p align="center">
  <img src=./Images/08.png />
</p>


2. Make this JAR accessible to Flink

   If you execute like service you need put this JAR in you Java ClassPATH using your .bashrc 
<p align="center">
  <img src=./Images/09.png />
</p>

  Check the actual value: ![alt text](./Images/10.png)
  
  Check the permission for this file (Must be accessible for you):
<p align="center">
  <img src=./Images/11.png />
</p>
 

  If you are executing from IDE, you need add your ClassPath manually:
  
  On IntelliJ: ![alt text](./Images/12.png)  > ![alt text](./Images/13.png) > ![alt text](./Images/14.png) > ![alt text](./Images/15.png)
  
  The result, will be similar to: ![alt text](./Images/16.png)
  
&nbsp;
## Solve Couldn’t access resultSet
<p align="center">
  <img src=./Images/17.png />
</p>

Is related with [Flink 4108](https://issues.apache.org/jira/browse/FLINK-4108)  [(MailList)](http://apache-flink-user-mailing-list-archive.2336050.n4.nabble.com/jdbc-JDBCInputFormat-td9393.html#a9415) and Timo Walther [make a PR](https://github.com/apache/flink/pull/2619)

If you are <= Flink 1.2 you will need apply this path and make clean install

&nbsp;
## Solve the casting
<p align="center">
  <img src=./Images/18.png />
</p>
In the error msg you have the problem and solution …. nice ;)  ¡¡

&nbsp;
## The result
The out must be similar to this, print the result of query by standard output:
<p align="center">
  <img src=./Images/19.png />
</p>

&nbsp;
## Now more complex
We can try with multi-colum and multi-type query:

```
select part_dt, sum(price) as total_selled, count(distinct seller_id) as sellers 
from kylin_sales 
group by part_dt 
order by part_dt
```

We will need changes in DB_ROWTYPE:
<p align="center">
  <img src=./Images/20.png />
</p>

And import lib of Java, to work with Data type of Java  ![alt text](./Images/21.png)

The new Result will be: ![alt text](./Images/22.png)

&nbsp;
## Error:  Reused Connection
<p align="center">
  <img src=./Images/23.png />
</p>
Check if your HBase and Kylin is working
Also you can use Kylin UI for it

&nbsp;
## Final Words
**Now we can read Kylin’s data from Apache Flink, great News ¡¡**

We solved all integration problems, and tested with different types of data (Long, BigDecimal and Dates)

Today  (12 Oct 2016) Flink 1.2-SnapShot, you need download, apply path, compile and make install …. but in new releases will not necessary

&nbsp;
&nbsp;

**For any suggestions, feel free to contact me**

**Thanks, Alberto**

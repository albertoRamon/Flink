package DataSources

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.flink.api.scala._
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat
import org.apache.flink.api.table.Row
import org.apache.flink.api.table.typeutils.RowTypeInfo
import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeInformation}


object WordCount {
  def main(args: Array[String]) {

    val PATH = getClass.getResource("").getPath

    // set up the execution environment
    val env = ExecutionEnvironment.getExecutionEnvironment


    // Read using textFile
    val text = env.readTextFile("file:///home/arp/GitHub/Flink/flink-scala-project/target/classes/DataSources/data.txt")
    val text2 = env.readTextFileWithValue("file:///home/arp/GitHub/Flink/flink-scala-project/target/classes/DataSources/data.txt")
    val counts = text.flatMap { _.toLowerCase.split("\\W+") }
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)
    //counts.print()

    //Read 3 (all) fields of CSV
    val text3 = env.readCsvFile[(String,String, String)]("file:///home/arp/GitHub/Flink/flink-scala-project/target/classes/DataSources/data.csv")
    val counts3 = text3.map(x=>x._1)  //Process only first column
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)
    //counts3.print()

    //Read selectd columns of CSV
    val text4 = env.readCsvFile [Tuple1[String]]("file:///home/arp/GitHub/Flink/flink-scala-project/target/classes/DataSources/data.csv"
      ,fieldDelimiter = ","
      ,includedFields = Array(0))
    val counts4 = text3
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)
    //counts4.print()
    var stringColum: TypeInformation[Int] = createTypeInformation[Int]
    val DB_ROWTYPE = new RowTypeInfo(Seq(stringColum))

    val inputFormat = JDBCInputFormat.buildJDBCInputFormat()
      .setDrivername("org.apache.kylin.jdbc.Driver")
      .setDBUrl("jdbc:kylin://172.17.0.2:7070/learn_kylin")
      .setUsername("ADMIN")
      .setPassword("KYLIN")
      .setQuery("select count(distinct seller_id) as sellers from kylin_sales group by part_dt order by part_dt")
      .setRowTypeInfo(DB_ROWTYPE)
      .finish()

      val dataset =env.createInput(inputFormat)
    dataset.print()

    println(PATH)
   }
}

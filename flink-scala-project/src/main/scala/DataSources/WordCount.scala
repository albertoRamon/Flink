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
    val text4 = env.readCsvFile [(String,String)]("file:///home/arp/GitHub/Flink/flink-scala-project/target/classes/DataSources/data.csv"
      ,fieldDelimiter = ","
      ,includedFields = Array(0,1))
    val counts4 = text3  //Process only first column
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)
    counts4.print()

    val text5 = env.generateSequence(1,10)
    val counts5 =text5
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)
    counts5.print()

    println(PATH)
   }
}

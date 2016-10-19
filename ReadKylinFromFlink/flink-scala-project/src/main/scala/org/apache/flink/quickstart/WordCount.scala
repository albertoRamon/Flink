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
import java.util.Date


object WordCount {
  def main(args: Array[String]) {

    val PATH = getClass.getResource("").getPath

    // set up the execution environment
    val env = ExecutionEnvironment.getExecutionEnvironment



    // Read data from JDBC (Kylin in our case)
    var longColum: TypeInformation[Long] = createTypeInformation[Long]
    var bigDecimalColum: TypeInformation[BigDecimal] = createTypeInformation[BigDecimal]
    var dateColum: TypeInformation[Date] = createTypeInformation[Date]

    val DB_ROWTYPE = new RowTypeInfo(Seq(dateColum,bigDecimalColum,longColum))

    val inputFormat = JDBCInputFormat.buildJDBCInputFormat()
      .setDrivername("org.apache.kylin.jdbc.Driver")
      .setDBUrl("jdbc:kylin://172.17.0.2:7070/learn_kylin")
      .setUsername("ADMIN")
      .setPassword("KYLIN")
      .setQuery("select part_dt, sum(price) as total_selled, count(distinct seller_id) as sellers from kylin_sales group by part_dt order by part_dt")
      .setRowTypeInfo(DB_ROWTYPE)
      .finish()

    val dataset =env.createInput(inputFormat)
    dataset.print()

    println(PATH)
  }
}

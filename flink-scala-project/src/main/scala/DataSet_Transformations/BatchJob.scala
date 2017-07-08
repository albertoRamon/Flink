package DataSet_Transformations


import org.apache.flink.api.scala._


object BatchJob {
  def main(args: Array[String]) {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val text =env.fromElements("a","b","a c","d,f")

    val t1=text.map (x=> (x,1))  //generate a new tuple by element
    t1.print()


  }
}
package com.charlie.world.bigtable.utils

import scala.collection.JavaConverters._

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 7.
  */
trait JavaScalaConverter {

  /**
    * Scala Seq to Java List
    *
    * @param seq Scala Seq
    * @return Java util List
    * */
  implicit def toJava[T](seq: Seq[T]) = seqAsJavaList(seq)

  /**
    * Java List to Scala Seq
    *
    * @param list Java util List
    * @return Scala Seq
    * */
  implicit def toScala[T](list: java.util.List[T]) = asScalaBufferConverter(list).asScala
}

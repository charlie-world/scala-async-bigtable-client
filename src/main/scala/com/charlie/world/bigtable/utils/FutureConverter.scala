package com.charlie.world.bigtable.utils

import com.google.common.util.concurrent.{FutureCallback, Futures, ListenableFuture}

import scala.concurrent.{Future, Promise}

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 5.
  */
trait FutureConverter {

  /**
    * Google Guava ListenableFuture to Scala Future
    *
    * @param lf ListenableFuture[T]
    * @return Future of T
    * */
  implicit def asScala[T](lf: ListenableFuture[T]): Future[T] = {
    val p = Promise[T]()
    Futures.addCallback(lf, new FutureCallback[T] {
      def onFailure(t: Throwable): Unit = p failure t
      def onSuccess(result: T): Unit    = p success result
    })
    p.future
  }
}

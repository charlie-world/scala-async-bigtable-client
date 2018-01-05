package com.charlie.world.bigtable.utils

import scala.concurrent.Future
import scala.concurrent.duration._

trait Await {

  implicit class AwaitFuture[A](fa: Future[A]) {
    def await(timeout: Duration = 10.seconds): A =
      concurrent.Await.result(fa, timeout)
  }
}

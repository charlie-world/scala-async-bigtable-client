package com.charlie.world.bigtable.read

import com.google.bigtable.v2.{Row, RowSet}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 5.
  */
trait BigtableRead {

  def createRowSet(startRowKey: String, endRowKey: String): RowSet

  def executeAsync(rowSet: RowSet)(implicit ec: ExecutionContext): Future[Seq[Row]]
}

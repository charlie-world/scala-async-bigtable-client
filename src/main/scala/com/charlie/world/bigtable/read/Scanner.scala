package com.charlie.world.bigtable.read

import com.charlie.world.bigtable.utils.{FutureConverter, JavaScalaConverter, StringConverter}
import com.google.bigtable.v2.{ReadRowsRequest, Row, RowRange, RowSet}
import com.google.cloud.bigtable.grpc.BigtableSession

import scala.concurrent.{ExecutionContext, Future}

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 5.
  */
class Scanner(table: String)(implicit session: BigtableSession)
  extends BigtableRead
    with StringConverter
    with JavaScalaConverter
    with FutureConverter {

  override def executeAsync(rowSet: RowSet)
                           (implicit ec: ExecutionContext): Future[Seq[Row]] = {
    val readRequest = ReadRowsRequest.newBuilder().setTableName(table).setRows(rowSet).build()
    session.getDataClient.readRowsAsync(readRequest).map(_.toSeq)
  }

  override def createRowSet(startRowKey: String, endRowKey: String): RowSet = {
    val rowRange = RowRange.newBuilder().setStartKeyOpen(startRowKey).setEndKeyOpen(endRowKey).build()
    RowSet.newBuilder().addRowRanges(rowRange).build()
  }
}

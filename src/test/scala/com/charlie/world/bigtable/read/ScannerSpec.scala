package com.charlie.world.bigtable.read

import com.charlie.world.bigtable.utils.{Await, JavaScalaConverter, StringConverter}
import com.google.bigtable.v2.{ReadRowsRequest, Row, RowRange, RowSet}
import com.google.cloud.bigtable.grpc.{BigtableDataClient, BigtableSession}
import com.google.common.util.concurrent.Futures
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito.when
import org.scalatest.{FlatSpec, Matchers}

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 5.
  */
class ScannerSpec
  extends FlatSpec
    with Matchers
    with MockitoSugar
    with JavaScalaConverter
    with StringConverter
    with Await {

  implicit val session = mock[BigtableSession]

  private val tableName = "TEST_TABLE"
  private val scanner = new Scanner(tableName)

  "createRowSet" should "return RowSet" in {
    val startRowKey = "START_ROW_KEY"
    val endRowKey = "END_ROW_KEY"
    val rowRange = RowRange.newBuilder().setStartKeyOpen(startRowKey).setEndKeyOpen(endRowKey).build()

    val result = scanner.createRowSet(startRowKey, endRowKey)
    result.getRowRangesList shouldBe Seq(rowRange)
  }

  "executeAsync" should "scan entries" in {
    import scala.concurrent.ExecutionContext.Implicits.global
    val rowKey = "TEST_ROW_KEY"
    val rowSet = RowSet.newBuilder().addRowKeys(rowKey).build()
    val readRequest = ReadRowsRequest.newBuilder().setTableName(tableName).setRows(rowSet).build()
    val dataClient = mock[BigtableDataClient]

    when(session.getDataClient)
      .thenReturn(dataClient)
    when(dataClient.readRowsAsync(readRequest))
      .thenReturn(Futures.immediateFuture(Seq[Row]()))

    scanner.executeAsync(rowSet).await() shouldBe Seq()
  }
}

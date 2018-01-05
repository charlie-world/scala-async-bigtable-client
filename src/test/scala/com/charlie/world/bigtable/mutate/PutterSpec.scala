package com.charlie.world.bigtable.mutate

import co.bitfinder.awair.bigtable.utils.{Await, FutureConverter}
import com.charlie.world.bigtable.utils.{Await, FutureConverter}
import com.google.bigtable.v2.MutateRowsRequest.Entry
import com.google.bigtable.v2.{MutateRowsRequest, MutateRowsResponse, Mutation}
import com.google.bigtable.v2.Mutation.SetCell
import com.google.cloud.bigtable.config.BigtableOptions
import com.google.cloud.bigtable.grpc.{BigtableDataClient, BigtableInstanceName, BigtableSession}
import com.google.common.util.concurrent.Futures
import com.google.protobuf.ByteString
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 5.
  */
class PutterSpec
  extends FlatSpec
    with Matchers
    with MockitoSugar
    with FutureConverter
    with Await {

  implicit val session = mock[BigtableSession]

  private val instanceId = "TEST_INSTANCE"
  private val tableName = "TEST_TABLE"
  private val putter = new Putter(tableName)

  "creatMutation" should "return mutation" in {
    val columnFamily = "TEST_FAMILY"
    val columnQualifier = ByteString.copyFromUtf8("TEST_QUALIFIER")
    val value = ByteString.copyFromUtf8("TEST_VALUE")
    val cell = SetCell
      .newBuilder()
      .setFamilyName(columnFamily)
      .setColumnQualifier(columnQualifier)
      .setTimestampMicros(System.currentTimeMillis())
      .setValue(value)
      .build()

    val result = putter.createMutation(columnFamily, columnQualifier, value)

    result.getSetCell shouldBe cell
  }

  "createEntry" should "return Entry" in {
    val rowKey = "TEST_ROW_KEY"
    val mutation = Mutation.newBuilder().build()

    val result = putter.createEntry(rowKey, Seq(mutation))

    result.getRowKey shouldBe ByteString.copyFromUtf8(rowKey)
    result.getMutationsList.asScala shouldBe Seq(mutation)
  }

  "createMutateRowsRequest" should "return MutateRowsRequest" in {

    val bigtableOptions = mock[BigtableOptions]
    val bigtableInstanceName = mock[BigtableInstanceName]

    when(session.getOptions)
      .thenReturn(bigtableOptions)
    when(bigtableOptions.getInstanceName)
      .thenReturn(bigtableInstanceName)
    when(bigtableInstanceName.toTableNameStr(tableName))
      .thenReturn(s"$instanceId/tables/$tableName")

    val entry = Entry.newBuilder().build()
    val result = putter.createMutateRowsRequest(Seq(entry))

    result.getEntriesList.asScala shouldBe Seq(entry)
  }

  "executeAsync" should "put MutateRowsRequest" in {
    import scala.concurrent.ExecutionContext.Implicits.global
    val dataClient = mock[BigtableDataClient]
    val request = MutateRowsRequest.newBuilder().build()
    val response = MutateRowsResponse.newBuilder().build()

    when(session.getDataClient)
      .thenReturn(dataClient)
    when(dataClient.mutateRowsAsync(request))
      .thenReturn(Futures.immediateFuture(Seq(response).asJava))

    putter.executeAsync(request).await() shouldBe Seq(response)
  }
}

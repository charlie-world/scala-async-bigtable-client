package com.charlie.world.bigtable.mutate

import com.charlie.world.bigtable.utils.{FutureConverter, StringConverter}
import com.google.bigtable.v2.MutateRowsRequest.Entry
import com.google.bigtable.v2.Mutation.SetCell
import com.google.bigtable.v2._
import com.google.cloud.bigtable.grpc.BigtableSession
import com.google.protobuf.ByteString

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 5.
  */
class Putter(table: String)
            (implicit session: BigtableSession)
  extends BigtableMutate
    with StringConverter
    with FutureConverter {

  private def getFullTableName(table: String): String = {
    session.getOptions.getInstanceName.toTableNameStr(table)
  }

  def createEntry(rowKey: String, mutations: Seq[Mutation]): Entry = {
    Entry
      .newBuilder()
      .setRowKey(rowKey)
      .addAllMutations(mutations.asJava)
      .build()
  }

  def createMutateRowsRequest(entries: Seq[Entry]): MutateRowsRequest = {
    MutateRowsRequest
      .newBuilder()
      .setTableName(getFullTableName(table))
      .addAllEntries(entries.asJava)
      .build()
  }

  override def executeAsync(mutateRowsRequest: MutateRowsRequest)
                           (implicit ec: ExecutionContext): Future[Seq[MutateRowsResponse]] = {
    session.getDataClient.mutateRowsAsync(mutateRowsRequest).asScala.map(_.asScala.toSeq)
  }

  override def createMutation(columnFamily: String, columnQualifier: ByteString, value: ByteString): Mutation = {
    val cell = SetCell
      .newBuilder()
      .setFamilyName(columnFamily)
      .setColumnQualifier(columnQualifier)
      .setTimestampMicros(System.currentTimeMillis())
      .setValue(value)
      .build()

    Mutation.newBuilder().setSetCell(cell).build()
  }
}

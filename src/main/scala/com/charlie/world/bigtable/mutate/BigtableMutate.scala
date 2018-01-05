package com.charlie.world.bigtable.mutate

import com.google.bigtable.v2.{MutateRowsRequest, MutateRowsResponse, Mutation}
import com.google.protobuf.ByteString

import scala.concurrent.{ExecutionContext, Future}

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 5.
  */
trait BigtableMutate {

  /**
    * Execute the row mutations asynchronously.
    *
    * @param mutateRowsRequest MutateRowsRequest
    * @return Future of MutateRowResponse. Failed future on error.
    */
  def executeAsync(mutateRowsRequest: MutateRowsRequest)
                  (implicit ec: ExecutionContext): Future[Seq[MutateRowsResponse]]

  /**
    * Create Mutation
    *
    * @param columnFamily String
    * @param columnQualifier ByteString
    * @param value ByteString
    * @return Mutation
    */
  def createMutation(columnFamily: String, columnQualifier: ByteString, value: ByteString): Mutation
}

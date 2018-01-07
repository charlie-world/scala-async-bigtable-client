package com.charlie.world.bigtable.utils

import com.google.protobuf.ByteString

/**
  * Writer Charlie Lee 
  * Created at 2018. 1. 5.
  */
trait StringConverter {

  /**
    * String to ByteString of Google Protobuf
    *
    * @param str Scala String
    * @return ByteString of Google Protobuf
    * */
  implicit def str2ByteString(str: String): ByteString = {
    ByteString.copyFromUtf8(str)
  }
}

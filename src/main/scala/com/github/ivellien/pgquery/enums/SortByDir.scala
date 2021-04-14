package com.github.ivellien.pgquery.enums

object SortByDir extends Enumeration with EnumerationDecoder {
  val SortByDefault = Value("")
  val SortByAsc = Value(" ASC")
  val SortByDesc = Value(" DESC")
  val SortByUsing = Value(" USING")
}

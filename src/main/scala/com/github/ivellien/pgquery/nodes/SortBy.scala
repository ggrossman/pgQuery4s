package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.enums.{SortByDir, SortByNulls}
import com.github.ivellien.pgquery.nodes.Node.{circeConfig, optionToQuery}
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class SortBy(
    node: Option[Node],
    sortby_dir: SortByDir.Value,
    sortby_nulls: SortByNulls.Value,
    location: Option[Int],
    useOp: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"${optionToQuery(node)}${sortby_dir.toString}"
}

package Nodes

import Enums.NodeTag
import io.circe.ACursor
import io.circe.Decoder
import io.circe.HCursor
import io.circe.generic.extras.Configuration

abstract class Node {
  def toQuery(): String
}

object Node {

  implicit val circeConfig: Configuration = Configuration.default

  implicit val decoder: Decoder[Node] = (c: HCursor) => {
    /*
      // ouch both .get and .head might throw an error - this should be type safe as well
      // feel free to rewrite this to smth like

      for {
        key <- c.keys.flatMap(_.headOption).toRight(???)
        value = c.downField(key)
      } yield {
        NodeTag.withName(key) match {
          case ... => ???
        }
      }

      and see what the individual combinators do in the process. For inspiration you can see that I
      rewrote some other error handling this way in some other part of the codebase.
    */

    val key: String = c.keys.get.head
    val value: ACursor = c.downField(key)

    NodeTag.withName(key) match {
      case NodeTag.T_SelectStmt => value.as[SelectStmt]
      case NodeTag.T_RawStmt => value.as[RawStmt]
      case NodeTag.T_ResTarget => value.as[ResTarget]
      case NodeTag.T_RangeVar => value.as[RangeVar]
      case NodeTag.T_ColumnRef => value.as[ColumnRef]
      case NodeTag.T_String => value.as[NodeString]
      case NodeTag.T_Integer => value.as[NodeInteger]
      case NodeTag.T_A_Expr => value.as[A_Expr]
      case NodeTag.T_A_Const => value.as[A_Const]
      case _ =>
        println("Unsupported yet - " + key)
        Right(EmptyNode())
    }
  }
}

case class EmptyNode() extends Node {
  override def toQuery(): String = ""
}

package com.pawscode.scalajstapir

import com.pawscode.scalajstapir.http.Requests
import org.scalajs.dom.document

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js.JSConverters._

object Main {

  def main(args: Array[String]): Unit = {
    RootComponent.Props("TouK kontakty")
      .make
      .renderIntoDOM(document.getElementById("App"))
  }



  @JSExportTopLevel("getServerTimeAAA")
  def getServerTimeAAA(): LocalDateTime = LocalDateTime.of(10,10,10,10,10,10,10)

  @JSExportTopLevel("getServerTime")
  def getServerTime(): Promise[String] = Requests.getTime().toJSPromise

}
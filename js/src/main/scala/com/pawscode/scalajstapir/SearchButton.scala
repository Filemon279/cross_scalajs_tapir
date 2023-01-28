package com.pawscode.scalajstapir

import com.pawscode.scalajstapir.http.Requests
import io.kinoplan.scalajs.react.material.ui.core.MuiButton
import japgolly.scalajs.react.extra.StateSnapshotF.StateSnapshot
import japgolly.scalajs.react.vdom.VdomElement
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{AsyncCallback, BackendScope, Callback, ReactEventFromInput, ScalaComponent}
import org.scalajs.dom
import sttp.client3.Response.ExampleGet.uri
import sttp.client3.{FetchBackend, Response, ResponseException, UriContext, basicRequest}
import sttp.client3.circe._
import sttp.model.Uri

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object SearchButton {

  case class Props(apiResponse: StateSnapshot[String], searchValue: StateSnapshot[String]) {
    def make = component(this)
  }

  class Backend($: BackendScope[Props, Unit]) {

    def render(p: Props): VdomElement =
    <.div(
      MuiButton(variant = MuiButton.Variant.contained)(
        "Search",
        ^.onClick --> AsyncCallback.fromFuture(Requests.countTextLength(p.searchValue.value)).>>=(
          a => p.apiResponse.setState(s"Response: $a").asAsyncCallback
        ).toCallback
      )
    )
  }

  val component = ScalaComponent.builder[Props]
    .stateless
    .renderBackend[Backend]
    .build

}

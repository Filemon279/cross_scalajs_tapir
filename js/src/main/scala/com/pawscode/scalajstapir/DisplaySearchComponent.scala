package com.pawscode.scalajstapir

import japgolly.scalajs.react.extra.internal.StateSnapshot
import japgolly.scalajs.react.vdom.VdomElement
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, ScalaComponent}

object DisplaySearchComponent {

  case class Props(searchField: StateSnapshot.withoutReuse.FromValue[String]) {
    def make = component(this)
  }

  class Backend($: BackendScope[Props, Unit]) {

    def render(p: Props): VdomElement =
      <.div(
        <.div(s"You are looking for: ${p.searchField.readOnly.value}")
      )
  }

  val component = ScalaComponent.builder[Props]
    .stateless
    .renderBackend[Backend]
    .build

}

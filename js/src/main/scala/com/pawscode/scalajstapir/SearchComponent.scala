package com.pawscode.scalajstapir

import io.kinoplan.scalajs.react.material.ui.core.MuiInput
import japgolly.scalajs.react.extra.StateSnapshotF.StateSnapshot
import japgolly.scalajs.react.vdom.VdomElement
import japgolly.scalajs.react.vdom.html_<^.{<, ^}
import japgolly.scalajs.react.{BackendScope, Callback, ReactEventFromInput, ScalaComponent}

object SearchComponent {

  case class Props(searchField: StateSnapshot[String]) {
    def make = component(this)
  }

  class Backend($: BackendScope[Props, Unit]) {

    def onSearchUpdate(p: Props)(e: ReactEventFromInput): Callback =
      p.searchField.setState(e.target.value)

    def render(p: Props): VdomElement =
      <.div(
        MuiInput()(^.onChange ==> onSearchUpdate(p)),
      )
  }

  val component = ScalaComponent.builder[Props]
    .stateless
    .renderBackend[Backend]
    .build

}

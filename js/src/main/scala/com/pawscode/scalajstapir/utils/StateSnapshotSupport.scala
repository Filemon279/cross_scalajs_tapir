package com.pawscode.scalajstapir.utils

import japgolly.scalajs.react.BackendScope
import japgolly.scalajs.react.extra.StateSnapshot
import monocle.Lens
import japgolly.scalajs.react.ReactMonocle._

trait StateSnapshotSupport[P, S] {

  def $: BackendScope[P, S]

  protected def stateSnapshot(s: S): StateSnapshot[S] = StateSnapshot(s).setStateVia($)

  protected def stateSnapshot[T](lens: Lens[S, T])(s: S): StateSnapshot[T] = StateSnapshot(lens.get(s)).setStateVia($.zoomStateL(lens))

}

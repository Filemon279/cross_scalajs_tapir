package com.pawscode.scalajstapir.routes

import sttp.tapir.{PublicEndpoint, endpoint, plainBody, query}

object Accounts {

  val countCharactersEndpoint: PublicEndpoint[String, Unit, Int, Any] =
    endpoint.in(query[String]("text")).out(plainBody[Int])

}

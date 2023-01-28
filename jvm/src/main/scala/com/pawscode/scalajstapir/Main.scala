package com.pawscode.scalajstapir

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.pawscode.scalajstapir.routes.Accounts.countCharactersEndpoint
import io.circe.syntax._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import sttp.tapir.{PublicEndpoint, endpoint, plainBody, query, stringBody}

import java.time.LocalDateTime
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "touk-server")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    def countCharacters(s: String): Future[Either[Unit, Int]] =
      Future.successful(Right[Unit, Int](s.length))

    val countCharactersRoute: Route =
      AkkaHttpServerInterpreter().toRoute(countCharactersEndpoint.serverLogic(countCharacters))

    val route = cors() {
      path("accounts") {
        get {
          parameters("owner") { owner =>
            complete(StatusCodes.OK, MockData.accounts.filter(_.owner.equals(owner)).asJson)
          }
        }
      } ~
      path("time") {
        get {
          complete(StatusCodes.OK, LocalDateTime.now().toString)
        }
      } ~ countCharactersRoute
    }


    /////
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server now online...\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

}


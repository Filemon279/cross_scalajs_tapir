package com.pawscode.scalajstapir.http

import com.pawscode.scalajstapir.routes.Accounts.countCharactersEndpoint
import sttp.capabilities
import sttp.client3.{FetchBackend, Response, ResponseException, SttpBackend, UriContext, asString, basicRequest}
import sttp.tapir.client.sttp.SttpClientInterpreter

import scala.concurrent.{ExecutionContext, Future}

object Requests {

  def getTime()(implicit ec: ExecutionContext): Future[String] = {
    implicit val sttpBackend = FetchBackend()
    for {
      response <- basicRequest
        .get(uri"http://localhost:8080/time")
        .header("Access-Control-Allow-Origin", "*")
        .response(asString)
        .send(sttpBackend)
        .flatMap(failFutureOnResponseError(_))
    } yield response
  }


  def countTextLength(testStr: String)(implicit ec: ExecutionContext): Future[Int] = {
    implicit val sttpBackend: SttpBackend[Future, capabilities.WebSockets] = FetchBackend()
    SttpClientInterpreter()
      .toClientThrowDecodeFailures(countCharactersEndpoint, Some(uri"http://localhost:8080"), sttpBackend)
      .apply(testStr)
      .flatMap(failFutureOnResponseErrorTapir)
  }

  private def failFutureOnResponseErrorTapir[T, E, B](r: Either[Unit, T]): Future[T] = {
    r match {
      case Right(value) => Future.successful(value)
      case Left(value) => Future.failed(new Error(""))
    }
  }

  def failFutureOnResponseError[T, E, B](r: Response[Either[String, String]]): Future[String] = {
    r.body match {
      case Right(value) => Future.successful(value)
      case Left(value) => Future.successful("error")
    }
  }

}

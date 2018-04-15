package com.kduda.akka.typed.complex

import akka.actor.typed.ActorSystem
import com.kduda.akka.typed.complex.actors.RootCoordinator

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps


/**
  * Create root actor
  */
object ComplexExample extends App {
  val system = ActorSystem(RootCoordinator.coordinator, "ChatRoomDemo")
  Await.result(system.whenTerminated, 3 seconds)
}


package com.kduda.akka.typed.simple

import akka.actor.typed.ActorSystem
import com.kduda.akka.typed.simple.actors.RootCoordinator


/**
  * 1 [[RootCoordinator]]
  * creates
  * 3 [[com.kduda.akka.typed.simple.actors.HelloBotActor]]
  * send messages to
  * 1 [[com.kduda.akka.typed.simple.actors.GreeterActor]]
  * replies to
  * 3 [[com.kduda.akka.typed.simple.actors.HelloBotActor]]
  */
object SimpleExample extends App {
  /**
    * Create Actor System named "hello-actor-system" with behaviour of [[com.kduda.akka.typed.simple.actors.RootCoordinator]]
    */
  val system: ActorSystem[RootCoordinator.StartHelloBot] =
    ActorSystem(RootCoordinator.coordinator, "hello-actor-system")

  /**
    * Start 3 instances of [[com.kduda.akka.typed.simple.actors.HelloBotActor.bot]]
    */
  system ! RootCoordinator.StartHelloBot("World-bot")
  system ! RootCoordinator.StartHelloBot("Akka-bot")
  system ! RootCoordinator.StartHelloBot("Typed-bot")
}

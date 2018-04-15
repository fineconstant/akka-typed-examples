package com.kduda.akka.typed.simple.actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}


/**
  * Receive Greet message
  * Do the greeting
  * Replay to replayDestination with Greeted message
  */
object GreeterActor {

  final case class Greet(whom: String, replayDestination: ActorRef[Greeted])

  final case class Greeted(whom: String, from: ActorRef[Greet])

  val greeter: Behavior[Greet] = Behaviors.receive { (ctx, msg) =>
    ctx.log.info(s"Hello, ${msg.whom}!")
    msg.replayDestination ! Greeted(msg.whom, ctx.self)
    Behaviors.same
  }
}

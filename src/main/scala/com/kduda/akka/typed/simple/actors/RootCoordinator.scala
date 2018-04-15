package com.kduda.akka.typed.simple.actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors


object RootCoordinator {

  final case class StartHelloBot(name: String)

  val coordinator: Behavior[StartHelloBot] =
    Behaviors.setup { context =>

      /**
        * Start one instance of [[GreeterActor]]
        */
      val greeter = context.spawn(GreeterActor.greeter, "greeter")

      /**
        * Create an instance of [[HelloBotActor]],
        * then send message to [[GreeterActor]] with [[HelloBotActor]] as an actor to replay to.
        * [[HelloBotActor]] will then continue to send messages to [[GreeterActor]] until maxGreetings reached.
        */
      Behaviors.receiveMessage { msg: StartHelloBot =>
        val botName = msg.name
        val helloBot = context.spawn(HelloBotActor.bot(greetingCounter = 0, maxGreetings = 3), botName)
        greeter ! GreeterActor.Greet(botName, helloBot)
        Behaviors.same
      }
    }
}

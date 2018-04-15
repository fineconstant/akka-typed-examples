package com.kduda.akka.typed.simple.actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors


object HelloBotActor {

  /**
    * When bot is greeted with [[GreeterActor.Greeted]] increase greetingCounter,
    * check if reached max greetings for current bot, if so then [[Behaviors.stopped]].
    *
    * If max greeting not reached, then send request for greeting [[GreeterActor.Greet]],
    * and modify the behaviour of current bot (new greeting counter)
    */
  def bot(greetingCounter: Int, maxGreetings: Int): Behavior[GreeterActor.Greeted] =
    Behaviors.receive { (ctx, msg: GreeterActor.Greeted) =>
      val n = greetingCounter + 1
      ctx.log.info(s"Greeting $n for ${msg.whom}")

      if (n == maxGreetings) Behaviors.stopped
      else {
        msg.from ! GreeterActor.Greet(msg.whom, ctx.self)
        bot(n, maxGreetings)
      }
    }

}


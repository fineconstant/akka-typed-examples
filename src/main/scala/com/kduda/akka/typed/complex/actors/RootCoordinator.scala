package com.kduda.akka.typed.complex.actors

import akka.NotUsed
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{Behavior, Terminated}
import com.kduda.akka.typed.complex.commands.GetSession


/**
  * Creates main ChatRoom actor and its user named "John".
  * Creates session for "John" in ChatRoom
  */
object RootCoordinator {
  val coordinator: Behavior[NotUsed] =
    Behaviors.setup { ctx ⇒
      val chatRoom = ctx.spawn(ChatRoomActor.behavior, "TypedChatRoom")

      val johnRef = ctx.spawn(ChatRoomUser.user, "John")
      ctx.watch(johnRef) // register for Terminated notification

      chatRoom ! GetSession("the ol’ John", johnRef)

      /**
        * When actor is terminated - stop it
        */
      Behaviors.receiveSignal {
        case (_, Terminated(_)) ⇒ Behaviors.stopped
      }
    }
}

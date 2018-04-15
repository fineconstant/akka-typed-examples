package com.kduda.akka.typed.complex.actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.kduda.akka.typed.complex.commands.PostMessage
import com.kduda.akka.typed.complex.events.{MessagePosted, SessionEvent, SessionGranted}


object ChatRoomUser {
  val user: Behavior[SessionEvent] =
    Behaviors.receive { (ctx, msg) => {
      msg match {
        /**
          * When session is granted, post a message
          */
        case SessionGranted(handle) =>
          handle ! PostMessage("Hello World!")
          Behaviors.same

        /**
          * Notification about new message from the chat room.
          */
        case MessagePosted(screenName, message) =>
          ctx.log.info(s"message has been posted by '$screenName': $message")
          Behaviors.stopped
      }
    }

      //    Behaviors.receiveMessage {
      //      /**
      //        * When session is granted, post a message
      //        */
      //      case SessionGranted(handle) =>
      //        handle ! PostMessage("Hello World!")
      //        Behaviors.same
      //
      //      /**
      //        * Notification about new message from the chat room.
      //        */
      //      case MessagePosted(screenName, message) =>
      //        println(s"message has been posted by '$screenName': $message")
      //        Behaviors.stopped
      //    }
    }
}

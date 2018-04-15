package com.kduda.akka.typed.complex.actors

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.kduda.akka.typed.complex.commands._
import com.kduda.akka.typed.complex.events.{MessagePosted, SessionEvent, SessionGranted}


object ChatRoomActor {
  /**
    * Default behavior is chatRoom with no sessions
    */
  val behavior: Behavior[RoomCommand] = chatRoom(List.empty)

  private def chatRoom(sessions: List[ActorRef[SessionCommand]]): Behavior[RoomCommand] =
    Behaviors.receive { (ctx, msg) ⇒
      msg match {
        /**
          * When asked for new sessions creates it for the specific client, sends it back to the client
          * and adds to the current sessions list (returning new chatRoom behavior)
          *
          */
        case GetSession(screenName, client) ⇒
          // create a child actor (session) for further interaction with the client
          // session has a reference to the room and session's client, it also knows client's screenName
          val encodedScreenName = URLEncoder.encode(screenName, StandardCharsets.UTF_8.name)
          val clientSession = ctx.spawn(session(ctx.self, screenName, client), encodedScreenName)

          client ! SessionGranted(clientSession)

          chatRoom(clientSession :: sessions)

        /**
          * When new message is published from some session, chatRoom must notify all its clients via their sessions.
          */
        case PublishSessionMessage(screenName, message) ⇒
          val notification = NotifyClient(MessagePosted(screenName, message))
          sessions foreach (_ ! notification)
          Behaviors.same
      }
    }

  /**
    * Create session for a specific client.
    */
  private def session(room: ActorRef[PublishSessionMessage], screenName: String, client: ActorRef[SessionEvent])
  : Behavior[SessionCommand] = {
    Behaviors.receive { (_, msg) ⇒
      msg match {
        /**
          * When client posts message to it's sessions, it then passes that message to the whole room (ChatRoom)
          */
        case PostMessage(message) ⇒
          // from client, publish to others via the room
          room ! PublishSessionMessage(screenName, message)
          Behaviors.same

        /**
          * When new message appears, chatRoom notifies its clients via their sessions.
          */
        case NotifyClient(message) ⇒
          // message published from the room
          client ! message
          Behaviors.same
      }
    }
  }
}

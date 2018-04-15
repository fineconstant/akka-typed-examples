package com.kduda.akka.typed.complex.events

import akka.actor.typed.ActorRef
import com.kduda.akka.typed.complex.commands.PostMessage


sealed trait SessionEvent

final case class SessionGranted(handle: ActorRef[PostMessage]) extends SessionEvent

final case class SessionDenied(reason: String) extends SessionEvent

final case class MessagePosted(screenName: String, message: String) extends SessionEvent

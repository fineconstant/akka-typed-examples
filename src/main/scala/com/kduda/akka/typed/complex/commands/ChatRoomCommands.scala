package com.kduda.akka.typed.complex.commands

import akka.actor.typed.ActorRef
import com.kduda.akka.typed.complex.events.SessionEvent


sealed trait RoomCommand

final case class GetSession(screenName: String, replyTo: ActorRef[SessionEvent]) extends RoomCommand

final case class PublishSessionMessage(screenName: String, message: String) extends RoomCommand

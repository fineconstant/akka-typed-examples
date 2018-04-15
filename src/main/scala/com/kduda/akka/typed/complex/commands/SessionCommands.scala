package com.kduda.akka.typed.complex.commands

import com.kduda.akka.typed.complex.events.MessagePosted


trait SessionCommand

final case class PostMessage(message: String) extends SessionCommand

final case class NotifyClient(message: MessagePosted) extends SessionCommand

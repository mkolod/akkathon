package com.visiblemeasures.hakkathon

import akka.actor.ActorSystem
import akka.actor.Props

object Pi extends App {
 
  calculate(nrOfWorkers = 4, nrOfElements = 10000/4, nrOfMessages = 10000)
 
  // actors and messages ...
 
  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) {
    // Create an Akka system
    val system = ActorSystem("PiSystem")
 
    // create the result listener, which will print the result and shutdown the system
    val listener = system.actorOf(Props[Listener], name = "listener")
 
    // create the master
    val master = system.actorOf(Props(new Master(
      nrOfWorkers, nrOfMessages, nrOfElements, listener)),
      name = "master")
 
    // start the calculation
    master ! Calculate
 
  }
}
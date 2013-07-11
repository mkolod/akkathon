package com.visiblemeasures.hakkathon

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinRouter

class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef)
  extends Actor {
 
  var pi = 0.0
  var nrOfResults = 0
  val start = System.currentTimeMillis
 
  val workerRouter = context actorOf(
    Props[Worker] withRouter(RoundRobinRouter(nrOfWorkers)), name = "workerRouter")
 
  def receive = {
 
    case Calculate =>
      for (i ← 0 until nrOfElements) workerRouter ! Work(i * nrOfElements, nrOfElements)
      
    case Result(value) =>
      
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfElements) {
        // Send the result to the listener
        listener ! PiApproximation(pi, duration = System.currentTimeMillis - start)
        // Stops this actor and all its supervised children
        context.stop(self)
      }
  }
 
}
package com.visiblemeasures.ma

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinRouter
import akka.actor.ActorSystem

class Master(window: Int, stocks: List[StockInit]) extends Actor {
 
  val start = System.currentTimeMillis
  val maSystem = ActorSystem("maSystem")
      
  def createActor(window: Int, initPrice: Double, label: String) =
    maSystem.actorOf(Props(new StockGetter(window, initPrice, label)), name = label)

  
  def receive = {
 
    case Start => {
      
<<<<<<< HEAD
        stocks.foreach(i => createActor(window, i.initPrice, i.sym.name) ! i.sym)
=======
        stocks.foreach(i => createActor(window, i.initPrice, i.sym.name) ! i)
>>>>>>> 7757857c938690ee320dda58c87da0f8ab1b6abb
    }
      
  }
 
}
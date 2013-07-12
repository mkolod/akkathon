package com.visiblemeasures.ma

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinRouter
import akka.actor.ActorSystem

class Master(window: Int) extends Actor {
 
  val start = System.currentTimeMillis
  
  val startingPrice = 100.0
  val stocks = List(Symbol("Apple", "AAPL"), Symbol("Amazon", "AMZN"))
  
  val maSystem = ActorSystem("maSystem")
      
  def createActor(window: Int, initPrice: Double, label: String) =
    maSystem.actorOf(Props(new StockGetter(window, initPrice, label)), name = label)

  
  //val router = maSystem.actorOf(Props[StockGetter]
   //   .withRouter(RoundRobinRouter(routees = List(ref("Apple", 100.0), ref("Amazon", 200.0)))))
  
  def receive = {
 
    case Start => {
      
    //  for (i <- stocks) router ! i
      for (i <- stocks) {
        createActor(window, startingPrice, i.name) ! i
      }
     
      
    }
      
  }
 
}
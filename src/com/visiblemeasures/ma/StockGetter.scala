package com.visiblemeasures.ma

import akka.actor.Actor
import akka.actor.actorRef2Scala
import scala.util.Random
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

class StockGetter(window: Int, initPrice: Double, label: String) extends Actor {
  
  val maSystem = ActorSystem("maSystem")
  val aggregator: ActorRef = maSystem.actorOf(Props(new MAAggregator(window)), name = "maAggregator")
 
  val r = new Random
  var price = initPrice
  
  def update = {
    price += r.nextGaussian
    price
  }
  
  def receive = {
 
    case Symbol(name, sym) => {
      
      println("in stockgetter")
      
       while (true) {
         
          aggregator ! Tick(Symbol(name, sym), update) // perform the work
          // Thread.sleep(1)
       }
      
    }
     
  }
  
  
}
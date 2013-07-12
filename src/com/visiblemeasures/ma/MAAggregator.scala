package com.visiblemeasures.ma

import akka.actor.Actor
import akka.actor.actorRef2Scala
import com.visiblemeasures.hakkathon.Work
import scala.collection.mutable.ArrayBuffer
import akka.actor.ActorSystem

case class MAAggregator(window: Int) extends Actor {
  
  val queue = ArrayBuffer[Double]()
  
  def movingAvg = queue.sum/queue.size
 
  def receive = {
    
    case Tick(symbol, price) => {
      
      queue += price
      
      if (queue.size == window) {
        
        println(Result(symbol.sym, window, movingAvg)) 
        queue.remove(0)
      }
    }
              
  }
  
}
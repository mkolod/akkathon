package com.visiblemeasures.hakkathon

import akka.actor.Actor

class Worker extends Actor {
 
  def receive = {
    case Work(start, nrOfElements) =>
      sender ! Result(calculatePiFor(start, nrOfElements)) // perform the work
  }
  
  
  def calculatePiFor(start: Int, nrOfElements: Int) = {
    
    def transformElement(i: Int) = 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    
    start until start + nrOfElements map transformElement sum
  }
  
  
}
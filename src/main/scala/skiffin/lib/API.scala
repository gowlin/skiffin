package skiffin.lib

import net.liftweb._
import net.liftweb.http._
import net.liftweb.http.rest._
import net.liftweb.common._
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._


object API extends RestHelper with Loggable {

  def jsonPerson(p: Person): JObject = ("email" -> p.email) ~
     	("in" -> p.in) ~ ("when" -> p.when.getTime)
  
  serve {
    
    case "api" :: "v1" :: "people" :: Nil JsonGet _ =>
       ("count" -> Status.people.size) ~
       ("people" -> Status.people.map(jsonPerson) ) : JObject


    case Req("api" :: "v1" :: email :: Nil, suffix, GetRequest) =>
      val address = "%s.%s" format (email,suffix) // doh!
      Status.people.find(_.email == address).map(jsonPerson)
     	  
    case req @ Req("api" :: "v1" :: email :: Nil, suffix, PostRequest) =>
      val address = "%s.%s" format (email,suffix)
      update(address, req.json).map(jsonPerson)
      
    case "api" :: "v1" :: "login" :: assertion :: Nil JsonGet _ => 
      for ( email <- BrowserId.verify(assertion) ) {
        logger.info("Login from "+email)
      }
      OkResponse()
    
  }
  
  
  def update(address: String, jsonreq: Box[JValue]): Option[Person] = for { 
	  p <- Status.people.find(_.email == address) 
      json <- jsonreq    
      //JBool(status) <- json \ "in" 
      } yield {
    	  val np = p.copy(in=true,when=new java.util.Date)
    	  Status ! np
    	  np
    }  
}
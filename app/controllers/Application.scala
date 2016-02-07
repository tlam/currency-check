package controllers

import javax.inject.Inject
import scala.concurrent.Future

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.ws._

class Application @Inject() (ws: WSClient) extends Controller {

  def index = Action {
    var greet = "Hello"
    //Ok(views.html.index("Your new application is ready."))
    Ok(views.html.index(greet))
  }

  def getCurrencies = Action.async { request =>
    implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
    val request: WSRequest = ws.url("http://api.fixer.io/2014-12-12?base=CAD")
    val futureResponse: Future[WSResponse] = request.get()
    futureResponse.map{
      jsResponse => Ok(jsResponse.body).as("application/json")
    }
  }

}

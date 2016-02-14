package controllers

import javax.inject.Inject
import scala.concurrent.Future

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._ 
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.i18n._

case class CurrencyData(eur: Double, usd: Double)
case class Currency(base: String, date: String)

class Application @Inject() (val messagesApi: MessagesApi, ws: WSClient) extends Controller with I18nSupport {
  val currencyForm = Form(mapping(
      "eur" -> of[Double],
      "usd" -> of[Double]
    )(CurrencyData.apply)(CurrencyData.unapply))

  def currencyPost = Action { implicit request => 
    currencyForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.index(formWithErrors)),
      currency => Ok("Calculated"))
  }

  def index = Action {
    Ok(views.html.index(currencyForm))
  }

  def getCurrencies = Action.async { request =>
    implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
    val request: WSRequest = ws.url("http://api.fixer.io/2014-12-12?base=CAD")
    implicit val currencyReads = Json.reads[Currency]
    val futureResult: Future[JsResult[Currency]] = request.get().map {
      response => (response.json).validate[Currency]
    }
    val response: Future[WSResponse] = request.get()
    var other = response.map {
      response => response.json
    }
    val baseReads: Reads[String] = (JsPath \ "base").read[String]
    //val baseResult: JsResult[String] = response.validate[String](baseReads)
    val futureResponse: Future[WSResponse] = request.get()
    futureResponse.map{
      jsResponse => Ok(jsResponse.body).as("application/json")
    }
  }

}

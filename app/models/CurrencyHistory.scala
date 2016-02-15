package models

import scala.concurrent.Future

import play.api.libs.json.Json
import play.api.libs.ws._
import play.api.Play.current


object CurrencyHistory {

  def currency: Future[WSResponse] = {
    return WS.url("http://api.fixer.io/2014-12-12?base=CAD").get()
  }
}

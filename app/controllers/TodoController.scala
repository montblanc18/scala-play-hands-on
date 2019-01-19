package controllers

import javax.inject._
import play.api.mvc._

import play.api.data._ // _はワイルドカードで参照するアイテム
import play.api.data.Forms._


// オリジナルパッケージのimport
import services._

// mockオブジェクト => 依存性注入dependency Injection。最初の()がコンストラクタ。メンバは絶対引数でいいでしょ？みたいな感じ。
class TodoController @Inject()(todoService: TodoService, mcc: MessagesControllerComponents)
  extends MessagesAbstractController(mcc) {

    // OkはHTTPのメソッドを表す関数
    def helloworld() = Action { implicit request: MessagesRequest[AnyContent] =>
      Ok("Hello World")
    }

    def list() = Action { implicit request: MessagesRequest[AnyContent] =>
     val items: Seq[Todo] = todoService.list()
     Ok(views.html.list(items))
    }

    val todoForm: Form[String] = Form("name" -> nonEmptyText)

    // Action自体が関数{}内が引数。implicitはその後の関数に引数を書かなくても与えることができる。
    def todoNew() = Action { implicit request: MessagesRequest[AnyContent] =>
     Ok(views.html.createForm(todoForm))
    }

    def todoAdd() = Action { implicit request: MessagesRequest[AnyContent] =>
      val name: String = todoForm.bindFromRequest().get
      todoService.insert(Todo(name))
      Redirect(routes.TodoController.list())
      // val name: String = todoForm.bindFromRequest().get
      // println(name)
      // Ok("Save")
    }

  }



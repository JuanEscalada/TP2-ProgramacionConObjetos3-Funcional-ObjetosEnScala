package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

class Variable(val nombre : String, var _contenido : Option[BasicType]) extends BasicType{
  def contenido  : BasicType = {
    _contenido match{
      case None => throw new ExcepcionPorVariableSinInicializar
      case Some(n) => n
    }
  }

  def asignar(nuevoCont : BasicType) : BasicType = {
    _contenido = Some(nuevoCont)
    nuevoCont
  }

  def seLlama(unNombre : String) : Boolean = nombre == unNombre
}

// Companion object de Variable
object Variable {

  def apply(nombre: String, contenido: BasicType): Variable = {
    val variable = new Variable(nombre, Some(contenido))
    Referencia.agregarVar(variable)
    variable
  }

  def apply(nombre: String) : Variable ={
    val variable = new Variable(nombre, None)
    Referencia.agregarVar(variable)
    variable
  }

  def unapply(arg: Variable): Option[(String, Option[BasicType])] = Some(arg.nombre, arg._contenido)

}

object Referencia{

  private var variables : List[Variable] = Nil

  def clear = variables = Nil

  def agregarVar(variable: Variable) ={
    variables.find(p => p.seLlama(variable.nombre)) match{
      case None => variables = variable :: variables
      case _ => throw new ExcepcionLaVariableQueIntentaCrearYaExiste
    }
  }

  def apply(nombre: String): Variable = {
    encontrarReferenciaCon(nombre, variables)
  }

  private def encontrarReferenciaCon(nombre : String, vars : List[Variable]) : Variable ={
    vars  match {
      case Nil => throw new ExcepcionLaVariableNoExiste
      case (variable @ Variable(nom, _)) :: xs if nom == nombre => variable
      case x :: xs => encontrarReferenciaCon(nombre, xs)
    }
  }
}

case class Asignar(variable : Variable, contenido : BasicType) extends BasicType

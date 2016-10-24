package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

//Representa a los tipos basicos
abstract class BasicType

// Numeros.
abstract class Numbers extends BasicType
case class Number(num : Int) extends Numbers

// Booleanos
abstract class Booleanos extends BasicType
object True extends Booleanos
object False extends Booleanos
case class Not(bool : BasicType) extends Booleanos

// Operaciones.
class Operacion(val first : BasicType, val second : BasicType) extends Numbers

object Operacion {
  //def apply(first: BasicType, second: BasicType) : Operacion = new Operacion(first, second)
  def unapply(op: Operacion): Option[(BasicType, BasicType)] = Some(op.first, op.second)
}


case class Suma(primero : BasicType, segundo : BasicType) extends Operacion(primero, segundo)
case class Resta(primero : BasicType, segundo : BasicType) extends Operacion(primero, segundo)
case class Multi(primero : BasicType, segundo : BasicType) extends Operacion(primero, segundo)
case class Div(primero : BasicType, segundo : BasicType) extends Operacion(primero, segundo)


// Comparaciones funcionan entre numeros y variables que contienen numeros.
class Comparacion(val first : BasicType, val second : BasicType) extends Booleanos

object Comparacion {
  def apply(first: BasicType, second: BasicType) : Comparacion = new Comparacion(first, second)
  def unapply(op: Comparacion): Option[(BasicType, BasicType)] = Some(op.first, op.second)
}

// Comparaciones

case class Igual(primero : BasicType, segundo : BasicType) extends Comparacion(primero, segundo)

case class Distinto(primero : BasicType, segundo : BasicType) extends Comparacion(primero, segundo)

case class Mayor(primero : BasicType, segundo : BasicType) extends Comparacion(primero, segundo)

case class MayorIgual(primero : BasicType, segundo : BasicType) extends Comparacion(primero, segundo)

case class Menor(primero : BasicType, segundo : BasicType) extends Comparacion(primero, segundo)

case class MenorIgual(primero : BasicType, segundo : BasicType) extends Comparacion(primero, segundo)


// Estructuras de Control

case class If(condicionBooleana : BasicType, bloqueTrue : BasicType, bloqueFalse : BasicType) extends BasicType


// Estructura de lista instrucciones

case class ListaInstrucciones(instrucciones : List[BasicType]) extends BasicType{
  override def equals(that : Any) : Boolean ={
    that match{
      case that : ListaInstrucciones =>
        !instrucciones.map(i => i == that.instrucciones.productElement(instrucciones.indexOf(i))).exists(!_)

      case _ => false
    }
  }
}

object ListaInstrucciones {
  def apply(instrucciones: BasicType*) = new ListaInstrucciones(instrucciones.toList)
}

// Funciones

class Function(val nombre : String, val parametros : List[Parametro], val instrucciones : ListaInstrucciones) extends BasicType

object Function {

  var funciones : List[Function] = Nil

  def apply(nombre: String, parametro : List[Parametro], instrucciones: BasicType*) : Function = {
    val function = new Function(nombre, parametro, new ListaInstrucciones(instrucciones.toList))
    funciones = function :: funciones
    function
  }

  def unapply(fun: Function): Option[(String, List[Parametro], ListaInstrucciones)] = {
    Some(fun.nombre, fun.parametros, fun.instrucciones)
  }
}


class Parametro(nombre : String) extends Variable(nombre, None)

object Parametro{
  def apply(nombre : String): Parametro = {
    val par = new Parametro(nombre)
    Referencia.agregarVar(par)
    par
  }

  def unapply(arg: Parametro): Option[String] = Some(arg.nombre)
}



class Retornar(val contenido : Option[BasicType]) extends BasicType

object Retornar{
  def apply(contenido : BasicType): Retornar = new Retornar(Some(contenido))

  def apply() : Retornar = new Retornar(None)

  def unapply(arg: Retornar): Option[Option[BasicType]] = Some(arg.contenido)
}
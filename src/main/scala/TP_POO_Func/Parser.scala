package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */


class Parser

//////////////////////////////////////////////////////////////////////////////

trait CheckeadorEstatico {

  def verificarPrograma(program : Program) : List[Problema] = verificarInstrucciones(program.instrucciones)

  def verificarInstrucciones(instrucciones : ListaInstrucciones) : List[Problema] ={
    var result : List[Problema] = Nil
    instrucciones match{
      case ListaInstrucciones(Nil) =>

      case ListaInstrucciones(List(e)) => result = verificarReglasPara(e) ++ result

      case ListaInstrucciones(e :: xs) => result = verificarReglasPara(e) ++ verificarInstrucciones(new ListaInstrucciones(xs)) ++ result
    }

    result
  }

  val verificarReglasPara = DynDispatch.defMulti[BasicType, List[Problema]]

  verificarReglasPara.defMethod({ case _ => Nil })

  val verificar = DynDispatch.defMulti[BasicType, List[Problema]]

  verificar.defMethod({ case _ => Nil })


}

trait Refactorizador {

  def hacerRefactor(program : Program) : Program =
    new Program(new ListaInstrucciones(refactorizarInstrucciones(program.instrucciones)))

  def refactorizarInstrucciones(instrucciones : ListaInstrucciones)  : List[BasicType]= {
    var newInstr: List[BasicType] = Nil

    instrucciones match {
      case ListaInstrucciones(Nil) =>

      case ListaInstrucciones(List(e)) => newInstr = refactorizar(e) :: newInstr

      case ListaInstrucciones(Variable(_) :: xs) => newInstr = refactorizarInstrucciones(ListaInstrucciones(xs)) ++ newInstr

      case ListaInstrucciones(e :: xs) => newInstr = refactorizar(e) :: refactorizarInstrucciones(ListaInstrucciones(xs)) ++ newInstr
    }
    newInstr
  }


  val refactorizar = DynDispatch.defMulti[BasicType, BasicType]

  refactorizar.defMethod({ case op => op})
}

trait Ejecutador {

  def ejecutarPrograma(program : Program) : BasicType =
    ejecutarInstrucciones(program.instrucciones)

  def ejecutarInstrucciones(instrucciones: ListaInstrucciones): BasicType ={
    instrucciones match {
      case ListaInstrucciones(e :: Nil) => ejecutar (e)
      case ListaInstrucciones(Variable(_) :: xs) => ejecutarInstrucciones(ListaInstrucciones(xs))
      case ListaInstrucciones(e :: xs) => ejecutar(e); ejecutarInstrucciones(ListaInstrucciones(xs))
    }
  }

  def aplicarOpBinaria(op : Operacion, first : BasicType, second : BasicType) : BasicType ={
    op match {
      case Suma(_, _) => ejecutar(Suma(first, second))
      case Resta(_, _) => ejecutar(Resta(first, second))
      case Multi(_, _) => ejecutar(Multi(first, second))
      case Div(_, _) => ejecutar(Div(first, second))
    }
  }

  def aplicarComparacionBinaria(comp : Comparacion, first : BasicType, second : BasicType) : BasicType ={
    comp match {
      case Igual(_, _) => ejecutar(Igual(first, second))
      case Distinto(_, _) => ejecutar(Not(Igual(first, second)))
      case Mayor(_, _) => ejecutar(Mayor(first, second))
      case MenorIgual(_, _) => ejecutar(Not(Mayor(first, second)))
      case Menor(_, _) => ejecutar(Menor(first, second))
      case MayorIgual(_, _) => ejecutar(Not(Menor(first, second)))
    }
  }

  val ejecutar = DynDispatch.defMulti[BasicType, BasicType]

}

//////////////////////////////////////////////////////////////////////////////

class Program(val instrucciones : ListaInstrucciones){
  override def equals(that : Any) : Boolean ={
    that match{
      case that : Program => instrucciones == that.instrucciones
      case _ => false
    }
  }
}

object Program{

  def apply(instrucciones: BasicType*): Program = new Program(new ListaInstrucciones(instrucciones.toList))

}

//////////////////////////////////////////////////////////////////////////////

class Problema(val desc : String, val op : BasicType){
  override def equals(that : Any) : Boolean ={
    that match{
      case that : Problema => that.desc == desc
      case _ => false
    }
  }
}
case class Advertencia(descripcion : String, operacion : BasicType) extends Problema(descripcion, operacion)
case class Error(descripcion : String, operacion : BasicType) extends Problema(descripcion, operacion)

//////////////////////////////////////////////////////////////////////////////

object DynDispatch {
  class DynMethod[A,R] {
    val methods = scala.collection.mutable.ListBuffer[PartialFunction[A,R]]()
    def defMethod(m: PartialFunction[A,R]) = {
      methods += m
    }
    def apply(args: A): R = {
      methods.reverse.find(_.isDefinedAt(args)) match {
        case Some(f) => f(args)
        case None => throw new Exception("type mismatch?")
      }
    }
  }
  def defMulti[A,R] = new DynMethod[A,R]
}

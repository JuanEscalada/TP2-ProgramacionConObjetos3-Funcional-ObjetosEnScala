package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

//Reglas de checkeo y refactor.

// Checkeos sobre operaciones.

trait CheckeoOperaciones extends CheckeadorEstatico with Refactorizador with CheckeoInst{

  verificarReglasPara.defMethod({ case op @ Operacion(Number(n), Number(m)) => verificar(op) ++ Nil})

  //Checkeos sobre las operaciones
  verificar.defMethod({ case op @ Suma(Number(0), Number(b)) => List(Advertencia(s"El resultado de la suma es $b ya que el numero que se le suma es 0", op))})
  verificar.defMethod({ case op @ Suma(Number(a), Number(0)) =>  List(Advertencia(s"El resultado de la suma es $a ya que el numero que se le suma es 0", op))})
  verificar.defMethod({ case op @ Resta(Number(a) , Number(0)) =>  List(Advertencia(s"El resultado de la resta es $a ya que el numero que se le resta es 0", op))})
  verificar.defMethod({ case op @ Multi(Number(a), Number(1)) =>  List(Advertencia(s"El resultado es $a porque 1 es el neutro de la multiplicacion.", op))})
  verificar.defMethod({ case op @ Div(Number(a), Number(1)) =>  List(Advertencia(s"El resultado es $a ya que el 1 es neutro en la division.", op))})
  verificar.defMethod({ case op @ Div( _ , Number(0)) =>  List(Error("La division por Cero no esta definida.", op))})

  //Refactors sobre las operaciones
  refactorizar.defMethod({ case Suma(num, Number(0)) => num})
  refactorizar.defMethod({ case Suma(Number(0), num) => num})
  refactorizar.defMethod({ case Resta(num, Number(0)) => num})
  refactorizar.defMethod({ case Resta(Number(0), Number(a)) => Number(-a)})
  refactorizar.defMethod({ case Multi(num, Number(1)) => num})
  refactorizar.defMethod({ case Multi(Number(1), num) => num})
  refactorizar.defMethod({ case Div(num, Number(1)) => num})
}

// Checkeos sobre comparaciones.

trait CheckeoComparaciones extends CheckeadorEstatico with Refactorizador{

  verificarReglasPara.defMethod({ case comp @ Comparacion(Number(n), Number(m)) => verificar(comp) ++ Nil})

//Checkeos
  verificar.defMethod({ case op @ Igual(Number(n), Number(m)) if n == m => List(Advertencia(s"El resultado sera siempre true porque se esta preguntando si $n es igual a $m", op))})
  verificar.defMethod({ case op @ Menor(Number(n), Number(m)) if n < m => List(Advertencia(s"El resultado sera siempre true porque se esta preguntando si $n es menor a $m", op))})
  verificar.defMethod({ case op @ Mayor(Number(n), Number(m)) if n > m => List(Advertencia(s"El resultado sera siempre true porque se esta preguntando si $n es mayor a $m", op))})

//Refactors
  refactorizar.defMethod({ case Not(True) => False})
  refactorizar.defMethod({ case Not(False) => True})
  refactorizar.defMethod({ case Igual(Number(n), Number(m)) if n == m => True})
  refactorizar.defMethod({ case Menor(Number(n), Number(m)) if n < m => True})
  refactorizar.defMethod({ case Mayor(Number(n), Number(m)) if n > m => True})
}

trait CheckeosRecursivos extends CheckeadorEstatico with Refactorizador{

  verificar.defMethod({ case op @Operacion(Operacion(_, _), n) => verificarReglasPara(op) ++ verificar(n)})
  verificar.defMethod({ case op @Operacion(n, Operacion(_, _)) => verificar(n) ++ verificarReglasPara(op)})

  verificarReglasPara.defMethod({ case Operacion(m, n) => verificar(m) ++ verificar(n) ++ Nil})

  verificarReglasPara.defMethod({ case Comparacion(m, n) => verificar(m) ++ verificar(n) ++ Nil})

  verificarReglasPara.defMethod({ case op @ Operacion(Number(n), Number(m)) => verificar(op) ++ Nil})
  verificarReglasPara.defMethod({ case comp @ Comparacion(Number(n), Number(m)) => verificar(comp) ++ Nil})

}

trait ComparacionTipos extends CheckeadorEstatico{
  verificarReglasPara.defMethod({ case op @ Comparacion(b : Booleanos, n : Numbers) => List(Error("Error de comparacion : No es posible comparar un numero con un booleano", op))})
  verificarReglasPara.defMethod({ case op @ Comparacion(n : Numbers, b : Booleanos) => List(Error("Error de comparacion : No es posible comparar un numero con un booleano", op))})
}

trait CheckeoVars extends CheckeadorEstatico{
  verificarReglasPara.defMethod({ case op @ Variable(name, None) => List(Error(s"La variable $name nunca fue inicializada", op))})
}


trait CheckeoIfs extends CheckeadorEstatico with Refactorizador{
  verificarReglasPara.defMethod({ case op @ If(True, _, _) => List(Advertencia("Se ejecutara siempre el bloque de True ya que la condicion es True", op))})
  verificarReglasPara.defMethod({ case op @ If(False, _, _) => List(Advertencia("Se ejecutara siempre el bloque de False ya que la condicion es False", op))})

  refactorizar.defMethod({ case If(True, t, _) => t})
  refactorizar.defMethod({ case If(False, _, f) => f})
}

trait CheckeoInst extends CheckeadorEstatico with Refactorizador{

  verificarReglasPara.defMethod({case list @ ListaInstrucciones(_) => verificarInstrucciones(list)})

  refactorizar.defMethod({case list @ ListaInstrucciones(_) => new ListaInstrucciones(refactorizarInstrucciones(list))})

}

trait CheckeoFunciones extends CheckeadorEstatico{

  verificarReglasPara.defMethod({ case f @ Function(_, _ , l @ ListaInstrucciones(_)) => verificarRetorno(l,f) })

  def verificarRetorno(instrucciones : BasicType, funcion : Function) : List[Problema] ={
    instrucciones match {

      case ListaInstrucciones(List(If(_,Retornar(_),Retornar(_)))) => Nil

      case ListaInstrucciones(List(If(_,Retornar(_),e))) => List(Error("La Funcion no contiene retorno en el segundo bloque del if", funcion))

      case ListaInstrucciones(List(If(_,e, Retornar(_)))) => List(Error("La Funcion no contiene retorno en el primer bloque del if", funcion))

      case ListaInstrucciones(List(Retornar(_))) => Nil

      case ListaInstrucciones(Retornar(_) :: e) => List(Error("La Funcion tiene intrucciones luego del retorno", funcion))

      case ListaInstrucciones(List(e)) => List(Error("La Funcion no contiene retorno", funcion))

      case _ => Nil
    }
  }

}

trait CheckeoCompleto extends CheckeoOperaciones
  with CheckeoComparaciones
  with CheckeosRecursivos
  with CheckeoVars
  with CheckeoIfs
  with CheckeoFunciones
  with ComparacionTipos
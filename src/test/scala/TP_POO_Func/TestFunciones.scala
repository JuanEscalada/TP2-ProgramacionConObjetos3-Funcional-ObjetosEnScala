package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

class TestFunciones extends BaseSpec {

  // Tests de Checkeo sobre Funciones.

  "Dada una funcion correctamente" should "La checkeo y no me da ningun error " in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones
      with CheckeoComparaciones
      with CheckeoIfs
      with CheckeoVars

    Referencia.clear

    val funcion = Function("unNombre", List(Parametro("a"), Parametro("b")), Retornar(Suma(Referencia("a"), Referencia("b"))))

    val prog = Program(funcion)

    assert(checkeador.verificarPrograma(prog).isEmpty)

  }

  "Cuando declaro una funcion con nombres de parametro repetidos" should "Levanta una excepcion" in {

    try Function("unNombre", List(Parametro("a"), Parametro("a")), Retornar())
    catch {
      case e: ExcepcionLaVariableQueIntentaCrearYaExiste => true
    }

  }

  "Cuando creo una funcion sin retorno y la checkeo " should "devolverme un error" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones
      with CheckeoComparaciones
      with CheckeoIfs
      with CheckeoVars
      with CheckeoFunciones

    Referencia.clear

    val funcion = Function("unNombre", List(Parametro("a"), Parametro("b")), Suma(Referencia("a"), Referencia("b")))

    val prog = Program(funcion)

    val error = Error("La Funcion no contiene retorno", funcion)

    assert(checkeador.verificarPrograma(prog).contains(error))

  }

  "Cuando hago una funcion que termina en un if y verifico que tenga retorno en ambos bloques" should
    "Darme un error si falta en alguno" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones
      with CheckeoComparaciones
      with CheckeoIfs
      with CheckeoVars
      with CheckeoFunciones

    Referencia.clear

    val funcion = Function("unNombre", List(Parametro("a"), Parametro("b")), If(Igual(Referencia("a"), True), Referencia("b"), Retornar()))

    val funcion2 = Function("unNombre", List(Parametro("c"), Parametro("d")), If(Igual(Referencia("a"), True), Retornar(), Number(5)))

    val prog = Program(funcion)

    val prog2 = Program(funcion2)

    val error = Error("La Funcion no contiene retorno en el primer bloque del if", funcion)

    val error2 = Error("La Funcion no contiene retorno en el segundo bloque del if", funcion2)

    assert(checkeador.verificarPrograma(prog).contains(error))

    assert(checkeador.verificarPrograma(prog2).contains(error2))

  }

  "Si declaro una funcion  que tiene una instruccion despues del retorno" should "me devuelve un error" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones
      with CheckeoComparaciones
      with CheckeoIfs
      with CheckeoVars
      with CheckeoFunciones

    Referencia.clear

    val funcion = Function("unNombre", List(Parametro("a"), Parametro("b")), Retornar(), Suma(Referencia("a"), Referencia("b")))

    val prog = Program(funcion)

    val error = Error("La Funcion tiene intrucciones luego del retorno", funcion)

    assert(checkeador.verificarPrograma(prog).contains(error))

  }
}
package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

class TestEjecutador extends BaseSpec{

  "Ejecutar un programa que solo tiene un numero" should "me devuelve ese numero" in{
     val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas

    val prog = Program(Number(2))

    ejecutador.ejecutarPrograma(prog) shouldBe Number(2)
  }

  "EjecutarUnaSuma7mas9" should "Me devuelve un numero de valor 16" in {
     val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas


    val programaSuma = Program(Suma(Number(7), Number(9)))

    ejecutador.ejecutarPrograma(programaSuma)  shouldBe Number(16)
  }

  "EjecutarUnaResta7menos9" should "Me devuelve un numero de valor -2" in {
     val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas


    val programaResta = Program(Resta(Number(7), Number(9)))

    ejecutador.ejecutarPrograma(programaResta) shouldBe Number(-2)
  }

  "EjecutarUnaDivision4div2" should "Me devuelve un numero de valor 2" in {
     val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas


    val programaDivision = Program(Div(Number(4), Number(2)))

    ejecutador.ejecutarPrograma(programaDivision) shouldBe Number(2)
  }

  "EjecutarUnaMultiplicacion4mul2" should "Me devuelve un numero de valor 8" in {
     val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas


    val programaMultiplicacion = Program(Multi(Number(4), Number(2)))

    ejecutador.ejecutarPrograma(programaMultiplicacion) shouldBe Number(8)
  }

  "Si intento ejecutar una division por cero" should "Me salta una excepcion" in {
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas

    val programaConDivPorCero = Program(Div(Number(5), Number(0)))

    try ejecutador.ejecutarPrograma(programaConDivPorCero)
    catch {
      case e: ExcepcionPorDivisionPorCero => true
    }
  }

  "Agrego una sola instruccion a un programa" should "El resultado deberia ser 2" in {
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas

    val programaConInstrucciones = Program(Div(Number(5), Number(2)))

    ejecutador.ejecutarPrograma(programaConInstrucciones) shouldBe Number(2)
  }

  "Agrego multiples instrucciones a un programa" should "El resultado deberia ser 10" in {
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas

    val programaConInstrucciones = Program(
      Div(Number(5), Number(2)),
      Div(Number(5), Number(5)),
      Multi(Number(5), Number(5)))

    ejecutador.ejecutarPrograma(programaConInstrucciones) shouldBe Number(25)
  }

  "Agrego una instruccion if a la lista de instrucciones de un programa" should "El resultado deberia ser 3" in {
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with EstructIf

    val programaConInstrucciones = Program(
      If(Igual(Mayor(Number(8), Number(5)), True),
        Suma(Number(1), Number(2)),
        Resta(Number(1), Number(2))))

    ejecutador.ejecutarPrograma(programaConInstrucciones) shouldBe Number(3)
  }

  "Agrego un condicional y otras operaciones a la lista de instrucciones de un programa" should "El resultado deberia ser 20" in {
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with EstructIf

    val programaConInstrucciones = Program(
      If(Igual(Mayor(Number(8), Number(5)), True),
        Suma(Number(1), Number(2)),
        Resta(Number(1), Number(2))),
      Multi(Number(5), Number(4)))

    ejecutador.ejecutarPrograma(programaConInstrucciones) shouldBe Number(20)
  }

  "Creo una Variable, le asigno el resultado de una suma, a eso le sumo el resultado de una resta y a eso lo multiplico por 5" should
  "devolverme el resultado correspondiente a la variable" in{
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with EstructIf
      with Variables

    val programa = Program(
      Variable("resultado"),
      Asignar(Referencia("resultado"), Suma(Number(3), Number(7))),
      Asignar(Referencia("resultado"), Suma(Resta(Number(20), Number(16)), Referencia("resultado"))),
      Asignar(Referencia("resultado"), Multi(Referencia("resultado"), Number(5)))
    )

    val variable = ejecutador.ejecutarPrograma(programa)

    variable shouldBe Number(70)

  }
}

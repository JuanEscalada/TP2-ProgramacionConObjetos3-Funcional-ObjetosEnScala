package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

class TestRefactorizador extends BaseSpec{

  /* No parece necesario que se haga un refactor de la Division por Cero, eso simplemente deberia levantar excepcion
  * al ejecutar.*/

  "CuandoAUnRefactorizadorConReglaDeRestaNula" should "Me devuelve un nuevo programa" +
    "con el numero resultante si es el primero, y el numero negado si es el segundo." in {
    val refactorizador = new Parser
      with Refactorizador
      with CheckeoOperaciones

    val restaNulaUno = Program(Resta(Number(7),Number(0)))
    val restaNulaDos = Program(Resta(Number(0),Number(7)))
    var progRefactored = refactorizador.hacerRefactor(restaNulaUno)

    progRefactored shouldBe Program(Number(7))

    progRefactored = refactorizador.hacerRefactor(restaNulaDos)

    progRefactored shouldBe Program(Number(-7))

  }

  "CuandoAUnRefactorizadorConReglaDeSumaNula" should "Me devuelve un nuevo programa" +
    "con el numero resultante independientemente de su posicion" in {
    val refactorizador = new Parser
      with Refactorizador
      with CheckeoOperaciones

    val sumaNulaUno = Program(Suma(Number(7),Number(0)))
    val sumaNulaDos = Program(Suma(Number(0),Number(7)))
    var progRefactored = refactorizador.hacerRefactor(sumaNulaUno)

    progRefactored shouldBe Program(Number(7))

    progRefactored = refactorizador.hacerRefactor(sumaNulaDos)

    progRefactored shouldBe Program(Number(7))

  }

  "CuandoAUnRefactorizadorConReglaDeMultiplicacionNeutra" should "Me devuelve un nuevo programa" +
    "con el numero resultante independientemente de su posicion" in {
    val refactorizador = new Parser
      with Refactorizador
      with CheckeoOperaciones

    val multiNeutraUno = Program(Multi(Number(7),Number(1)))
    val multiNeutraDos = Program(Multi(Number(1),Number(7)))
    var progRefactored = refactorizador.hacerRefactor(multiNeutraUno)

    progRefactored shouldBe Program(Number(7))

    progRefactored = refactorizador.hacerRefactor(multiNeutraDos)

    progRefactored shouldBe Program(Number(7))

  }

  "CuandoAUnRefactorizadorConReglaDeDivisionNeutra" should "Me devuelve un nuevo programa" +
    "con el numero resultante independientemente de su posicion" in {
    val refactorizador = new Parser
      with Refactorizador
      with CheckeoOperaciones

    val divNeutra = Program(Div(Number(7),Number(1)))
    val progRefactored = refactorizador.hacerRefactor(divNeutra)

    progRefactored shouldBe Program(Number(7))

  }

  /// Test de refactor de comparaciones

  "CuandoAUnRefactorizadorConReglaDeComparacionDeIgual" should "Me devuelve un nuevo programa" +
    "con el resultadoDeLaComparacion" in {
    val refactorizador = new Parser
      with Refactorizador
      with CheckeoComparaciones
      with CheckeoIfs

    val igualdad = Program(Igual(Number(7),Number(7)))
    var progRefactored = refactorizador.hacerRefactor(igualdad)

    progRefactored shouldBe Program(True)

  }

  "CuandoAUnRefactorizadorConReglaDeComparacionMenor" should "Me devuelve un nuevo programa" +
    "con el resultadoDeLaComparacion" in {
    val refactorizador = new Parser
      with Refactorizador
      with CheckeoComparaciones
      with CheckeoIfs

    val menorValido = Program(Menor(Number(2), Number(3)))
    var progRefactored = refactorizador.hacerRefactor(menorValido)

    progRefactored shouldBe Program(True)

  }

  "CuandoAUnRefactorizadorConReglaDeComparacionMayor" should "Me devuelve un nuevo programa" +
    "con el resultadoDeLaComparacion" in {
    val refactorizador = new Parser
      with Refactorizador
      with CheckeoComparaciones

    val mayorValido = Program(Mayor(Number(6), Number(3)))
    var progRefactored = refactorizador.hacerRefactor(mayorValido)

    progRefactored shouldBe Program(True)

  }


  // TESTS DE REFACTOR SOBRE IFS

  "Si hacemos un if cuyo primer argumento es true o false y lo refactorizamos" should
    "Se refactoriza de manera que queda el unico bloque que se ejecuta" in{
    val refactorizador = new Parser
      with Refactorizador
      with CheckeoComparaciones
      with CheckeoIfs

    var programa = Program(If(True, Number(1), Number(2)))

    refactorizador.hacerRefactor(programa) shouldBe Program(Number(1))

    programa = Program(If(False, Number(1), Number(2)))

    refactorizador.hacerRefactor(programa) shouldBe Program(Number(2))

    programa = Program(If(Menor(Number(3), Number(7)), Number(1), Number(2)))

    refactorizador.hacerRefactor(programa) shouldBe programa
  }
}

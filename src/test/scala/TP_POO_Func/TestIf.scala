package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

class TestIf extends BaseSpec {

  "La condicion entre booleanos en el If da True" should "se ejecuta el primer bloque" in{
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with EstructIf

    val prog = Program(If(Igual(Mayor(Number(8), Number(5)), True), Suma(Number(1), Number(2)), Resta(Number(1), Number(2))))

    ejecutador.ejecutarPrograma(prog) shouldBe Number(3)
  }

  "La condicion entre booleanos en el If da False" should "se ejecuta el segundo bloque" in{
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with EstructIf

    val prog = Program(If(Igual(True, False), Suma(Number(1), Number(2)), Resta(Number(1), Number(2))))

    ejecutador.ejecutarPrograma(prog) shouldBe Number(-1)
  }

  "La condicion entre numeros en el If da True" should "se ejecuta el primer bloque" in{
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with EstructIf

    val prog = Program(If(Igual(Number(5), Number(5)), Suma(Suma(Number(1), Number(0)), Number(2)), Resta(Number(1), Number(2))))

    ejecutador.ejecutarPrograma(prog) shouldBe Number(3)
  }

  "La condicion entre numeros en el If da False" should "se ejecuta el segundo bloque" in{
    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with EstructIf

    val prog = Program(If(Igual(Number(5), Number(6)), Suma(Number(1), Number(2)), If(MenorIgual(Number(7), Number(13)), Resta(Number(1), Number(2)), Number(2))))

    ejecutador.ejecutarPrograma(prog) shouldBe Number(-1)
  }
}


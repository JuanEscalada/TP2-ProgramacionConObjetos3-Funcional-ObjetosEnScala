package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

class TestVariables extends BaseSpec {

  "Creo una variable y la inicializo con el numero 9 y pregunto por la referencia a su nombre" should "devolverme la variable" +
    "y si le pido su contenido deveria devolverme Number(9)" in{
    val variable = Variable("nueve", Number(9))

    Referencia("nueve") shouldBe variable

    Referencia("nueve").contenido shouldBe Number(9)
  }

  "Creo una variable sin inicializar y pregunto por la referencia a su nombre" should "devolverme la variable" +
    "y si le pido su contenido deveria levantar una excepcion" +
    "y si luego le asigno a esa variable un numero y le pido su contenido deberia devolverme ese numero" in{
    val variableNoInicializada = Variable("notIni")

    Referencia("notIni") shouldBe variableNoInicializada

    try Referencia("notIni").contenido
    catch {case e : ExcepcionPorVariableSinInicializar => true}

    Referencia("notIni").asignar(Number(129))

    Referencia("notIni").contenido shouldBe Number(129)
  }

  "Si creo una variable llamada var1 e intento crear una segunda variable con el mismo nombre" should
  "Levanta una Excepcion" in{
    Variable("var1", Number(2))

    try Variable("var1", Number(8))
    catch {case e : ExcepcionLaVariableQueIntentaCrearYaExiste => true}
  }
}


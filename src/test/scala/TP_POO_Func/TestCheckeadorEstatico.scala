package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

import org.scalatest.FlatSpec
import org.scalatest.Matchers

trait BaseSpec extends FlatSpec with Matchers

class TestsCheckeadorEstatico extends BaseSpec{

  // TESTS DE OPERACIONES / COMPARACIONES


  "CuandoAUnCheckeadorEstaticoConReglaDeDivisionPorCeroLePasoUnaDivisionPorCero" should "Me devuelve un Option con" +
    "Error por division por cero y en caso contrario me devuelve None" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones

    val divisionPorCero = Program(Div(Number(7),Number(0)))
    val divisionValida = Program(Div(Number(3),Number(3)))
    var error = Error("La division por Cero no esta definida.", Div(Number(7),Number(0)))

    checkeador.verificarPrograma(divisionPorCero).contains(error) shouldBe true

    checkeador.verificarPrograma(divisionValida).isEmpty shouldBe true
  }

  "CuandoAUnCheckeadorEstaticoConReglaRestaNuloaLePasoUnaRestaNula" should "Me devuelve un Option con" +
    "Advertencia por resta nula o un None en caso contrario" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones

    val restaNula = Program(Resta(Number(7),Number(0)))
    val restaNormal = Program(Resta(Number(3),Number(1)))
    var error  = Advertencia("El resultado de la resta es 7 ya que el numero que se le resta es 0", Resta(Number(7),Number(0)))

    checkeador.verificarPrograma(restaNula).contains(error) shouldBe true

    checkeador.verificarPrograma(restaNormal).isEmpty shouldBe true
  }

  "CuandoAUnCheckeadorEstaticoConReglaSumaNulaLePasoUnaSumaNula" should "Me devuelve un Option con" +
    "Advertencia por suma nula o un None en caso contrario" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones

    val sumaNulaPrimero = Program(Suma(Number(0),Number(5)))
    val sumaNulaSegundo = Program(Suma(Number(7),Number(0)))
    val sumaNulaTodo = Program(Suma(Number(0),Number(0)))
    val sumaNormal = Program(Suma(Number(3),Number(1)))

    checkeador.verificarPrograma(sumaNulaPrimero).contains(Advertencia("El resultado de la suma es 5 ya que el numero que se le suma es 0", Suma(Number(0),Number(5)))) shouldBe true

    checkeador.verificarPrograma(sumaNulaSegundo).contains(Advertencia("El resultado de la suma es 7 ya que el numero que se le suma es 0", Suma(Number(7),Number(0)))) shouldBe true

    checkeador.verificarPrograma(sumaNulaTodo).contains(Advertencia("El resultado de la suma es 0 ya que el numero que se le suma es 0", Suma(Number(0),Number(0)))) shouldBe true

    checkeador.verificarPrograma(sumaNormal).isEmpty shouldBe true
  }

  "CuandoAUnCheckeadorEstaticoConReglaDeMultiplicacionPorUnoLePasoUnaMultiplicacionPorUno" should "Me devuelve un Option con" +
    "Advertencia por multiplicacion por uno y en caso contrario me devuelve None" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones

    val multiplicacionPorUno = Program(Multi(Number(7),Number(1)))
    val multiplicacionNormal = Program(Multi(Number(3),Number(2)))
    var error = Advertencia("El resultado es 7 porque 1 es el neutro de la multiplicacion.", Multi(Number(7), Number(1)))

    checkeador.verificarPrograma(multiplicacionPorUno).contains(error) shouldBe true

    checkeador.verificarPrograma(multiplicacionNormal).isEmpty shouldBe true
  }

  "CuandoAUnCheckeadorEstaticoConReglaDeDivisionPorUnoLePasoUnaDivisionPorUno" should "Me devuelve un Option con" +
    "Advertencia por division por uno y en caso contrario me devuelve None" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones

    val divisionPorUno = Program(Div(Number(7),Number(1)))
    val divisionValida = Program(Div(Number(3),Number(2)))
    var error = Advertencia("El resultado es 7 ya que el 1 es neutro en la division.", Div(Number(7),Number(1)))

    checkeador.verificarPrograma(divisionPorUno).contains(error) shouldBe true

    checkeador.verificarPrograma(divisionValida).isEmpty shouldBe true
  }

  "Dada una division entre un numero y una suma de la cual uno de los elementos es cero" should "dar una advertencia" in{
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones
      with CheckeoComparaciones
      with CheckeosRecursivos
      with CheckeoVars
      with CheckeoIfs

    var operacion : Operacion = Div(Number(3), Suma(Number(2), Number(0)))

    val primerError = Advertencia("El resultado de la suma es 2 ya que el numero que se le suma es 0", Suma(Number(2),Number(0)))

    val error = Error("La division por Cero no esta definida.", Div(Number(2),Number(0)))

    checkeador.verificarPrograma(Program(operacion)).contains(primerError) shouldBe true

    //Se puede anidar mas aun.

    operacion = Multi(Suma(Number(7), Resta(Number(5), Div(Number(2), Number(0)))), Div(Number(3), Number(2)))

    checkeador.verificarPrograma(Program(operacion)).contains(error) shouldBe true

  }



// Checkeos sobre comparaciones

  "CuandoAUnCheckeadorEstaticoConReglaDeComparacionMenorLePasoUnaCompariacionMenor" should "Me devuelve un Option con" +
    "Advertencia por menor y en caso contrario me devuelve None" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoComparaciones
      with CheckeoVars
      with CheckeoIfs

    val operacionConMenor = Program(Menor(Number(3), Number(4)))
    val operacionConMenorValida = Program(Menor(Number(2),Number(2)))
    var error =  Advertencia("El resultado sera siempre true porque se esta preguntando si 3 es menor a 4", Menor(Number(3), Number(4)))

    assert(checkeador.verificarPrograma(operacionConMenor).contains(error))

    assert(checkeador.verificarPrograma(operacionConMenorValida).isEmpty)
  }
  "CuandoAUnCheckeadorEstaticoConReglaDeComparacionMayorLePasoUnaCompariacionMayor" should "Me devuelve un Option con" +
    "Advertencia por mayor y en caso contrario me devuelve None" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoComparaciones
      with CheckeoVars
      with CheckeoIfs

    val operacionConMayor= Program(Mayor(Number(4), Number(3)))
    val operacionConMayorValida = Program(Mayor(Number(2),Number(2)))
    var error =  Advertencia("El resultado sera siempre true porque se esta preguntando si 4 es mayor a 3", Mayor(Number(4), Number(3)))

    checkeador.verificarPrograma(operacionConMayor).contains(error) shouldBe true

    checkeador.verificarPrograma(operacionConMayorValida).isEmpty shouldBe true
  }

  "CuandoAUnCheckeadorEstaticoConReglaDeComparacionPorIgualLePasoUnaCompariacionPorIgual" should "Me devuelve un Option con" +
    "Advertencia por igual y en caso contrario me devuelve None" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoComparaciones
      with CheckeoVars
      with CheckeoIfs

    val operacionConIgual= Program(Igual(Number(4), Number(4)))
    val operacionConIgualValida = Program(Igual(Number(3),Number(2)))
    var errorEsperado = Advertencia("El resultado sera siempre true porque se esta preguntando si 4 es igual a 4", Igual(Number(4), Number(4)))

    checkeador.verificarPrograma(operacionConIgual).contains(errorEsperado) shouldBe true

    checkeador.verificarPrograma(operacionConIgualValida).isEmpty shouldBe true
  }

  "Si tengo una comparacion que tiene una operacion adentro que no cumple algun checkeo" should "Dar advertencia o error correspondientemente" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoOperaciones
      with CheckeoComparaciones
      with CheckeosRecursivos
      with CheckeoVars
      with CheckeoIfs

    var comparacion : Comparacion = Igual(Div(Number(3), Suma(Number(2), Number(0))), Number(3))

    val errorEsperado = Advertencia("El resultado de la suma es 2 ya que el numero que se le suma es 0", Suma(Number(2),Number(0)))

    checkeador.verificarPrograma(Program(comparacion)).contains(errorEsperado) shouldBe true

  }








  // TESTS SOBRE VARIABLES

  "Cuando a un Checkeador le paso una Variable que fue inicializada" should "Me devuelve none" +
    "y si no fue inicializada me devuelve un error" in {
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoComparaciones
      with CheckeoVars
      with CheckeoIfs

    var programa = Program(Variable("yesIni", Number(3)))

    checkeador.verificarPrograma(programa).isEmpty shouldBe true

    programa = Program(Variable("notIni"))

    checkeador.verificarPrograma(programa).contains(Error("La variable notIni nunca fue inicializada", Referencia("notIni"))) shouldBe true

  }

  // TESTS SOBRE IFS

  "Si hacemos un if cuyo primer argumento es true o false y lo checkeamos" should "nos da una advertencia ya que uno de" +
    "los bloques nunca se ejecuta y sino nos da none" in{
    val checkeador = new Parser
      with CheckeadorEstatico
      with CheckeoComparaciones
      with CheckeoVars
      with CheckeoIfs

    var programa = Program(If(True, Number(1), Number(2)))

    checkeador.verificarPrograma(programa).contains(Advertencia("Se ejecutara siempre el bloque de True ya que la condicion es True", If(True, Number(1), Number(2)))) shouldBe true

    programa = Program(If(False, Number(1), Number(2)))

    checkeador.verificarPrograma(programa).contains(Advertencia("Se ejecutara siempre el bloque de False ya que la condicion es False", If(False, Number(1), Number(2)))) shouldBe true

    programa = Program(If(Menor(Number(3), Number(7)), Number(1), Number(2)))

    checkeador.verificarPrograma(programa).isEmpty shouldBe true
  }

}

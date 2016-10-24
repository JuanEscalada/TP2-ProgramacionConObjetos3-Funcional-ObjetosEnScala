package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

class TestEjecucionDeVariables extends BaseSpec{

  //Testeo de variables en suma

  "Si tengo un programa con una suma en la cual uno de los valores es una variable conteniendo un numero" +
    "si la ejecuto" should "Dar el resultado normalmente" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables
    Variable("var1", Number(10))

    //Sumas validas

    ejecutador.ejecutarPrograma(Program(Suma(Number(7), Referencia("var1")))) shouldBe Number(17)


    ejecutador.ejecutarPrograma(Program(Suma(Referencia("var1"), Number(7)))) shouldBe Number(17)


    ejecutador.ejecutarPrograma(Program(Suma(Referencia("var1"), Referencia("var1")))) shouldBe Number(20)

  }

  "Si tengo un programa con una suma en la cual uno de los valores es una variable sin inicializar" +
    "si la ejecuto" should "Levantan Excepcion" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables
    Variable("var2")

    //Sumas en variables sin inicializar levantan excepcion

    try ejecutador.ejecutarPrograma(Program(Suma(Number(7), Referencia("var2"))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

    try ejecutador.ejecutarPrograma(Program(Suma(Referencia("var2"), Number(7))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

    try ejecutador.ejecutarPrograma(Program(Suma(Referencia("var2"), Referencia("var2"))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

  }

  "Si tengo un programa cuyo contenido es una variable con una suma en su interior cuando lo" +
    "ejecuto" should "devolver el resultado de esa suma" in{

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables

    Variable("suma", Suma(Number(5), Number(7)))

    ejecutador.ejecutarPrograma(Program(Referencia("suma"))) shouldBe Number(12)

  }

  "Finalmente si tengo un programa que tiene una variable con una suma con variables cuando lo ejecuto" should
  "devolver el resultado de la suma" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables

    Variable("siete", Number(7))
    Variable("sumaInterna", Suma(Number(8), Number(18)))
    Variable("suma", Suma(Referencia("siete"), Referencia("sumaInterna")))

    ejecutador.ejecutarPrograma(Program(Referencia("suma"))) shouldBe Number(33)
  }

  ////////////////////////////////////////////////////////////////////////

  //Testeo de variables en Resta

  "Si tengo un programa con una resta en la cual uno de los valores es una variable conteniendo un numero" +
    "si la ejecuto" should "Dar el resultado normalmente" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables
    Variable("var1", Number(10))

    //Restas validas

    ejecutador.ejecutarPrograma(Program(Resta(Number(22), Referencia("var1")))) shouldBe Number(12)


    ejecutador.ejecutarPrograma(Program(Resta(Referencia("var1"), Number(7)))) shouldBe Number(3)


    ejecutador.ejecutarPrograma(Program(Resta(Referencia("var1"), Referencia("var1")))) shouldBe Number(0)

  }

  "Si tengo un programa con una resta en la cual uno de los valores es una variable sin inicializar" +
    "si la ejecuto" should "Levantan Excepcion" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables
    Variable("var2")

    //Restas en variables sin inicializar levantan excepcion

    try ejecutador.ejecutarPrograma(Program(Resta(Number(7), Referencia("var2"))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

    try ejecutador.ejecutarPrograma(Program(Resta(Referencia("var2"), Number(7))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

    try ejecutador.ejecutarPrograma(Program(Resta(Referencia("var2"), Referencia("var2"))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

  }

  "Si tengo un programa cuyo contenido es una variable con una resta en su interior cuando lo" +
    "ejecuto" should "devolver el resultado de esa resta" in{

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables

    Variable("resta", Resta(Number(5), Number(7)))

    ejecutador.ejecutarPrograma(Program(Referencia("resta"))) shouldBe Number(-2)

  }

  "Finalmente si tengo un programa que tiene una variable con una resta con variables cuando lo ejecuto" should
    "devolver el resultado de la resta" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables

    Variable("dieciSiete", Number(17))
    Variable("restaInterna", Resta(Number(18), Number(8)))
    Variable("resta", Resta(Referencia("dieciSiete"), Referencia("restaInterna")))

    ejecutador.ejecutarPrograma(Program(Referencia("resta"))) shouldBe Number(7)
  }


  ////////////////////////////////////////////////////////////////////////

  //Testeo de variables en Division

  "Si tengo un programa con una division en la cual uno de los valores es una variable conteniendo un numero" +
    "si la ejecuto" should "Dar el resultado normalmente" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables
    Variable("var1", Number(10))

    //Divisiones validas

    ejecutador.ejecutarPrograma(Program(Div(Number(20), Referencia("var1")))) shouldBe Number(2)


    ejecutador.ejecutarPrograma(Program(Div(Referencia("var1"), Number(2)))) shouldBe Number(5)


    ejecutador.ejecutarPrograma(Program(Div(Referencia("var1"), Referencia("var1")))) shouldBe Number(1)

  }

  "Si tengo un programa con una division en la cual uno de los valores es una variable sin inicializar" +
    "o cero en el divisor" +
    "y la ejecuto" should "Levantan Excepcion" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables
    Variable("var2")
    Variable("cero", Number(0))

    //Divisiones en variables sin inicializar levantan excepcion

    try ejecutador.ejecutarPrograma(Program(Div(Number(7), Referencia("var2"))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

    try ejecutador.ejecutarPrograma(Program(Div(Referencia("var2"), Number(7))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

    try ejecutador.ejecutarPrograma(Program(Div(Referencia("var2"), Referencia("var2"))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}


    // Division por cero sigue siendo invalida.

    try ejecutador.ejecutarPrograma(Program(Div(Number(8), Referencia("cero"))))
    catch { case e : ExcepcionPorDivisionPorCero => true}

  }

  "Si tengo un programa cuyo contenido es una variable con una division en su interior cuando lo" +
    "ejecuto" should "devolver el resultado de esa division" in{

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables

    Variable("div", Div(Number(10), Number(5)))

    ejecutador.ejecutarPrograma(Program(Referencia("div"))) shouldBe Number(2)

  }

  "Finalmente si tengo un programa que tiene una variable con una division con variables cuando lo ejecuto" should
    "devolver el resultado de la division" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables

    Variable("veintiCuatro", Number(24))
    Variable("divInterna", Div(Number(18), Number(6)))
    Variable("div", Div(Referencia("veintiCuatro"), Referencia("divInterna")))

    ejecutador.ejecutarPrograma(Program(Referencia("div"))) shouldBe Number(8)
  }

  ////////////////////////////////////////////////////////////////////////

  //Testeo de variables en Multiplicacion

  "Si tengo un programa con una multiplicacion en la cual uno de los valores es una variable conteniendo un numero" +
    "si la ejecuto" should "Dar el resultado normalmente" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables
    Variable("var1", Number(10))

    //Multiplicaciones validas

    ejecutador.ejecutarPrograma(Program(Multi(Number(20), Referencia("var1")))) shouldBe Number(200)


    ejecutador.ejecutarPrograma(Program(Multi(Referencia("var1"), Number(2)))) shouldBe Number(20)


    ejecutador.ejecutarPrograma(Program(Multi(Referencia("var1"), Referencia("var1")))) shouldBe Number(100)

  }

  "Si tengo un programa con una multiplicacion en la cual uno de los valores es una variable sin inicializar" +
    "y la ejecuto" should "Levantan Excepcion" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables
    Variable("var2")
    Variable("cero", Number(0))

    //Multiplicacion en variables sin inicializar levantan excepcion

    try ejecutador.ejecutarPrograma(Program(Multi(Number(7), Referencia("var2"))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

    try ejecutador.ejecutarPrograma(Program(Multi(Referencia("var2"), Number(7))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

    try ejecutador.ejecutarPrograma(Program(Multi(Referencia("var2"), Referencia("var2"))))
    catch { case e : ExcepcionPorVariableSinInicializar => true}

  }

  "Si tengo un programa cuyo contenido es una variable con una Multiplicacion en su interior cuando lo" +
    "ejecuto" should "devolver el resultado de esa Multiplicacion" in{

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables

    Variable("multi", Multi(Number(10), Number(5)))

    ejecutador.ejecutarPrograma(Program(Referencia("multi"))) shouldBe Number(50)

  }

  "Finalmente si tengo un programa que tiene una variable con una multiplicacion con variables cuando lo ejecuto" should
    "devolver el resultado de la multiplicacion" in {

    Referencia.clear

    val ejecutador = new Parser
      with Ejecutador
      with OperacionesBasicas
      with ComparacionesBasicas
      with Variables

    Variable("diez", Number(10))
    Variable("multiInterna", Multi(Number(5), Number(6)))
    Variable("multi", Multi(Referencia("diez"), Referencia("multiInterna")))

    ejecutador.ejecutarPrograma(Program(Referencia("multi"))) shouldBe Number(300)
  }
}

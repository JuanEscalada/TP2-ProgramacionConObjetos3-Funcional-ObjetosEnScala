package TP_POO_Func

/**
  * Creado por Juan Escalada, Programacion con Objetos III
  */

trait Variables extends Ejecutador{

  ejecutar.defMethod({ case v @ Variable(_, None) => throw new ExcepcionPorVariableSinInicializar})

  ejecutar.defMethod({ case v @ Variable(_, Some(_)) => ejecutar(v.contenido)})

  ejecutar.defMethod({ case Asignar(vars, contenido) => vars.asignar(ejecutar(contenido))})

}

trait OperacionesBasicas extends Ejecutador{

  ejecutar.defMethod({ case o @ Operacion(n, m) => aplicarOpBinaria(o, ejecutar(n), ejecutar(m))})

  ejecutar.defMethod({ case Suma(Number(n), Number(m)) => Number(n + m)})

  ejecutar.defMethod({ case Resta(Number(n), Number(m)) => Number(n - m)})

  ejecutar.defMethod({ case Multi(Number(n), Number(m)) => Number(n * m)})

  ejecutar.defMethod({ case Div(Number(n), Number(m)) => Number(n / m)})

  ejecutar.defMethod({ case Div(_, Number(0)) => throw new ExcepcionPorDivisionPorCero})

  ejecutar.defMethod({ case Operacion(n : Booleanos, _) => throw new ExcepcionPorErrorDeTipos})

  ejecutar.defMethod({ case Operacion(_, n : Booleanos) => throw new ExcepcionPorErrorDeTipos})

  ejecutar.defMethod({ case Number(n) => Number(n)})

}

trait ComparacionesBasicas extends Ejecutador{

  ejecutar.defMethod({ case b : Booleanos => b})

  ejecutar.defMethod({ case o @ Comparacion(n, m) => aplicarComparacionBinaria(o, ejecutar(n), ejecutar(m))})

  ejecutar.defMethod({ case Igual(True, False) => False})

  ejecutar.defMethod({ case Igual(False, True) => False})

  ejecutar.defMethod({ case Igual(True, True) => True})

  ejecutar.defMethod({ case Igual(False, False) => True})

  ejecutar.defMethod({ case Igual(Number(n), Number(m)) => False})

  ejecutar.defMethod({ case Igual(Number(n), Number(m)) if n == m => True})

  ejecutar.defMethod({ case Mayor(b : Booleanos, b2 : Booleanos) => False})

  ejecutar.defMethod({ case Mayor(Number(n), Number(m)) => False})

  ejecutar.defMethod({ case Mayor(Number(n), Number(m)) if n > m => True})

  ejecutar.defMethod({ case Menor(b : Booleanos, b2 : Booleanos) => False})

  ejecutar.defMethod({ case Menor(Number(n), Number(m)) => False})

  ejecutar.defMethod({ case Menor(Number(n), Number(m)) if n < m => True})

  ejecutar.defMethod({ case Not(b) => ejecutar(Not(ejecutar(b)))})

  ejecutar.defMethod({ case Not(True) => False})

  ejecutar.defMethod({ case Not(False) => True})

  ejecutar.defMethod({ case Not(n : Numbers) => throw new ExcepcionPorErrorDeTipos})

  ejecutar.defMethod({ case Comparacion(b : Booleanos, n : Numbers) => throw new ExcepcionPorErrorDeTipos})

  ejecutar.defMethod({ case Comparacion(n : Numbers, b : Booleanos) => throw new ExcepcionPorErrorDeTipos})

}

trait EstructIf extends Ejecutador{

  ejecutar.defMethod({ case If(True, t, f) => ejecutar(t)})

  ejecutar.defMethod({ case If(False, t, f) => ejecutar(f)})

  ejecutar.defMethod({ case If(c @ Comparacion(pri, sec), t, f) => ejecutar(If(aplicarComparacionBinaria(c, pri, sec), t, f))})

  ejecutar.defMethod({ case If(n : Numbers, _, _) => throw new ExcepcionPorErrorDeTipos})

}
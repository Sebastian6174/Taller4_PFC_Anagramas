package object Anagramas {

  type Palabra = String
  type Frase = List[Palabra]
  type Ocurrencias = List[(Char, Int)]

  val diccionario: List[Palabra] = List("cosas", "como", "yo", "y", "ocasos", "oca", "cayo", "mocosos", "roca", "moco", "sos", "saca")

  def lOcPal(p: Palabra): Ocurrencias = {
    p.groupBy(letra => letra).map {
      case (letra, ocurrencias) => (letra, ocurrencias.length)
    }.toList.sortBy(_._1)
  }

  def lOcFrase(f: Frase): Ocurrencias = {
    lOcPal(f.foldLeft("")((acumulador, palabra) => acumulador + palabra).trim).sortBy(_._1)
  }

  lazy val diccionarioPorOcurrencias: Map[Ocurrencias, List[Palabra]] = {
    diccionario.groupBy(palabra => lOcPal(palabra))
  }

  def anagramasDePalabra(pal: Palabra): List[Palabra] = {
    diccionarioPorOcurrencias.getOrElse(lOcPal(pal), List()).filter(_ != pal)
  }

  def combinaciones(lOcurrencias: Ocurrencias): List[Ocurrencias] = {
    lOcurrencias match {
      case Nil => List(Nil)
      case (letra, nOcurrencias) :: cola =>
        val combinacionesDeLaCola = combinaciones(cola)
        {
          for {
            n <- 1 to nOcurrencias
            combinacion <- combinacionesDeLaCola
          } yield (letra, n) :: combinacion
        }.toList ++ combinacionesDeLaCola
    }
  }

  def complemento(lOc: Ocurrencias, slOc: Ocurrencias): Ocurrencias = {
    val mapaSlOc = slOc.toMap

    lOc
      .map { case (letra, cantidad) =>
        val cantRestar = mapaSlOc.getOrElse(letra, 0)
        (letra, cantidad - cantRestar)
      }
      .filter { case (_, nuevaCantidad) => nuevaCantidad > 0 }
  }

  def anagramasDeFrase(frase: Frase): List[Frase] = {
    def anagramasLiOc(liOc: Ocurrencias): List[Frase] = {
      liOc match {
        case Nil => List(Nil)
        case _ =>
          val comb = combinaciones(liOc)
          for {
            c <- comb
            (liOcurrencias, palabras) <- diccionarioPorOcurrencias
            if c == liOcurrencias && c.nonEmpty
            palabra <- palabras
            palabrasComplemento <- anagramasLiOc(complemento(liOc, c))
          } yield palabra :: palabrasComplemento
      }
    }

    anagramasLiOc(lOcFrase(frase)).filter(_ != frase)
  }
}
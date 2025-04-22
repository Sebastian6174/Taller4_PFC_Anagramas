package object Anagramas {

  type Palabra = String
  type Frase = List[Palabra]
  type Ocurrencias = List[(Char, Int)]

  val diccionario: List[Palabra] = List("cosas", "como", "yo", "y", "ocasos", "oca", "cayo", "mocosos", "roca", "moco", "sos", "saca")
  //val diccionario: List[Palabra] = List("yo", "oy", "sos", "oso", "so", "os")

  def lOcPal(p: Palabra): Ocurrencias = {
    p.groupBy(letra => letra).map {
      case (letra, ocurrencias) => (letra, ocurrencias.length)
    }.toList
  }

  def lOcFrase(f: Frase): Ocurrencias = {
    lOcPal(f.foldLeft("")((acumulador, palabra) => acumulador + palabra).trim)
  }

  lazy val diccionarioPorOcurrencias: Map[Ocurrencias, List[Palabra]] = {
    diccionario.groupBy(palabra => lOcPal(palabra))
  }

  def anagramasDePalabra(pal: Palabra): List[Palabra] = {
    val ocuPal = lOcPal(pal)
    {
      for {
        (liOcurrencia, palabras) <- diccionarioPorOcurrencias
        if liOcurrencia.toSet == ocuPal.toSet
      } yield palabras
    }.flatten.toList

    // Otra forma: diccionarioPorOcurrencias.getOrElse(lOcPal(pal), List())
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

    lOc.flatMap {
      case (letra, cantidad) =>
        mapaSlOc.get(letra) match {
          case Some(cantRestar) if cantidad - cantRestar > 0 => Some((letra, cantidad - cantRestar))
          case Some(_) => None 
          case None => Some((letra, cantidad)) 
        }
    }
  } // se necesita usar tailrec ¿¿??

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

    anagramasLiOc(lOcFrase(frase))
  }
}
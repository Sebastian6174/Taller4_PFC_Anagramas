import Anagramas._

// ----------- lOcPal -----------
println("=== Pruebas lOcPal ===")
println(lOcPal("casa"))        // List(('a',2), ('c',1), ('s',1))
println(lOcPal("moco"))        // List(('c',1), ('m',1), ('o',2))
println(lOcPal("sos"))         // List(('o',1), ('s',2))
println(lOcPal("roca"))        // List(('a',1), ('c',1), ('o',1), ('r',1))
println(lOcPal("yo"))          // List(('o',1), ('y',1))
println(lOcPal("saca"))        // List(('a',2), ('c',1), ('s',1))

// ----------- combinaciones -----------
println("\n=== Pruebas combinaciones ===")
println(combinaciones(lOcPal("casa")))    // muchas combinaciones, puedes imprimir length
println(combinaciones(lOcPal("moco")))
println(combinaciones(lOcPal("sos")))
println(combinaciones(lOcPal("roca")))
println(combinaciones(lOcPal("yo")))

// ----------- lOcFrase -----------
println("\n=== Pruebas lOcFrase ===")
println(lOcFrase(List("cosas", "como", "yo"))) // List(('a',1),('c',2),('m',1),('o',3),('s',3),('y',1))
println(lOcFrase(List("sos", "moco")))         // List(('c',1),('m',1),('o',2),('s',2))
println(lOcFrase(List("roca", "yo")))          // List(('a',1),('c',1),('o',1),('r',1),('y',1))
println(lOcFrase(List("casa", "saca")))        // List(('a',3),('c',2),('s',2))
println(lOcFrase(List("yo")))                  // List(('o',1),('y',1))
println(lOcFrase(List("sos")))                 // List(('o',1),('s',2))

// ----------- complemento -----------
println("\n=== Pruebas complemento ===")
println(complemento(lOcPal("casa"), List(('a',1)))) // List(('a',1),('c',1),('s',1))
println(complemento(lOcPal("casa"), List(('c',1)))) // List(('a',2),('s',1))
println(complemento(lOcPal("casa"), List(('s',1)))) // List(('a',2),('c',1))
println(complemento(lOcPal("casa"), List(('a',2)))) // List(('c',1),('s',1))
println(complemento(lOcPal("casa"), List(('a',1),('s',1)))) // List(('a',1),('c',1))
println(complemento(lOcPal("casa"), List(('z',1)))) // List(('a',2),('c',1),('s',1))

// ----------- anagramasDePalabra -----------
println("\n=== Pruebas anagramasDePalabra ===")
println(anagramasDePalabra("ocasos"))   // Esperado: List("ocasos")
println(anagramasDePalabra("moco"))    // Esperado: List("como")
println(anagramasDePalabra("como"))    // Esperado: List("moco")
println(anagramasDePalabra("sos"))     // Esperado: List()
println(anagramasDePalabra("roca"))    // Esperado: List()

// ----------- anagramasDeFrase -----------
println("\n=== Pruebas anagramasDeFrase ===")
def probar(frase: Frase, descripcion: String): Unit = {
  println(s"🔍 $descripcion")
  println(s"Frase: $frase")
  val resultado = anagramasDeFrase(frase)
  if (resultado.isEmpty) println("Resultado: (ningún anagrama encontrado)\n")
  else resultado.foreach(anagrama => println(s"  ➤ ${anagrama.mkString(" ")}"))
  println("-" * 50 + "\n")
}

probar(List(), "Frase vacía")
probar(List("yo"), "Una sola palabra que está en el diccionario")
probar(List("xyz"), "Palabra no existente en el diccionario")
probar(List("sos", "yo"), "Frase con varias combinaciones posibles")
probar(List("mocosos"), "Frase con letras repetidas (múltiples caminos)")
probar(List("ocasos"), "Frase con múltiples anagramas posibles")
probar(List("abc"), "Frase con letras que no están en ninguna palabra del diccionario")
probar(List("casa", "saca"), "Frase con palabras anagramas entre sí")
probar(List("roca", "yo"), "Frase con palabras cortas")
probar(List("moco", "sos"), "Frase con palabras del diccionario")

import Anagramas._

lOcPal("casa")
combinaciones(lOcPal("casa"))
val frase = List("cosas", "como", "yo")
combinaciones(lOcFrase(frase))

anagramasDeFrase(frase)

import Anagramas._

// Función para imprimir resultados de prueba
def probar(frase: Frase, descripcion: String): Unit = {
  println(s"🔍 $descripcion")
  println(s"Frase: $frase")
  val resultado = anagramasDeFrase(frase)
  if (resultado.isEmpty) println("Resultado: (ningún anagrama encontrado)\n")
  else resultado.foreach(anagrama => println(s"  ➤ ${anagrama.mkString(" ")}"))
  println("-" * 50 + "\n")
}

// Casos de prueba
probar(List(), "Frase vacía")
probar(List("yo"), "Una sola palabra que está en el diccionario")
probar(List("xyz"), "Palabra no existente en el diccionario")
probar(List("sos", "yo"), "Frase con varias combinaciones posibles")
probar(List("mocosos"), "Frase con letras repetidas (múltiples caminos)")
probar(List("ocasos"), "Frase con múltiples anagramas posibles")
probar(List("abc"), "Frase con letras que no están en ninguna palabra del diccionario")
probar(List("cosas", "como", "yo"), "Boss final")
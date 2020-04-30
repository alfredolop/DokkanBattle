package net.azarquiel.dokkanbattle.model

import java.io.Serializable
import java.security.AlgorithmConstraints

data class Carta(var leader_skill:String = "",
                 var super_atk:String = "",
                 var sobrenombre:String = "",
                 var links:String = "",
                 var active_skill:String = "",
                 var iddokkan:Long = 0,
                 var rarezapredokkan:String = "",
                 var idtipoelementopredokkan:Long = 0,
                 var idpersonaje:Long = 0,
                 var nombre:String = "",
                 var idtipoelementodokkan:Long = 0,
                 var categories:String = "",
                 var elemento:String = "",
                 var rarezadokkan:String = "",
                 var idtipoelemento:Long = 0,
                 var idpredokkan:Long = 0,
                 var passive_skill:String = "",
                 var tipo:String = "",
                 var poster:String = "",
                 var rareza:String = ""
                 ): Serializable
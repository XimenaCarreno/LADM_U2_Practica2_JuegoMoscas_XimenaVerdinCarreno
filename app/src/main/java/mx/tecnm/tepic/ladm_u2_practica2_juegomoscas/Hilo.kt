package mx.tecnm.tepic.ladm_u2_practica2_juegomoscas

import kotlin.random.Random

class Hilo(p: Lienzo, m:Mosca): Thread(){ //Se le da tanto el parametro para dibujar, como el objeto que usará el hilo
    var puntero = p
    var moscax = m.x
    var moscay = m.y
    var mosca = m
    var espera = 0
    var limitex=true
    var limitey=true
    var jugando =true
    var viva = true

    fun rebotar()
    {
        if(limitex==true)
        {
            moscax=moscax+2
            if(moscax>=puntero.width)
            {
                limitex=false
            }
        }
        else
        {
            moscax=moscax-2
            if(moscax<=0f)
            {
                limitex=true
            }
        }
        if(limitey==true)
        {
            moscay=moscay+2
            if(moscay>=puntero.width)
            {
                limitey=false
            }
        }
        else
        {
            moscay=moscay-2
            if(moscay<=0f)
            {
                limitey=true
            }
        }
        mosca.repintar(moscax.toInt(),moscay.toInt())
        puntero.invalidate()
    }

    override fun run() {
        super.run()

        while(jugando){
            if(viva==true)
            {
                rebotar()
            }
            else
            {
                if(espera==100)
                {
                    viva=true
                    moscax = Random.nextInt(0,puntero.width).toFloat()
                    moscay = Random.nextInt(0,puntero.width).toFloat()
                    mosca.repintar(moscax.toInt(),moscax.toInt())
                    espera=0
                }
                else
                {
                    espera++
                    puntero.invalidate()
                }
            }
            sleep(2)
        }
    }
}

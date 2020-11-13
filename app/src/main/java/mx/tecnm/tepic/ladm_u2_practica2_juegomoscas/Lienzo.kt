package mx.tecnm.tepic.ladm_u2_practica2_juegomoscas

import android.graphics.Canvas
import android.graphics.Paint
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import java.lang.Exception
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class Lienzo (p:MainActivity):View(p)
{
    //VARIABLES PARA LAS MOSCAS
    var rposition = Random
    var m1x= rposition.nextInt(900).toFloat()
    var m1y=rposition.nextInt(1500).toFloat()
    var m2x=rposition.nextInt(900).toFloat()
    var m2y=rposition.nextInt(1500).toFloat()
    var m3x=rposition.nextInt(900).toFloat()
    var m3y=rposition.nextInt(1500).toFloat()
    var m4x=rposition.nextInt(900).toFloat()
    var m4y=rposition.nextInt(1500).toFloat()
    var incX=5
    var incY=5

    //LAS MOSCAS
    var mosca1 = Mosca(this,m1x,m1y,R.drawable.fly)
    var mosca2 = Mosca(this,m2x,m2y,R.drawable.fly)
    var mosca3 = Mosca(this,m3x,m3y,R.drawable.fly)
    var mosca4 = Mosca(this,m4x,m4y,R.drawable.fly)

    //RASTROS DE LAS MOSCAS
    var rastro1=Mosca(this,-1000f,-1000f,R.drawable.splash)//A esa distancia, se dibujan pero no se ven en la pantalla
    var rastro2=Mosca(this,-1000f,-1000f,R.drawable.splash)//A esa distancia, se dibujan pero no se ven en la pantalla
    var rastro3=Mosca(this,-1000f,-1000f,R.drawable.splash)//A esa distancia, se dibujan pero no se ven en la pantalla
    var rastro4=Mosca(this,-1000f,-1000f,R.drawable.splash)//A esa distancia, se dibujan pero no se ven en la pantalla

    //HILOS DE LAS MOSCAS
    var hiloMosca1 = Hilo(this,mosca1)
    var hiloMosca2 = Hilo(this,mosca2)
    var hiloMosca3 = Hilo(this,mosca3)
    var hiloMosca4 = Hilo(this,mosca4)

    //CONTROLES
    var puntaje = "Moscas Eliminadas = "
    var puntos = 1
    var inicio=false
    var bplay = Mosca(this,0f,0f,R.drawable.bplay)
    var p = Paint()
    var tiempoRestante:Int=0
    var moscas = Random.nextInt(80,100)


    //VARIABLES DEL TIMER
    var contadorTimer=0
    val LAPSO=1000
    val TIEMPOTOTAL=9999999 //Para que siga corriendo hasta que se mande terminar el proceso

    //TIMER
    var timer =object: CountDownTimer(TIEMPOTOTAL.toLong(),LAPSO.toLong()){
        override fun onTick(p0: Long)
        {
           contadorTimer++
        }
        override fun onFinish()
        {
            start()
        }
    }

    override fun onDraw(c: Canvas) {
        try
        {
            super.onDraw(c)
            c.drawARGB(108, 217, 181, 167)
            bplay.pintar(c)
            p.textSize=60f
            c.drawText(puntaje,300f,50f,p)

            //Dibujar las moscas
            mosca1.pintar(c)
            mosca2.pintar(c)
            mosca3.pintar(c)
            mosca4.pintar(c)

            //TIEMPO DE ESPERA DEL RASTRO
            //Repintamos las manchas para que desaparezcan de la pantalla
            desaparecer()

            rastro1.pintar(c)
            rastro2.pintar(c)
            rastro3.pintar(c)
            rastro4.pintar(c)

            joins(1)

            //TIMER
            timer.onTick(1)//Se le indica que debe esperar
            tiempoRestante=60-contadorTimer/50 //60 segundos menos el tiempo transcurrido en el timer
            c.drawText("Tiempo Restante = "+tiempoRestante,300f,100f,p)
            if(tiempoRestante<=0)
            {
                finalizar()
                c.drawText("Tiempo Restante = "+tiempoRestante,300f,100f,p)
                if(puntos>=moscas)
                {
                    c.drawText("¡GANASTE!",400f,1200f,p)
                }
                else
                {
                    c.drawText("¡PERDISTE!",400f,1200f,p)
                }
            }

            //INICIAR EL JUEGUITO
            if(inicio==true)
            {
                try
                {
                    hiloMosca1.start()
                    hiloMosca2.start()
                    hiloMosca3.start()
                    hiloMosca4.start()
                }
                catch (e:Exception)
                {
                    try
                    {
                        //Se llama al metodo con todos los joins, pasandole el tiempo (milisegudos) de espera
                        joins(2)
                    }
                    catch (e:InterruptedException){}//Esta excepción se encarga de los join para los hilos
                }
                inicio=false //Termina el juego
            }
        }
        catch(e:InterruptedException)//Esta excepción se encarga de los join para los hilos
        {
            //Se llama al metodo con todos los joins, pasandole el tiempo (milisegudos) de espera
            joins(1)
        }
    }

    //METODO PARA DESAPARECER LAS MANCHAS DE LAS MOSCAS
    fun desaparecer()
    {
        if (hiloMosca1.espera>=99)
        {
            rastro1.repintar(-1000,-1000)
        }
        if (hiloMosca2.espera>=99)
        {
            rastro2.repintar(-1000,-1000)
        }
        if (hiloMosca3.espera>=99)
        {
            rastro3.repintar(-1000,-1000)
        }
        if (hiloMosca4.espera>=99)
        {
            rastro4.repintar(-1000,-1000)
        }
    }

    //METODO PARA INVOCAR LOS JOINS DE LOS HILOS
    fun joins(tiempo:Int)
    {
        //El método join() suele utilizarse para mantener un orden en la secuencia de los hilos.
        // Así, podemos arrancar una secuencia de hilos llamando a join() para que cada uno finalice
        // en el orden que hemos marcado según la llamada a join().
        hiloMosca1.join(tiempo.toLong())
        hiloMosca2.join(tiempo.toLong())
        hiloMosca3.join(tiempo.toLong())
        hiloMosca4.join(tiempo.toLong())
    }

    //METODO PARA FINALIZAR LOS HILOS (y el juego)
    fun finalizar()
    {
        hiloMosca1.jugando=false
        hiloMosca2.jugando=false
        hiloMosca3.jugando=false
        hiloMosca4.jugando=false
        bplay.repintar(0,0)
        tiempoRestante=0
        inicio=false
    }

    //ACCIONES DEL JUEGO
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action)
        {
            MotionEvent.ACTION_DOWN ->
            {
                //BOTON PLAY PARA COMENZAR A JUGAR
                if(bplay.estaEnArea(event.x,event.y)==true)
                {
                    bplay.repintar(-100,-100)
                    inicio=true
                    tiempoRestante=60-contadorTimer/50 //60 segundos menos el tiempo transcurrido en el timer
                }

                if(mosca1.estaEnArea(event.x,event.y)==true)
                {
                    if(tiempoRestante>=0)
                    {
                        puntaje = "Moscas Eliminadas = "+puntos++
                        rastro1.repintar(event.x.toInt(),event.y.toInt())
                        hiloMosca1.viva=false
                        mosca1.repintar(2000,2000)
                    }
                }
                if(mosca2.estaEnArea(event.x,event.y)==true)
                {
                    if(tiempoRestante>=0)
                    {
                        puntaje = "Moscas Eliminadas = "+puntos++
                        rastro2.repintar(event.x.toInt(),event.y.toInt())
                        hiloMosca2.viva=false
                        mosca2.repintar(2000,2000)
                    }
                }
                if(mosca3.estaEnArea(event.x,event.y)==true)
                {
                    if(tiempoRestante>=0)
                    {
                        puntaje = "Moscas Eliminadas = "+puntos++
                        rastro3.repintar(event.x.toInt(),event.y.toInt())
                        hiloMosca3.viva=false
                        mosca3.repintar(2000,2000)
                    }
                }
                if(mosca4.estaEnArea(event.x,event.y)==true)
                {
                    if(tiempoRestante>=0)
                    {
                        puntaje = "Moscas Eliminadas = "+puntos++
                        rastro4.repintar(event.x.toInt(),event.y.toInt())
                        hiloMosca4.viva=false
                        mosca4.repintar(2000,2000)
                    }
                }
            }
            MotionEvent.ACTION_MOVE ->{

            }
            MotionEvent.ACTION_UP->{

            }
        }
        invalidate()
        return true
    }
}


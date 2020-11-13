package mx.tecnm.tepic.ladm_u2_practica2_juegomoscas

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint

class Mosca (punteroLienzo: Lienzo, posX:Float, posY:Float, nombreImagen:Int)
{
    var x = posX
    var y = posY
    var incX = 5
    var incY = 5
    var imagen = BitmapFactory.decodeResource(punteroLienzo.resources, nombreImagen)

    fun pintar(c: Canvas)
    {
        c.drawBitmap(imagen,x,y, Paint())
    }

    fun estaEnArea(toqueX:Float, toqueY:Float): Boolean{
        var x2= x + imagen.width+50
        var y2 = y + imagen.height+50

        if(toqueX>= x && toqueX<=x2)
        {
            if(toqueY>=y && toqueY<=y2)
            {
                return true
            }
        }
        return false
    }

    fun repintar(ancho:Int, alto:Int)
    {
        x= ancho.toFloat()
        y= alto.toFloat()
    }
}
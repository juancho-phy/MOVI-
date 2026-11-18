package com.example.movi.Data.Model.Util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import com.example.movi.Data.Model.AliadoUi
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PdfReportUtil {

    fun generarComprobanteCanje(
        context: Context,
        aliado: AliadoUi,
        puntosUsuario: Int
    ): File? {

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 14f
            isAntiAlias = true
        }

        val titlePaint = Paint().apply {
            color = Color.rgb(229, 0, 16)
            textSize = 22f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val subtitlePaint = Paint().apply {
            color = Color.rgb(110, 110, 120)
            textSize = 12f
            isAntiAlias = true
        }

        // Encabezado
        canvas.drawText("MOVI+ - Comprobante de Canje", 40f, 60f, titlePaint)
        canvas.drawText("Sistema de Puntos y Beneficios", 40f, 85f, subtitlePaint)

        canvas.drawLine(
            40f, 110f, 555f, 110f,
            Paint().apply {
                color = Color.BLACK
                strokeWidth = 2f
            }
        )

        // Detalles del beneficio
        val boldPaint = Paint().apply {
            color = Color.BLACK
            textSize = 16f
            isFakeBoldText = true
            isAntiAlias = true
        }

        canvas.drawText("Detalles del Beneficio:", 40f, 150f, boldPaint)
        canvas.drawText("Aliado: ${aliado.nombre}", 40f, 190f, paint)
        canvas.drawText("Oferta: ${aliado.oferta}", 40f, 220f, paint)
        canvas.drawText("Categoría: ${aliado.categoria.etiqueta}", 40f, 250f, paint)

        val redBoldPaint = Paint().apply {
            color = Color.rgb(229, 0, 16)
            textSize = 14f
            isFakeBoldText = true
            isAntiAlias = true
        }

        canvas.drawText(
            "Puntos Canjeados: -${aliado.puntos} pts",
            40f,
            280f,
            redBoldPaint
        )

        // Línea divisoria
        val grayLinePaint = Paint().apply {
            color = Color.parseColor("#CCCCCC")
            strokeWidth = 1f
        }

        canvas.drawLine(40f, 320f, 555f, 320f, grayLinePaint)

        val remainingPointsPaint = Paint(boldPaint).apply {
            textSize = 14f
        }

        canvas.drawText(
            "Puntos restantes en tu cuenta: ${puntosUsuario - aliado.puntos} pts",
            40f,
            360f,
            remainingPointsPaint
        )

        canvas.drawText(
            "¡Presenta este comprobante en el establecimiento aliado!",
            40f,
            410f,
            subtitlePaint
        )

        pdfDocument.finishPage(page)

        // Guardar archivo en caché
        val nombreArchivo = aliado.nombre.replace(" ", "_")
        val archivo = File(
            context.cacheDir,
            "Comprobante_Canje_$nombreArchivo.pdf"
        )

        return try {
            FileOutputStream(archivo).use { output ->
                pdfDocument.writeTo(output)
            }

            Toast.makeText(
                context,
                "PDF generado con éxito",
                Toast.LENGTH_SHORT
            ).show()

            archivo

        } catch (e: IOException) {
            e.printStackTrace()

            Toast.makeText(
                context,
                "Error al generar el PDF",
                Toast.LENGTH_SHORT
            ).show()

            null

        } finally {
            pdfDocument.close()
        }
    }
}
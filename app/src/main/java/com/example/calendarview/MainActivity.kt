package com.example.calendarview

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorLong
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cal = CalendarView(this)
        cal.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
        var dataprzyjazdu = 0L
        var datapowrotu = 0L
        val formatter = SimpleDateFormat("dd/MM/yyyy")

        findViewById<TextView>(R.id.textView_wyjazd).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString()
        findViewById<TextView>(R.id.textView_powrot).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString()
        findViewById<CalendarView>(R.id.kalendarz).minDate = findViewById<CalendarView>(R.id.kalendarz).date
        findViewById<CalendarView>(R.id.kalendarz).maxDate = findViewById<CalendarView>(R.id.kalendarz).date + 63113852000

        findViewById<Button>(R.id.btn_przyjazd).setOnClickListener {
            val a = findViewById<CalendarView>(R.id.kalendarz).date
            val dd = findViewById<CalendarView>(R.id.kalendarz).minDate

            if  (datapowrotu != 0L)
            {
                if (a == dd)
                {
                    dataprzyjazdu = a
                    findViewById<TextView>(R.id.textView_ERROR).text = ""
                    findViewById<TextView>(R.id.textView_wyjazd).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString()
                }

                else
                {
                    if (datapowrotu >= dataprzyjazdu)
                    {
                        if (dataprzyjazdu - a < 0)
                        {
                            findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu jest przed datą przyjazdu! Błąd!"
                        }

                        else
                        {
                            dataprzyjazdu = a
                            findViewById<TextView>(R.id.textView_ERROR).text = ""
                            findViewById<TextView>(R.id.textView_wyjazd).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString()
                        }
                    }

                    else
                    {
                        findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu jest przed datą przyjazdu! Błąd!"
                    }
                }
            }

            else
            {
                dataprzyjazdu = a
                findViewById<TextView>(R.id.textView_ERROR).text = ""
                findViewById<TextView>(R.id.textView_wyjazd).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString()
            }
        }

        val b = findViewById<CalendarView>(R.id.kalendarz)

        b.setOnDateChangeListener { calendarView, i, i2, i3 ->
            b.date = Date.UTC(i - 1900, i2, i3, 4, 4, 0)
        }

        findViewById<Button>(R.id.btn_powrot).setOnClickListener {
            val a = findViewById<CalendarView>(R.id.kalendarz).date

            if (dataprzyjazdu != 0L)
            {
                if (a - dataprzyjazdu >= 0)
                {
                    datapowrotu = a
                    findViewById<TextView>(R.id.textView_ERROR).text = ""
                    findViewById<TextView>(R.id.textView_powrot).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString()
                }
                else
                {
                    findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu jest przed datą przyjazdu! Błąd!"
                }
            }

            else
            {
                findViewById<TextView>(R.id.textView_ERROR).text = "Najpierw zatwierdź datę wyjazdu!"
            }
        }

        findViewById<Button>(R.id.btn_count).setOnClickListener {

            findViewById<TextView>(R.id.textView_count).text = ""


            if (dataprzyjazdu != 0L && datapowrotu != 0L)
            {
                if (dataprzyjazdu < datapowrotu)
                {
                    findViewById<TextView>(R.id.textView_ERROR).text = ""

                    val wynik = ((datapowrotu - dataprzyjazdu) / 86400000) + 1
                    findViewById<TextView>(R.id.textView_count).text = ""
                    findViewById<TextView>(R.id.textView_count).text = "Wyjazd trwa " + wynik + " dni"
                }

                else if (dataprzyjazdu == datapowrotu)
                {
                    findViewById<TextView>(R.id.textView_count).text = ""
                    findViewById<TextView>(R.id.textView_count).text = "Wyjazd trwa 0 dni"
                }

                else
                {
                    findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu jest przed datą przyjazdu! Błąd!"
                }
            }

            else if (dataprzyjazdu == 0L && datapowrotu == 0L)
            {
                findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu i powrotu nie została zatwierdzona! Błąd!"
            }

            else if (dataprzyjazdu == 0L && datapowrotu != 0L)
            {
                findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu nie została zatwierdzona! Błąd!"
            }

            else if (dataprzyjazdu != 0L && datapowrotu == 0L)
            {
                findViewById<TextView>(R.id.textView_ERROR).text = "Data powrotu nie została zatwierdzona! Błąd!"
            }
        }

        (findViewById<CalendarView)(R.id.kalendarz).toColor() = "purple_200";
    }
}
package com.example.calendarview

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cal = CalendarView(this) // USTAWIENIE ZMIENNEJ KALENDARZA
        cal.setDate(Calendar.getInstance().getTimeInMillis(),false,true); // POBRANIE CZASU W MILISEKUNDACH

        val formatter = SimpleDateFormat("dd/MM/yyyy") // USTAWIENIE FORMATU DATY

        var departure_date = 0L
        var date_of_return = 0L
        val data = findViewById<CalendarView>(R.id.kalendarz).date

        findViewById<TextView>(R.id.textView_wyjazd).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString() // TEXTVIEW OTRZYMUJE WARTOSC WYJAZDU (DEFAULT: DZISIEJSZA DATA)
        findViewById<TextView>(R.id.textView_powrot).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString() // TEXTVIEW OTRZYMUJE WARTOSC POWROTU (DEFAULT: DZISIEJSZA DATA)

        findViewById<CalendarView>(R.id.kalendarz).minDate = findViewById<CalendarView>(R.id.kalendarz).date // USTAWIENIE MIN. DATY NA AKTUALNY DZIEŃ
        findViewById<CalendarView>(R.id.kalendarz).maxDate = findViewById<CalendarView>(R.id.kalendarz).date + 63113852000 // USTAWIENIE MAKS. DATY O 2 LATA DO PRZODU

        findViewById<Button>(R.id.btn_przyjazd).setOnClickListener { // USTAWIENIE DATY PRZYJAZDU
            val a = findViewById<CalendarView>(R.id.kalendarz).date
            val dd = findViewById<CalendarView>(R.id.kalendarz).minDate

            if  (date_of_return != 0L) // SPRAWDZENIE: CZY ZOSTAŁA ZATWIERDZONA DATA POWROTU
            {
                if (a == dd)
                {
                    departure_date = a
                    findViewById<TextView>(R.id.textView_ERROR).text = ""
                    findViewById<TextView>(R.id.textView_wyjazd).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString()
                }

                else
                {
                    if (date_of_return >= departure_date)
                    {
                        if (date_of_return - a < 0) // ZABEZPIECZENIE: GDY DATA POWROTU JEST STARSZA OD DATY PRZYJAZDU WYRZUCA BŁĄD I NIE OBLICZA RÓŻNICY
                        {
                            findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu jest przed datą przyjazdu! Błąd!"
                        }

                        else
                        {
                            departure_date = a
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
                departure_date = a
                findViewById<TextView>(R.id.textView_ERROR).text = ""
                findViewById<TextView>(R.id.textView_wyjazd).text = formatter.format(findViewById<CalendarView>(R.id.kalendarz).date).toString()
            }
        }

        val b = findViewById<CalendarView>(R.id.kalendarz)

        b.setOnDateChangeListener { calendarView, i, i2, i3 ->
            b.date = Date.UTC(i - 1900, i2, i3, 4, 4, 0)
        }

        findViewById<Button>(R.id.btn_powrot).setOnClickListener { // USTAWIENIE DATY POWROTU
            val a = findViewById<CalendarView>(R.id.kalendarz).date

            if (departure_date != 0L) // SPRAWDZENIE: CZY ZOSTAŁA ZATWIERDZONA DATA PRZYJAZDU
            {
                if (a - departure_date >= 0)  // ZABEZPIECZENIE: GDY DATA POWROTU JEST STARSZA OD DATY PRZYJAZDU WYRZUCA BŁĄD
                {
                    date_of_return = a
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

        findViewById<Button>(R.id.btn_count).setOnClickListener { // OBLICZANIE RÓŻNICY

            findViewById<TextView>(R.id.textView_count).text = ""


            if (departure_date != 0L && date_of_return != 0L) // SPRAWDZENIE: CZY DATY ZOSTAŁY ZATWIERDZONE
            {
                if (departure_date < date_of_return) // ZABEZPIECZENIE: GDY DATA POWROTU JEST STARSZA OD DATY PRZYJAZDU WYRZUCA BŁĄD I NIE OBLICZA RÓŻNICY
                {
                    findViewById<TextView>(R.id.textView_ERROR).text = ""

                    val result = ((date_of_return - departure_date) / 86400000) + 1 // OBLICZANIE DŁUGOŚCI WYJAZDU, ZMIANA MILISEKUND NA DNI
                    findViewById<TextView>(R.id.textView_count).text = ""
                    findViewById<TextView>(R.id.textView_count).text = "Wyjazd trwa " + result + " dni"
                }

                else if (departure_date == date_of_return) // GDY DZIEN PRZYJAZDU JEST TAKI SAM JAK DATA POWROTU
                {
                    findViewById<TextView>(R.id.textView_count).text = ""
                    findViewById<TextView>(R.id.textView_count).text = "Wyjazd trwa 0 dni"
                }

                else
                {
                    findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu jest przed datą przyjazdu! Błąd!"
                }
            }

            else if (departure_date == 0L && date_of_return == 0L) // ZABECZPIECZENIE: ŻADNA DATA NIE ZOSTAŁA ZATWIERDZONA
            {
                findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu i powrotu nie została zatwierdzona! Błąd!"
            }

            else if (departure_date == 0L && date_of_return != 0L) // ZABEZPIECZENIE: DATA WYJAZDU NIE ZOSTAŁA ZATWIERDZONA
            {
                findViewById<TextView>(R.id.textView_ERROR).text = "Data wyjazdu nie została zatwierdzona! Błąd!"
            }

            else if (departure_date != 0L && date_of_return == 0L) // ZABEZPIECZENIE: DATA POWROTU NIE ZOSTAŁA ZATWIERDZONA
            {
                findViewById<TextView>(R.id.textView_ERROR).text = "Data powrotu nie została zatwierdzona! Błąd!"
            }
        }
    }
}
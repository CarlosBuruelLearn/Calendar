package buruel.carlos.mx.calendario;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity
	extends AppCompatActivity
	implements View.OnClickListener
{
	String tag = "Main";
	Button currentMonth;
	ImageView prevMonth;
	ImageView nextMonth;
	GridView calendarView;
	GridCellAdapter adapter;
	Calendar _calendar;
	int month, year;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH);
		year = _calendar.get(Calendar.YEAR);

		prevMonth = (ImageView) findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (Button) findViewById(R.id.currentMonth);
		currentMonth.setText(_calendar.getTime().toString());

		nextMonth = (ImageView) findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) findViewById(R.id.calendar);

		adapter = new GridCellAdapter(this, month, year);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	@Override
	public void onClick(View vista)
	{
		//region mes anterior
		if( vista == prevMonth )
		{
			if( month<= 1 )
			{
				month = 11;
				year--;
			}
			else
			{
				month--;
			}

			Log.d(tag, "Before 1 MONTH " + "Month: " + month + " " + "Year: " + year);
			adapter = new GridCellAdapter(this, month, year);
			_calendar.set(year, month, _calendar.get(Calendar.DAY_OF_MONTH));
			currentMonth.setText(_calendar.getTime().toString());

			adapter.notifyDataSetChanged();
			calendarView.setAdapter(adapter);
		}
		//endregion
		//region mes siguiente
		if( vista == nextMonth )
		{
			if( month >= 11 )
			{
				month = 0;
				year++;
			}
			else
			{
				month++;
			}
			Log.d(tag, "After 1 MONTH " + "Month: " + month + " " + "Year: " + year);
			adapter = new GridCellAdapter(this, month, year);
			_calendar.set(year, month, _calendar.get(Calendar.DAY_OF_MONTH));
			currentMonth.setText(_calendar.getTime().toString());
			adapter.notifyDataSetChanged();
			calendarView.setAdapter(adapter);
		}
		//endregion
	}
}
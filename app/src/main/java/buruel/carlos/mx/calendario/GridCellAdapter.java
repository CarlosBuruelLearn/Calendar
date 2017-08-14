package buruel.carlos.mx.calendario;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


class GridCellAdapter
	extends BaseAdapter
	implements View.OnClickListener
{
	private String tag = "GridCellAdapter";
	private Context _context;
	private List<String> list;
	private String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private int month, year;
	private int currentDayOfMonth, trailingSpaces;


	GridCellAdapter(Context _context, int month, int year)
	{
		super();
		this._context = _context;
		this.month = month;
		this.year = year;
		list = new ArrayList<>();

		Log.d(tag, "Month: " + month + " Year: " + year);
		Calendar calendar = Calendar.getInstance();
		currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		//pendiente funcion de impresion
		printMonth(month, year);
	}

	private void printMonth(int mm, int yy)
	{
		int daysInMonth;
		int daysInPrevMonth;
		int prevMonth;
		int prevYear;
		int nextMonth;
		int nextYear;

		GregorianCalendar cal = new GregorianCalendar(yy, mm, currentDayOfMonth);

		//Days in current month
		daysInMonth = daysOfMonth[mm];
		if(mm == 11)
		{
			prevMonth = 10;
			daysInPrevMonth = daysOfMonth[prevMonth];
			nextMonth = 0;
			prevYear = yy;
			nextYear = yy +1;
		}
		else if( mm == 0 )
		{
			prevMonth = 11;
			prevYear = yy - 1;
			nextYear = yy;
			daysInPrevMonth = daysOfMonth[prevMonth];
			nextMonth = 1;
		}
		else
		{
			prevMonth = mm - 1;
			nextMonth = mm + 1;
			nextYear = yy;
			prevYear = yy;
			daysInPrevMonth = daysOfMonth[prevMonth];
		}

		//Si es año biciesto y es febrero, sumar el maldito dia
		if( cal.isLeapYear( cal.get(Calendar.YEAR) ) && mm == 1 )
		{
			++daysInMonth;
		}

		//Modificamos el calendario a dia primero para ver si habra dias antes
		cal = new GregorianCalendar(yy, mm, 1);
		trailingSpaces = cal.get(Calendar.DAY_OF_WEEK) - 1;

		for( int i = 0; i < trailingSpaces; i++ )
		{
			list.add(String.valueOf( (daysInPrevMonth - trailingSpaces + 1)+ i) + "-GREY-" + months[prevMonth] + "-" + prevYear);
		}

		for( int i = 1; i <= daysInMonth; i++ )
		{
			list.add(String.valueOf(i) + "-WHITE-" + months[mm] + "-" + yy);
		}

		for( int  i = 0; i < list.size() % 7; i++ )
		{
			Log.d(tag, "NEXT MONTH:= " + months[nextMonth]);
			list.add(String.valueOf(i+1) + "-GREY-" + months[nextMonth] + "-" + nextYear);
		}
	}

	//region metodo click
	@Override
	public void onClick(View vista)
	{
		String date_month_year = (String) vista.getTag();
		Toast.makeText(_context, date_month_year, Toast.LENGTH_LONG).show();
	}
	//endregion

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Log.d(tag, "getView ...");
		View row = convertView;
		if(row == null)
		{
			Log.d(tag, "Starting XML Row Inflation ... ");
			LayoutInflater inflater =
				(LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.gridcell, parent, false);
			Log.d(tag, "Successfully completed XML Row Inflation!");
		}

		Button gridcell = row.findViewById(R.id.gridcell);
		gridcell.setOnClickListener(this);

		Log.d(tag, "Current Day: " + currentDayOfMonth);
		String[] day_color = list.get(position).split("-");
		gridcell.setText(day_color[0]);
		gridcell.setTag(day_color[0] + "-" + day_color[2] + "-" + day_color[3]);

		if( day_color[1].equals("GREY") )
		{
			gridcell.setTextColor(Color.TRANSPARENT);
		}
		if( day_color[1].equals("WHITE") )
		{
			gridcell.setTextColor(Color.WHITE);
		}
		if( position == currentDayOfMonth + trailingSpaces -1 )
		{
			gridcell.setTextColor(Color.BLUE);
		}
		return row;
	}
}
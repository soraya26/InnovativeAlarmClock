package com.iac.innovativealarmclock;

import android.text.format.Time;

public class Alarm {

	// Basic alarm information
	private long timeNextOccurance;
	private boolean isSet;
	private long ID;


	// Alarm repetition information
	private boolean repeats;
	//TODO: support for repetition details

	// User-set alarm identifiers
	private String name;
	
	// Default values
	private static final String default_time_string = "7:00";
	private static final boolean default_isSet = true;
	private static final String default_name = "";

	// Constructors

	public Alarm()
	{
		timeNextOccurance = -1;
		isSet = false;
		repeats = false;
		name = "";
		ID = -1;
	}
	
	// Getters and setters

	public boolean getIsSet()
	{
		return isSet;
	}

	public long getNextOccurance()
	{
		return timeNextOccurance;
	}

	public boolean getRepeats()
	{
		return repeats;
	}

	public String getName()
	{
		return name;
	}

	public boolean inDB()
	{
		return (ID != -1);
	}

	public long getID()
	{
		return ID;
	}

	public void set(boolean setVal)
	{
		isSet = setVal;
	}

	public void setNextOccurance(long time)
	{
		timeNextOccurance = time;
	}

	public void setNextOccurance(String time)
	{
		String[] pieces = time.split(":");
		int hour = Integer.parseInt(pieces[0]);
		int minute = Integer.parseInt(pieces[1]);
		Time now = new Time();
		now.setToNow();
		Time todayTime = new Time(now);
		todayTime.hour = hour;
		todayTime.minute = minute;
		todayTime.second = 0;
		timeNextOccurance = todayTime.toMillis(false);
		
		setToNextTimeOccurance();
	}

	public void setName(String aName)
	{
		name = aName;
	}

	public void setID(long aID)
	{
		ID = aID;
	}
	
	// Methods
	
	public String toString()
	{
		return getAlarmSummary();
	}

	public String getAlarmSummary()
	{
		Time t = new Time();
		t.set(timeNextOccurance);
		String readable_time = t.format("%H:%M");

		if (name.length() > 0)
		{
			readable_time += " (" + name + ')';
		}
		if (isSet)
		{
			readable_time += " (Enabled)";
		}
		
		return readable_time;
	}
	
	public String getTimeString()
	{
		Time t = new Time();
		t.set(timeNextOccurance);
		
		return t.format("%H:%M");
	}
	
	public boolean equals(Alarm other)
	{
		if (other == null)
		{
			return false;
		}
		return this.getID() == other.getID();
	}

	public void setToNextTimeOccurance()
	{
		Time now = new Time();
		now.setToNow();
		Time currentTime = new Time();
		currentTime.set(timeNextOccurance);
		if (!currentTime.after(now))
		{
			int hour = currentTime.hour;
			int minute = currentTime.minute;
			currentTime = new Time(now);
			currentTime.hour = hour;
			currentTime.minute = minute;
			timeNextOccurance = currentTime.toMillis(false);
			if (!currentTime.after(now))
			{
				timeNextOccurance += (24*60*60*1000);
			}
		}
	}
	
	public void setDefaultValues()
	{
		setNextOccurance(default_time_string);
		isSet = default_isSet;
		name = default_name;
	}

}

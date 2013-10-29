/*
  Filename: AlarmClock.ino
  Purpose: To create a wristband that uses BLE to communicate with a smartphone to track sleep and intelligently wake up a user.
  Author: Jay F. Cady (October 2013)
*/

#include <SPI.h>
#include <boards.h>
#include <ble_shield.h>
#include <services.h>

/**************************************************************/
/********************   Global Variables  *********************/
/**************************************************************/
bool sleeping = false;
bool alarmOn = false;
bool vibrateOn = false;
bool buzzerOn = false;

bool useVibration = true;
bool useBuzzer = true;
bool useTracking = true;

bool needFlashUpdate = false; //Tells us whether we need to update flash or not
unsigned long nextFlashUpdate = 0; //Check to see if we need to update flash when this time is reached

unsigned long nextTrackingUpdate = 0;
unsigned long nextVibrateUpdate = 0;
unsigned long nextBuzzerUpdate = 0;

/**************************************************************/
/******************   Function Prototypes  ********************/
/**************************************************************/
/*Main Functions*/
void setup(void);             //Initialization function
void loop(void);              //Main function
void loadFlash(void);         //Loads variables from flash into RAM

/*Maintainence Functions*/
void processFlash(void);
void updateFlash(void);  //Commits RAM variables to flash

/*BLE Input*/
void checkInput(void); //Checks BLE for user input

/*Alarm Functions*/
void initAlarm(void); //Initialize alarm state

void vibrateOn(void);
void vibrateOff(void);
void toggleVibrate(void);

void buzzerOn(void);
void buzzerOff(void);
void toggleBuzzer(void);

void updatePreferences(byte prefs);

/*System Functions*/
void updateSystem(void); //Updates alarm components and tracking as necessary

/**************************************************************/
/*********************   Main Functions  **********************/
/**************************************************************/
void setup()
{
  #ifdef DEBUG
    Serial.begin(115200); //For debugging
  #endif
  
  // Default pins set to 9 and 8 for REQN and RDYN
  // Set your REQN and RDYN here before ble_begin() if you need
  //ble_set_pins(3, 2);
  
  ble_begin(); //Init BLE library
  DEBUG_PRINTLN("System Initialized");
}

void loop()
{
  checkInput();
  updateSystem();
  processFlash();
  
  // Allow BLE Shield to send/receive data
  ble_do_events();  
}
void loadFlash()
{
  useVibration = EEPROM.read(VIBRATE_STATE_LOCATION);
  useBuzzer = EEPROM.read(BUZZER_STATE_LOCATION);
  useTracking = EEPROM.read(TRACKING_STATE_LOCATION);
}

/**************************************************************/
/****************   Maintainence Functions  *******************/
/**************************************************************/
void processFlash()
{
  if (millis() > nextFlashUpdate)
  {
    DEBUG_PRINTLN("Check For Flash Update");
    
    //Only update if we need it (saves writing every times)
    if (needFlashUpdate)
    {
      DEBUG_PRINTLN("Flash Update");
      
      updateFlash();
    }
    nextFlashUpdate = millis() + FLASH_UPDATE_INTERVAL;
  }
}
void updateFlash()
{
  EEPROM.write(VIBRATE_STATE_LOCATION, useVibration);
  EEPROM.write(BUZZER_STATE_LOCATION, useBuzzer);
  EEPROM.write(TRACKING_STATE_LOCATION, useTracking);
  
  needFlashUpdate = false;
}

/**************************************************************/
/************************  BLE Input  *************************/
/**************************************************************/
void checkInput()
{
  //Check if BLE data is available
  if (ble_available())
  {
    byte command = ble_read(); //Get BLE command
    DEBUG_PRINT("Got BLE Command: ");
    DEBUG_PRINTLN(command);
    
    switch (command)
    {
      case START_SLEEP:
      {
        DEBUG_PRINTLN("Sleep Started.");
        sleeping = true;
        startTracking();
      }
      
      case STOP_SLEEP:
      {
        DEBUG_PRINTLN("Sleep Stopped.");
        sleeping = false;
        stopTracking();
      }
      
      case ALARM_ON:
      {
        DEBUG_PRINTLN("Alarm On.");
        alarmOn = true;
        initAlarm();
      }
      break;
      
      case ALARM_OFF:
      {
        DEBUG_PRINTLN("Alarm Off.");
        alarmOn = false;
        vibrateOff();
        soundOff();
      }
      break;
      
      case UPDATE_PREFS
      {
        DEBUG_PRINTLN("Updating Preferences.");
        unsigned long timeout = millis() + READ_TIMEOUT;
        while (millis() < timeout)
        {
          if (ble_available())
          {
            byte prefs = ble_read(); //Get preferences
            updatePreferences(prefs);
            break;
          }
        }
      }
      break;
    }
  }
}

/**************************************************************/
/********************   Alarm Functions  **********************/
/**************************************************************/
void initAlarm()
{
  
}
void vibrateOn()
{
  vibrateOn = true;
  
  //TODO: Vibrate on stuff
}
void vibrateOff()
{
  vibrateOff = false;
  
  //TODO: Vibrate off stuff
}
void toggleVibrate()
{
  if (vibrateOn)
  {
    vibrateOff();
  }
  else
  {
    vibrateOn();
  }
}
void buzzerOn()
{
  buzzerOn = true;
  
  //TODO: Buzzer on stuff
}
void buzzerOff()
{
  buzzerOff = false;
  
  //TODO: Buzzer off stuff
}
void toggleBuzzer()
{
  if (buzzerOn)
  {
    buzzerOff();
  }
  else
  {
    buzzerOn();
  }
}
void updatePreferences(byte prefs)
{
  /*Preferences are in a bitmap of this format:
    Bit    Value
    0      Vibrate On/Off
    1      Buzzer On/Off
    2      Tracking On/Off
    3      UNUSED
    4      UNUSED
    5      UNUSED
    6      UNUSED
    7      UNUSED
  */
  if (prefs &= 0b1) //Vibrate
  {
    useVibration = true;
  }
  else
  {
    useVibration = false;
  }
  
  if (prefs &= 0b10) //Buzzer
  {
    useBuzzer = true;
  }
  else
  {
    useBuzzer = false;
  }
  
  if (prefs &= 0b100) //Tracking
  {
    useTracking = true;
  }
  else
  {
    useTracking = false;
  }
  
  needFlashUpdate = true;
}

/**************************************************************/
/*******************   System Functions  **********************/
/**************************************************************/
void updateSystem()
{
  if (sleeping)
  {
    if (useTracking)
    {
      if (millis() > nextTrackingUpdate)
      {
        //TODO: Sleep tracking stuff
        nextTrackingUpdate = millis() + TRACKING_UPDATE_INTERVAL;
      }
    }
  }
  if (alarmOn)
  {
    if (useVibration)
    {
      if (millis() > nextVibrateUpdate)
      {
        toggleVibrate();
        nextVibrateUpdate = millis() + VIBRATE_UPDATE_INTERVAL;
      }
    }
    if (useBuzzer)
    {
      if (millis() > nextBuzzerUpdate)
      {
        toggleBuzzer();
        nextBuzzerUpdate = millis() + BUZZER_UPDATE_INTERVAL;
      }
    }
  }
}

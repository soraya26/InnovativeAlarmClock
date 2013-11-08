/*Debug Defines*/
#define DEBUG //Comment this to turn off debugging

#ifdef DEBUG //This is used to make debug statements easier
  #define DEBUG_PRINT(x)  Serial.print(x)
  #define DEBUG_PRINTLN(x)  Serial.println(x)
#else
  #define DEBUG_PRINT(x)
  #define DEBUG_PRINTLN(x)
#endif

/*Command Defines*/
#define START_SLEEP 
#define STOP_SLEEP 
#define ALARM_ON 
#define ALARM_OFF 
#define UPDATE_PREFS 

/*Alarm Defines*/
#define VIBRATE_UPDATE_INTERVAL 5000 //Time between vibrate toggle (ms)
#define BUZZER_UPDATE_INTERVAL 5000 //Time between buzzer toggle (ms)

/*Maintenance Defines*/
#define FLASH_UPDATE_INTERVAL 30000 //Frequency to check to update flash (ms)

/*Memory Location Defines*/
#define VIBRATE_STATE_LOCATION 0
#define BUZZER_STATE_LOCATION 1
#define TRACKING_STATE_LOCATION 2

/*Misc Defines*/
#define READ_TIMEOUT 50 //Max time to wait for next byte to be received

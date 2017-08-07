
#include <SoftwareSerial.h> 
#include <SparkFunESP8266WiFi.h>

//////////////////////////////
// WiFi Network Definitions //
//////////////////////////////
// Replace these two character strings with the name and
// password of your WiFi network.
const char mySSID[] = "theWave";
const char myPSK[] = "zicl8928";

//////////////////////////////
// ESP8266Server definition //
//////////////////////////////
// server object used towards the end of the demo.
// (This is only global because it's called in both setup()
// and loop()).
ESP8266Server server = ESP8266Server(80);

// while the AT connection is active, the serial port between the pc and the arduino is occuipied.
// You can manipluate the data on arduino, but to display on the serial monitor you need to exit the AT mode
char Data[100];
char RAW[3];
int INDEX;
char Value = '-';

int beaconNotFoundIndicatorPin = 10;     // BLUE
int beaconFoundIndicatorPin = 11;        // GREEN
// pin 12 multiplexed for both purposes
int errorIndicationPin = 12;              // RED
int objectNotInShelfIndicatorPin = 12;    // RED
// pin 13
int objectInShelfIndicatorPin = 13;       // GREEN

typedef enum {LOC_UNKNOWN, IN_LOCATION, NOT_IN_LOCATION} location_type; 
typedef enum {NO_ACTION, SET_OBJECT_IN_LOCATION, SET_OBJECT_LEFT_LOCATION} trigger_type;
location_type location = LOC_UNKNOWN;
trigger_type trigger = NO_ACTION;
unsigned long beaconNearingDebounceCounter = 0;
unsigned long beaconLeavingDebounceCounter = 0;

unsigned long previousMillis = 0;   
const long interval = 30000;  // interval at which to awitch the connection (milliseconds), at least 10 seconds
typedef enum {START_FINDING_MAC, WAIT_AT, WAIT_MAC, WAIT_EXIT, CHECK_MAC_AND_SET_BOARD_ID, END_FINDING_MAC} state_type;
state_type state = START_FINDING_MAC;
int boardID = 0;

void setup() {
  pinMode(beaconNotFoundIndicatorPin, OUTPUT);
  pinMode(beaconFoundIndicatorPin, OUTPUT);
  pinMode(objectNotInShelfIndicatorPin, OUTPUT);
  pinMode(objectInShelfIndicatorPin, OUTPUT);
  
  Serial.begin(115200);    //Initiate the Serial comm
  state = START_FINDING_MAC;
  
  delay(3000);
  //serialTrigger(F("Press any key to begin."));

  // initializeESP8266() verifies communication with the WiFi
  // shield, and sets it up.
  initializeESP8266();

  // connectESP8266() connects to the defined WiFi network.
  connectESP8266();

  // displayConnectInfo prints the Shield's local IP
  // and the network it's connected to.
  displayConnectInfo();

#if 0
  Serial.print("+"); 
  Serial.print("+"); 
  Serial.print("+");   // Enter the AT mode
  delay(500); // Slow down and wait for connectin establishment
#endif  
 }

void loop() {
  if (state != END_FINDING_MAC) {
    mac_loop ();
  }
  else {
    rssi_loop ();
  }
}


void rssi_loop ()
{
  trigger = NO_ACTION;
  unsigned long currentMillis = millis();

  if (isBeaconFoundNearby () == true)
  {
    digitalWrite(beaconFoundIndicatorPin, HIGH);
    delay(1500);
    digitalWrite(beaconFoundIndicatorPin, LOW);
    delay(1500);
      
    beaconNearingDebounceCounter++; 
  }
  else
  {
    digitalWrite(beaconNotFoundIndicatorPin, HIGH);
    delay(1500);
    digitalWrite(beaconNotFoundIndicatorPin, LOW);
    delay(1500);
    
    beaconLeavingDebounceCounter++;
  }

  
  if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;

    if (beaconNearingDebounceCounter >= 4)
      trigger = SET_OBJECT_IN_LOCATION;
    else
      trigger = SET_OBJECT_LEFT_LOCATION;

    beaconNearingDebounceCounter = 0;
    beaconLeavingDebounceCounter = 0;   
  }
  
  switch (location)
  {
    case LOC_UNKNOWN: 
    {
      if (trigger == SET_OBJECT_IN_LOCATION)
      {
        location = IN_LOCATION;
        updateDeviceInLocation ();
      }
      if (trigger == SET_OBJECT_LEFT_LOCATION)
      {
        location = NOT_IN_LOCATION;
        updateDeviceLeftLocation ();
      }     
      break;
    }
    case IN_LOCATION: 
    {
     if (trigger == SET_OBJECT_IN_LOCATION)
      {
        // Nothing to do
      }
      if (trigger == SET_OBJECT_LEFT_LOCATION)
      {
        location = NOT_IN_LOCATION;
        updateDeviceLeftLocation ();
      }     
      break;
    }
    case NOT_IN_LOCATION: 
    {
     if (trigger == SET_OBJECT_IN_LOCATION)
      {
        location = IN_LOCATION;
        updateDeviceInLocation ();
      }
      if (trigger == SET_OBJECT_LEFT_LOCATION)
      {
        // Nothing to do
      }     
      break;
    }    
    default:
      break;
  }
}

#if 0
boolean isBeaconFoundNearby() {
  // read the input on analog pin 0:
  int sensorValue = analogRead(A0);
  // Convert the analog reading (which goes from 0 - 1023) to a voltage (0 - 5V):
  float voltage = sensorValue * (5.0 / 1023.0);
  // print out the value you read:
  //Serial.println(voltage);
  if (voltage < 4)
    return true;
  else
    return false;
}
#endif

boolean isBeaconFoundNearby (){
    Serial.println("AT+RSSI=?"); // Ask about the RSSI
    for(int x=0 ; Serial.available() > 0 ; x++ ){    // get the Enter AT mode words
    delay(20); // Slow down for accuracy
    Data[x] = Serial.read(); // Read and store Data Byte by Byte
    if (Data[x] == Value ) // Look for the elemnt of the array that have "-" that's the start of the RSSI value
      {
        INDEX=x;
      }
    }
    
    //Serial.println("AT+EXIT");    
    RAW[0] = Data[INDEX]; // Copy the RSSI value to RAW Char array
    RAW[1] = Data[INDEX+1]; 
    RAW[2] = Data[INDEX+2]; 
    RAW[3] = Data[INDEX+3];
    int RSSI = atoi(RAW);  //Convert the Array to an integer
    //Serial.println(RSSI);
    //delay(200); // Give the program time to process. Serial Comm sucks
    double D = exp(((RSSI+60)/-10)); //Calculate the distance but this is VERY inaccurate
    //Serial.println(D);
    if (RSSI != 0) // If the device gets far, excute the following>>
    {
      return true;
    }
    else
    {
      return false;
    }
}

/* ---------  */


void updateDeviceInLocation()
{

  digitalWrite(objectInShelfIndicatorPin, HIGH);
  delay(500);
  digitalWrite(objectInShelfIndicatorPin, LOW);
  delay(500);

  //return;
  
  const char destServer[] = "192.168.43.169";
  String buffer = "GET /et1544/updateDeviceInLocation.php?";
  
  // To use the ESP8266 as a TCP client, use the 
  // ESP8266Client class. First, create an object:
  ESP8266Client client;

  // ESP8266Client connect([server], [port]) is used to 
  // connect to a server (const char * or IPAddress) on
  // a specified port.
  // Returns: 1 on success, 2 on already connected,
  // negative on fail (-1=TIMEOUT, -3=FAIL).
  int retVal = client.connect(destServer, 10);
  if (retVal <= 0)
  {
    //Serial.println(F("Failed to connect to server."));
    return;
  }

  // print and write can be used to send data to a connected
  // client connection.
  //client.print(httpRequest);
  buffer += "deviceID=";
  buffer += String(1); 
  buffer += "&locationID=";
  buffer += String(boardID);
  buffer += "&inUse=";
  buffer += String(boardID==1?0:1); // board ID 1 is the DECK. So when in DECK, the device is NOT IN USE
  buffer += " HTTP/1.1";  
  //client.println("GET /et1544/updateDeviceInLocation.php?deviceID=1&locationID= HTTP/1.1");
  blinkBlueLED(boardID);
  client.println(buffer);
  client.println("Host: 192.168.43.169");
  client.println("Connection: close");
  client.println();
  
  // available() will return the number of characters
  // currently in the receive buffer.
  while (client.available()) {
    //Serial.write(client.read()); // read() gets the FIFO char
    client.read();
  }
    
  // connected() is a boolean return value - 1 if the 
  // connection is active, 0 if it's closed.
  if (client.connected())
    client.stop(); // stop() closes a TCP connection.
}


void updateDeviceLeftLocation()
{

  digitalWrite(objectNotInShelfIndicatorPin, HIGH);
  delay(500);
  digitalWrite(objectNotInShelfIndicatorPin, LOW);
  delay(500);  

  //return;
  
  const char destServer[] = "192.168.43.169";
  String buffer = "GET /et1544/updateDeviceLeftLocation.php?";
    
  // To use the ESP8266 as a TCP client, use the 
  // ESP8266Client class. First, create an object:
  ESP8266Client client;

  // ESP8266Client connect([server], [port]) is used to 
  // connect to a server (const char * or IPAddress) on
  // a specified port.
  // Returns: 1 on success, 2 on already connected,
  // negative on fail (-1=TIMEOUT, -3=FAIL).
  int retVal = client.connect(destServer, 10);
  if (retVal <= 0)
  {
    //Serial.println(F("Failed to connect to server."));
    return;
  }

  // print and write can be used to send data to a connected
  // client connection.
  //client.print(httpRequest);
  buffer += "deviceID=";
  buffer += String(1); 
  buffer += "&locationID=";
  buffer += String(boardID);
  buffer += "&inUse=";
  buffer += String(boardID==1?0:1); // board ID 1 is the DECK. So when in DECK, the device is NOT IN USE
  buffer += " HTTP/1.1";
  //client.println("GET /et1544/updateDeviceLeftLocation.php?receiverID=2 HTTP/1.1");
  blinkBlueLED(boardID);
  client.println(buffer);  
  client.println("Host: 192.168.43.169");
  client.println("Connection: close");
  client.println();
  
  // available() will return the number of characters
  // currently in the receive buffer.
  while (client.available()) {
    //Serial.write(client.read()); // read() gets the FIFO char
    client.read();
  }
    
  // connected() is a boolean return value - 1 if the 
  // connection is active, 0 if it's closed.
  if (client.connected())
    client.stop(); // stop() closes a TCP connection.
}

void initializeESP8266()
{
  // esp8266.begin() verifies that the ESP8266 is operational
  // and sets it up for the rest of the sketch.
  // It returns either true or false -- indicating whether
  // communication was successul or not.
  // true
  int test = esp8266.begin();
  if (test != true)
  {
    //Serial.println(F("Error talking to ESP8266."));
    //errorLoop(test);
    errorLoop(1);
  }
  //Serial.println(F("ESP8266 Shield Present"));
}

void connectESP8266()
{
  // The ESP8266 can be set to one of three modes:
  //  1 - ESP8266_MODE_STA - Station only
  //  2 - ESP8266_MODE_AP - Access point only
  //  3 - ESP8266_MODE_STAAP - Station/AP combo
  // Use esp8266.getMode() to check which mode it's in:
  int retVal = esp8266.getMode();
  if (retVal != ESP8266_MODE_STA)
  { // If it's not in station mode.
    // Use esp8266.setMode([mode]) to set it to a specified
    // mode.
    retVal = esp8266.setMode(ESP8266_MODE_STA);
    if (retVal < 0)
    {
      //Serial.println(F("Error setting mode."));
      //errorLoop(retVal);
      errorLoop(2);
    }
  }
  //Serial.println(F("Mode set to station"));

  // esp8266.status() indicates the ESP8266's WiFi connect
  // status.
  // A return value of 1 indicates the device is already
  // connected. 0 indicates disconnected. (Negative values
  // equate to communication errors.)
  retVal = esp8266.status();
  if (retVal <= 0)
  {
    //Serial.print(F("Connecting to "));
    //Serial.println(mySSID);
    // esp8266.connect([ssid], [psk]) connects the ESP8266
    // to a network.
    // On success the connect function returns a value >0
    // On fail, the function will either return:
    //  -1: TIMEOUT - The library has a set 30s timeout
    //  -3: FAIL - Couldn't connect to network.
    retVal = esp8266.connect(mySSID, myPSK);
    if (retVal < 0)
    {
      //Serial.println(F("Error connecting"));
      //errorLoop(retVal);
      errorLoop(3);
    }
  }
}

void displayConnectInfo()
{
  char connectedSSID[24];
  memset(connectedSSID, 0, 24);
  // esp8266.getAP() can be used to check which AP the
  // ESP8266 is connected to. It returns an error code.
  // The connected AP is returned by reference as a parameter.
  int retVal = esp8266.getAP(connectedSSID);
  if (retVal > 0)
  {
    //Serial.print(F("Connected to: "));
    //Serial.println(connectedSSID);
    
    digitalWrite(objectInShelfIndicatorPin, HIGH);
    delay(500);
    digitalWrite(objectInShelfIndicatorPin, LOW);
    delay(500);
  
  }

  // esp8266.localIP returns an IPAddress variable with the
  // ESP8266's current local IP address.
  IPAddress myIP = esp8266.localIP();
  //Serial.print(F("My IP: ")); Serial.println(myIP);
}

// errorLoop prints an error code, then loops forever.
void errorLoop(int error)
{
  //Serial.print(F("Error: ")); Serial.println(error);
  //Serial.println(F("Looping forever."));
  // Looping forever
  for (;;)
  {
    for (int i=0; i< error; i++)
    {
      digitalWrite(errorIndicationPin, HIGH);
      delay(500);
      digitalWrite(errorIndicationPin, LOW);
      delay(500); 
    }
    delay (3000);
  }

  
}

// serialTrigger prints a message, then waits for something
// to come in from the serial port.
void serialTrigger(String message)
{
  Serial.println();
  Serial.println(message);
  Serial.println();
  while (!Serial.available())
    ;
  while (Serial.available())
    Serial.read();
}

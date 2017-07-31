char chr;
String buffer, mac_string, data;
bool reading = false;

void mac_loop() {
   if (state == START_FINDING_MAC) {
      start();
   } else if (state == WAIT_AT) {
      wait_at();
   } else if (state == WAIT_MAC) {
      wait_mac();
   } else if (state == WAIT_EXIT) {
      wait_exit();
   } else if (state == CHECK_MAC_AND_SET_BOARD_ID) {
      set_board_id();
   }
}

void start() {
   delay(10);
   Serial.print("+");
   Serial.print("+");
   Serial.print("+");
   data = "";
   state = WAIT_AT;
}

void wait_at() {
   while (Serial.available() > 0) {
      chr = Serial.read();
      if (chr == 'E') {
         reading = true;
         buffer = "";
      }
      if (reading) {
         buffer += chr;
      } else {
         data += chr;
      }
      if (reading && buffer == "Enter AT Mode\r\n") {
         Serial.println("AT+MAC=?");
         reading = false;
         state = WAIT_MAC;
      }
   }
}

void wait_mac() {
   while (Serial.available() > 0) {
      chr = Serial.read();
      if (chr == 'x') {
         reading = true;
         buffer = "";
      }
      if (reading) {
         buffer += chr;
      }
      if (buffer.length() == 13) {
         mac_string = buffer;
         reading = false;
         //original to EXIT AT mode and Print the MAC string
         //Serial.println("AT+EXIT");         
         //state = WAIT_EXIT;
         //modification 1 - based on MAC ID set the Board ID and leave this loop
         state = CHECK_MAC_AND_SET_BOARD_ID;
      }
   }
}

void wait_exit() {
   while (Serial.available() > 0) {
      chr = Serial.read();
      if (chr == 'O') {
         reading = true;
         buffer = "";
      }
      if (reading) {
         buffer += chr;
      }
      if (buffer == "OK\r\n") {
         reading = false;
         state = CHECK_MAC_AND_SET_BOARD_ID;
      }
   }
}

void set_board_id() {
   //Serial.println("\r\n<<<");
   //Serial.println(mac_string);
   //Serial.println(">>>\r\n");
   if (mac_string == "x883314DC751A") {
      boardID = 1;
      blinkBlueLED(6);
   }   
   else if (mac_string == "x883314DC72A7") {
      boardID = 2;
      blinkBlueLED(boardID);
       /* for (;;) {
        Serial.println (mac_string);
        Serial.println (boardID);
      } */
   }
   else if (mac_string == "x883314DC760A") {
      boardID = 3;
      blinkBlueLED(boardID);
      /* for (;;) {
        Serial.println (mac_string);
        Serial.println (boardID);
      } */     
   }
   else {
    invalidMAC_errorLoop (5);   
   }
    
   Serial.flush();
   state = END_FINDING_MAC;

  // No need to Enter the AT mode as we HAVE NOT EXITED YET
  // JUST Slow down and before handing it over
  delay(500);    
}

void blinkBlueLED(int count)
{
    for (int i=0; i< count; i++)
    {
      digitalWrite(beaconNotFoundIndicatorPin, HIGH);
      delay(500);
      digitalWrite(beaconNotFoundIndicatorPin, LOW);
      delay(500); 
    }
    delay (3000);
}

// errorLoop prints an error code, then loops forever.
void invalidMAC_errorLoop(int error)
{
  // Looping forever
  for (;;)
  {
    for (int i=0; i< error; i++)
    {
      digitalWrite(beaconNotFoundIndicatorPin, HIGH);
      delay(500);
      digitalWrite(beaconNotFoundIndicatorPin, LOW);
      delay(500); 
    }
    delay (3000);
  }

  
}



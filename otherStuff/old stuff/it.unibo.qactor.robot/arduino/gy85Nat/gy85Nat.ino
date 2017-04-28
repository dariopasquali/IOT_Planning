#include "GY_85.h"
#include <Wire.h>

GY_85 GY85;     //create the object

  int inbyte;
  int curInput = 0; 
/*
 * 0 : acceler
 * 1 : gyro
 * 2 : compass
 * 3 : temperat
 */
 
void explain(){
  Serial.println("------------------------------------------------------------");
  Serial.println("gy85Nat in it.unibo.qactor.mixin arduino/bbb"); 
  Serial.println("Updated by AN from GY85"); 
  Serial.println("Writes data in CSV format Starlino/SerialChart"); 
  Serial.println("that requires ports named COMx with x=0-9 ");
  if( curInput == 0 ){
    Serial.println(" *** ACCELEROMETER *** ");
  }else if( curInput == 1 ){
    Serial.println(" *** GYROSCOPE *** ");
  }else if( curInput == 2 ){
    Serial.println(" *** COMPASS *** ");
  }else if( curInput == 3 ){
    Serial.println(" *** TEMPERATURE *** ");
  }
  Serial.println("------------------------------------------------------------");
}

void setup()
{
    Wire.begin();
    delay(10);
    Serial.begin(9600);
    delay(10);
    GY85.init();
    delay(10);
}

void original(){
    int ax = GY85.accelerometer_x( GY85.readFromAccelerometer() );
    int ay = GY85.accelerometer_y( GY85.readFromAccelerometer() );
    int az = GY85.accelerometer_z( GY85.readFromAccelerometer() );
    
    int cx = GY85.compass_x( GY85.readFromCompass() );
    int cy = GY85.compass_y( GY85.readFromCompass() );
    int cz = GY85.compass_z( GY85.readFromCompass() );

    float gx = GY85.gyro_x( GY85.readGyro() );
    float gy = GY85.gyro_y( GY85.readGyro() );
    float gz = GY85.gyro_z( GY85.readGyro() );
    float gt = GY85.temp  ( GY85.readGyro() );
    
    Serial.print  ( "accelerometer" );
    Serial.print  ( " x:" );
    Serial.print  ( ax );
    Serial.print  ( " y:" );
    Serial.print  ( ay );
    Serial.print  ( " z:" );
    Serial.print  ( az );
    
    Serial.print  ( "  compass" );
    Serial.print  ( " x:" );
    Serial.print  ( cx );
    Serial.print  ( " y:" );
    Serial.print  ( cy );
    Serial.print  (" z:");
    Serial.print  ( cz );
    
    Serial.print  ( "  gyro" );
    Serial.print  ( " x:" );
    Serial.print  ( gx );
    Serial.print  ( " y:" );
    Serial.print  ( gy );
    Serial.print  ( " z:" );
    Serial.print  ( gz );
    Serial.print  ( " gyro temp:" );
    Serial.println( gt );  
}

void getAccelerometerDataForSerialchart(){
    int ax = GY85.accelerometer_x( GY85.readFromAccelerometer() );
    int ay = GY85.accelerometer_y( GY85.readFromAccelerometer() );
    int az = GY85.accelerometer_z( GY85.readFromAccelerometer() );
    Serial.print  ( ax );
    Serial.print  ( "," );
    Serial.print  ( ay );
    Serial.print  ( "," );
    Serial.print  ( az );
    Serial.println("");    

}

void getGyroDataForSerialchart(){
    float gx = GY85.gyro_x( GY85.readGyro() );
    float gy = GY85.gyro_y( GY85.readGyro() );
    float gz = GY85.gyro_z( GY85.readGyro() );
    Serial.print  ( gx );
    Serial.print  ( "," );
    Serial.print  ( gy );
    Serial.print  ( "," );
    Serial.print  ( gz );
    Serial.print  ( "," );
    Serial.println("");  
     
  
}

void getTemperatureDataForSerialchart(){
    float gt = GY85.temp  ( GY85.readGyro() );
    Serial.print( gt ); 
    Serial.print  (",");
    Serial.print  ( 0 );
    Serial.print  (",");
    Serial.print  ( 0 );
    Serial.println(""); 
}
void getCompassDataForSerialchart(){
    int cx = GY85.compass_x( GY85.readFromCompass() );
    int cy = GY85.compass_y( GY85.readFromCompass() );
    int cz = GY85.compass_z( GY85.readFromCompass() );

    Serial.print  ( cx );
    Serial.print  ( "," );
    Serial.print  ( cy );
    Serial.print  (",");
    Serial.print  ( cz );
    Serial.println("");  
 
}

void no3AxisData(){
    Serial.print( 0 ); 
    Serial.print  (",");
    Serial.print  ( 0 );
    Serial.print  (",");
    Serial.print  ( 0 );
    Serial.println(""); 
  
}

 int getInput(){
  inbyte = Serial.read();
  //Serial.println("*** " + String(inbyte) ); 
  if( inbyte < 0 ) return curInput;
  inbyte = inbyte - 48;
  if( inbyte >= 0 && inbyte <= 3 ){
    curInput = inbyte;
    return curInput;
  }else{
    return curInput;
  }
}

void loop()
{
  explain();
  int input = getInput();
  if( input == 0 ){
    getAccelerometerDataForSerialchart();
  }else if ( input == 1 ){
    getGyroDataForSerialchart();  
  }else if ( input == 2 ){
    getCompassDataForSerialchart();  
  }else if ( input == 3 ){
    getTemperatureDataForSerialchart();
  }else{ 
    no3AxisData();
  }
  
    delay(500);             // only read every 0,5 seconds, 10ms for 100Hz, 20ms for 50Hz
}

/*   
Scaling factors for accelerometer values :
Acceleration Limit  |   Sensitivity
----------------------------------------
2g                  |    16,384
4g                  |    8,192  
8g                  |    4,096 
16g                 |    2,048 

Scaling factors for gyro values :
Angular Velocity Limit  |   Sensitivity
----------------------------------------
250º/s                  |    131
500º/s                  |    65.5 
1000º/s                 |    32.8 
2000º/s                 |    16.4

 */

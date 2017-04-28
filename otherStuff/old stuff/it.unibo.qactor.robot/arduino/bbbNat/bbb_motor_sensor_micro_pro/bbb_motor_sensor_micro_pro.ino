//ARDUINO MICRO
#include "GY_85.h"
#include <ArduinoJson.h>
#include<Wire.h>        //For MPU8050 and GY85

#define usingGY85    true     //change to false if Arduino is connected to MPU6050
#define jsonFormat   true
#define serialFormat false  
#define gyroscope    true 
#define acceler      false
#define aceeleromData     0
#define gyroData          1
#define magnetomData      2
#define temperatureData   3

void explain(){
  Serial.println("------------------------------------------------------------");
  Serial.println("bbb_motor_sensor_micro_pro in it.unibo.qactor.robot/arduino/bbbNat"); 
  Serial.println("By AN from the Sam's original version bbb"); 
  if( usingGY85 ) explainGy85();
  else explain6050();
  Serial.println("------------------------------------------------------------");
}

void setup()
{
  Serial.begin(9600);
  setupSensors();
  if( usingGY85 ) setupGY85() ;
  else setup6050();
}

/*
 * =================================================================================
 * JSON data
 * =================================================================================
 */
//SAM specification of Json sensor data : 
//    "p":"f","t":"d","d":{"cm":10,"tm":1452263995862}

String buildJsonTempeature(int gt ){
    String ss = "{\"p\":\"t\",\"t\":\"t\",\"d\":{\"celsius\":";
    ss = ss + String(gt) ;
    ss = ss + "},\"tm\":" + millis() +"}";
    return ss;  
}
String buildJsonDistance(int r, String dir ){
    String ss = "{\"p\":\"f\",\"t\":\"d\",\"d\":{\"cm\":";
    ss = ss + String(r) ;
    ss = ss + ",\"dir\":\"" ;
    ss = ss + dir + "\"" ;
    ss = ss + "},\"tm\":" + millis() +"}";
    //testJson(ss);
    return ss; 
}
String buildJsonLine(int r ){
    String ss = "{\"p\":\"b\",\"t\":\"l\",\"d\":{\"detection\":";
    ss = ss + String(r) ;
    ss = ss + "}},\"tm\":" + millis() + "}";
    return ss;   
}

String buildJsonMagnetometer(int x, int y, int z){
    String ss = "{\"p\":\"t\",\"t\":\"m\",\"d\":{\"raw3axes\":";
    ss = ss + "{\"x\":" + x + ", \"y\":" + y + ", \"z\":" + z+ "}" ;
    ss = ss + "}}";
    return ss;    
}
/*
 * =================================================================================
 * GY85
 * =================================================================================
 */
GY_85 GY85;     //create the object
int curcx, curcy, curcz = 0;

void explainGy85(){
  Serial.println("Updated by AN from project GY85 on GITHUB"); 
}
void setupGY85()
{
    Wire.begin();
    delay(10);
    Serial.begin(9600);
    delay(10);
    GY85.init();
    delay(10);
}

void getGY85AccelerometerData(){
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

void getGY85GyroData(){
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

void getGY85TemperatureData(boolean json){
    float gt = GY85.temp  ( GY85.readGyro() );
    if( json) {
       String ss = buildJsonTempeature(gt);
       Serial.println(ss);   
    }else{
      Serial.print( gt ); 
      Serial.print  (",");
      Serial.print  ( 0 );
      Serial.print  (",");
      Serial.print  ( 0 );
      Serial.println(""); 
   }
}

void getGY85CompassData(boolean json){
    int cx = GY85.compass_x( GY85.readFromCompass() );
    int cy = GY85.compass_y( GY85.readFromCompass() );
    int cz = GY85.compass_z( GY85.readFromCompass() );

    if( json) {
      /*
      Serial.print  ( curcx );
      Serial.print  ( "," );
      Serial.print  ( curcy );
      Serial.print  (",");
      Serial.print  ( curcz );
      Serial.println(""); 
      */ 
     if( abs( cx - curcx ) > 10 || abs( cy - curcy ) > 10 || abs( cz - curcz ) > 10 ){     
        String ss = buildJsonMagnetometer(cx,cy,cz);
        Serial.println(ss); 
        curcx = cx;
        curcy = cy;
        curcz = cz;
      }
    }else{   
      Serial.print  ( cx );
      Serial.print  ( "," );
      Serial.print  ( cy );
      Serial.print  (",");
      Serial.print  ( cz );
      Serial.println("");  
    }
}

/*
 * 0 : acceler
 * 1 : gyro
 * 2 : compass
 * 3 : temperat
 */
void jobGY85(int v){
  if( v == aceeleromData )   getGY85AccelerometerData();
  else if( v == gyroData )  getGY85GyroData();
  else if( v == magnetomData )  getGY85CompassData(jsonFormat);
  else if( v == temperatureData )  getGY85TemperatureData(jsonFormat);
  else{
   getGY85AccelerometerData(); 
   getGY85GyroData();
   getGY85CompassData(serialFormat);
   getGY85TemperatureData(serialFormat);
  }
}


/*
 * =================================================================================
 * MPU6050
 * =================================================================================
 */
const int MPU_addr=0x68;  // I2C address of the MPU-6050
int16_t AcX,AcY,AcZ,Tmp,GyX,GyY,GyZ;

float sclaleAccel  = 16384.0;
float sclaleGyro   = 131.0;
float aX,aY,aZ,aTmp,agX,agY,agZ;

void explain6050(){
  Serial.println("Writes data in CSV format Starlino/SerialChart"); 
  Serial.println("that requires ports named COMx with x=0-9 ");
}
void setup6050(){
  Wire.begin();
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x6B);  // PWR_MGMT_1 register
  Wire.write(0);     // set to zero (wakes up the MPU-6050)
  Wire.endTransmission(true);
  Serial.begin(9600);
}
void initWire(){
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x3B);  // starting with register 0x3B (ACCEL_XOUT_H)
  Wire.endTransmission(false);
  Wire.requestFrom(MPU_addr,14,true);  // request a total of 14 registers
  
}
 
void readRawData(){
  AcX=Wire.read()<<8|Wire.read();  // 0x3B (ACCEL_XOUT_H) & 0x3C (ACCEL_XOUT_L)     
  AcY=Wire.read()<<8|Wire.read();  // 0x3D (ACCEL_YOUT_H) & 0x3E (ACCEL_YOUT_L)
  AcZ=Wire.read()<<8|Wire.read();  // 0x3F (ACCEL_ZOUT_H) & 0x40 (ACCEL_ZOUT_L)
  Tmp=Wire.read()<<8|Wire.read();  // 0x41 (TEMP_OUT_H) & 0x42 (TEMP_OUT_L)
  GyX=Wire.read()<<8|Wire.read();  // 0x43 (GYRO_XOUT_H) & 0x44 (GYRO_XOUT_L)
  GyY=Wire.read()<<8|Wire.read();  // 0x45 (GYRO_YOUT_H) & 0x46 (GYRO_YOUT_L)
  GyZ=Wire.read()<<8|Wire.read();  // 0x47 (GYRO_ZOUT_H) & 0x48 (GYRO_ZOUT_L)
}

void getGyroDataForSerialchart(){
 
  agX = GyX / sclaleGyro;
  agY = GyY / sclaleGyro;
  agZ = GyZ / sclaleGyro;
  
  Serial.print(agX);
  Serial.print(","); 
  Serial.print(agY);
  Serial.print(","); 
  Serial.print(agZ);
  Serial.println(""); 

}

void getAccelerometerDataForSerialchart(){
  aX = AcX / sclaleAccel;
  aY = AcY / sclaleAccel;
  aZ = AcZ / sclaleAccel;
  
  Serial.print(aX);
  Serial.print(","); 
  Serial.print(aY);
  Serial.print(","); 
  Serial.print(aZ);
  Serial.println(""); 
}

void job6050(boolean gyro){
  initWire();  //in setup has no effect
  readRawData();
  if( gyro ) 
    getGyroDataForSerialchart();
  else getAccelerometerDataForSerialchart(); 
  //delay(133);
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


/*
 * =================================================================================
 * SENSORS
 * =================================================================================
 */
#define CLOSE 20
#define MEDIUM 40
#define FAR 60

  int inbyte;
  int count = 0;
  int min = 0;
  int vl = 0;
  int vr = 0;
  
  int IN1 = 11;
  int IN2 = 10;
  int IN3 = 5;
  int IN4 = 6;
  
  //ultrasonic
  //forward  
  int us_NORTH_echo = A5;
  int us_NORTH_trig = A4;
  int current_NORTH = FAR;
   
  //line 
  int line_N = SCK;
  int line_N_detect = 0;

void move(int lw, int rw);
int read_hcsr04(int vcc, int trig, int current,String dir);

void setupSensors(){
  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);
    
  pinMode(us_NORTH_trig, OUTPUT);
  pinMode(us_NORTH_echo, INPUT);

  pinMode(line_N, INPUT);  
}

void readSonar(){
   current_NORTH = read_hcsr04(us_NORTH_trig,us_NORTH_echo,current_NORTH,"forward");   
}

int read_hcsr04(int trig, int echo, int current, String dir){
  int r=1;
  digitalWrite( trig, LOW );
  digitalWrite( trig, HIGH );
  delayMicroseconds( 10 );
  digitalWrite( trig, LOW );
  long duration = pulseIn( echo, HIGH );
  //Serial.println("read_hcsr04 " + String(duration) ); 
  if( duration > 28000 ) {
    r = -1;
  }else { 
    r = 0.017 * duration;  
  }
  //Serial.println("read_hcsr04 " + String( abs(current - r)) ); 
  //testJson( json  );              //by AN
  
  //if(r > 1 && abs(current - r) > r/3){  //by SAM:generates info only if some movement is detected
  if(r > 1 && r < 150 && abs(current - r) > 0.5){   
    //Serial.println("us " + dir + " " + String(r));  //old SAM
    String ss = buildJsonDistance( r,dir );
    Serial.println( ss );   
    current = r;
  }
  
  return current;
}

void readLine(){
  if(digitalRead(line_N) != line_N_detect){
    line_N_detect = digitalRead(line_N);
    String ss = buildJsonLine( line_N_detect );
    //Serial.println("l n " + String(line_N_detect));
    Serial.println(ss);
  }  
}



/*
 * =================================================================================
 * MOTORS
 * =================================================================================
 */
void elabInput() {
  inbyte = Serial.read(); 
  //Serial.println(inbyte); 
    if (inbyte > 0)
    { 
   //   Serial.println(inbyte);
      if(inbyte == 45){
        min = 1;
      }else{
        count = count + 1;
        if(count == 1){
          vl = inbyte - 48;
          if(min == 1){
            vl = - vl;
            min =0;
          }
        }
        if(count == 2){
          vr = inbyte - 48;
          if(min == 1){
            vr = -vr;
            min =0;
          }
           move(vl,vr);     
          count =0;
        }
      }     
    }
}

void move(int vl, int vr){
  Serial.println("move vl=" + String(vl) + " vr=" + String(vr) + " LOW=" + LOW );
  if(vl >= 0){
    digitalWrite(IN1 , LOW);
  }else{
    //vl=-vl;
    digitalWrite(IN1 , HIGH);
  }
  analogWrite(IN2, vl*28);
  
  if(vr >= 0){
    digitalWrite(IN3 , LOW);
  }else{
   // vr=-vr;
    digitalWrite(IN3 , HIGH);
  }
  analogWrite(IN4, vr*28);
  if(vr==0){
    digitalWrite(IN3, HIGH);
    digitalWrite(IN4, HIGH);
  }
    if(vl==0){
    digitalWrite(IN1, HIGH);
    digitalWrite(IN2, HIGH);
  }
}


/*
 * =================================================================================
 * MAIN
 * =================================================================================
 */
void loop()
{
   //explain();
   readSonar();  
   //readLine();
   elabInput();
   if( usingGY85 ){
      jobGY85(magnetomData);
      //jobGY85(temperatureData);      
   }
   else job6050(gyroscope);
   //delay(200); //sends 5 info/sec
   delay(500);             // only read every 0,5 seconds, 10ms for 100Hz, 20ms for 50Hz
}

/*
 * ====================================================================
 * Added by AN to test the Json library
 * ====================================================================
 */
void testJson(  String ss ){
//char json[] = "{\"sensor\":\"gps\",\"time\":1351824120,\"data\":[48.756080,2.302038]}";
// Step 1: Reserve memory space
StaticJsonBuffer<200> jsonBuffer;
// Step 2: Deserialize the JSON string
JsonObject& root = jsonBuffer.parseObject(ss);
if (!root.success())
{
  Serial.println("parseObject() failed");
  return;
}
// Step 3: Retrieve the values
Serial.println("Retrieve the values");
/*
const char* sensor    = root["sensor"];
long        time      = root["time"];
double      latitude  = root["data"][0];
double      longitude = root["data"][1];
*/ 
String        p      = root["p"];
Serial.println("p= " + p );
String dist      = root["d"];
JsonObject& distance = jsonBuffer.parseObject(dist);
int cm = distance["cm"];
Serial.println("cm= " + String(cm) );
  }

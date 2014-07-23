#define portaRele1 2
#define portaRele2 3
#define portaRele3 4
#define portaRele4 5

#define portaRele5 10
#define portaRele6 11
#define portaRele7 12
#define portaRele8 13

#define tempoDelay 1000

String str;

int c;
void setup()  
{                
  Serial.begin(9600);
  pinMode(portaRele1, OUTPUT);
  pinMode(portaRele2, OUTPUT);
  pinMode(portaRele3, OUTPUT);
  pinMode(portaRele4, OUTPUT);
  pinMode(portaRele5, OUTPUT);
  pinMode(portaRele6, OUTPUT);
  pinMode(portaRele7, OUTPUT);
  pinMode(portaRele8, OUTPUT);
  
  digitalWrite(portaRele1, HIGH);
  digitalWrite(portaRele2, HIGH);
  digitalWrite(portaRele3, HIGH);
  digitalWrite(portaRele4, HIGH);
  digitalWrite(portaRele5, HIGH);
  digitalWrite(portaRele6, HIGH);
  digitalWrite(portaRele7, HIGH);
  digitalWrite(portaRele8, HIGH);
}

void loop()                    
{
   int i=0;
   if (Serial.available() > 0) {
     str = Serial.readStringUntil('\n');
          
     if (str == "01") {
       abreRele(portaRele1);
     } else if (str == "02") {
       abreRele(portaRele2);       
     } else if (str == "03") {
       abreRele(portaRele3);       
     } else if (str == "04") {
       abreRele(portaRele4);       
     } else if (str == "05") {
       abreRele(portaRele5);       
     } else if (str == "06") {
       abreRele(portaRele6);       
     } else if (str == "07") {
       abreRele(portaRele7);       
     } else if (str == "08") {
       abreRele(portaRele8);
     }
   }
  delay(500);
}

void abreRele(int rele) {
  Serial.write("Porta ");
  String s = String(rele);
  Serial.println(s);
  
  digitalWrite(rele, LOW);
  delay(tempoDelay);
  digitalWrite(rele, HIGH);
}


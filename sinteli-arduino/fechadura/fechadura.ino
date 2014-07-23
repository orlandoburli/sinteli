#define portaRele1 2
#define portaRele2 12
#define tempoDelay 2000

int c;
void setup()  
{                
  Serial.begin(9600);
  pinMode(portaRele1, OUTPUT);
  pinMode(portaRele2, OUTPUT);
  
  digitalWrite(portaRele1, HIGH);
  digitalWrite(portaRele2, HIGH);
}

void loop()                    
{
  if(Serial.available())
  {
    //if (c != '0')
    //{
      //Serial.println("Serial Read");
      c = Serial.read();
    //}
    //Serial.println(c);
    if (c == '1')
    {
      Serial.println("Enviando comando LOW Porta Rele 1");
      digitalWrite(portaRele1, LOW);
      delay(tempoDelay);
      Serial.println("Enviando comando HIGH Porta Rele 1");
      digitalWrite(portaRele1, HIGH);
    } else if (c == '2') {
      Serial.println("Enviando comando HIGH Porta Rele 2");
      digitalWrite(portaRele2, HIGH);
      delay(tempoDelay);
      Serial.println("Enviando comando LOW Porta Rele 2");
      digitalWrite(portaRele2, LOW);
    } else if (c == '9') {
    }
    //Serial.println("segundo");
    ///digitalWrite(10,HIGH);
    Serial.println("Final loop");
  }
  delay(500);
}


//
//  SinteliCripto.h
//  SinteliCripto
//
//  Created by Orlando Burli on 10/06/14.
//  Copyright (c) 2014 Sinteli Sistemas Inteligentes. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SinteliCripto : NSObject

-(NSString *) cripto : (NSString *) chave valorStr: (NSString *) valor;

-(NSString *) decripto : (NSString *) chave valorStr: (NSString *) valor;

-(void) debug :(NSString *) debugStr;

@end


/**
 public static String decripto(String key, String value) throws CriptoException {

 int digitos = value.length() - 1;
 
 if (digitos % 6 > 0) {
 throw new CriptoException("Invalid value cripto size!");
 }
 
 int digito = Integer.parseInt(value.substring(value.length() - 1, value.length()));
 
 int numCaracteres = digitos / 6;
 
 String retorno = "";
 
 int keyPosition = 0;
 
 for (int i = 0; i < numCaracteres; i++) {
 int x = getKeyPos(key, keyPosition);
 int start = (i) * 6;
 String item = value.substring(start, start + 6);
 
 int z = Integer.parseInt(item, 16);
 
 int y = (z / x / digito);
 
 // char c = y;
 
 retorno += (char) y;
 
 keyPosition++;
 
 if (keyPosition >= key.length()) {
 keyPosition = 0;
 }
 }
 
 return retorno;

*/
//
//  SinteliCripto.m
//  SinteliCripto
//
//  Created by Orlando Burli on 10/06/14.
//  Copyright (c) 2014 Sinteli Sistemas Inteligentes. All rights reserved.
//

#import "SinteliCripto.h"
#import "NSInvalidCriptoKeyException.h"

@implementation SinteliCripto


/***********************
 *                     *
 *    FUNCAO CRIPTO    *
 *                     *
 **********************/
-(NSString *) cripto : (NSString *) chave valorStr: (NSString *) valor{
    [self debug:@"Iniciando cripto"];
    
    // Valida chave, tem que ter tamanho maior que 0
    if ([chave length] <= 0) {
        @throw [NSInvalidCriptoKeyException exceptionWithName:@"Chave inválida!" reason:@"Chave com tamanho zero." userInfo:nil];
    }
    
    int keyPosition = 0;
    int valuePosition = 0;
    
    // Calculo do digito verificador
    int digito = [valor length];
    
    [self debug : [[NSString alloc] initWithFormat:@"Digito: %i", digito] ];
    
    NSString *digitoStr = [NSString stringWithFormat:@"%i", digito];
    digitoStr = [digitoStr substringFromIndex:[digitoStr length]];
    digitoStr =  [[NSString alloc] initWithFormat:@"%c", [digitoStr characterAtIndex:[digitoStr length] - 1] ];
    
    digito = [digitoStr integerValue];
    
    if (digito <= 0) {
        digito = 1;
    }
    
    digitoStr = [NSString stringWithFormat:@"%i", digito];
    
    [self debug : [[NSString alloc] initWithFormat:@"Digito: %i", digito] ];
    
    NSMutableString *result = [[NSMutableString alloc] initWithFormat:@""];
    
    for (int i=0; i < [valor length]; i++) {
        int x = [self getKeyPos:chave pos:keyPosition];
        int y = [self getKeyPos:valor pos:valuePosition];
        
        // Multiplica pelo digito
        int z = x * y * digito;
        
        // Converte pra hexadecimal
        NSString *temp = [[[NSString alloc] initWithFormat:@"%x", z ] uppercaseString];
        
        // Completa com zeros
        temp = [self padZeroLeft:temp intSize:6];

        NSString *teste = [[NSString alloc] initWithFormat:@"%c", y ];
        
        [self debug:[[ NSString alloc]initWithFormat:@"X: %i, Y: %i, Z: %i, Char: %@", x, y, z, teste]];

        // Adiciona ao final da string
        [result appendString:temp];
        
        keyPosition++;
        valuePosition++;
        
        if (keyPosition >= [chave length]) {
            keyPosition = 0;
        }
        
        if (valuePosition >= [valor length]) {
            valuePosition = 0;
        }
    }
    
    // Adiciona o digito ao final
    [result appendString:digitoStr];
    
    return result;
}

/***********************
 *                     *
 *   FUNCAO DECRIPTO   *
 *                     *
 **********************/
-(NSString *) decripto : (NSString *) chave valorStr: (NSString *) valor {
    [self debug:@"Iniciando decripto"];
    
    int digitos = [valor length] - 1;
    
    if (digitos % 6 > 0) {
        @throw [NSInvalidCriptoKeyException exceptionWithName:@"Invalid value cripto size!" reason:@"Invalid value cripto size." userInfo:nil];
    }
    
    NSMutableString *retorno  =[[NSMutableString alloc] initWithCapacity:0];
 
    // Pega o digito verificador
    int digito = [[valor substringFromIndex:[valor length] - 1] intValue];
    
    // Numero de caracteres
    int numCaracteres = digitos / 6;
    
    int keyPosition = 0;
    
    // loop dos caracteres
    for (int i = 0; i < numCaracteres; i++) {
        //
        int x = [self getKeyPos:chave pos:keyPosition];
        int start = i * 6;

        NSRange range = NSMakeRange(start, 6);
        NSString *item = [valor substringWithRange:range];
        
        // Hexadecimal para decimal
        unsigned int z = 0;
        NSScanner *scanner = [NSScanner scannerWithString:item];
        [scanner setScanLocation:1];
        [scanner scanHexInt:&z];
        
        int y = (z / x / digito);
        
        char c = y;
        NSString *strChar = [[NSString alloc] initWithFormat:@"%c", c];
        
        [self debug:strChar];
        
        [retorno appendString:strChar];
        
        keyPosition++;
        
        if (keyPosition >= [chave length]) {
            keyPosition = 0;
        }
    }
    
    return retorno;
}

-(NSString *) padZeroLeft : (NSString *)valor intSize: (int) size {
    
    NSMutableString *temp = [[NSMutableString alloc] initWithString:valor];
    
    int restante = size - [temp length];
    
    for (int i = 0; i < restante; i++) {
        [temp insertString:@"0" atIndex:0];
    }
    
    return temp;
}

-(int) getKeyPos : (NSString *) key pos:(int) intPosition {
    int retorno = [key characterAtIndex:intPosition];
    
    return retorno;
}

-(void) debug :(NSString *) debugStr {
#if DEBUG
    NSLog(@"DEBUG CRIPTO: %@", debugStr);
#endif
}


@end

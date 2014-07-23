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

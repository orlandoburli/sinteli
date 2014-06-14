//
//  SinteliCriptoTests.m
//  SinteliCriptoTests
//
//  Created by Orlando Burli on 10/06/14.
//  Copyright (c) 2014 Sinteli Sistemas Inteligentes. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "SinteliCripto.h"
#import "NSInvalidCriptoKeyException.h"

@interface SinteliCriptoTests : XCTestCase

@end

@implementation SinteliCriptoTests

- (void)setUp
{
    [super setUp];
    // Put setup code here. This method is called before the invocation of each test method in the class.
}

- (void)tearDown
{
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    [super tearDown];
}

- (void)testeCripto
{
    NSString *chave = @"cripto123";
    NSString *valor = @"Orlando Burli Júnior";
    NSString *dadosCriptoAmostra = @"001E8D0032C4002C4C002A700031D8002B5C00153F000640000D26002D3F0032C4002C4C002DF0000E80002016002FDA00157C0014EB002AED0032C41";
    
    
    // Testa a criptografia
    NSString *dadosCripto = [[[SinteliCripto alloc] init] cripto:chave valorStr:valor];
    
    XCTAssertTrue([dadosCripto isEqualToString:dadosCriptoAmostra], @"Criptografia inválida! Esperado: %@ Obtido: %@", dadosCriptoAmostra, dadosCripto);
    
    
    // Testa a decriptografia
    
    NSString *dadosDecripto = [[[SinteliCripto alloc] init] decripto:chave valorStr:dadosCriptoAmostra];

    XCTAssertTrue([valor isEqualToString:dadosDecripto], @"Decriptografia inválida! Esperado: %@ Obtido: %@", valor, dadosDecripto);

}


-(void) testaChaveInvalida {
    @try {
        [[SinteliCripto alloc] cripto:@"" valorStr:@"Qualquer valor"];
    }@catch (NSInvalidCriptoKeyException *e) {
        return;
    }
    
    XCTFail(@"Falha na validação da chave de tamanho zero.");
}
@end

//
//  ViewController.m
//  Sinteli
//
//  Created by Orlando Burli on 10/06/14.
//  Copyright (c) 2014 Sinteli Sistemas Inteligentes. All rights reserved.
//

#import "ViewController.h"
#import <SinteliCripto.h>

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)cliqueTeste:(id)sender {
    //

    SinteliCripto *cripto = [SinteliCripto alloc];
    NSString *usuario = [cripto cripto:@"cripto123" valorStr:@"sinteli@sinteli.com.br"];
    NSString *senha = [cripto cripto:@"cripto123" valorStr:@"1"];
    
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc]initWithURL: [NSURL URLWithString:@"http://10.0.0.163/sintel/rest/pgetusuario"]] ;
    [request setHTTPMethod:@"POST"];
    
    NSString *dadosLogin = [[NSString alloc] initWithFormat:@"{UsuLogin: %@, UsuSenha: %@}", usuario, senha];
    
    [request setHTTPBody:dadosLogin];
    
    NSError *errorCode = [[NSError alloc] init];
    NSHTTPURLResponse *responseCode = nil;
    
    NSData *oResponseData = [NSURLConnection sendSynchronousRequest:request returningResponse:responseCode error:errorCode];
    
//    
//    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Dados Criptografia"
//                                                    message:dadosCripto
//                                                   delegate:nil
//                                          cancelButtonTitle:@"OK"
//                                          otherButtonTitles:nil];
//    [alert show];
}
@end

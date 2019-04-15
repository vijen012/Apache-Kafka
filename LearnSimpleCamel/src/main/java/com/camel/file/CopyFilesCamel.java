package com.camel.file;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CopyFilesCamel {

    public static void main(String[] args)  {

        CamelContext context= new DefaultCamelContext();
        try {
            /*
            1. context- > Route Engine(N number of routes and DSLs) -> Series of processor(from and to are processor)
            2. An endpoint is divided into three parts:
            schema -> file
            context path -> data/input
            options -> noop=true
             */
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("file:data/input?noop=true") //noop -> it instruct camel not to perform any operation after copies the file into directory so file won't get deleted from input directory
                            .to("file:data/output");
                }
            });
            context.start();
            Thread.sleep(5000);
            context.stop();

        } catch(Exception e){
            System.out.println("Inside Exception : " + e);
        }
    }
}
